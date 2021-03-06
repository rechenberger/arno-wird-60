package game.content.heros.brocky;

import game.attributes.Attribute;
import game.attributes.AttributeValueList;
import game.effects.DirectEffect;
import game.objects.NonStatic;

/**
 * Kinnhaken, der Effekt.
 * @author Tristan
 *
 */
public class KinnhakenEffect extends DirectEffect {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -6564192900820668136L;


	/**
	 * Konstruktor. Initialisiert die Werte der AttributeValueList.
	 * @param effectedNonStatic betroffenes NonStatic
	 * @param level Skilllevel des Ausfuehrers.
	 */
	public KinnhakenEffect(final NonStatic effectedNonStatic, final int level) {
		
		// Der Effekt dauert pro Level 1 Sekunde an.
		super(effectedNonStatic);
		
		this.name = "Kinnhaken<br>bekommen";
		
		// Der Gegner wird betauebt. Er kann nicht kaempfen und sich nicht bewegen.
		getAttributeValueListWithSkillLevel(level, getAttributeValueList());
		
		this.setImageURL("usercontrols", "skill", "brocky/kinnhaken");
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

		av.setAttribute(Attribute.movementSpeed, 0); 
		av.setAttribute(Attribute.fightingSpeed, 0);
		av.setAttribute(Attribute.currentHealth, -25 * skillLevel);
		return av;
	}
}
