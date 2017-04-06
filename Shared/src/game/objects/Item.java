package game.objects;

import game.attributes.Attribute;
import game.attributes.AttributeValueList;
import game.effects.ItemEffect;

/**
 * Item. Der Character kann sie kaufen und von ihren Werten profitieren.
 * @author Tristan
 *
 */
public abstract class Item extends Drawable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -3450527044562511740L;
	
	/**
	 * Speichert die Attribute des Items.
	 */
	protected AttributeValueList attributeValueList = new AttributeValueList();
	
	/**
	 * @return die Attribute des Items.
	 */
	public AttributeValueList getAttributeValueList() {
		return this.attributeValueList;
	}
	
	/**
	 * Name des Items.
	 */
	protected String name;
	
	/**
	 * Konstruktor.
	 * @param setName Name des Items
	 */
	public Item(final String setName) {
		this.name = setName;
	}
	
	/**
	 * @return Name des Items
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Gibt einem NonStatic ein Item.
	 * @param nonStatic neuer gluecklicher Itembesitzer.
	 */
	public void giveItem(final NonStatic nonStatic) {
		if (this.canEffort(nonStatic)) {
			new ItemEffect(nonStatic, this).ready();
		} else {
			System.out.println("Zu frueh gefreut: Nicht Genug Geld auf Tasche");
		}
	}
	
	/**
	 * 
	 * @param nonStatic NonStatic
	 * @return Ob sich das NonStatic das Item leisten kann.
	 */
	public boolean canEffort(final NonStatic nonStatic) {
		return nonStatic.getAttributeValue(Attribute.money) + this.attributeValueList.getValue(Attribute.money) >= 0;
	}
}
