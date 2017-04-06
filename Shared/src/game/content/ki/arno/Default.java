package game.content.ki.arno;

import game.attributes.Attribute;
import game.objects.Fraction;

/**
 * Der Standart neutrale Mob.
 * @author Marius
 *
 */
public class Default extends Neutral {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2850950508193362877L;
	
	/**
	 * 
	 */
	public Default() {
		this.setFraction(Fraction.Arno);
		this.name = "Nahkampf Killer";
		this.getAttributeValueList().setAttribute(Attribute.fightingRange, 1);
		this.getAttributeValueList().setAttribute(Attribute.movementSpeed, 25);
		this.getAttributeValueList().setAttribute(Attribute.maxHealth, 40);
		this.getAttributeValueList().setAttribute(Attribute.damage, 4);
		int rand = (int) (Math.random() * 4 + 1);
		this.setImageURL("match", "monster", "monster-" + rand);
	}

}
