package game.content.items;

import game.attributes.Attribute;

/**
 * Soeckchen. Gehoeren zur Grundausstattung jedes Helden.
 * @author Melanie
 *
 */
public class Gewehr extends game.objects.Item {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -221893692472792013L;

	/**
	 * serialVersionUID.
	 */

	/**
	 * Konstruktor. Initialisert Werte.
	 */
	public Gewehr() {

		super("Gewehr");
		this.attributeValueList.setAttribute(Attribute.money, -400);
		// Die Angriffsgeschwindigkeit wird effizienter.
		this.attributeValueList.setAttribute(Attribute.fightingSpeed, 5);
		// Die Angriffskraft wird erhoeht 
		this.attributeValueList.setAttribute(Attribute.damage, 5);
		// Der Held wird langsamer weil er das Gewehr tragen muss
		this.attributeValueList.setAttribute(Attribute.movementSpeed, -5);
		// Defensive wird verringert, weil er das Gewehr laden muss
		this.attributeValueList.setAttribute(Attribute.defense, -5);
		this.setImageURL("usercontrols", "item", "gewehr");
		
	}
}

