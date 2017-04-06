package game.skills;


import game.objects.NonStatic;

/**
 * Ein Skill der auf ein anderes NonStatic zielt.
 * @author Tristan
 */
public abstract class AimedOnNonStaticSkill extends ActiveSkill {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 4508172555061769202L;

	/**
	 * Konstruktor.
	 * @param setName Name des Skills.
	 */
	public AimedOnNonStaticSkill(final String setName) {
		super(setName);
	}

	/**
	 * Der Skill wird ausgefuehrt.
	 * @param executer Ausfuehrer
	 * @param target Ziel.
	 */
	public void execute(final NonStatic executer, final NonStatic target) {
		super.execute(executer, target.getCoord());
	}
}
