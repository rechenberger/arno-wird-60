package game.content.heros.brocky;

import game.attributes.Attribute;
import game.attributes.AttributeValueList;
import game.effects.DurationEffect;
import game.objects.NonStatic;

/**
 * Aufpumpen, der Effekt.
 * @author Tristan
 *
 */
public class AufpumpenEffect extends DurationEffect {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -6564164900820668136L;


	/**
	 * Konstruktor. Initialisiert die Werte der AttributeValueList.
	 * @param effectedNonStatic betroffenes NonStatic
	 * @param level Skilllevel des Ausfuehrers.
	 */
	public AufpumpenEffect(final NonStatic effectedNonStatic, final int level) {
		
		// Der Effekt dauert 5 Sekunden an.
		super(effectedNonStatic, 5000);
		
		this.name = "Aufgepumpt";
		
		getAttributeValueListWithSkillLevel(level, getAttributeValueList());
		
		this.setImageURL("usercontrols", "skill", "brocky/aufpumpen");
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
		av.setAttribute(Attribute.damage, 10 * skillLevel); 
		
		av.setAttribute(Attribute.fightingSpeed, 1.1f * skillLevel); 
		return av;
	}
}
