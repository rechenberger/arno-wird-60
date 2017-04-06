package game.content.items;

import game.attributes.Attribute;

/**
 * Verbessert die Angriffskraft.
 * @author Marius
 *
 */
public class Angriffskraft extends game.objects.Item {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 415949144758062013L;

	/**
	 * Konstruktor. Initialisert Werte.
	 */
	public Angriffskraft() {
		super("Angriffskraft+");
		this.attributeValueList.setAttribute(Attribute.money, -250);
		this.attributeValueList.setAttribute(Attribute.damage, 5);
		this.setImageURL("usercontrols", "item", "powerplus");

	}
}
