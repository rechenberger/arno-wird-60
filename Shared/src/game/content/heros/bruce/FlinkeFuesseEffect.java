package game.content.heros.bruce;

import game.attributes.Attribute;
import game.effects.PassiveSkillEffect;
import game.objects.NonStatic;

/**
 * Flinke Fuesse, der Effekt.
 * @author Alex
 *
 */
public class FlinkeFuesseEffect extends PassiveSkillEffect {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 8662952170055632533L;

	/**
	 * Konstruktor. Initialisiert die Werte der AttributeValueList.
	 * @param effectedNonStatic betroffenes NonStatic
	 * @param level Skilllevel des Ausfuehrers.
	 */
	public FlinkeFuesseEffect(final NonStatic effectedNonStatic, final int level) {
		super(effectedNonStatic);
		this.name = "Flinke F\u00fcsse";
		
		//TODO Eigentlich soll hier nicht die Verteidigung erhoeht werden, sondern er soll ausweichen.
		this.getAttributeValueList().setAttribute(Attribute.defense, 1.2f * level); 
		
	}
}
