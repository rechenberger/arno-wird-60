package game.content.effects.direct;

import game.attributes.Attribute;
import game.effects.DirectEffect;
import game.objects.NonStatic;

/**
 * Erhoeht die Manapunkte.
 * @author Tristan
 *
 */
public class Mana extends DirectEffect {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 5466547559041572892L;

	/**
	 * Konstruktor.
	 * @param effects betroffenes NonStatic
	 * @param value Hoehe der Manaveraenderung. (Darf auch negativ sein)
	 */
	public Mana(final NonStatic effects, final int value) {
		super(effects);
		this.getAttributeValueList().setAttribute(Attribute.currentMana, value);
	}

}
