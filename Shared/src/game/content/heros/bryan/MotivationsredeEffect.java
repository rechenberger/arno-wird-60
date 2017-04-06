package game.content.heros.bryan;

import game.attributes.Attribute;
import game.attributes.AttributeValueList;
import game.effects.DurationEffect;
import game.objects.NonStatic;

/**
 * Tarnen, der Effekt.
 * @author Alex
 *
 */
public class MotivationsredeEffect extends DurationEffect {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -6564192900820668136L;


	/**
	 * Konstruktor. Initialisiert die Werte der AttributeValueList.
	 * @param effectedNonStatic betroffenes NonStatic
	 * @param level Skilllevel des Ausfuehrers.
	 * @param healthRatio currentHealth pro maxHealth
	 */
	public MotivationsredeEffect(final NonStatic effectedNonStatic, final int level, final float healthRatio) {
		
		super(effectedNonStatic, level * 3 * 1000);
		
		this.name = "Motivations<br>rede<br>geh\u00f6rt";
		
		getAttributeValueListWithSkillLevel(level, getAttributeValueList(), healthRatio);
		
		this.setImageURL("usercontrols", "skill", "bryan/rede");
	}
	
	/**
	 * Gibt die Attributevalue Liste Zurueck, die die Auswirkungen dieses Effekts wiederspiegelt.
	 * <b>Wenn die Uebergebene avListe null ist, dann wird eine neue erstellt.</b>
	 * @param skillLevel Das Level auf welchem der Skill ausgefuehrt wurde.
	 * @param avListe Die Attribute value Liste in die der Wert eingefuegt werden soll.
	 * @param healthRatio currentHealth pro maxHealth
	 * @return Die AttributeVAlue liste.
	 */
	public static AttributeValueList getAttributeValueListWithSkillLevel(final int skillLevel, final AttributeValueList avListe, final float healthRatio)  {
		AttributeValueList av = avListe;
		if (av == null) {
			av = new AttributeValueList();
		}
		float tmp = 2.0f - healthRatio;
		tmp += skillLevel * 0.1f;
		
		av.setAttribute(Attribute.damage, tmp); 
		av.setAttribute(Attribute.defense, tmp); 
		return av;
	}
}