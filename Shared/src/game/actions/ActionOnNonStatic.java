package game.actions;

import java.awt.Point;

import settings.DebugSettings;

import game.content.effects.active.Following;
import game.content.effects.active.Moving;
import game.objects.NonStatic;
import game.skills.AimedOnNonStaticSkill;
/**
 * Auszufuehrende Aktion, die auf ein anderes NonStatic verweist.
 *
 *  @author Tristan Rechenberger
 */
public class ActionOnNonStatic extends Action {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -6861969640132417078L;
	
	/**
	 * Ziel. (id)
	 */
	private int targetId;
	
	/** 
	 *  Konstrukteur.
	 *  @param setExecuter Ausfuehrer
	 *  @param setSkill Auszufuehrender Skill
	 *  @param setTarget Ziel
	 */
	public ActionOnNonStatic(final NonStatic setExecuter, final AimedOnNonStaticSkill setSkill, final NonStatic setTarget) {
		super(setExecuter, setSkill);
		this.targetId = setTarget.getId();
	}

	@Override
	public Point getTargetPoint() {
		return this.getTargetNonStatic().getCoord();
	}
	
	/**
	 * @return Ziel.
	 */
	protected NonStatic getTargetNonStatic() {
		NonStatic target = NonStatic.getById(targetId);
		if (!target.isAlive()) {
			if (DebugSettings.MATCH_PRINT_TRIES_TO_ATTACK_DEAD_GUY) {
				System.out.println(this.getExecuter() + " hat vesucht nen Toten anzugreifen, pfui!");
			}
			this.end();
		}
		return target;
	}


	@Override
	public void executeSkill() {
		((AimedOnNonStaticSkill) this.getSkill()).execute(this.getExecuter(), this.getTargetNonStatic());
	}
	
	@Override
	protected void getInRange() {
		if (!Following.hasEffect(getExecuter(), this.getTargetNonStatic())) {
			Moving.stopMoving(this.getExecuter());
			new Following(this.getExecuter(), this.getTargetNonStatic(), this.getRange()).ready();
		}
	}
}
