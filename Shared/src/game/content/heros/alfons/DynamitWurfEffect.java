package game.content.heros.alfons;

import game.attributes.Attribute;
import game.attributes.AttributeValueList;
import game.effects.DirectEffect;
import game.objects.NonStatic;

/**
 * Dynamit Wurf, der Effekt.
 * @author Alex
 *
 */
public class DynamitWurfEffect extends DirectEffect {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -8381181347541794651L;

	/**
	 * Konstruktor. Initialisiert die Werte der AttributeValueList.
	 * @param effectedNonStatic betroffenes NonStatic
	 * @param level Skilllevel des Ausfuehrers.
	 */
	public DynamitWurfEffect(final NonStatic effectedNonStatic, final int level) {
		
		super(effectedNonStatic);
		
		getAttributeValueListWithSkillLevel(level, getAttributeValueList());
		
		this.setImageURL("usercontrols", "skill", "alfons/dynamit");
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
		av.setAttribute(Attribute.currentHealth, -60 * skillLevel);
		return av;
	}

}
