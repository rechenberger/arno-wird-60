package game.content.items.useable;

import game.attributes.Attribute;
import game.skills.ItemSkill;

/**
 * Krafttrank.
 * @author Tristan
 *
 */
public class ManaPotion extends UseableItem {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 6397454464384100964L;

	/**
	 * Konstruktor. Initialisert Werte.
	 */
	public ManaPotion() {
		super("Krafttrank");
		this.attributeValueList.setAttribute(Attribute.money, -100);
		this.setImageURL("usercontrols", "item", "powerpotion");
	}

	@Override
	public ItemSkill getItemSkill() {
		return game.content.skills.item.ManaPotionSkill.getInstance();
	}

}
