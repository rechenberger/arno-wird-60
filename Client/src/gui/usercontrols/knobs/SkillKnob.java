package gui.usercontrols.knobs;

import game.skills.Skill;
import gui.Colors;
import java.awt.Dimension;
import java.awt.Graphics;

import module.ModuleHandler;

/**
 * Zeichnet den Knopf eines Skills.
 * @author Tristan
 *
 */
public class SkillKnob extends Knob {

	/**
	 * SerialUID.
	 */
	private static final long serialVersionUID = -6935733560058608905L;
	
	/**
	 * Skill, der durch den Knob verwaltet wird.
	 */
	private Skill skill;
	
	/**
	 * Setzt die Farbe und das Bild des Skills, sowie die maximale Groesse.
	 * @param icon URL, wo das Bild gespeichert ist
	 */
	public SkillKnob(final String icon) {
		super(Colors.CYAN, ModuleHandler.GUI.getImageSet().getImage(icon), true);
		this.setAnchorRight(false);
		this.maxSize = 40;
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(120, 120);
	}
	
	@Override
	public Dimension getMinimumSize() {
		return new Dimension(120, 120);
	}
	
	@Override
	public void paintComponent(final Graphics g) {
		super.paintComponent(g);
		float cooldownRatio = skill.getCooldownRatio(ModuleHandler.MATCH.getMyHero());
		this.setArcAngle(1.0f - cooldownRatio);
	}
	
	/**
	 * Setzt den zu verwaltenen Skill.
	 * @param sk Welcher Skill soll verwaltet werden?
	 */
	public void setSkill(final Skill sk) {
		this.skill = sk;
		this.setName(this.skill.getName());
		if (!sk.isShownInGui()) {
			this.arcColor = Colors.BLACK_08;
		}
	}
	
	/**
	 * Liefert den Skill zurueck der verwaltet wird.
	 * @return Verwalteter Skill
	 */
	public Skill getSkill() {
		return this.skill;
	}
	
	@Override
	protected String getValueText() {
		return "Level " + ModuleHandler.MATCH.getMyHero().getSkillLevel(this.getSkill());
	}
}
