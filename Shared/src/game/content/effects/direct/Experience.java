package game.content.effects.direct;

import game.attributes.Attribute;
import game.objects.NonStatic;

/**
 * Erfahrungsgewinn.
 * @author Tristan
 *
 */
public class Experience extends game.effects.DirectEffect {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 2605304708669828543L;


	/**
	 * Konstruktor.
	 * @param effects betroffener.
	 * @param experience Hoehe der Erfahrung.
	 */
	public Experience(final NonStatic effects, final int experience) {
		super(effects);
		this.getAttributeValueList().setAttribute(Attribute.experience, experience);
	}
	
	
}
