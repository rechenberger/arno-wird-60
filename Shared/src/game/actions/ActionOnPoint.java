package game.actions;

import game.objects.NonStatic;
import game.skills.AimedOnPointSkill;

import java.awt.Point;
/**
 * Auszufuehrende Aktion, die auf ein anderes NonStatic verweist.
 *
 *  @author Tristan
 */
public class ActionOnPoint extends Action {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 3382890753390706632L;
	/**
	 * serialVersionUID.
	 */
	private Point target;
	
	/** 
	 *  Konstrukteur.
	 *  @param setExecuter Ausfuehrer
	 *  @param setSkill Auszufuehrender Skill
	 *  @param setTarget Punkt auf den der Skill gewirkt wird
	 */
	public ActionOnPoint(final NonStatic setExecuter, final AimedOnPointSkill setSkill, final Point setTarget) {
		super(setExecuter, setSkill);
		this.target = setTarget;
	}

	@Override
	public Point getTargetPoint() {
		return this.target;
	}


	@Override
	public void executeSkill() {
		((AimedOnPointSkill) this.getSkill()).execute(this.getExecuter(), this.getTargetPoint());
	}
}
