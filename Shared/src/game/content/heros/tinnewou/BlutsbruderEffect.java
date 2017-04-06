package game.content.heros.tinnewou;

import game.attributes.Attribute;
import game.attributes.AttributeValueList;
import game.effects.DurationEffect;
import game.objects.NonStatic;

/**
 * Blutsbruder, der Effect.
 * @author Alex
 *
 */
public class BlutsbruderEffect extends DurationEffect {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1936063447417160929L;

	/**
	 * Konstruktor. Initialisiert die Werte der AttributeValueList.
	 * @param effectedNonStatic betroffenes NonStatic
	 * @param level Skilllevel des Ausfuehrers.
	 */
	public BlutsbruderEffect(final NonStatic effectedNonStatic, final int level) {
		
		// Der Effekt dauert pro Level 5 Sekunde an.
		super(effectedNonStatic, level * 5 * 1000);
		
		this.name = "Blutsbruder";
		
		// Der Verbuendete greift schneller an und regeneriert schneller Leben.
		getAttributeValueListWithSkillLevel(level, this.getAttributeValueList());
		
		this.setImageURL("usercontrols", "skill", "tinnewou/blutsbruder");
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
		av.setAttribute(Attribute.fightingSpeed, 1.1f * skillLevel);
		av.setAttribute(Attribute.damage, 1.2f * skillLevel);
		return av;
	}
}
