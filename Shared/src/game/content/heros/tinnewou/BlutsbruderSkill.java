package game.content.heros.tinnewou;

import game.attributes.AttributeValueList;
import game.objects.NonStatic;

/**
 * Blutsbruder, der Skill.
 * @author Alex
 *
 */
public class BlutsbruderSkill extends game.skills.AimedOnNonStaticSkill {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -5421387282637164346L;

	/**
	 * Konstruktor. Initialisiert die effects-boolean.
	 */
	public BlutsbruderSkill() {
		super("Blutsbruder");

		this.effectsSelf = false;
		this.effectsHeroOnly = false;
		this.effectsAllies = true;
		this.effectsEnemies = false;

		this.radius = 0;

		this.range = 5;

		this.cast = 1 * 1000;
		
		this.cooldown = 5 * 1000; 
		this.mana = 100;
		
		this.setImageURL("usercontrols", "skill", "tinnewou/blutsbruder");
	}
	
	@Override
	public void giveEffect(final NonStatic executer, final NonStatic effectedNonStatic) {
		// Kreiert den entsprechenden Effekt und ruft seine Methode ready() auf.
		new BlutsbruderEffect(effectedNonStatic, executer.getSkillLevel(this)).ready();
		//TODO funktioniert nicht.
	}

	@Override
	public AttributeValueList getEffectsAttributeValueList(final NonStatic executer) {
		return BlutsbruderEffect.getAttributeValueListWithSkillLevel(executer.getSkillLevel(this), null);
	}
}
