package game.content.heros;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

import settings.GlobalSettings;

import com.messages.SkillPointMessage;

import game.attributes.Attribute;
import game.objects.Character;
import game.objects.Fraction;
import game.objects.GameObject;
import game.objects.Player;
import game.skills.Skill;

/**
 * Helden.
 * @author Tristan
 *
 */
public class Hero extends Character {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 4654156915486283692L;
	
	/**
	 * Erfahrungspunkte zwischen zwei Leveln.
	 */
	private static final int EXPERIENCEPOINTSPERLEVEL = 100;	
	
	/**
	 * Der steuernde Spieler.
	 */
	private Player player;
	
	/**
	 * Benutzte Skillpunkte.
	 */
	private int skillPointsUsed = 0;

	/**
	 * Konstruktor.
	 * Initialisert Attribute mit Standartwerten.
	 */
	public Hero() {
		super();
		this.getAttributeValueList().setAttribute(Attribute.experience, 0);
		this.getAttributeValueList().setAttribute(Attribute.money, 0);
		this.getAttributeValueList().setAttribute(Attribute.moneygeneration, 10);
		this.getAttributeValueList().setAttribute(Attribute.maxMana, 200);
		this.getAttributeValueList().setAttribute(Attribute.currentMana, 200);
		this.getAttributeValueList().setAttribute(Attribute.manageneration, 10);
		this.getAttributeValueList().setAttribute(Attribute.movementSpeed, 50);
		this.setImageURL("nonstatic", "hero", "standart");
		this.addSkill(game.content.skills.cheats.Teleport.getInstance(), 1);
		this.addSkill(game.content.skills.cheats.MoneyCheat.getInstance(), 1);
		this.addSkill(game.content.skills.cheats.HealthCheat.getInstance(), 1);
		this.addSkill(game.content.skills.cheats.ExperienceCheat.getInstance(), 1);
	}

	/**
	 * Druckt alle Effekte.
	 */
	public void printEffects() {
		System.out.println(this.effects.toString());
		
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Gibt alle Helden zurueck.
	 * @return Liste aller Helden.
	 */
	public static LinkedList<Hero> getAllHeros() {
		LinkedList<Hero> ll = GameObject.getGameObjectsByClassName("Hero");
//		for (GameObject go : GameObject.allGameObjects.values()) {
		return ll;
	}
	
	/**
	 * 
	 * @return Liste aller Helden sortiert nach Name und Fraktion.
	 */
	public static Collection<Hero> getAllHerosSorted() {
		HashMap<String, Hero[]> hashMap = new HashMap<String, Hero[]>();
		for (Hero hero : getAllHeros()) {
			if (!hashMap.keySet().contains(hero.getName())) {
				hashMap.put(hero.getName(), new Hero[2]);
			}
			
			int i = 1;
			if (hero.getFraction() == Fraction.TeamA) {
				i = 0;
			}
			
			hashMap.get(hero.getName())[i] = hero;
		}
		
		Collection<Hero> heros = new LinkedList<Hero>();
		for (String key : hashMap.keySet()) {
			heros.add(hashMap.get(key)[0]);
			heros.add(hashMap.get(key)[1]);
		}
		
		return heros;
	}

	/**
	 * 
	 * @return Id des steuernden Benutzers.
	 */
	public int getUserId() {
		if (this.player == null) {
			return 0;
		} else {
			return this.player.getUserId();
		}
	}

	/**
	 * @param setPlayer steuernder Spieler.
	 */
	public void setPlayer(final Player setPlayer) {
		this.player = setPlayer;
		if (setPlayer != null && this.player.getHero() != this) {
			this.player.setHero(this);
		}
	}
	
	/**
	 * @return steuernder Spieler.
	 */
	public Player getPlayer() {
		return this.player;
	}
	
	/**
	 * @return Erfahrungspunkte nach Levelkurve.
	 */
	public int getExperiencePostLevelCurve() {
		double tmp = this.getAttributeValue(Attribute.experience);
		tmp /= EXPERIENCEPOINTSPERLEVEL * 2;
		tmp = Math.sqrt(tmp);
		tmp *= EXPERIENCEPOINTSPERLEVEL * 2;
		return (int) tmp;
	}
	
	/**
	 * @return Level des Helden
	 */
	public int getLevel() {
		return (this.getExperiencePostLevelCurve() / EXPERIENCEPOINTSPERLEVEL) + 1;
	}
	
	/**
	 * @return Fortschritt bis zum naechsten Level.
	 */
	public float getLevelProgress() {
		return (((float) this.getExperiencePostLevelCurve() % EXPERIENCEPOINTSPERLEVEL)) / (float) EXPERIENCEPOINTSPERLEVEL;
	}
//	
//	/**
//	 * @return Jene Skills, die nicht jedes NonStatic hat.
//	 */
//	public LinkedList<Skill> getSpecialSkills() {
//		LinkedList<Skill> specialSkills = new LinkedList<Skill>();
//		for (Skill skill : this.getSkills()) {
//			if (!(skill instanceof game.content.skills.Move) && !(skill instanceof game.content.skills.Attack) && !(skill instanceof game.content.skills.Die)) {
//				specialSkills.add(skill);
//			}
//		}
//		
//		return specialSkills;
//	}

	/**
	 * @return Benutzte Skillpunkte.
	 */
	public int getSkillPointsUsed() {
		return skillPointsUsed;
	}
	
	/**
	 * Erhoeht SkillLevel um eins.
	 * @param skill Skill.
	 */
	public void incSkillLevel(final Skill skill) {
		if (this.skillPointsUsed >= this.getLevel()) {
			System.out.println("Nicht genug Skillpunkte!");
			return;
		} else {
			this.setSkillLevel(skill, this.getSkillLevel(skill) + 1);
			this.skillPointsUsed++;
			if (matchModule.isClient()) {
				matchModule.sendMessage(new SkillPointMessage(this, skill));
			}
		}
	}
	
	/**
	 * 
	 * @return Liste aller speziellen Skills die das NonStatic beherscht.
	 */
	public LinkedList<Skill> getSpecialSkills() {
		LinkedList<Skill> list = new LinkedList<Skill>();
		for (Skill skill : this.getSkills()) {
			if (skill.isShownInGui() || (GlobalSettings.GUI_SHOW_HIDDEN_SKILLS && !(skill instanceof game.content.skills.Die) && !(skill instanceof game.content.skills.Move) && !(skill instanceof game.content.skills.Attack))) {
				list.add(skill);
			}
		}
		return list;
	}

}
