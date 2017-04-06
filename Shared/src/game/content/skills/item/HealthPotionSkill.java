package game.content.skills.item;

import game.content.effects.direct.Heal;
import game.objects.NonStatic;

/**
 * Heiltrank.
 * @author Tristan
 *
 */
public final class HealthPotionSkill extends game.skills.ItemSkill {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 695647284282168578L;

	/**
	 * Instanz.
	 */
	private static HealthPotionSkill instance = new HealthPotionSkill();
	
	/**
	 * @return Instanz
	 */
	public static HealthPotionSkill getInstance() {
		return instance;
	}
	
	/**
	 * Konstruktor.
	 * @param
	 */
	private HealthPotionSkill() {
		super();
		this.effectsAllies = true;
		this.effectsEnemies = false;
		this.effectsHeroOnly = true;
		this.effectsSelf = true;
		this.radius = 0;
	}
	
	@Override
	public void giveEffect(final NonStatic executer, final NonStatic effectedNonStatic) {
		new Heal(effectedNonStatic, 50).ready();
	}
	
}
