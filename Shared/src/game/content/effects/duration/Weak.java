package game.content.effects.duration;

import java.awt.Color;

import game.attributes.Attribute;
import game.objects.NonStatic;

/**
 * Diese Klasse symbolisiert den Effekt, den ein Vasal bekommt, wenn der Inhibitor zerstoert wurde.
 * @author Marius
 *
 */
public class Weak extends game.effects.DurationEffect implements PulsingEffect {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3266570178625770889L;

	/**
	 * 
	 * @param effects Wenn dieser Effekt betreffen soll.
	 * @param millisecounds Wie lange er anhaelt. (Wert vollkommen egal, da autoend = false)
	 */
	public Weak(final NonStatic effects, final int millisecounds) {
		super(effects, millisecounds);
		
		this.autoEnd = false;
		this.getAttributeValueList().setAttribute(Attribute.maxHealth, 0.75f);
		this.getAttributeValueList().setAttribute(Attribute.damage, 0.75f);
		this.getAttributeValueList().setAttribute(Attribute.movementSpeed, 0.75f);
		this.getAttributeValueList().setAttribute(Attribute.fightingSpeed, 0.75f);
	}

	@Override
	public Color getPulsingColor() {
		//CHECKSTYLE:OFF
		return Color.BLACK.brighter();
	}

}
