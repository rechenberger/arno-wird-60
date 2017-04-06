package game.skills;

import game.objects.NonStatic;

/**
 * 
 * @author Tristan
 *
 */
public abstract class NotAimedSkill extends ActiveSkill {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -2726680754998284833L;

	/**
	 * Konstruktor.
	 * @param setName Name des Skills
	 */
	public NotAimedSkill(final String setName) {
		super(setName);
	}
	
	/**
	 * Fuehrt den Skill aus.
	 * @param executer Ausfuehrender NonStatic
	 */
	public void execute(final NonStatic executer) {
		super.execute(executer, executer.getCoord());
	}
	

}
