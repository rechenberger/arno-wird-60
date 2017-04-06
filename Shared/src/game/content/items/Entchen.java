package game.content.items;

import game.attributes.Attribute;

/**
 * Entchen. Das Assesoire, welches jeder haben sollte.
 * @author Melanie
 *
 */
public class Entchen extends game.objects.Item {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 782128168739520971L;

	/**
	 * serialVersionUID.
	 */
	

	/**
	 * Konstruktor. Initialisert Werte.
	 */
	public Entchen() {

		super("Entchen");
		//Und ein Level hoeher juhuuuu
		this.attributeValueList.setAttribute(Attribute.money, -5000);
		this.attributeValueList.setAttribute(Attribute.experience, 100);
		this.setImageURL("usercontrols", "item", "entchen");
		
		
				
	}
}

