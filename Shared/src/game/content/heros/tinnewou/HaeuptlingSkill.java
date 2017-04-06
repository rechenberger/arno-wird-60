package game.content.heros.tinnewou;

import game.attributes.AttributeValueList;
import game.objects.NonStatic;
import game.skills.PassiveSkill;

/**
 * Haueptling der Apachen, der Skill.
 * @author Alex
 *
 */
public class HaeuptlingSkill extends PassiveSkill {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 4620041015093058154L;

	/**
	 * Konstruktor. Initialisiert die effects-boolean.
	 */
	public HaeuptlingSkill() {
		// Name des Skills.
		super("H\u00e4uptling<br>der Apachen");
		this.effectsAllies = true;
		this.effectsEnemies = false;
		this.effectsHeroOnly = false;
		this.effectsSelf = false;
		this.setImageURL("usercontrols", "skill", "tinnewou/haeuptling");
	}
	
//	@Override
//	public void execute(final NonStatic executer) {
//		LinkedList<NonStatic> inCircle = matchModule.getMap().getFightablesInCircle(executer.getCoord(), 10);
//		
//		for (NonStatic ns : inCircle) {
//			if (executer.getFraction() == ns.getFraction()) {
//				new HaeuptlingEffect(ns, executer.getSkillLevel(this)).ready();
//			}
//		}
//	}
	
	@Override
	public void giveEffect(final NonStatic executer, final NonStatic effectedNonStatic) {
		// Kreiert den entsprechenden Effekt und ruft seine Methode ready() auf.
		new HaeuptlingEffect(effectedNonStatic, executer.getSkillLevel(this)).ready();
	}
	
	/**
	 * Gibt die AttributeValue List des Effektes zurueck, welcher dieser Skill ausfuehrt.
	 * @param executer Der Ausfuehrer des Skills
	 * @return Die AtributeValueList
	 */
	public AttributeValueList getEffectsAttributeValueList(final NonStatic executer) {
		return HaeuptlingEffect.getAttributeValueListWithSkillLevel(executer.getSkillLevel(this), null);

	}
}
