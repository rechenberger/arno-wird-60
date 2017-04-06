package game.content.heros.alfons;

import game.attributes.AttributeValueList;
import game.objects.NonStatic;

/**
 * Mobbel Preis, der Skill.
 * @author Alex
 *
 */
public class MobbelPreisSkill extends game.skills.AimedOnNonStaticSkill {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2697978598165335147L;

	/**
	 * Konstruktor. Initialisiert die effects-boolean.
	 */
	public MobbelPreisSkill() {
		super("Mobbel<br>Preis");

		this.effectsSelf = false;
		this.effectsHeroOnly = false;
		this.effectsAllies = true;
		this.effectsEnemies = false;

		this.radius = 0;

		this.range = 5;

		this.cast = 1 * 1000;
		
		this.cooldown = 5 * 1000; 
		this.mana = 100;
		
		this.setImageURL("usercontrols", "skill", "alfons/mobbelpreis");
	}
	
	@Override
	public void giveEffect(final NonStatic executer, final NonStatic effectedNonStatic) {
		// Kreiert den entsprechenden Effekt und ruft seine Methode ready() auf.
		new MobbelPreisEffect(effectedNonStatic, executer.getSkillLevel(this)).ready();
	}

	@Override
	public AttributeValueList getEffectsAttributeValueList(final NonStatic executer) {
		return MobbelPreisEffect.getAttributeValueListWithSkillLevel(executer.getSkillLevel(this), null);
	}
}
