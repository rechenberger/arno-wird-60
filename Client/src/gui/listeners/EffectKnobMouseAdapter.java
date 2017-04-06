package gui.listeners;

import game.objects.GameObject;
import gui.usercontrols.UserControls;
import gui.usercontrols.knobs.EffectKnob;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import module.ModuleHandler;

/**
 * Horch auf den Knobs.
 * @author Christian Westhoff
 *
 */
public class EffectKnobMouseAdapter extends MouseAdapter {

	@Override
	public void mouseEntered(final MouseEvent event) {
		if (event.getComponent() instanceof EffectKnob) {
			EffectKnob knob = (EffectKnob) event.getComponent();
			ModuleHandler.GUI.getPanel(UserControls.class).setNonStaticInfoPanel((GameObject) knob.getEffect());
		}
	}

	@Override
	public void mouseExited(final MouseEvent event) {
		ModuleHandler.GUI.getPanel(UserControls.class).setNonStaticInfoPanel(null);
	}
}
