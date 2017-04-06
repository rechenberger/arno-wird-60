package game.effects;

import game.objects.Item;
import game.objects.NonStatic;

/**
 * Den Effekt den man beim Tragen eines Items erhaelt.
 * @author Tristan
 *
 */
public class ItemEffect extends Effect {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -1208978098431449314L;
	
	/**
	 * Id des Item.
	 */
	private final int itemId;
	
	
	/**
	 * Verpasst einem NonStatic ein Item.
	 * @param effects Item tragendes NonStatic
	 * @param item Item
	 */
	public ItemEffect(final NonStatic effects, final Item item) {
		super(effects);
		this.itemId = item.getId();
		this.attributeValueList = item.getAttributeValueList();
	}
	
	/**
	 * @return Item.
	 */
	public Item getItem() {
		Item item = Item.getById(itemId);
		return item;
	}
}
