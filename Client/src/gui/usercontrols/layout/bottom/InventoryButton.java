package gui.usercontrols.layout.bottom;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import game.objects.Item;
import gui.StringHelper;
import gui.shop.ShopButton;

/**
 * Inventarknopf.
 * @author Christian Westhoff
 *
 */
public class InventoryButton extends ShopButton {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 7479041161141365734L;
	
	/**
	 * Gibt die Anzahl des Items an.
	 */
	private String number;
	
	/**
	 * Inventarknopf.
	 * @param item Item
	 * @param number Anzahl
	 */
	public InventoryButton(final Item item, final int number) {
		super(item);
		this.number = new Integer(number).toString();
	}

	@Override
	public void paintComponent(final Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		Rectangle2D rect = StringHelper.stringSize(g2d, number);
		g2d.drawString(number, 
				(int) (this.getWidth() - rect.getWidth() - 10), (int) (this.getHeight() - rect.getHeight()));
	}
}
