package game.actions;

import java.awt.Point;

import game.objects.NonStatic;
import game.skills.Skill;
import game.skills.NotAimedSkill;

/**
 * Aktion die auf nichts zielts.
 * @author Tristan
 *
 */
public class ActionNotAimed extends Action {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2639805424242693665L;
	
	/** 
	 *  Konstrukteur.
	 *  @param setExecuter Ausfuehrer
	 *  @param setSkill Auszufuehrender Skill
	 */
	public ActionNotAimed(final NonStatic setExecuter, final Skill setSkill) {
		super(setExecuter, setSkill);
	}

	@Override
	public Point getTargetPoint() {
		return this.getExecuter().getCoord();
	}

	@Override
	public void executeSkill() {
		((NotAimedSkill) this.getSkill()).execute(this.getExecuter());
	}
}
