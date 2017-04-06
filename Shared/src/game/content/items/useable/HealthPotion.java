package game.content.items.useable;

import game.attributes.Attribute;
import game.skills.ItemSkill;

/**
 * Heiltrank.
 * @author Tristan
 *
 */
public class HealthPotion extends UseableItem {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 6397454464384100964L;

	/**
	 * Konstruktor. Initialisert Werte.
	 */
	public HealthPotion() {
		super("Heiltrank");
		this.attributeValueList.setAttribute(Attribute.money, -100);
		this.setImageURL("usercontrols", "item", "healpotion");
	}

	@Override
	public ItemSkill getItemSkill() {
		return game.content.skills.item.HealthPotionSkill.getInstance();
	}

}
