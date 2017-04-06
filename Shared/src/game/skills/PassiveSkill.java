package game.skills;

import settings.GlobalSettings;
import game.objects.NonStatic;

/**
 * Ein Passiver Skill wird automatisch ausgefuehrt. Vergleichbar mit einem aktiven Effekt.
 * @author Tristan
 *
 */
public abstract class PassiveSkill extends Skill {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 7059779175351847027L;
	
	/**
	 * Konstruktor der den Namen definiert.
	 * @param setName Name des Skills
	 */
	public PassiveSkill(final String setName) {
		super(setName);
	}
	
	/**
	 * Fuehrt den Skill aus.
	 * @param executer Ausfuehrender NonStatic
	 */
	public void execute(final NonStatic executer) {
		super.execute(executer, executer.getCoord());
		this.cooldown(executer);
	}
	
	@Override
	public int getCooldown(final NonStatic executer) {
		return GlobalSettings.MATCH_PASSIVE_SKILLS_TICKER;
	}
}
