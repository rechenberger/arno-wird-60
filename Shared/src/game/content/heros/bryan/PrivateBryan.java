package game.content.heros.bryan;

import game.attributes.Attribute;
import game.content.heros.Hero;

/**
 * Private Bryan.
 * @author Alex
 */
public class PrivateBryan extends Hero {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor. Initialisert den Helden mit Namen, Attributen und Faehigkeiten.
	 */
	public PrivateBryan() {
		// Der Held heisst PrivateBryan
			this.name = "Private Bryan";
		// Setzte die Initialwerte seiner Attribute
			this.getAttributeValueList().setAttribute(Attribute.fightingRange, 40);
			this.getAttributeValueList().setAttribute(Attribute.fightingRange, 20);
			this.getAttributeValueList().setAttribute(Attribute.fightingSpeed, 20);
			this.getAttributeValueList().setAttribute(Attribute.damageType, 1);
		// Fuege seine Skills mit Level 0 hinzu. (0 bedeutet nicht ausfuehrbar. Muss erst erlernt werden)
			this.addSkill(new MotivationsredeSkill(), 1);
			this.addSkill(new ScharfschussSkill(), 1);
			this.addSkill(new KriegerSkill(), 1);
			this.setImageURL("match", "hero", "bryan");
	}

}
