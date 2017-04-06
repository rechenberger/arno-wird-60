package gui.shop;

import game.content.buildings.Shop;
import game.objects.Item;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;

import javax.swing.JPanel;

/**
 * Zeichnet den Shop.
 * @author Sean
 *
 */
public class ShopPanel extends JPanel {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 477610808023377721L;
	
	/**
	 * Setzt einen Rahmen und die Sichtbarkeit auf false.
	 * @param shop Shop Objekt
	 */
	public ShopPanel(final Shop shop) {
		this.setLayout(new GridLayout(4, 2));
		
		this.setVisible(true);
		
		for (Item item : shop.getItems()) {
			this.add(new ShopItemPanel(item));
		}
		
		this.revalidate();
		
		
	}
	
	/**
	 * Horcht in ShopButtons auf Mauseingaben.
	 * @author Sean
	 *
	 */
	protected class ShopButtonMouseListener extends MouseAdapter {

	
	}
}
