package game.content.effects.direct;

import game.attributes.Attribute;
import game.effects.DirectEffect;
import game.objects.NonStatic;

/**
 * Heilung erhoeht die Lebenspunkte.
 * @author Tristan
 *
 */
public class Heal extends DirectEffect {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -4099715362411759661L;

	/**
	 * 
	 * @param effects betroffenes NonStatic
	 * @param value Hoehe der Heilung (in Lebenspunkten)
	 * Fuer Schaden bitte Damage benutzen.
	 */
	public Heal(final NonStatic effects, final int value) {
		super(effects);
		this.getAttributeValueList().setAttribute(Attribute.currentHealth, value);
	}

	
}
