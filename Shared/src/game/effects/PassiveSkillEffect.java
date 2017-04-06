package game.effects;

import settings.GlobalSettings;
import game.objects.NonStatic;

/**
 * Permamenter Effekt, der von Passiven Skills ausgeloest wird.
 * @author Tristan
 *
 */
public class PassiveSkillEffect extends DurationEffect {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 7103450168166591711L;

	/**
	 * Konstrukor. ready() nicht vergessen.
	 * @param effects 
	 */
	public PassiveSkillEffect(final NonStatic effects) {
		super(effects, GlobalSettings.MATCH_PASSIVE_SKILLS_TICKER);
	}

}
