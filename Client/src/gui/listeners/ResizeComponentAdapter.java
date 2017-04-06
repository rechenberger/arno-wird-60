package gui.listeners;

import gui.interfaces.Resizable;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * Horcht ob die Groesse einer Component veraendert wurde und ruft dann die Methode resized()
 * der Component auf. Component muss dafuer die Klasse Resizable implementieren.
 * @author Sean
 *
 */
public class ResizeComponentAdapter extends ComponentAdapter {

	@Override
	public void componentResized(final ComponentEvent arg0) {
		if (arg0.getComponent() instanceof Resizable) {
			Resizable r = (Resizable) arg0.getComponent();
	 		r.resized();
		} else {
			throw new IllegalArgumentException("Keine Instanz von Resizable. Die Component muss Resizable implementieren!");
		}
	}

}
