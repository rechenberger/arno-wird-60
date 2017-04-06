package module;

import java.util.Collection;

import settings.DebugSettings;
import settings.GlobalSettings;

import mapgenerator.Mapgenerator;
import match.KiHandler;

import com.Administration;
import com.messages.ChatMessage;
import com.messages.MatchBeginMessage;
import com.messages.MatchTimeMessage;
import com.messages.Message;
import com.messages.NewKiMessage;

import game.actions.Action;
import game.attributes.Attribute;
import game.content.effects.direct.ChooseHeroEffect;
import game.content.effects.direct.Heal;
import game.content.effects.direct.Mana;
import game.content.effects.direct.Money;
import game.content.heros.Hero;
import game.content.ki.hero.HeroKi;
import game.effects.ActiveEffect;
import game.effects.PermanentEffect;
import game.objects.Fraction;
import game.objects.GameObject;
import game.objects.KiPlayer;
import game.objects.Match;
import game.objects.NonStatic;
import game.objects.Player;
import game.skills.PassiveSkill;
import game.skills.Skill;

/**
 *  Das MatchModule der Server Seite.
 * @author Tristan
 */
public class MatchModuleServer extends MatchModuleShared {
	/**
	 * Ist hier eigentlich Fehl am Platz kommt noch woanders hin.
	 */
	protected KiHandler kiHandler;
	
	/**
	 * Konstruktor.
	 */
	public MatchModuleServer() {
		super();
		this.isClient = false;
	}
	
	@Override
	public void run() {
		this.match = new Match();
		Mapgenerator.run();
		kiHandler = new KiHandler(match.getMap());
		this.spawnHeros();
		ModuleHandler.COM.notifyWaitForMatch();
		super.run();
	}
	
	@Override
	protected void turn() {
		this.time += this.sleepTime;
		this.sendMessage(new MatchTimeMessage(this.time));
		super.turn();
		if (this.isMatchRunning()) {
			this.turnPlanKIActions();
			this.turnExecutePassiveSkills();
			this.turnExecuteActiveEffects();
			this.turnAttributeGeneration();
			this.turnPermanentEffectTimerUpdate();
		} else if (Player.allPlayersReady()) {
			this.getMatch().decStartingIn(this.getSleepTime());
			if (this.getMatch().getStartingIn() <= 0) {
				this.getMatch().setStartingIn(0);
				this.startRunning();
			}
			if (this.getMatch().getStartingIn() <= 0 || this.turn % (1000 / this.getSleepTime()) == 0) {
//				System.out.println("Match startet in: " + this.getMatch().getStartingIn() / 1000 + " Sekunden.");
				this.sendMessage(new MatchBeginMessage(this.getMatch().getStartingIn()));
			}
		}
		this.turnExecutePlannedActions();
	}
	
	/**
	 * Aktualisiert den Timer der permanenten Effekte.
	 */
	private void turnPermanentEffectTimerUpdate() {
		PermanentEffect.updateAllTimer();
	}

	/**
	 * Fuehrt alle passiven Skills aus.
	 * @author Tristan
	 */
	private void turnExecutePassiveSkills() {
		if (this.turn % (GlobalSettings.MATCH_PASSIVE_SKILLS_TICKER / this.sleepTime) == 0) {
		Collection<Hero> heros = Hero.getGameObjectsByClassName("Hero");
			for (Hero hero : heros) {
				if (hero.isAlive()) {
					for (Skill skill : hero.getSkills()) {
						if (skill instanceof PassiveSkill) {
							if (!skill.isCoolingDown(hero)) {
								((PassiveSkill) skill).execute(hero);
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * Fuehrt alle geplanten Aktionen aus.
	 * @author Tristan
	 */
	private void turnExecutePlannedActions() {
		Collection<NonStatic> list = GameObject.getGameObjectsByClassName("NonStatic");
		for (NonStatic ns : list) {
			if (ns.getNextAction() != null) {
				Action action = ns.getNextAction();
				action.execute();
			}
		}
	}
	
	/**
	 * Ki Aktionen berechnen und planen.
	 * @author Tristan
	 */
	private void turnPlanKIActions() {
		if (this.turn % GlobalSettings.MATCH_KI_TURNS_NOT_CALCULATING == 0) {
			kiHandler.handleKi(turn);
		}
	}
	
	/**
	 * Fuehrt alle aktiven Effekte aus.
	 * @author Tristan
	 */
	private void turnExecuteActiveEffects() {
		ActiveEffect.executeAll();
	}
	
	/**
	 * Geld-, Lebens- und Mana- Generation.
	 */
	private void turnAttributeGeneration() {
		if (this.turn % (1000 / this.getSleepTime()) != 0) {
			return;
		} else {
			for (NonStatic nonStatic : NonStatic.getAllNonStatics()) {
				new Money(nonStatic, nonStatic.getAttributeValue(Attribute.moneygeneration)).ready();
				new Heal(nonStatic, nonStatic.getAttributeValue(Attribute.healthgeneration)).ready();
				new Mana(nonStatic, nonStatic.getAttributeValue(Attribute.manageneration)).ready();
			}
		}
	}

	@Override
	public void sendMessage(final Message msg) {
		for (Player p : Player.getAllPlayers()) {
			if (p != null) {
				ModuleHandler.COM.pushMessage(msg, p.getUserId());
			}
		}
	}
	
	@Override
	public void sendMessage(final Message msg, final int userId) {
		ModuleHandler.COM.pushMessage(msg, userId);
		
	}
	
	@Override
	public void pushMessage(final Message msg) {
		if (msg instanceof NewKiMessage) {
			this.executeAddKiToHeroMessage((NewKiMessage) msg);
		} else {
			super.pushMessage(msg);
		}
	}
	
	
	/**
	 * Legt neuen Benutzer an.
	 * @param userId ID des Benutzers.
	 * @param userName Benutzername.
	 */
	public void newUser(final int userId, final String userName) {
		// Konsolenausgabe: Neuer Benutzer im Match ...
		if (DebugSettings.DEBUG && DebugSettings.MATCH_PRINT_NEWUSER) {
			System.out.println("Neuer Benutzer im Match: " + userName + " (" + userId + ")");
		}
		
		// Neuen Spieler anlegen.
		Player player = new Player(userId, userName);
		player.setStatisticsAllTime(Administration.loadStatistics(userName));
		player.send();
		player.setOnlineState(true);
		

		this.sendMessage(new ChatMessage("Willkommen " + player.getName()), userId);
		
		
		// Helden zuteilen.
		String msgString = "Neuer Spieler: " + player.getName() + " ";
		Hero hero = this.getFreeHero();
		
		// TODO Brauchen wir das wirklich?
		if (hero == null) {
			msgString += "hat keinen freien Platz gefunden, Schorry!";
		} else {
			new ChooseHeroEffect(player, hero).ready();
			msgString += "steuert den mutigen Helden " + hero.getName() + ", " + hero.getFraction();
		}
		
		// Messages senden.
		this.sendMessage(new ChatMessage(msgString));
		this.sendMessage(GameObject.getAllGameObjectsMessage(), userId);
		
		
	}
	
	/**
	 * Setzt je 5 Helden in die Basen von Team A und Team B.
	 */
	private void spawnHeros() {
		
		for (int i = 0; i < 10; i++) {
			Hero hero;
			if (i < 2) {
				hero = new game.content.heros.alfons.AlfonsMobbel();
			} else if (i < 4) {
				hero = new game.content.heros.brocky.BrockyAloa();
			} else if (i < 6) {
				hero = new game.content.heros.bryan.PrivateBryan();
			} else if (i < 8) {
				hero = new game.content.heros.geronimo.GeronimoVonNazareth();
			} else if (i < 10) {
				hero = new game.content.heros.tinnewou.Tinnewou();
			} else {
				hero = new game.content.heros.brocky.BrockyAloa();
			}
			
			if (i % 2 == 0) {
				hero.setFraction(Fraction.TeamA);
			} else {
				hero.setFraction(Fraction.TeamB);
			}
			
			hero.spawn();
			
		}
	}
	
	/**
	 * @return Freier Held.
	 */
	private Hero getFreeHero() {
		
		for (Hero hero : Hero.getAllHerosSorted()) {
			if (hero.getPlayer() == null) {
				return hero;
			}
		}
		return null;
	}
	
	@Override
	public void startRunning() {
		for (Hero hero : Hero.getAllHeros()) {
			if (hero.getPlayer() == null) {
				hero.die();
//				KiPlayer p = new KiPlayer();				
//				new ChooseHeroEffect(p, hero).ready();
//				
//				HeroKi tmp = HeroKi.getNewHeroKi(hero);
//				KiHandler.getHeroes().add(tmp);
//				
//				//speichert das Objekt welches den Graphomaten verwaletet im Player
//				p.setKi(tmp);
			}
		}
		super.startRunning();
	}
	
	@Override
	public void end() {
		super.end();
		for (Player p : Player.getAllPlayers()) {
			Administration.saveStatistics(p.getName(), p.getStatisticAllTime());
		}
	}
	
	/**
	 * Legt neue Ki an.
	 * @param msg NewKiMessage
	 */
	private void executeAddKiToHeroMessage(final NewKiMessage msg) {
		Hero hero = msg.getHero();		
		if (msg.getAddOrRemove()) {
			if (hero.getPlayer() != null) {
				return;
			} else {
				KiPlayer p = new KiPlayer();			
				p.send();	
				new ChooseHeroEffect(p, hero).ready();
					
				HeroKi tmp = HeroKi.getNewHeroKi(hero);
				KiHandler.getHeroes().add(tmp);
	
				p.setKi(tmp);
			}
		} else {
			if (hero.getPlayer() instanceof KiPlayer) {
				KiPlayer kiPlayer = (KiPlayer) hero.getPlayer();
				HeroKi heroKi = kiPlayer.getKi();
				
				KiHandler.getHeroes().remove(heroKi);
				kiPlayer.setKi(null);
				new ChooseHeroEffect(null, hero).ready();
				kiPlayer.unregisterGameObject();
				heroKi.unregisterGameObject();
			} else {
				return;
			}
		}
		
		
	}
	
}
