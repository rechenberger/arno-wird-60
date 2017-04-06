package gui.listeners;

import gui.match.MatchPanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import match.LeftClick;
import match.RightClick;
import module.ModuleHandler;

/**
 * Horcht im MatchPanel auf Mauseingaben.
 * @author Sean
 *
 */
public class MatchPanelMouseAdapter extends MouseAdapter {
	@Override
	public void mouseClicked(final MouseEvent event) {
		// Linke
		if (event.getButton() == MouseEvent.BUTTON1) {
			// Es war die linke Maustaste
			new LeftClick(ModuleHandler.GUI.getPanel(MatchPanel.class).calcPixelToCoord(event.getPoint()));
		}		
		// Rechte Taste
		if (event.getButton() == MouseEvent.BUTTON3) {
			// Rechte Maustaste
			new RightClick(ModuleHandler.GUI.getPanel(MatchPanel.class).calcPixelToCoord(event.getPoint()));
		}	
	}
}
