package game.skills;

/**
 * Ein aktiver Skill wird vom Character ausgefuehrt.
 * @author Tristan
 *
 */
public abstract class ActiveSkill extends Skill {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -8665845320575271763L;

	/**
	 * Konstruktor der den Namen definiert.
	 * @param setName Name des Skills
	 */
	public ActiveSkill(final String setName) {
		super(setName);
	}

}
