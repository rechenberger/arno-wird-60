package game.content.heros.geronimo;

import game.attributes.Attribute;
import game.attributes.AttributeValueList;
import game.effects.PassiveSkillEffect;
import game.objects.NonStatic;

/**
 * Gesalbte Fuesse, der Effekt.
 * @author Alex
 *
 */
public class GesalbteFuesseEffect extends PassiveSkillEffect {

	/**
	 * 2143662809707322730L.
	 */
	private static final long serialVersionUID = -2143662809707322730L;

	/**
	 * Konstruktor.
	 * @param effects NonStatic auf den der Effekt wirken soll.
	 */
	public GesalbteFuesseEffect(final NonStatic effects) {
		super(effects);
		this.name = "Gesalbte<br>F\u00fc\u00dfe";
		
		getAttributeValueListWithSkillLevel(1, this.getAttributeValueList());
		
		this.setImageURL("usercontrols", "skill", "geronimo/fuesse");
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
		av.setAttribute(Attribute.flying, 1 * skillLevel);
		return av;
	}
	
	

}
