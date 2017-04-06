package game.content.heros.bruce;

import game.attributes.Attribute;
import game.attributes.AttributeValueList;
import game.effects.DirectEffect;
import game.objects.NonStatic;

/**
 * Tod von Oben, der Effekt.
 * @author Alex
 *
 */
public class TodVonObenEffect extends DirectEffect {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 4776503756252885600L;

	/**
	 * Konstruktor. Initialisiert die Werte der AttributeValueList.
	 * @param effectedNonStatic betroffenes NonStatic
	 * @param level Skilllevel des Ausfuehrers.
	 */
	public TodVonObenEffect(final NonStatic effectedNonStatic, final int level) {
		
		super(effectedNonStatic);

		
		getAttributeValueListWithSkillLevel(level, getAttributeValueList());		
		//TODO Bruce muss vor den Gegner teleportiert werden um Sprung zu simulieren.
		
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
		av.setAttribute(Attribute.currentHealth, -100 * skillLevel); 
		av.setAttribute(Attribute.experience, +100);  
		return av;
	}
}
