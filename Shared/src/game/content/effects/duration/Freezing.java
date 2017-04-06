package game.content.effects.duration;

import java.awt.Color;

import game.attributes.Attribute;
import game.objects.NonStatic;

/**
 * Betroffener friert.
 * @author Tristan
 *
 */
public class Freezing extends game.effects.DurationEffect implements PulsingEffect {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -2839707418148448395L;
	
	/**
	 * Schaden pro Sekunde.
	 */
	private static final int DAMAGE_PER_SECOUND = 5;

	
	/**
	 * Konstrukor.
	 * @param effects NonStatic
	 * @param damage Schaden insgesamt
	 */
	public Freezing(final NonStatic effects, final int damage) {
		super(effects, (damage / DAMAGE_PER_SECOUND) * 1000);
		this.getAttributeValueList().setAttribute(Attribute.healthgeneration, -DAMAGE_PER_SECOUND);
		this.getAttributeValueList().setAttribute(Attribute.movementSpeed, 0.8f);
	}


	@Override
	public Color getPulsingColor() {
		//CHECKSTYLE:OFF
		return Color.BLUE.brighter().brighter().brighter();
	}

}
