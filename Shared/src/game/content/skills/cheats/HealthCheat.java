package game.content.skills.cheats;

import game.attributes.Attribute;
import game.content.effects.direct.Heal;
import game.objects.NonStatic;

/**
 * Cheat um Leben vollstaendig wiederherzustellen.
 * @author Tristan
 */
public final class HealthCheat extends CheatSkill {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 6609441789220739815L;
	
	/**
	 * Instanz.
	 */
	private static HealthCheat instance = new HealthCheat();
	
	/**
	 * @return Instanz
	 */
	public static HealthCheat getInstance() {
		return instance;
	}

	/**
	 * Konstrukor.
	 */
	private HealthCheat() {
		super("Leben Cheat");
	}
	
	
	@Override
	public void giveEffect(final NonStatic executer, final NonStatic effectedNonStatic) {
		new Heal(effectedNonStatic, effectedNonStatic.getAttributeValue(Attribute.maxHealth)).ready();
	}

}
