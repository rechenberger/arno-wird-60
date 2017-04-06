package gui.listeners;

import game.actions.ActionNotAimed;
import game.objects.GameObject;
import game.skills.ActiveSkill;
import game.skills.NotAimedSkill;
import gui.usercontrols.UserControls;
import gui.usercontrols.knobs.SkillKnob;

import java.awt.event.MouseEvent;

import module.ModuleHandler;

/**
 * Horcht in SkillKnobs auf Mauseingaben.
 * @author Alex
 *
 */
public class SkillKnobMouseAdapter extends EffectKnobMouseAdapter {

	@Override
	public void mouseClicked(final MouseEvent event) {
		if (event.getButton() == MouseEvent.BUTTON1) {
			if (event.getComponent() instanceof SkillKnob) {
				SkillKnob knob = (SkillKnob) event.getComponent();
				if (knob.getSkill() instanceof NotAimedSkill) {
					new ActionNotAimed(ModuleHandler.MATCH.getMyHero(), knob.getSkill()).plan();
				} else if (knob.getSkill() instanceof ActiveSkill) {
	        	  	ModuleHandler.GUI.setNextSkillToUse(knob.getSkill());
				}
			}
		} else if (event.getButton() == MouseEvent.BUTTON3) {
			if (event.getComponent() instanceof SkillKnob) {
				SkillKnob knob = (SkillKnob) event.getComponent();
				ModuleHandler.MATCH.getMyHero().incSkillLevel(knob.getSkill());
			}
		}
	}
	
	@Override
	public void mouseEntered(final MouseEvent event) {
		if (event.getComponent() instanceof SkillKnob) {
			SkillKnob knob = (SkillKnob) event.getComponent();
			ModuleHandler.GUI.getPanel(UserControls.class).setNonStaticInfoPanel((GameObject) knob.getSkill());
			((SkillKnob) event.getComponent()).setSmall(false);
		}
	}
	@Override
	public void mouseExited(final MouseEvent event) {
		ModuleHandler.GUI.getPanel(UserControls.class).setNonStaticInfoPanel(null);
		((SkillKnob) event.getComponent()).setSmall(true);
	}
}
