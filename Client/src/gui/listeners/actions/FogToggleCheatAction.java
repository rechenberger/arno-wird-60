package gui.listeners.actions;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import module.ModuleHandler;

/**
 * Toggelt den Nebel.
 * @author Tristan
 *
 */
public class FogToggleCheatAction extends AbstractAction {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -6022929690152980534L;
	
	/**
	 * Konstrukor.
	 */
	public FogToggleCheatAction() {
	}

	@Override
	public void actionPerformed(final ActionEvent arg0) {
		ModuleHandler.GUI.toggleFog();
	}

}
