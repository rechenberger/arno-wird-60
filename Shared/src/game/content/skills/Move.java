package game.content.skills;

import java.awt.Point;

import game.attributes.Attribute;
import game.attributes.AttributeValueList;
import game.content.effects.active.MovingToPoint;
import game.objects.NonStatic;
import game.skills.AimedOnPointSkill;

/**
 * Initialisiert eine Bewegung auf einen Punkt.
 * @author Tristan
 *
 */
public final class Move extends AimedOnPointSkill {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 157736583731039839L;
	
	/**
	 * Instanz.
	 */
	private static Move instance = new Move();

	/**
	 * 
	 * @return Instanz.
	 */
	public static Move getInstance() {
		return instance;
	}
	
	/**
	 * Konstruktor.
	 */
	private Move() {
		super("Bewegen");
		this.shownInGui = false;
	}

	@Override
	public void execute(final NonStatic executer, final Point target) {
		new MovingToPoint(executer, target, 0).ready();
	}
	
	@Override
	public int getCooldown(final NonStatic executer) {
		return executer.getAttributeValueList().getValue(Attribute.movementSpeed);
	}
	
	@Override
	public AttributeValueList getEffectsAttributeValueList(final NonStatic executer) {
		return new AttributeValueList();
	}
}
