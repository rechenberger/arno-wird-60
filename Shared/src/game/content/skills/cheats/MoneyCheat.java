package game.content.skills.cheats;

import game.content.effects.direct.Money;
import game.objects.NonStatic;

/**
 * 
 * @author Tristan
 */
public final class MoneyCheat extends CheatSkill {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 6609441489220739815L;
	
	/**
	 * Instanz.
	 */
	private static MoneyCheat instance = new MoneyCheat();
	
	/**
	 * @return Instanz
	 */
	public static MoneyCheat getInstance() {
		return instance;
	}
	
	/**
	 * Konstrukor.
	 */
	private MoneyCheat() {
		super("Geld Cheat");
	}
	
	@Override
	public void giveEffect(final NonStatic executer, final NonStatic effectedNonStatic) {
		new Money(effectedNonStatic, 1000).ready();
	}

}
