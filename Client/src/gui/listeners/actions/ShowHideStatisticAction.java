package gui.listeners.actions;

import gui.statistic.InGameStatistic;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import module.ModuleHandler;

/**
 * Wird ausgefuehrt, wenn man die s-Taste benutzt um die Statistik anzuzeigen.
 * @author Alex
 *
 */
public class ShowHideStatisticAction extends AbstractAction {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 4246428568658205709L;

	/**
	 * Konstrukor.
	 */
	public ShowHideStatisticAction() {
	}

	@Override
	public void actionPerformed(final ActionEvent arg0) {
		if (ModuleHandler.GUI.getPanel(InGameStatistic.class).isVisible()) {
			ModuleHandler.GUI.getPanel(InGameStatistic.class).setVisible(false);
			ModuleHandler.GUI.getPanel(InGameStatistic.class).removeModale();
			ModuleHandler.GUI.getContentPane().requestFocus();
		} else {
			ModuleHandler.GUI.getPanel(InGameStatistic.class).setVisible(true);
			ModuleHandler.GUI.getPanel(InGameStatistic.class).updateStatistikTable();
		}	
	}
}
