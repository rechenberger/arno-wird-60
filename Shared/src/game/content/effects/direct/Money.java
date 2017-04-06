package game.content.effects.direct;

import game.attributes.Attribute;
import game.effects.DirectEffect;
import game.objects.NonStatic;

/**
 * Gutschreibung von Geld.
 * @author Tristan
 *
 */
public class Money extends DirectEffect {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -1452703995291933196L;
	
	/**
	 * Konstruktor.
	 * @param effects betroffenes NonStatic
	 * @param value Hoehe des gutgeschriebenen Geldbetrags. (Darf auch negativ sein)
	 */
	public Money(final NonStatic effects, final int value) {
		super(effects);
		this.getAttributeValueList().setAttribute(Attribute.money, value);
	}
	
	


}
