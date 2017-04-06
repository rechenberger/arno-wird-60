package gui.listeners;

import gui.usercontrols.modal.FrameCloseModal;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Listener, des Hauptfensters.
 * @author Sean
 */
public class MainWindowAdapter extends WindowAdapter {

	@Override
	public void windowClosing(final WindowEvent arg0) {
		new FrameCloseModal().show();
	}
}
