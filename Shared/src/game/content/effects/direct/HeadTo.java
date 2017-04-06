package game.content.effects.direct;

import game.attributes.Attribute;
import game.effects.DirectEffect;
import game.objects.NonStatic;

/**
 * Setzt Attribut: headingTo.
 * @author Tristan
 *
 */
public class HeadTo extends DirectEffect {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 5466547559041572893L;
	
	/**
	 * Wert von headingTo. (Winkel in Grad).
	 */
	private int value;

	/**
	 * Konstruktor.
	 * @param effects betroffenes NonStatic
	 * @param value Wert von headingTo. (Winkel in Grad)
	 */
	public HeadTo(final NonStatic effects, final int value) {
		super(effects);
		this.value = value;
	}
	
	
	@Override
	public void execute() {
		this.getEffects().getAttributeValueList().setAttribute(Attribute.headingTo, this.value);
	}

}
