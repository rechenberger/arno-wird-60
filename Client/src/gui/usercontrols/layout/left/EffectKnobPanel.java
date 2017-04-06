package gui.usercontrols.layout.left;

import gui.listeners.EffectKnobMouseAdapter;
import gui.usercontrols.knobs.EffectKnob;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 * Zeichnet die Effekt Knobs.
 * @author Tristan.
 *
 */
public class EffectKnobPanel extends JPanel {
	
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = -3523937648745147065L;
	
	/**
	 * Letzte Instanz.
	 */
	private static EffectKnobPanel instance;
	
	/**
	 * Erstellt die Skillbuttons und setzt einen Listener darauf.
	 */
	public EffectKnobPanel() {
		this.setOpaque(false);
		instance = this;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}
	
	/**
	 * Fuegt Knob hinzu.
	 * @param effectKnob Knob
	 */
	public static void addKnob(final EffectKnob effectKnob) {
		effectKnob.addMouseListener(new EffectKnobMouseAdapter());
		instance.add(effectKnob);
		instance.revalidate();
	}
	
	/**
	 * Loescht Knob raus.
	 * @param effectKnob Knob
	 */
	public static void removeKnob(final EffectKnob effectKnob) {
		instance.remove(effectKnob);
		instance.revalidate();
	}
	
	
	
	
}
