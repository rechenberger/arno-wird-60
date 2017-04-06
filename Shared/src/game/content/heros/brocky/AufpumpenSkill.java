package game.content.heros.brocky;

import game.attributes.AttributeValueList;
import game.objects.NonStatic;
import game.skills.NotAimedSkill;

/**
 * Aufpumpen, der Skill.
 * @author Tristan
 *
 */
public class AufpumpenSkill extends NotAimedSkill {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -2593697526891849152L;

	/**
	 * Konstruktor. Initialisiert die effects-boolean.
	 */
	public AufpumpenSkill() {
		// Der Skill heisst Aufpumpen.
		super("Aufpumpen");
		// Nur er selber wird betroffen.
		this.effectsSelf = true;
		this.effectsHeroOnly = true;
		this.effectsAllies = true;
		this.effectsEnemies = false;
		// 0 Felder Radius
		this.radius = 0;
		// 0 Felder Reichweite.
		this.range = 0;
		// 0 Sekunden Castzeit
		this.cast = 0 * 1000;
		// 10 Sekunden Cooldown
		this.cooldown = 10 * 1000; 
		
		this.mana = 70;
		
		this.setImageURL("usercontrols", "skill", "brocky/aufpumpen");
	}
	
	@Override
	public void giveEffect(final NonStatic executer, final NonStatic effectedNonStatic) {
		// Kreiert den entsprechenden Effekt und ruft seine Methode ready() auf.
		new AufpumpenEffect(effectedNonStatic, executer.getSkillLevel(this)).ready();
	}
	
	/**
	 * Gibt die AttributeValue List des Effektes zurueck, welcher dieser Skill ausfuehrt.
	 * @param executer Der Ausfuehrer des Skills
	 * @return Die AtributeValueList
	 */
	public AttributeValueList getEffectsAttributeValueList(final NonStatic executer) {		
		return BoxerherzEffect.getAttributeValueListWithSkillLevel(executer.getSkillLevel(this), null);
	}
}
