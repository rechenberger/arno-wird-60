package game.content.effects.duration;

import java.awt.Color;

import game.attributes.Attribute;
import game.objects.NonStatic;

/**
 * Betroffener ist vergifted.
 * @author Tristan
 *
 */
public class Poisoned extends game.effects.DurationEffect implements PulsingEffect {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -2839707418148448396L;
	
	/**
	 * Schaden pro Sekunde.
	 */
	private static final int DAMAGE_PER_SECOUND = 10;

	
	/**
	 * Konstrukor.
	 * @param effects NonStatic
	 * @param damage Schaden insgesamt
	 */
	public Poisoned(final NonStatic effects, final int damage) {
		super(effects, (damage / DAMAGE_PER_SECOUND) * 1000);
		this.getAttributeValueList().setAttribute(Attribute.healthgeneration, -DAMAGE_PER_SECOUND);
	}


	@Override
	public Color getPulsingColor() {
		//CHECKSTYLE:OFF
		return Color.GREEN.brighter();
	}

}
