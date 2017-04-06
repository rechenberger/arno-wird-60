package game.content.heros.geronimo;

import game.attributes.AttributeValueList;
import game.content.effects.direct.Damage;
import game.objects.NonStatic;
import game.skills.AimedOnNonStaticSkill;

/**
 * Opfergabe, der Skill.
 * @author Alex
 *
 */
public class OpfergabeSkill extends AimedOnNonStaticSkill {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -194321989027929665L;
	
	/**
	 * Konstruktor.
	 */
	public OpfergabeSkill() {
		super("Opfergabe");
		this.effectsSelf = false;
		this.effectsHeroOnly = false;
		this.effectsAllies = false;
		this.effectsEnemies = true;
		
		this.radius = 1;
		this.range = 5;
		this.cast = 1 * 1000;
		this.cooldown = 5 * 1000;
		this.mana = 100;
		
		this.description = "Opfert 10 Lebenspunkte<br>pro betroffenem Gegner";
		
		this.setImageURL("usercontrols", "skill", "geronimo/opfer");
	}

	@Override
	public void giveEffect(final NonStatic executer,
			final NonStatic effectedNonStatic) {
		// Kreiert den entsprechenden Effekt und ruft seine Methode ready() auf.
		new OpfergabeEffect(effectedNonStatic,
				executer.getSkillLevel(this)).ready();
		new Damage(executer, 10).ready();
	}

	@Override
	public AttributeValueList getEffectsAttributeValueList(
			final NonStatic executer) {
		return OpfergabeEffect.getAttributeValueListWithSkillLevel(
				executer.getSkillLevel(this), null);
	}

}
