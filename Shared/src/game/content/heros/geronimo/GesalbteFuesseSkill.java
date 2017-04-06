package game.content.heros.geronimo;

import game.attributes.AttributeValueList;
import game.objects.NonStatic;
import game.skills.PassiveSkill;

/**
 * Gesalbte Fuesse, der Skill.
 * @author Alex
 *
 */
public class GesalbteFuesseSkill extends PassiveSkill {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 8676207126716075430L;
	
	/**
	 * Konstruktor.
	 */
	public GesalbteFuesseSkill() {
		super("Gesalbte<br>F\u00fc\u00dfe");
		this.effectsSelf = true;
		this.effectsHeroOnly = true;
		this.effectsAllies = true;
		this.effectsEnemies = false;
		this.radius = 0;
		this.range = 10;
		
		this.setImageURL("usercontrols", "skill", "geronimo/fuesse");
	}
	
	@Override
	public void giveEffect(final NonStatic executer, final NonStatic effectedNonStatic) {
		// Kreiert den entsprechenden Effekt und ruft seine Methode ready() auf.
		new GesalbteFuesseEffect(effectedNonStatic).ready();
	}
	
	/**
	 * Gibt die AttributeValue List des Effektes zurueck, welcher dieser Skill ausfuehrt.
	 * @param executer Der Ausfuehrer des Skills
	 * @return Die AtributeValueList
	 */
	public AttributeValueList getEffectsAttributeValueList(final NonStatic executer) {		
		return GesalbteFuesseEffect.getAttributeValueListWithSkillLevel(executer.getSkillLevel(this), null);
	}
}
