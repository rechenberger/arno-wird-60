package game.content.heros.tinnewou;

import game.attributes.AttributeValueList;
import game.objects.NonStatic;
import game.skills.NotAimedSkill;


/**
 * Litschi, der Skill.
 * @author Tristan
 *
 */
public class LitschiSkill extends NotAimedSkill {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 5404685680733439522L;

	/**
	 * Konstruktor. Initialisiert die effects-boolean.
	 */
	public LitschiSkill() {
		super("Auf Litschi<br>reiten");
		this.effectsSelf = true;
		this.effectsHeroOnly = true;
		this.effectsAllies = true;
		this.effectsEnemies = false;
		this.radius = 0;
		this.range = 0;
		this.cast = 1 * 1000;
//		this.cooldown = 10 * 1000; 
		
		this.setImageURL("usercontrols", "skill", "tinnewou/litschi");
	}
	
	@Override
	public void giveEffect(final NonStatic executer, final NonStatic effectedNonStatic) {
		// Kreiert den entsprechenden Effekt und ruft seine Methode ready() auf.
		new LitschiEffect(effectedNonStatic, executer.getSkillLevel(this)).ready();
	}
	
	/**
	 * Gibt die AttributeValue List des Effektes zurueck, welcher dieser Skill ausfuehrt.
	 * @param executer Der Ausfuehrer des Skills
	 * @return Die AtributeValueList
	 */
	public AttributeValueList getEffectsAttributeValueList(final NonStatic executer) {
		return LitschiEffect.getAttributeValueListWithSkillLevel(executer.getSkillLevel(this), null);

	}

}

