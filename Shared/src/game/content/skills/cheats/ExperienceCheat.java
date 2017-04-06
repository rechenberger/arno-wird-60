package game.content.skills.cheats;

import game.content.effects.direct.Experience;
import game.objects.NonStatic;

/**
 * 
 * @author Tristan
 */
public final class ExperienceCheat extends CheatSkill {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 6009441489220739815L;
	
	/**
	 * Instanz.
	 */
	private static ExperienceCheat instance = new ExperienceCheat();
	
	/**
	 * @return Instanz
	 */
	public static ExperienceCheat getInstance() {
		return instance;
	}
	
	/**
	 * Konstrukor.
	 */
	private ExperienceCheat() {
		super("Erfahrungs Cheat");
	}
	
	@Override
	public void giveEffect(final NonStatic executer, final NonStatic effectedNonStatic) {
		new Experience(effectedNonStatic, 100).ready();
	}

}
