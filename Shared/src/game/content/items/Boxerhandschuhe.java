package game.content.items;

import game.attributes.Attribute;

/**
 * Soeckchen. Gehoeren zur Grundausstattung jedes Helden.
 * @author Melanie
 *
 */
public class Boxerhandschuhe extends game.objects.Item {
	
	/**
	 * serialVersionUID.
	 */

	private static final long serialVersionUID = 9041461331549841436L;

	/**
	 * Konstruktor. Initialisert Werte.
	 */
	public Boxerhandschuhe() {

		super("Boxerhandschuhe");
		this.attributeValueList.setAttribute(Attribute.money, -400);
		// Die Angriffsgeschwindigkeit wird effizienter.
		this.attributeValueList.setAttribute(Attribute.fightingSpeed, 5);
		// Die Defensive wird erhoeht 
		this.attributeValueList.setAttribute(Attribute.defense, 5);
		// Der Held generiert sich langsamer 
		this.attributeValueList.setAttribute(Attribute.healthgeneration, -5);
		// Manageneration wird verringert
		this.attributeValueList.setAttribute(Attribute.manageneration, -5);
		this.setImageURL("usercontrols", "item", "boxer");
		
	}
}
