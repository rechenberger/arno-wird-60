package gui.usercontrols.layout.bottom;


import game.skills.Skill;
import gui.listeners.SkillKnobMouseAdapter;
import gui.usercontrols.knobs.SkillKnob;

import javax.swing.JPanel;

import module.ModuleHandler;

/**
 * Zeichnet die Skill Buttons.
 * @author Alex
 *
 */
public class SkillKnobPanel extends JPanel {
	
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = -3523937648745147065L;
	
	/**
	 * Erstellt die Skillbuttons und setzt einen Listener darauf.
	 */
	public SkillKnobPanel() {
		this.setOpaque(false);
		
		for (Skill skill : ModuleHandler.MATCH.getMyHero().getSpecialSkills()) {
			SkillKnob knob = new SkillKnob(skill.getImageURL());
			knob.setSkill(skill);
			knob.addMouseListener(new SkillKnobMouseAdapter());
			this.add(knob);
		}		
	}
}
