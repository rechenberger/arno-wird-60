package game.content.items;

import game.attributes.Attribute;

/**
 * Erhoeht die Bewegungsgeschwindigkeit.
 * @author Marius
 *
 */
public class Bewegungsgeschwindigkeit extends game.objects.Item {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2198241593536891257L;

	/**
	 * Konstruktor. Initialisert Werte.
	 */
	public Bewegungsgeschwindigkeit() {
		super("Bewegungsgeschwindigkeit+");
		this.attributeValueList.setAttribute(Attribute.money, -500);
		this.attributeValueList.setAttribute(Attribute.movementSpeed, 5);
		this.setImageURL("usercontrols", "item", "speedplus");
	}
	
	

}
