package game.content.items;

import game.attributes.Attribute;

/**
 * Soeckchen. Gehoeren zur Grundausstattung jedes Helden.
 * @author Tristan
 *
 */
public class Soeckchen extends game.objects.Item {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 5552692022116445612L;

	/**
	 * Konstruktor. Initialisert Werte.
	 */
	public Soeckchen() {
		super("S\u00f6ckchen");
		this.attributeValueList.setAttribute(Attribute.money, -820);
		// Die Fuss-Schuh-Kraftuebertragen ist um 25% effizienter.
		this.attributeValueList.setAttribute(Attribute.movementSpeed, 1.25f);
		// Tritte werden gedaempft.
		this.attributeValueList.setAttribute(Attribute.damage, -3);
		// In nem alten Socken findt sich ab und an mal ne Muenze.
		this.attributeValueList.setAttribute(Attribute.moneygeneration, 1);
		
		this.setImageURL("usercontrols", "item", "soeckchen");
		
	}
}
