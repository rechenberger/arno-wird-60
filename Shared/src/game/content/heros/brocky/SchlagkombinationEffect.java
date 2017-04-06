package game.content.heros.brocky;

import game.attributes.Attribute;
import game.attributes.AttributeValueList;
import game.effects.DurationEffect;
import game.objects.NonStatic;

/**
 * Schlagkombination, der Effekt.
 * @author Alex
 *
 */
public class SchlagkombinationEffect extends DurationEffect {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 9184687621308527483L;

	/**
	 * Konstruktor. Initialisiert die Werte der AttributeValueList.
	 * @param effectedNonStatic betroffenes NonStatic
	 * @param level Skilllevel des Ausfuehrers.
	 */
	public SchlagkombinationEffect(final NonStatic effectedNonStatic, final int level) {
		
		// Der Effekt dauert pro Level 1 Sekunde an.
		super(effectedNonStatic, level * 1000);
		
		this.name = "Schlag<br>kombination<br>erlitten";
		
		// Der Gegner wird betauebt. Er kann nicht kaempfen und sich nicht bewegen.
		
		getAttributeValueListWithSkillLevel(0, getAttributeValueList());
		
		this.setImageURL("usercontrols", "skill", "brocky/schlag");
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
		av.setAttribute(Attribute.currentHealth, -35 * skillLevel);	
		return av;
	}
}
