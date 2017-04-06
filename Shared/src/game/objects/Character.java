package game.objects;

import game.attributes.Attribute;
import game.content.skills.Move;

/**
 * Charaktere sind Menschenartige Wesen.
 * (Jene Fightables die sich bewegen koennen)
 * @author Tristan
 *
 */
public class Character extends Fightable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 8398879969459309849L;
	

	/**
	 * Konstruktor.
	 * Initialisert Attribute mit Standartwerten.
	 */
	public Character() {
		super();
		this.addSkill(Move.getInstance());
		this.getAttributeValueList().setAttribute(Attribute.movementSpeed, 100);
	}

}
