package game.content.heros.geronimo;

import game.attributes.AttributeValueList;
import game.objects.NonStatic;
import game.skills.NotAimedSkill;

/**
 * Abendmahl, der Skill.
 * @author Tristan
 *
 */
public class AbendmahlSkill extends NotAimedSkill {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -2593697526829849152L;

	/**
	 * Konstruktor. Initialisiert die Werte.
	 */
	public AbendmahlSkill() {
		super("Abendmahl");
			
		this.effectsSelf = true;
		this.effectsHeroOnly = false;
		this.effectsAllies = true;
		this.effectsEnemies = true;
			
		this.radius = 10;
		this.range = 0;
		this.mana = 100;
		this.cast =  300;
		this.cooldown = 5 * 1000; 
		
		this.setImageURL("usercontrols", "skill", "geronimo/abendmahl");
	}
	
	@Override
	public void giveEffect(final NonStatic executer, final NonStatic effectedNonStatic) {
		// Kreiert den entsprechenden Effekt und ruft seine Methode ready() auf.
		boolean isAlly = executer.getFraction().equals(effectedNonStatic.getFraction());
		new AbendmahlEffect(effectedNonStatic, executer.getSkillLevel(this), isAlly).ready();
	}
	
	/**
	 * Gibt die AttributeValue List des Effektes zurueck, welcher dieser Skill ausfuehrt.
	 * @param executer Der Ausfuehrer des Skills
	 * @return Die AtributeValueList
	 */
	public AttributeValueList getEffectsAttributeValueList(final NonStatic executer) {		
		return AbendmahlEffect.getAttributeValueListWithSkillLevel(executer.getSkillLevel(this), null);
	}
}
