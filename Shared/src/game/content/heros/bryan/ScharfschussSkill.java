package game.content.heros.bryan;

import game.attributes.AttributeValueList;
import game.objects.NonStatic;
import game.skills.AimedOnNonStaticSkill;

/**
 * Gezielter Scharfschuss, der Skill.
 * @author Alex
 *
 */
public class ScharfschussSkill extends AimedOnNonStaticSkill {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1068378429158752699L;
	
	/**
	 * Konstruktor. Initialisiert die effects-boolean.
	 */
	public ScharfschussSkill() {
		super("Gezielter<br>Scharfschuss");

		this.effectsSelf = false;
		this.effectsHeroOnly = false;
		this.effectsAllies = false;
		this.effectsEnemies = true;

		this.radius = 0;
		this.range = 7;
		this.cast = 1 * 1000;
		this.cooldown = 5 * 1000; 
		this.mana = 100;
		
		this.setImageURL("usercontrols", "skill", "bryan/scharfschuss");
	}
	
	@Override
	public void giveEffect(final NonStatic executer, final NonStatic effectedNonStatic) {
		// Kreiert den entsprechenden Effekt und ruft seine Methode ready() auf.
		new ScharfschussEffect(effectedNonStatic, executer.getSkillLevel(this)).ready();
	}

	@Override
	public AttributeValueList getEffectsAttributeValueList(final NonStatic executer) {
		return ScharfschussEffect.getAttributeValueListWithSkillLevel(executer.getSkillLevel(this), null);
	}
}
