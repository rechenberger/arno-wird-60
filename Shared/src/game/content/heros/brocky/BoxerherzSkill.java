package game.content.heros.brocky;

import game.attributes.AttributeValueList;
import game.objects.NonStatic;
import game.skills.PassiveSkill;

/**
 * Boxerherz, der Skill.
 * @author Alex
 *
 */
public class BoxerherzSkill extends PassiveSkill {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -1815855805789597853L;

	/**
	 * Konstruktor. Initialisiert die effects-boolean.
	 */
	public BoxerherzSkill() {
		super("Boxerherz");
		this.effectsSelf = true;
		this.effectsHeroOnly = true;
		this.effectsAllies = true;
		this.effectsEnemies = false;
		
		this.radius = 0;
		this.range = 0;
		
		this.setImageURL("usercontrols", "skill", "brocky/boxerherz");
	}
	
	@Override
	public void execute(final NonStatic executer) {
		if (matchModule.getMap().getFightablesInCircle(executer.getCoord(), 10).size() == 2) {
		// Kreiert den entsprechenden Effekt und ruft seine Methode ready() auf.
		new BoxerherzEffect(executer, executer.getSkillLevel(this)).ready();
		}
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
