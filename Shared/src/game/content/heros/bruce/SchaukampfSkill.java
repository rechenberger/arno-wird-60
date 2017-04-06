package game.content.heros.bruce;

import game.attributes.AttributeValueList;
import game.objects.NonStatic;
import game.skills.AimedOnPointSkill;

/**
 * Schaukampf, der Skill.
 * @author Alex
 *
 */
public class SchaukampfSkill extends AimedOnPointSkill {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -7886395307232944132L;

	/**
	 * Konstruktor, initialisiert Skillparameter.
	 */
	public SchaukampfSkill() {
		super("Schaukampf");

		this.effectsSelf = false;
		this.effectsHeroOnly = false;
		this.effectsAllies = false;
		this.effectsEnemies = true;

		this.radius = 5;

		this.range = 1;

		this.cast = 1 * 1000;
		
		this.cooldown = 5 * 1000; 
		this.mana = 100;
	}
	
	@Override
	public void giveEffect(final NonStatic executer, final NonStatic effectedNonStatic) {
		// Kreiert den entsprechenden Effekt und ruft seine Methode ready() auf.
		new SchaukampfEffect(effectedNonStatic, executer.getSkillLevel(this)).ready();
	}
	
	@Override
	public AttributeValueList getEffectsAttributeValueList(final NonStatic executer) {
		return new AttributeValueList();
	}

}
