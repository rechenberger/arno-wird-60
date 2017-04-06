package gui.listeners;

import game.content.items.useable.UseableItem;
import gui.usercontrols.UserControls;
import gui.usercontrols.layout.bottom.InventoryButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import module.ModuleHandler;

/**
 * Listener fuer das Item Panel.
 * @author Christian Westhoff
 *
 */
public class InventoryPanelMouseAdapter extends MouseAdapter {
	@Override
	public void mouseClicked(final MouseEvent event) {
		if (event.getButton() == MouseEvent.BUTTON1) {
			// Linke Maustaste
			UseableItem ui = (UseableItem) ((InventoryButton) event.getComponent()).getItem();
			ui.planUse(ModuleHandler.MATCH.getMyHero());
			ModuleHandler.GUI.getContentPane().requestFocus();
		}
	}
	
	@Override
	public void mouseEntered(final MouseEvent event) {
		ModuleHandler.GUI.getPanel(UserControls.class).setNonStaticInfoPanel(
				((InventoryButton) event.getComponent()).getItem());
	}
	
	@Override
	public void mouseExited(final MouseEvent event) {
		ModuleHandler.GUI.getPanel(UserControls.class).setNonStaticInfoPanel(
				null);
	}
}
