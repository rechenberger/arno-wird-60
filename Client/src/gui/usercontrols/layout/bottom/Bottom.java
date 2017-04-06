package gui.usercontrols.layout.bottom;


import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 * Zeichnet den unteren Rand der UserControls.
 * @author Sean
 *
 */
public class Bottom extends JPanel {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 425392250430858046L;

	/**
	 * FlowLayout ist linksseitig.
	 */
	public Bottom() {
		this.setOpaque(false);
		this.setLayout(new BorderLayout());

		
		JPanel characterWrapper = new JPanel();
		characterWrapper.setOpaque(false);
		characterWrapper.setLayout(new BoxLayout(characterWrapper, BoxLayout.Y_AXIS));
		characterWrapper.add(new InventoryPanel());
		characterWrapper.add(new MatchCharacterView());
		this.add(characterWrapper, BorderLayout.WEST);
		
		JPanel knobWrapper = new JPanel();
		knobWrapper.setOpaque(false);
		knobWrapper.setLayout(new FlowLayout(FlowLayout.LEFT));
		knobWrapper.add(new HealthKnob());
		knobWrapper.add(new ManaKnob());
		knobWrapper.add(new SkillKnobPanel());
		
		JPanel knobsWrapper = new JPanel();
		knobsWrapper.setOpaque(false);
		knobsWrapper.setLayout(new BorderLayout());
		knobsWrapper.add(knobWrapper, BorderLayout.SOUTH);
		this.add(knobsWrapper, BorderLayout.CENTER);
		
		this.add(new MiniMap(), BorderLayout.EAST);
	}
}
