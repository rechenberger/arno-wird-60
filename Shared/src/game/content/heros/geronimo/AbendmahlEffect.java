package game.content.heros.geronimo;

import game.attributes.Attribute;
import game.attributes.AttributeValueList;
import game.effects.DirectEffect;
import game.objects.NonStatic;

/**
 * Abendmahl, der Effekt.
 * @author Tristan
 *
 */
public class AbendmahlEffect extends DirectEffect {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -6564192928820668136L;


	/**
	 * Konstruktor. Initialisiert die Werte der AttributeValueList.
	 * @param effectedNonStatic betroffenes NonStatic
	 * @param level Skilllevel des Ausfuehrers.
	 * @param isAlly ob betroffener Verbuendet ist.
	 */
	public AbendmahlEffect(final NonStatic effectedNonStatic, final int level, final boolean isAlly) {
		super(effectedNonStatic);
		getAttributeValueListWithSkillLevel(level, this.getAttributeValueList());
		
		this.setImageURL("usercontrols", "skill", "geronimo/abendmahl");
		
	}
	
	/**
	 * Gibt die Attributevalue Liste Zurueck, die die Auswirkungen dieses Effekts wiederspiegelt.
	 * <b>Wenn die Uebergebene avListe null ist, dann wird eine neue erstellt.</b>
	 * @param skillLevel Das Level auf welchem der Skill ausgefuehrt wurde.
	 * @param avListe Die Attribute value Liste in die der Wert eingefuegt werden soll.
	 * @return Die AttributeVAlue liste.
	 */
	public static AttributeValueList getAttributeValueListWithSkillLevel(final int skillLevel, final AttributeValueList avListe)  {
		AttributeValueList av = avListe;
		if (av == null) {
			av = new AttributeValueList();
		}
		
		// Heilt um 50 pro level. Feinde um die Haelfte.
		int heal = 50 * skillLevel;
		
		av.setAttribute(Attribute.currentHealth, heal); 
		return av;
	}
}
