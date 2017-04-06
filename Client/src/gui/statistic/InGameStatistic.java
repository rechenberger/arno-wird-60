package gui.statistic;

import gui.listeners.actions.ShowHideStatisticAction;
import gui.usercontrols.modal.ComponentModal;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

/**
 * Panel, das waehrend dem Spiel die Statistik anzeigt.
 * @author Alex
 *
 */
public class InGameStatistic extends JPanel {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 2609575746912481422L;
	
	/**
	 * Das ComponentModal der Statistik.
	 */
	private ComponentModal modal;
	
	/**
	 * Konstruktor.
	 */
	public InGameStatistic() {
		this.setVisible(false);
		this.setOpaque(false);
		
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke('s'), "InGameStatistic");
		this.getActionMap().put("InGameStatistic", new ShowHideStatisticAction());
	}
	
	/**
	 * Daten in der Tabelle werden aktualisiert.
	 */
	public void updateStatistikTable() {
		modal = new ComponentModal("Statistik des Spiels", new CurrentGameStatisticTable());
		modal.removeCancelButton();
		modal.show();
	}
	
	/**
	 * Entfernt das Modal.
	 */
	public void removeModale() {
		modal.remove();
	}
}
