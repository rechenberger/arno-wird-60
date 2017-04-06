package game.actions;

import game.content.skills.Attack;
import game.objects.NonStatic;
import game.skills.Skill;

/**
 * Ein geplanter Angriff.
 * @author Tristan
 *
 */
public class AttackAction extends ActionOnNonStatic {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -8946820135515770641L;

	/**
	 * @param setExecuter Ausfuehrer
	 * @param setTarget Ziel des Angriffs
	 */
	public AttackAction(final NonStatic setExecuter, final NonStatic setTarget) {
		super(setExecuter, getAttackSkill(setExecuter), setTarget);
		this.endAfterExecution = false;
	}
	
	/**
	 * @param setExecuter Ausfuehrer
	 * @return Attack-Skill des Ausfuehrers.
	 */
	private static Attack getAttackSkill(final NonStatic setExecuter) {
		for (Skill skill : setExecuter.getSkills()) {
			if (skill instanceof Attack) {
				return (Attack) skill;
			}
		}
		System.out.println(setExecuter.toString() + " kann nich angreifen!");
		return null;
	}

}
