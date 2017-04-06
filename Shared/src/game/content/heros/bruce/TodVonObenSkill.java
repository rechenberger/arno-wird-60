package game.content.heros.bruce;

import game.attributes.AttributeValueList;
import game.objects.NonStatic;

/**
 * Tod von Oben, der Skill.
 * @author Alex
 *
 */
public class TodVonObenSkill extends game.skills.AimedOnNonStaticSkill {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -3980834867846681069L;

	/**
	 * Konstruktor. Initialisiert die effects-boolean.
	 */
	public TodVonObenSkill() {
		super("Tod<br>von Oben");

		this.effectsSelf = true;
		this.effectsHeroOnly = false;
		this.effectsAllies = false;
		this.effectsEnemies = true;

		this.radius = 0;

		this.range = 5;

		this.cast = 1 * 1000;
		
		this.cooldown = 5 * 1000; 
		this.mana = 100;
	}
	
	@Override
	public void giveEffect(final NonStatic executer, final NonStatic effectedNonStatic) {
		// Kreiert den entsprechenden Effekt und ruft seine Methode ready() auf.
		new TodVonObenEffect(effectedNonStatic, executer.getSkillLevel(this)).ready();
	}

	@Override
	public AttributeValueList getEffectsAttributeValueList(final NonStatic executer) {
		return TodVonObenEffect.getAttributeValueListWithSkillLevel(executer.getSkillLevel(this), null);
	}
}
