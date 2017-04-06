package gui.shop;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import module.ModuleHandler;

import game.objects.Item;

/**
 * Button fuer die Items die ein Held besitzt. Muss spaeter durch die Images der Bilder erstetzt werden.
 * @author Alex
 *
 */
public class ShopButton extends JButton {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 5713549371070368255L;
	/**
	 * Das ShopItem.
	 */
	private Item item;
	
	/**
	 * Uebergibt den Namen an die Elternmethode, die den Button erstellt.
	 * @param item Item
	 */
	public ShopButton(final Item item) {
		super(new ImageIcon(ModuleHandler.GUI.getImageSet().getImage(item.getImageURL())));
		this.setPreferredSize(new Dimension(65, 65));
		this.item = item;
	}
	
	/**
	 * @return Item, das mit dem Button verbunden ist
	 */
	public Item getItem() {
		return item;
	}
}
