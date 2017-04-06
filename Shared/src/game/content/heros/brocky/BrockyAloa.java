package game.content.heros.brocky;

import game.attributes.Attribute;
import game.content.heros.Hero;

/**
 * Brocky Aloa.
 * @author Tristan
 */
public class BrockyAloa extends Hero {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 4835358020561684417L;

	/**
	 * Konstruktor. Initialisert den Helden mit Namen, Attributen und Faehigkeiten.
	 */
	public BrockyAloa() {
		// Der Held heisst Brocky Aloa
			this.name = "Brocky Aloa";
		// Setzte die Initialwerte seiner Attribute
			this.getAttributeValueList().setAttribute(Attribute.fightingRange, 1);
			this.getAttributeValueList().setAttribute(Attribute.fightingSpeed, 20);
			this.getAttributeValueList().setAttribute(Attribute.damage, 30);
			this.getAttributeValueList().setAttribute(Attribute.movementSpeed, 40);
			// Fuege seine Skills mit Level 0 hinzu. (0 bedeutet nicht ausfuehrbar. Muss erst erlernt werden)
			this.addSkill(new AufpumpenSkill(), 1);
			this.addSkill(new KinnhakenSkill(), 1);
			this.addSkill(new BoxerherzSkill(), 1);
			this.addSkill(new SchlagkombinationSkill(), 1);
			this.setImageURL("match", "hero", "brocky");
	}
}
