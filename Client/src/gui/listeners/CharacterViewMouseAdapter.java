package gui.listeners;

import game.objects.GameObject;
import gui.usercontrols.UserControls;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import module.ModuleHandler;

/**
 * MouseAdapter fuer die CharacterView.
 * 
 * @author Christian Westhoff
 * 
 */
public class CharacterViewMouseAdapter extends MouseAdapter {

	@Override
	public void mouseEntered(final MouseEvent event) {
		ModuleHandler.GUI.getPanel(UserControls.class).setNonStaticInfoPanel(
				(GameObject) ModuleHandler.MATCH.getMe().getHero());
	}

	@Override
	public void mouseExited(final MouseEvent arg0) {
		ModuleHandler.GUI.getPanel(UserControls.class).setNonStaticInfoPanel(
				null);
	}
}
