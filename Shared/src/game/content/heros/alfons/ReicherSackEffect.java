package game.content.heros.alfons;

import game.attributes.Attribute;
import game.effects.PassiveSkillEffect;
import game.objects.NonStatic;

/**
 * Aufpumpen, der Effekt.
 * @author Tristan
 *
 */
public class ReicherSackEffect extends PassiveSkillEffect {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -6564164900820668136L;


	/**
	 * Konstruktor. Initialisiert die Werte der AttributeValueList.
	 * @param effectedNonStatic betroffenes NonStatic
	 * @param level Skilllevel des Ausfuehrers.
	 */
	public ReicherSackEffect(final NonStatic effectedNonStatic, final int level) {
		super(effectedNonStatic);
		this.name = "Reicher<br>Sack";
		
		this.getAttributeValueList().setAttribute(Attribute.moneygeneration, 3f * level);
		
		this.setImageURL("usercontrols", "skill", "alfons/sack");
		
	}
}
