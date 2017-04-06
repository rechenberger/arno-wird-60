package game.content.heros.bruce;

import game.attributes.Attribute;
import game.effects.DirectEffect;
import game.objects.NonStatic;

/**
 * Schaukampf, der Effekt.
 * @author Alex
 *
 */
public class SchaukampfEffect extends DirectEffect {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1686205481816395103L;

	/**
	 * Konstruktor. Initialisiert die Werte der AttributeValueList.
	 * @param effects betroffenes NonStatic
	 * @param level Skilllevel des Ausfuehrers.
	 */
	public SchaukampfEffect(final NonStatic effects, final int level) {
		super(effects);
		this.name = "Unz\u00e4hmbarer Krieger";
		
		this.getAttributeValueList().setAttribute(Attribute.currentHealth, 1.2f * level);
	}

}
