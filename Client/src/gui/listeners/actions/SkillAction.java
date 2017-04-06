package gui.listeners.actions;

import game.actions.ActionNotAimed;
import game.skills.NotAimedSkill;
import game.skills.Skill;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import module.ModuleHandler;

/**
 * Fuehrt CheatSkill aus.
 * @author Tristan
 *
 */
public class SkillAction extends AbstractAction {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -6022929690152980532L;
	
	/**
	 * CheatSkill der ausgefuehrt werden soll.
	 */
	private Skill cheatSkill;
	
	/**
	 * @param cheatSkill CheatSkill der ausgefuehrt werden soll.
	 */
	public SkillAction(final Skill cheatSkill) {
		this.cheatSkill = cheatSkill;
	}

	@Override
	public void actionPerformed(final ActionEvent arg0) {
		if (this.cheatSkill instanceof NotAimedSkill) {
			new ActionNotAimed(ModuleHandler.MATCH.getMyHero(), this.cheatSkill).plan();
		} else {
    	  	ModuleHandler.GUI.setNextSkillToUse(this.cheatSkill);
		} 
	}

}
