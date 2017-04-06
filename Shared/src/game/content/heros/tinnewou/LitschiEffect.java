package game.content.heros.tinnewou;

import game.attributes.Attribute;
import game.attributes.AttributeValueList;
import game.effects.DurationEffect;
import game.objects.NonStatic;

/**
 * Litschi, der Effekt.
 * @author Tristan
 *
 */
public class LitschiEffect extends DurationEffect {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -6564192900820668136L;


	/**
	 * Konstruktor. Initialisiert die Werte der AttributeValueList.
	 * @param effectedNonStatic betroffenes NonStatic
	 * @param level Skilllevel des Ausfuehrers.
	 */
	public LitschiEffect(final NonStatic effectedNonStatic, final int level) {
		super(effectedNonStatic, level * 3 * 1000);
		
		this.name = "Auf Litschi";
		
		getAttributeValueListWithSkillLevel(level, this.getAttributeValueList());
		
		this.setImageURL("usercontrols", "skill", "tinnewou/litschi");
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
		av.setAttribute(Attribute.movementSpeed, 2.0f); 
		return av;
	}
	
}