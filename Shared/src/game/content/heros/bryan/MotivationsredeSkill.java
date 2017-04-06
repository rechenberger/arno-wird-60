package game.content.heros.bryan;

import game.attributes.Attribute;
import game.attributes.AttributeValueList;
import game.objects.NonStatic;
import game.skills.NotAimedSkill;


/**
 * Motivationsrede, der Skill.
 * @author Alex
 *
 */
public class MotivationsredeSkill extends NotAimedSkill {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor. Initialisiert die effects-boolean.
	 */
	public MotivationsredeSkill() {
		super("Motivations<br>rede");
		
		this.effectsSelf = true;
		this.effectsHeroOnly = false;
		this.effectsAllies = true;
		this.effectsEnemies = false;
		
		this.radius = 10;
		this.range = 0;
		this.cast = 1 * 1000;
		this.cooldown = 20 * 1000;
		this.mana = 80;
		
		this.setImageURL("usercontrols", "skill", "bryan/rede");
		
		this.description = "Bei Verb\u00fcndeten im Umkreis erh\u00f6ht sich die Verteidigung und der Schaden, den sie verursachen. Je weniger Leben Private Bryan hat, desto staerker wirkt der Effekt.";
	}
	
	@Override
	public void giveEffect(final NonStatic executer, final NonStatic effectedNonStatic) {
		// Kreiert den entsprechenden Effekt und ruft seine Methode ready() auf.
		new MotivationsredeEffect(effectedNonStatic, executer.getSkillLevel(this), ((float) executer.getAttributeValue(Attribute.currentHealth)) / ((float) executer.getAttributeValue(Attribute.maxHealth))).ready();
	}
	
	/**
	 * Gibt die AttributeValue List des Effektes zurueck, welcher dieser Skill ausfuehrt.
	 * @param executer Der Ausfuehrer des Skills
	 * @return Die AtributeValueList
	 */
	public AttributeValueList getEffectsAttributeValueList(final NonStatic executer) {		
		return MotivationsredeEffect.getAttributeValueListWithSkillLevel(executer.getSkillLevel(this), null, ((float) executer.getAttributeValue(Attribute.currentHealth)) / ((float) executer.getAttributeValue(Attribute.maxHealth)));

	}

}

