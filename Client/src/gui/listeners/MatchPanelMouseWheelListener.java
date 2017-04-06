package gui.listeners;

import gui.match.MatchPanel;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import module.ModuleHandler;

/**
 * Zoomt die Karte heran.
 * @author Sean
 *
 */
public class MatchPanelMouseWheelListener implements MouseWheelListener {

	@Override
	public void mouseWheelMoved(final MouseWheelEvent arg0) {
		int notches = arg0.getWheelRotation();
		ModuleHandler.GUI.getPanel(MatchPanel.class).translateZoom(notches * -0.05f);
	}

}
