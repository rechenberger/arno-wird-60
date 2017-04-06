package game.content.skills.cheats;

import java.awt.Point;

import game.attributes.AttributeValueList;
import game.content.effects.direct.Step;
import game.content.heros.Hero;
import game.objects.NonStatic;
import game.skills.AimedOnPointSkill;

/**
 * Teleportiert den Helden.
 * @author Tristan
 *
 */
public final class Teleport extends AimedOnPointSkill {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 8916703835754282953L;

	/**
	 * Instanz.
	 */
	private static Teleport instance = new Teleport();

	/**
	 * 
	 * @return Instanz.
	 */
	public static Teleport getInstance() {
		return instance;
	}
	
	/**
	 * Konstruktor.
	 */
	private Teleport() {
		super("Teleport");
		this.range = 1000;
		this.effectsAllies = true;
		this.effectsEnemies = false;
		this.effectsHeroOnly = true;
		this.effectsSelf = true;
		this.cooldown = 0 * 1000;
		this.shownInGui = false;
	}
	
	@Override
	public void execute(final NonStatic executer, final Point targetPoint) {
		if (matchModule.getMap().ifWalkable(targetPoint)) {
			new Step(executer, targetPoint).ready();
		} else {
			if (executer instanceof Hero) {
				((Hero) executer).getPlayer().sendSystemChatMessege("Von diesem Telport rate ich ab.");
			}
		}
	}
	
	@Override
	public AttributeValueList getEffectsAttributeValueList(final NonStatic executer) {
		return new AttributeValueList();
	}
	

}
