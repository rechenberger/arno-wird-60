package game.content.skills;
import com.messages.ChatMessage;
import com.messages.MatchEndMessage;
import com.messages.Message;

import game.attributes.Attribute;
import game.attributes.AttributeValueList;
import game.content.buildings.Nexus;
import game.content.effects.active.Moving;
import game.content.heros.Hero;
import game.effects.CooldownEffect;
import game.objects.KiPlayer;
import game.objects.NonStatic;
import game.skills.NotAimedSkill;

/**
 * Wird ausgefuehrt wenn NonStatic stribt.
 * @author Tristan
 *
 */
public final class Die extends NotAimedSkill {
	

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 2466965494862517092L;
	
	/**
	 * Instanz.
	 */
	private static Die instance = new Die();
	
	/**
	 * @return Instanz
	 */
	public static Die getInstance() {
		return instance;
	}
	

	/**
	 * Konstruktor.
	 */
	private Die() {
		super("Sterben");
		this.effectsAllies = false;
		this.effectsEnemies = true;
		this.effectsHeroOnly = true;
		this.effectsSelf = false;
		this.radius = 10;
		this.shownInGui = false;
	}
	
	@Override
	public void giveEffect(final NonStatic executer, final NonStatic effectedNonStatic) {
		// Kreiert den entsprechenden Effekt und ruft seine Methode ready() auf.
		
		int experience = 15;
		int money = 45;
		if (executer instanceof game.content.heros.Hero) {
			experience *= 3;
			experience *= ((game.content.heros.Hero) executer).getLevel();
			money *= 3;
			money *= ((game.content.heros.Hero) executer).getLevel();
		}
		new game.content.effects.direct.Experience(effectedNonStatic, experience).ready();
		new game.content.effects.direct.Money(effectedNonStatic, money).ready();
		if (effectedNonStatic instanceof Hero) {
			if (((Hero) effectedNonStatic).getPlayer() != null) {
				((Hero) effectedNonStatic).getPlayer().incStatistic("Kills");
			}
		}
	}
	
	
	@Override
	public void execute(final NonStatic executer) {
		executer.getAttributeValueList().setAttribute(Attribute.alive, 0);
		new game.content.effects.direct.Kill(executer).ready();
		super.execute(executer);
		Moving.stopMoving(executer);
		executer.removeNextAction();
		
		new CooldownEffect(executer, game.content.skills.Spawn.getInstance(), 5000).ready();
		
		if (executer instanceof Nexus) {
			matchModule.sendMessage(new MatchEndMessage(executer.getFraction().getEnemyTeam()));
			Message msg = new ChatMessage(executer.getFraction().getEnemyTeam() + " gewinnt");
			matchModule.sendMessage(msg);
			matchModule.end();
		} else if (executer instanceof Hero && ((Hero) executer).getPlayer() instanceof KiPlayer) {
			Hero hero = (Hero) executer;
			((KiPlayer) hero.getPlayer()).getKi().getStates().resetStates();
			
			hero.getPlayer().incStatistic("Deaths");
			String text = executer.getFraction() + ": " + hero.getName() + " ist gestorben. ";
			text += "Schande \u00fcber Spieler " + hero.getPlayer().getName();
			
			matchModule.sendMessage(new ChatMessage(text));
		} else if (executer instanceof Hero && ((Hero) executer).getPlayer() != null) {
			Hero hero = ((Hero) executer);
			hero.getPlayer().incStatistic("Deaths");
			String text = executer.getFraction() + ": " + hero.getName() + " ist gestorben. ";
			text += "Schande \u00fcber Spieler " + hero.getPlayer().getName();
			
			matchModule.sendMessage(new ChatMessage(text));
		} else if (executer instanceof game.content.buildings.Tower) {
			matchModule.sendMessage(new ChatMessage("Turm von " + executer.getFraction() + " zerst\u00f6rt"));
		}	else if (executer instanceof game.content.buildings.Inhibitor) {
			matchModule.sendMessage(new ChatMessage("Inhibitor von " + executer.getFraction() + " zerst\u00f6rt"));
			matchModule.sendMessage(new ChatMessage("Die Vasallen des " + executer.getFraction() + " sind nun schw\u00e4cher"));
		}
	}
	
	@Override
	public AttributeValueList getEffectsAttributeValueList(final NonStatic executer) {
		return new AttributeValueList();
	}
	

}
