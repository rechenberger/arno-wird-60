package game.objects;

import game.attributes.Attribute;

/**
 * Fighables sind jene NonStatic die kaempfen koennen.
 * @author Tristan
 *
 */
public class Fightable extends NonStatic {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -4330547257732178221L;
	/**
	 * Name des Fightable.
	 */
	protected String name;
	
	/**
	 * Konstruktor.
	 * Initialisert Attribute mit Standartwerten.
	 * Initialisiert Skill Standartattacke.
	 */
	public Fightable() {
		super();
		this.getAttributeValueList().addAttribute(Attribute.alive);
		this.getAttributeValueList().setAttribute(Attribute.maxHealth, 200);
		this.getAttributeValueList().setAttribute(Attribute.currentHealth, 200);
		this.getAttributeValueList().setAttribute(Attribute.healthgeneration, 5);
		this.getAttributeValueList().setAttribute(Attribute.damage, 10);
		this.getAttributeValueList().setAttribute(Attribute.defense, 5);
		this.getAttributeValueList().setAttribute(Attribute.fightingRange, 1);
		this.getAttributeValueList().setAttribute(Attribute.viewRange, 10);
		this.getAttributeValueList().setAttribute(Attribute.fightingSpeed, 115);
		this.addSkill(game.content.skills.Attack.getInstance());
		this.addSkill(game.content.skills.Die.getInstance());
		this.addSkill(game.content.skills.Spawn.getInstance());
	}
	
	/**
	 * Gibt den Namen zurueck.
	 * @return Name des Fightable
	 */
	public String getName() {
		return name;
	}
	
}
