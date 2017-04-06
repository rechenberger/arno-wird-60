package game.content.items;

import game.attributes.Attribute;

/**
 * Ein Item was die maximalen Lebenspunkte des Spieler erhoeht.
 * @author Marius
 *
 */
public class Lebenspunkte extends game.objects.Item {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6506920092621332867L;

	/**
	 * Konstruktor. Initialisert Werte.
	 */
	public Lebenspunkte() {
		super("Lebenspunkte+");
		this.attributeValueList.setAttribute(Attribute.money, -425);
		this.attributeValueList.setAttribute(Attribute.maxHealth, 50);
		this.setImageURL("usercontrols", "item", "healthplus");
	}
}
