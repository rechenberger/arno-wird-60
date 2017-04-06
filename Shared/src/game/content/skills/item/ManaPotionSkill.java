package game.content.skills.item;

import game.objects.NonStatic;

/**
 * Manatrank.
 * @author Tristan
 *
 */
public final class ManaPotionSkill extends game.skills.ItemSkill {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 695647284282168570L;

	/**
	 * Instanz.
	 */
	private static ManaPotionSkill instance = new ManaPotionSkill();
	
	/**
	 * @return Instanz
	 */
	public static ManaPotionSkill getInstance() {
		return instance;
	}
	
	/**
	 * Konstruktor.
	 * @param
	 */
	private ManaPotionSkill() {
		super();
		this.effectsAllies = true;
		this.effectsEnemies = false;
		this.effectsHeroOnly = false;
		this.effectsSelf = true;
		this.radius = 0;
	}
	
	@Override
	public void giveEffect(final NonStatic executer, final NonStatic effectedNonStatic) {
		new game.content.effects.direct.Mana(effectedNonStatic, 50).ready();
	}
	
}
