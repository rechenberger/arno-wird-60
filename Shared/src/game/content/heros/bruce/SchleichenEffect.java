package game.content.heros.bruce;

import game.attributes.Attribute;
import game.effects.DurationEffect;
import game.objects.NonStatic;

/**
 * Schleichen, der Effekt.
 * @author Alex
 *
 */
public class SchleichenEffect extends DurationEffect {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -6564192900820668136L;


	/**
	 * Konstruktor. Initialisiert die Werte der AttributeValueList.
	 * @param effectedNonStatic betroffenes NonStatic
	 * @param level Skilllevel des Ausfuehrers.
	 */
	public SchleichenEffect(final NonStatic effectedNonStatic, final int level) {
		
		// Der Effekt dauert pro Level 1 Sekunde an.
		super(effectedNonStatic, level * 1000);
		
		// Der Gegner wird betauebt. Er kann nicht kaempfen und sich nicht bewegen.
		this.getAttributeValueList().setAttribute(Attribute.movementSpeed, 50); 
		this.getAttributeValueList().setAttribute(Attribute.fightingSpeed, 0); 
		
	}
}