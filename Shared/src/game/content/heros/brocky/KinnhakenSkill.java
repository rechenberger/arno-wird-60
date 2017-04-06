package game.content.heros.brocky;

import game.attributes.AttributeValueList;
import game.objects.NonStatic;

/**
 * Kinnhaken des Todes, der Skill.
 * @author Tristan
 *
 */
public class KinnhakenSkill extends game.skills.AimedOnNonStaticSkill {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -2593697526829849152L;

	/**
	 * Konstruktor. Initialisiert die effects-boolean.
	 */
	public KinnhakenSkill() {
		super("Kinnhaken<br>des Todes");
		this.effectsSelf = false;
		this.effectsHeroOnly = false;
		this.effectsAllies = false;
		this.effectsEnemies = true;
		
		this.radius = 0;
		this.range = 1;
		this.cast = 1 * 1000;
		this.cooldown = 5 * 1000; 
		this.mana = 100;
		this.setImageURL("usercontrols", "skill", "brocky/kinnhaken");
	}
	
	@Override
	public void giveEffect(final NonStatic executer, final NonStatic effectedNonStatic) {
		// Kreiert den entsprechenden Effekt und ruft seine Methode ready() auf.
		new KinnhakenEffect(effectedNonStatic, executer.getSkillLevel(this)).ready();
	}

	@Override
	public AttributeValueList getEffectsAttributeValueList(final NonStatic executer) {
		return KinnhakenEffect.getAttributeValueListWithSkillLevel(executer.getSkillLevel(this), null);
	}
}
