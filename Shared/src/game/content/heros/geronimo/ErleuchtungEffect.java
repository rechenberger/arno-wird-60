package game.content.heros.geronimo;

import game.attributes.AttributeValueList;
import game.effects.DurationEffect;
import game.objects.NonStatic;

/**
 * Erleuchtung, der Effekt.
 * @author Alex
 *
 */
public class ErleuchtungEffect extends DurationEffect {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -3287398948769700201L;

	/**
	 * Konstruktor. Initialisiert die Werte der AttributeValueList.
	 * @param effectedNonStatic betroffenes NonStatic
	 * @param level Skilllevel des Ausfuehrers.
	 */
	public ErleuchtungEffect(final NonStatic effectedNonStatic, final int level) {
		super(effectedNonStatic, level * 5000);
		
		this.setImageURL("usercontrols", "skill", "geronimo/erleuchtung");
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
		return av;
	}
}
