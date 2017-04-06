package game.content.items;

import game.attributes.Attribute;

/**
 * Soeckchen. Gehoeren zur Grundausstattung jedes Helden.
 * @author Melanie
 *
 */
public class Mobbelpreis extends game.objects.Item {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1090409612648375486L;
	
	/**
	 * Konstruktor. Initialisert Werte.
	 */
	public Mobbelpreis() {

		super("Mobbelpreis");
		this.attributeValueList.setAttribute(Attribute.money, -400);
		// Die Verteidigung wird effizienter.
		this.attributeValueList.setAttribute(Attribute.defense, 5);
		// Die Heilung wird erhoeht 
		this.attributeValueList.setAttribute(Attribute.healthgeneration, 5);
		// Die Angriffskraft wird verringert
		this.attributeValueList.setAttribute(Attribute.damage, -5);
		// Manageneration wird verringert
		this.attributeValueList.setAttribute(Attribute.manageneration, -5);
		this.setImageURL("usercontrols", "item", "mobbel");
		
		
	}
}
