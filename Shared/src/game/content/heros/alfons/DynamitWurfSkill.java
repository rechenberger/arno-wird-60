package game.content.heros.alfons;

import game.attributes.AttributeValueList;
import game.objects.NonStatic;
import game.skills.AimedOnNonStaticSkill;

/**
 * Dynamit Wurf, der Skill.
 * @author Alex
 *
 */
public class DynamitWurfSkill extends AimedOnNonStaticSkill {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -6619727348192791730L;
	
	/**
	 * Konstruktor.
	 */
	public DynamitWurfSkill() {
		super("Dynamit<br>Wurf");

		this.effectsSelf = false;
		this.effectsHeroOnly = false;
		this.effectsAllies = false;
		this.effectsEnemies = true;

		this.radius = 0;

		this.range = 7;

		this.cast = 1 * 1000;
		
		this.cooldown = 5 * 1000; 
		this.mana = 100;
		
		this.setImageURL("usercontrols", "skill", "alfons/dynamit");
	}

	@Override
	public void giveEffect(final NonStatic executer, final NonStatic effectedNonStatic) {
		// Kreiert den entsprechenden Effekt und ruft seine Methode ready() auf.
		new DynamitWurfEffect(effectedNonStatic, executer.getSkillLevel(this)).ready();
	}

	@Override
	public AttributeValueList getEffectsAttributeValueList(final NonStatic executer) {
		return DynamitWurfEffect.getAttributeValueListWithSkillLevel(executer.getSkillLevel(this), null);
	}

}
