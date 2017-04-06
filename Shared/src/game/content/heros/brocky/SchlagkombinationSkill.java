package game.content.heros.brocky;

import game.attributes.AttributeValueList;
import game.objects.NonStatic;

/**
 * Schlagkombination, der Skill.
 * @author Alex
 *
 */
public class SchlagkombinationSkill extends game.skills.AimedOnNonStaticSkill {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -2593697526829849152L;

	/**
	 * Konstruktor. Initialisiert die effects-boolean.
	 */
	public SchlagkombinationSkill() {
		super("Schlag<br>kombination");
		
		this.effectsSelf = false;
		this.effectsHeroOnly = false;
		this.effectsAllies = false;
		this.effectsEnemies = true;
		
		this.radius = 0;
		this.range = 1;
		this.cast = 1 * 1000;
		this.cooldown = 5 * 1000; 
		this.mana = 100;
		
		this.setImageURL("usercontrols", "skill", "brocky/schlag");
	}
	
	@Override
	public void giveEffect(final NonStatic executer, final NonStatic effectedNonStatic) {
		// Kreiert den entsprechenden Effekt und ruft seine Methode ready() auf.
		new SchlagkombinationEffect(effectedNonStatic, executer.getSkillLevel(this)).ready();
	}

	@Override
	public AttributeValueList getEffectsAttributeValueList(final NonStatic executer) {
		return SchlagkombinationEffect.getAttributeValueListWithSkillLevel(executer.getSkillLevel(this), null);
	}
}
