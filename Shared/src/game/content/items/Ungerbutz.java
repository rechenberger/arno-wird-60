package game.content.items;

import game.attributes.Attribute;

/**
 * Ungabutz. Zwickt, aber passt.
 * @author Tristan
 *
 */
public class Ungerbutz extends game.objects.Item {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -7267929053712550196L;

	/**
	 * Konstruktor. Initialisert Werte.
	 */
	public Ungerbutz() {
		super("Ungerbutz");
		this.attributeValueList.setAttribute(Attribute.money, -1050);
		// Zwickt.
		this.attributeValueList.setAttribute(Attribute.movementSpeed, 0.9f);
		// Wunden an sensiblen Stellen verheilen schneller. 
		this.attributeValueList.setAttribute(Attribute.healthgeneration, 1.2f);
		// Wo nimmt er nur diese Kraft her?
		this.attributeValueList.setAttribute(Attribute.manageneration, 1.2f);
		// Wirkt Entzuendungshemmend.
		this.attributeValueList.setAttribute(Attribute.defense, 1);
		this.setImageURL("usercontrols", "item", "ungerbutz");
	}
}
