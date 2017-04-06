package gui.listeners;

import game.actions.ItemBuyAction;
import game.objects.Item;
import gui.shop.ShopButton;
import gui.usercontrols.UserControls;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import module.ModuleHandler;

/**
 * MouseAdapter fuer den Shop.
 * @author Christian Westhoff, Tristan
 *
 */
public class ShopPanelMouseAdapter extends MouseAdapter {

	@Override
	public void mouseClicked(final MouseEvent event) {
		if (event.getButton() == MouseEvent.BUTTON1) {
			Item item = ((ShopButton) event.getComponent()).getItem();
			new ItemBuyAction(ModuleHandler.MATCH.getMyHero(), item).plan();
		}
	}

	@Override
	public void mouseEntered(final MouseEvent event) {
		ModuleHandler.GUI.getPanel(UserControls.class).setNonStaticInfoPanel(((ShopButton) event.getComponent()).getItem());
	}
	
	@Override
	public void mouseExited(final MouseEvent event) {
		ModuleHandler.GUI.getPanel(UserControls.class).setNonStaticInfoPanel(null);
	}
}
