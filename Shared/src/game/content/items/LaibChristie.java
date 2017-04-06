package game.content.items;

import game.attributes.Attribute;

/**
 * Soeckchen. Gehoeren zur Grundausstattung jedes Helden.
 * @author Melanie
 *
 */
public class LaibChristie extends game.objects.Item {
	

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 6889762804148113417L;

	/**
	 * Konstruktor. Initialisert Werte.
	 */
	public LaibChristie() {

		super("LeibChrisitie");
		this.attributeValueList.setAttribute(Attribute.money, -400);
		// Die Bewegungsgeschwindigkeit wird effizienter.
		this.attributeValueList.setAttribute(Attribute.movementSpeed, 5);
		// Die Manaregeneration wird erhoeht 
		this.attributeValueList.setAttribute(Attribute.manageneration, 5);
		// Die Angriffsgeschwindigkeit wird langsamer
		this.attributeValueList.setAttribute(Attribute.fightingSpeed, -5);
		// Defensive wird verringert
		this.attributeValueList.setAttribute(Attribute.damage, -5);
		this.setImageURL("usercontrols", "item", "brot");
		
	}
}
