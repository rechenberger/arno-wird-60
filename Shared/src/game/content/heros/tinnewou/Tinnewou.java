package game.content.heros.tinnewou;

import game.attributes.Attribute;
import game.content.heros.Hero;

/**
 * Tinnewou.
 * @author Tristan
 */
public class Tinnewou extends Hero {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor. Initialisert den Helden mit Namen, Attributen und Faehigkeiten.
	 */
	public Tinnewou() {
		// Der Held heisst Tinnewou
			this.name = "Tinnewou";
		// Setzte die Initialwerte seiner Attribute
			this.getAttributeValueList().setAttribute(Attribute.fightingRange, 1);
			this.getAttributeValueList().setAttribute(Attribute.fightingSpeed, 20);
			this.getAttributeValueList().setAttribute(Attribute.movementSpeed, 75);
			this.getAttributeValueList().setAttribute(Attribute.damageType, 4);
		// Fuege seine Skills mit Level 0 hinzu. (0 bedeutet nicht ausfuehrbar. Muss erst erlernt werden)
			this.addSkill(new LitschiSkill(), 1);
			this.addSkill(new BlutsbruderSkill(), 1);
			this.addSkill(new FeuerpfeilSkill(), 1);
			this.addSkill(new HaeuptlingSkill(), 1);
			this.setImageURL("match", "hero", "tinnewou");
	}

}
