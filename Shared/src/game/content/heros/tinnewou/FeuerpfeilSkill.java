package game.content.heros.tinnewou;

import game.attributes.AttributeValueList;
import game.objects.NonStatic;
import game.skills.AimedOnNonStaticSkill;

/**
 * Feuerpfeil, der Skill.
 * @author Alex
 *
 */
public class FeuerpfeilSkill extends AimedOnNonStaticSkill {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -8594038043393063353L;

	/**
	 * Konstruktor. Initialisiert die effects-boolean.
	 */
	public FeuerpfeilSkill() {
		super("Feuerpfeil");

		this.effectsSelf = false;
		this.effectsHeroOnly = false;
		this.effectsAllies = false;
		this.effectsEnemies = true;

		this.radius = 0;

		this.range = 7;

		this.cast = 1 * 1000;
		
		this.cooldown = 5 * 1000; 
		this.mana = 100;
		
		this.setImageURL("usercontrols", "skill", "tinnewou/pfeil");
	}
	
	@Override
	public void giveEffect(final NonStatic executer, final NonStatic effectedNonStatic) {
		// Kreiert den entsprechenden Effekt und ruft seine Methode ready() auf.
		new FeuerpfeilEffect(effectedNonStatic, executer.getSkillLevel(this)).ready();
	}

	@Override
	public AttributeValueList getEffectsAttributeValueList(final NonStatic executer) {
		return FeuerpfeilEffect.getAttributeValueListWithSkillLevel(executer.getSkillLevel(this), null);

	}
}
