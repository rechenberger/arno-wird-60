package game.content.skills;

import java.awt.Point;


import game.attributes.Attribute;
import game.attributes.AttributeValueList;
import game.content.effects.active.Moving;
import game.objects.NonStatic;
import game.skills.NotAimedSkill;

/**
 * Wird ausgefuehrt wenn NonStatic stribt.
 * @author Tristan
 *
 */
public final class Spawn extends NotAimedSkill {
	

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 2466965494862517792L;
	
	/**
	 * Instanz.
	 */
	private static Spawn instance = new Spawn();
	
	/**
	 * @return Instanz
	 */
	public static Spawn getInstance() {
		return instance;
	}
	

	/**
	 * Konstruktor.
	 */
	private Spawn() {
		super("Auftauchen");
		this.effectsAllies = false;
		this.effectsEnemies = false;
		this.effectsHeroOnly = false;
		this.effectsSelf = false;
		this.radius = 0;
		this.cooldown = 5000;
		this.shownInGui = false;
	}
	
	
	@Override
	public void execute(final NonStatic executer) {
//		super.execute(executer);
		
		Point spawn = executer.getSpawnPoint();
		Moving.stopMoving(executer);
		executer.removeNextAction();
		
		new game.content.effects.direct.Heal(executer, executer.getAttributeValue(Attribute.maxHealth)).ready();
//		new game.content.effects.direct.Mana(executer, executer.getAttributeValue(Attribute.maxMana)).ready();
		new game.content.effects.direct.Revive(executer).ready();
		new game.content.effects.direct.Step(executer, spawn).ready();
	}
	
	@Override
	public AttributeValueList getEffectsAttributeValueList(final NonStatic executer) {
		return new AttributeValueList();
	}
	

}
