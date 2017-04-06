package game.content.items;

import game.attributes.Attribute;

/**
 * Erhoeht die Lebensregeneration.
 * @author Marius
 *
 */
public class Lebensregeneration extends game.objects.Item {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1840599153169316014L;

	/**
	 * Konstruktor. Initialisert Werte.
	 */
	public Lebensregeneration() {
		super("Lebensregenaration+");
		this.attributeValueList.setAttribute(Attribute.money, -750);
		this.attributeValueList.setAttribute(Attribute.healthgeneration, 1.35f);
		this.setImageURL("usercontrols", "item", "healthregplus");
	}

}
