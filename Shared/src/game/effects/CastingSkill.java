package game.effects;

import game.objects.NonStatic;
import game.skills.Skill;

/**
 * Grade dabei einen Skill zu casten.
 * @author Tristan
 *
 */
public class CastingSkill extends ActiveEffect {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -201580561837981690L;

	/**
	 * Konstruktor.
	 * @param effects betroffenes NonStatic.
	 * @param skill Skill.
	 */
	public CastingSkill(final NonStatic effects, final Skill skill) {
		super(effects);
		
	}

}
