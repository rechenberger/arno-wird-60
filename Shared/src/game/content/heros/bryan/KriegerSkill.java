package game.content.heros.bryan;

import java.util.LinkedList;
import game.attributes.AttributeValueList;
import game.objects.NonStatic;
import game.skills.PassiveSkill;

/**
 * Boxerherz, der Skill.
 * @author Alex
 *
 */
public class KriegerSkill extends PassiveSkill {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -2330263610185912718L;

	/**
	 * Konstruktor. Initialisiert die effects-boolean.
	 */
	public KriegerSkill() {
		// Der Skill heisst unzaehmbarer Krieger.
		super("Unz\u00e4hm<br>barer<br>Krieger");
		this.effectsAllies = true;
		this.effectsHeroOnly = true;
		this.effectsSelf = true;
		
		this.description = "Wird aktiviert, wenn er in Unterzahl ist.";
		
		this.setImageURL("usercontrols", "skill", "bryan/krieger");
	}
	
	@Override
	public void giveEffect(final NonStatic executer, final NonStatic effectedNonStatic) {
		LinkedList<NonStatic> inCircle = matchModule.getMap().getFightablesInCircle(executer.getCoord(), 10);
		int sameTeam = 0;
		int otherTeam = 0;
		for (NonStatic ns : inCircle) {
			if (executer.getFraction() == ns.getFraction()) {
				sameTeam++;
			} else {
				otherTeam++;
			}
		}
		if (sameTeam < otherTeam) {
			// Kreiert den entsprechenden Effekt und ruft seine Methode ready() auf.
			new KriegerEffect(effectedNonStatic, effectedNonStatic.getSkillLevel(this)).ready();
		}
	}
	
	/**
	 * Gibt die AttributeValue List des Effektes zurueck, welcher dieser Skill ausfuehrt.
	 * @param executer Der Ausfuehrer des Skills
	 * @return Die AtributeValueList
	 */
	public AttributeValueList getEffectsAttributeValueList(final NonStatic executer) {		
		return KriegerEffect.getAttributeValueListWithSkillLevel(executer.getSkillLevel(this), null);

	}
}
