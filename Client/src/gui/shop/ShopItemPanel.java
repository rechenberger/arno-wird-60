package gui.shop;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import game.attributes.Attribute;
import game.objects.Item;
import gui.StringHelper;
import gui.listeners.ShopPanelMouseAdapter;

import javax.swing.JPanel;
/**
 * Erzeugt ein ShopItemPanel.
 * @author Christian Westhoff
 *
 */
public class ShopItemPanel extends JPanel {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -911329794090348617L;
	/**
	 * Das Item.
	 */
	private Item item;
	
	/**
	 * Konstruktor.
	 * @param item Item
	 */
	public ShopItemPanel(final Item item) {
		this.item = item;
		this.setOpaque(false);
		ShopButton button = new ShopButton(item);
		button.addMouseListener(new ShopPanelMouseAdapter());
		this.add(button);
		this.setPreferredSize(new Dimension(170, 120));	
	}

	@Override
	public void paintComponent(final Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		String name = this.item.getName();
		Rectangle2D rect1 = StringHelper.stringSize(g2d, name);
		
		String money = Math.abs(this.item.getAttributeValueList().getValue(Attribute.money)) + " Arno-Moneten";
		Rectangle2D rect2 = StringHelper.stringSize(g2d, money);
		
		g2d.drawString(name, 
				(int) (this.getWidth() / 2 - rect1.getWidth() / 2), (int) (this.getHeight() - rect1.getHeight() - rect2.getHeight() - 5));

		g2d.drawString(money, 
				(int) (this.getWidth() / 2 - rect2.getWidth() / 2), (int) (this.getHeight() - rect2.getHeight()));
	}
}
