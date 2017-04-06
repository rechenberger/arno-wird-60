package game.content.heros.bruce;

import game.attributes.Attribute;
import game.content.heros.Hero;

/**
 * Bruce Bee.
 * @author Alex
 */
public class BruceBee extends Hero {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor. Initialisert den Helden mit Namen, Attributen und Faehigkeiten.
	 */
	public BruceBee() {
		// Der Held heisst BruceBee
			this.name = "Brucen Bee";
		// Setzte die Initialwerte seiner Attribute
			this.getAttributeValueList().setAttribute(Attribute.fightingRange, 1);
			this.getAttributeValueList().setAttribute(Attribute.fightingSpeed, 20);
			this.getAttributeValueList().setAttribute(Attribute.movementSpeed, 40);
			// Fuege seine Skills mit Level 0 hinzu. (0 bedeutet nicht ausfuehrbar. Muss erst erlernt werden)
			this.addSkill(new SchleichenSkill(), 0);
			this.addSkill(new TodVonObenSkill(), 0);
			this.addSkill(new SchaukampfSkill(), 0);
			this.addSkill(new FlinkeFuesseSkill(), 0);
		// Fuege das Bild des Helden hinzu
			this.setImageURL("match", "hero", "BruceBee");
	}

}
