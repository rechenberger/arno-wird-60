package game.content.items;

import game.attributes.Attribute;

/**
 * Soeckchen. Gehoeren zur Grundausstattung jedes Helden.
 * @author Melanie
 *
 */
public class Tomahawk extends game.objects.Item {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 8594598279655237096L;

	/**
	 * Konstruktor. Initialisert Werte.
	 */
	public Tomahawk() {

		super("Tomahawk");
		this.attributeValueList.setAttribute(Attribute.money, -400);
		// Die Manaregeneration wird effizienter.
		this.attributeValueList.setAttribute(Attribute.manageneration, 5);
		// Die Angriffskraft wird erhoeht 
		this.attributeValueList.setAttribute(Attribute.damage, 5);
		// Der Held wird langsamer
		this.attributeValueList.setAttribute(Attribute.fightingSpeed, -5);
		// Defensive wird verlangsamt
		this.attributeValueList.setAttribute(Attribute.defense, -5);
		this.setImageURL("usercontrols", "item", "tomahawk");
		
	}
}
