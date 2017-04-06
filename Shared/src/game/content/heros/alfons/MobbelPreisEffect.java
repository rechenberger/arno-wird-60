package game.content.heros.alfons;

import game.attributes.Attribute;
import game.attributes.AttributeValueList;
import game.effects.DurationEffect;
import game.objects.NonStatic;

/**
 * Mobbel Preis, der Effect.
 * @author Alex
 *
 */
public class MobbelPreisEffect extends DurationEffect {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 6848869158623822498L;

	/**
	 * Konstruktor. Initialisiert die Werte der AttributeValueList.
	 * @param effectedNonStatic betroffenes NonStatic
	 * @param level Skilllevel des Ausfuehrers.
	 */
	public MobbelPreisEffect(final NonStatic effectedNonStatic, final int level) {
		
		// Der Effekt dauert pro Level 1 Sekunde an.
		super(effectedNonStatic, level * 1000);
		
		this.name = "Mobbel<br>Preis<br>erhalten";
		
		// Der Gegner wird betauebt. Er kann nicht kaempfen und sich nicht bewegen.
		getAttributeValueListWithSkillLevel(level, getAttributeValueList());
		
		this.setImageURL("usercontrols", "skill", "alfons/mobbelpreis");
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

		av.setAttribute(Attribute.moneygeneration, 1.1f * skillLevel);
		av.setAttribute(Attribute.experience, +100);  
		return av;
	}
}
