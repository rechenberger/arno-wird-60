package game.content.ki.vasallen;

import game.attributes.Attribute;
import game.objects.Fraction;

/**
 * Ein Fernkaempfer.
 * @author Marius
 *
 */
public class Ranged extends Vasal {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 8951174708970493123L;


	/**
	 * Konstruktor. Initialisert den Helden mit Namen, Attributen und Faehigkeiten.
	 * @param f Die Fraktion
	 */
	public Ranged(final Fraction f) {
		

		this.setFraction(f);
		this.name = "Fernkampf Killer";
		this.getAttributeValueList().setAttribute(Attribute.fightingRange, 10);
		this.getAttributeValueList().setAttribute(Attribute.movementSpeed, 25);
		this.getAttributeValueList().setAttribute(Attribute.fightingSpeed, 15);
		this.getAttributeValueList().setAttribute(Attribute.maxHealth, 60);
		this.getAttributeValueList().setAttribute(Attribute.damage, 2);
		this.setImageURL("match", "vasal", "sheep-pink");
		
	}
}
