package gui.listeners;

import gui.usercontrols.knobs.Knob;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Listener fuer die Knobs.
 * @author Christian Westhoff
 *
 */
public class KnobListener extends MouseAdapter {

	@Override
	public void mouseEntered(final MouseEvent event) {
		if (event.getComponent() instanceof Knob) {
			((Knob) event.getComponent()).setSmall(false);
		}
	}

	@Override
	public void mouseExited(final MouseEvent event) {
		if (event.getComponent() instanceof Knob) {
			((Knob) event.getComponent()).setSmall(true);
		}
	}
}
