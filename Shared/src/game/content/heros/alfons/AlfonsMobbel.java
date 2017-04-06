package game.content.heros.alfons;

import game.attributes.Attribute;
import game.content.heros.Hero;

/**
 * Ein Held.
 * @author Tristan
 */
public class AlfonsMobbel extends Hero {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 4835358020561684417L;

	/**
	 * Konstruktor. Initialisert den Helden mit Namen, Attributen und Faehigkeiten.
	 */
	public AlfonsMobbel() {
		// Der Held heisst Brocky Aloa
			this.name = "Alfons Mobbel";
		// Setzte die Initialwerte seiner Attribute
			this.getAttributeValueList().setAttribute(Attribute.fightingRange, 4);
			this.getAttributeValueList().setAttribute(Attribute.fightingSpeed, 30);
			this.getAttributeValueList().setAttribute(Attribute.damage, 30);
			this.getAttributeValueList().setAttribute(Attribute.damageType, 2);
			this.getAttributeValueList().setAttribute(Attribute.movementSpeed, 40);
			// Fuege seine Skills mit Level 0 hinzu. (0 bedeutet nicht ausfuehrbar. Muss erst erlernt werden)
			this.addSkill(new ReicherSackSkill(), 1);
			this.addSkill(new MobbelPreisSkill(), 1);
			this.addSkill(new DynamitWurfSkill(), 1);
			this.setImageURL("match", "hero", "alfons");
	}

}
