package game.content.effects.direct;

import game.attributes.Attribute;
import game.effects.DirectEffect;
import game.objects.NonStatic;

/**
 * Schaden mindert die Lebenspunkte.
 * @author Tristan
 *
 */
public class Damage extends DirectEffect {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1427627037650488211L;

	/**
	 * @param effects betroffenes NonStatic
	 * @param damage Hoehe des Schadens (in Lebenspunkten)
	 * Fuer Heilung bitte Heal benutzen.
	 */
	public Damage(final NonStatic effects, final int damage) {
		super(effects);
		this.getAttributeValueList().setAttribute(Attribute.currentHealth, -damage);
	}

	
}
