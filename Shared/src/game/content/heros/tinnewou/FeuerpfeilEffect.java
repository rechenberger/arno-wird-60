package game.content.heros.tinnewou;

import game.attributes.Attribute;
import game.attributes.AttributeValueList;
import game.effects.DirectEffect;
import game.objects.NonStatic;

/**
 * Gezielter Scharfschuss, der Effekt.
 * @author Alex
 *
 */
public class FeuerpfeilEffect extends DirectEffect {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 4075886570448204446L;
	
	/**
	 * Konstruktor. Initialisiert die Werte der AttributeValueList.
	 * @param effectedNonStatic betroffenes NonStatic
	 * @param level Skilllevel des Ausfuehrers.
	 */
	public FeuerpfeilEffect(final NonStatic effectedNonStatic, final int level) {
		
		super(effectedNonStatic);
		
		// Der Gegner wird betauebt. Er kann nicht kaempfen und sich nicht bewegen.
		getAttributeValueListWithSkillLevel(level, this.getAttributeValueList());
		
		this.setImageURL("usercontrols", "skill", "tinnewou/pfeil");
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
		av.setAttribute(Attribute.currentHealth, -75 * skillLevel);
		return av;
	}
}
