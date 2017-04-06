package game.content.ki.vasallen;

import game.attributes.Attribute;
import game.objects.Fraction;

/**
 * Ein Vasalle der ein Nahkaempfer ist.
 * @author Marius
 *
 */
public class Melee extends Vasal {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 8951174708970493123L;


	/**
	 * Konstruktor. Initialisert den Helden mit Namen, Attributen und Faehigkeiten.
	 * @param f Die Fraktion
	 */
	public Melee(final Fraction f) {
		

		this.setFraction(f);
		this.name = "Nahkampf Killer";
		this.getAttributeValueList().setAttribute(Attribute.fightingRange, 1);
		this.getAttributeValueList().setAttribute(Attribute.movementSpeed, 25);
		this.getAttributeValueList().setAttribute(Attribute.maxHealth, 40);
		this.getAttributeValueList().setAttribute(Attribute.damage, 4);
		this.setImageURL("match", "vasal", "sheep-black");
		
	}
}
