package gui.usercontrols.layout.bottom;

import game.objects.Item;
import gui.listeners.InventoryPanelMouseAdapter;
import gui.shop.ShopButton;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.JPanel;

import module.ModuleHandler;

/**
 * Panel, das das Inventar des Helden darstellt.
 * @author Alex, Sean
 *
 */
public class InventoryPanel extends JPanel {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -4665765605419572356L;
	
	/**
	 * Momentan gezeichnete Inventar Button.
	 */
	LinkedList<ShopButton> currentInventory;
	
	/**
	 * Speichert das aktuelle inventory um Unterschiede festzustellen.
	 */
	HashMap<Item, Integer> inventory;

	/**
	 * Konstruktor.
	 */
	public InventoryPanel() {
		this.setOpaque(false);
		this.setPreferredSize(new Dimension(175, 65));
		currentInventory = new LinkedList<ShopButton>();
		this.setVisible(true);
	}
	
	@Override
	public void paintComponent(final Graphics g) {
		super.paintComponent(g);
		HashMap<Item, Integer> inventory = ModuleHandler.MATCH.getMyHero().getInventoryUsable();
		if (!inventory.equals(this.inventory)) {
			for (ShopButton button : currentInventory) {
				this.remove(button);
			}
			
			for (Item item : inventory.keySet()) {
				InventoryButton button = new InventoryButton(item, inventory.get(item));
				button.addMouseListener(new InventoryPanelMouseAdapter());
				
				this.add(button);
				currentInventory.add(button);
			}
			
			this.inventory = inventory;
			
			this.revalidate();
		}
	}
}
