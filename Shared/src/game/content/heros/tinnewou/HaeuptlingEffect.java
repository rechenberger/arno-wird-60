package game.content.heros.tinnewou;

import game.attributes.Attribute;
import game.attributes.AttributeValueList;
import game.effects.DurationEffect;
import game.objects.NonStatic;

/**
 * Haeuptling der Apachen, der Effekt.
 * @author Alex
 *
 */
public class HaeuptlingEffect extends DurationEffect {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -3921723008968274417L;

	/**
	 * Konstruktor. Initialisiert die Werte der AttributeValueList.
	 * @param effectedNonStatic betroffenes NonStatic
	 * @param level Skilllevel des Ausfuehrers.
	 */
	public HaeuptlingEffect(final NonStatic effectedNonStatic, final int level) {
		super(effectedNonStatic, level * 2000);
		
		this.name = "H\u00e4uptling<br>der Apachen<br>in der<br>N\u00e4he";
		//TODO funktioniert nicht richtig
		getAttributeValueListWithSkillLevel(level, this.getAttributeValueList());
		
		this.setImageURL("usercontrols", "skill", "tinnewou/haeuptling");
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
		av.setAttribute(Attribute.movementSpeed, 1.2f * skillLevel);
		return av;
	}

}
