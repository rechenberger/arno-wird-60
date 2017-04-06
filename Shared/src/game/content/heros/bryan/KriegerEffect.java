package game.content.heros.bryan;

import game.attributes.Attribute;
import game.attributes.AttributeValueList;
import game.effects.PassiveSkillEffect;
import game.objects.NonStatic;

/**
 * Boxerherz, der Effekt.
 * @author Alex
 *
 */
public class KriegerEffect extends PassiveSkillEffect {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 8118233666866937608L;
	
	/**
	 * Konstruktor. Initialisiert die Werte der AttributeValueList.
	 * @param effectedNonStatic betroffenes NonStatic
	 * @param level Skilllevel des Ausfuehrers.
	 */
	public KriegerEffect(final NonStatic effectedNonStatic, final int level) {
		super(effectedNonStatic);
		this.name = "Unz\u00e4hm<br>barer<br>Krieger";
		
		getAttributeValueListWithSkillLevel(level, getAttributeValueList());
		
		this.setImageURL("usercontrols", "skill", "bryan/krieger");
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
		av.setAttribute(Attribute.healthgeneration, 1.2f * skillLevel); 
		return av;
	}
}
