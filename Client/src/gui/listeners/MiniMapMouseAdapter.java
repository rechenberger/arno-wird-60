package gui.listeners;

import gui.match.MatchPanel;
import gui.usercontrols.layout.bottom.MiniMap;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import match.RightClick;
import module.ModuleHandler;

/**
 * Horcht auf MouseEvents im Panel der MiniMap.
 * @author Alex
 *
 */
public class MiniMapMouseAdapter extends MouseAdapter {

	@Override
	public void mouseClicked(final MouseEvent arg0) {
		// Linke Taste
		if (arg0.getButton() == MouseEvent.BUTTON1) {
			
			if (arg0.getComponent() instanceof MiniMap) {
				Point newCenterCoord = ((MiniMap) arg0.getComponent()).inverseCalcCoordToPixel(arg0.getPoint());
				ModuleHandler.GUI.getPanel(MatchPanel.class).setCenterCoord(newCenterCoord);
				ModuleHandler.GUI.getPanel(MatchPanel.class).setFollowMyHero(false);
			}
		}		
		// Rechte Taste
		if (arg0.getButton() == MouseEvent.BUTTON3) {
			if (arg0.getComponent() instanceof MiniMap) {
				Point coord = ((MiniMap) arg0.getComponent()).inverseCalcCoordToPixel(arg0.getPoint());
				new RightClick(coord);
			}
		}
	}
}
