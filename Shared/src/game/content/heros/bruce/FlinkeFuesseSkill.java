package game.content.heros.bruce;

import game.attributes.AttributeValueList;
import game.objects.NonStatic;
import game.skills.PassiveSkill;

/**
 * Flinke Fuesse, der Skill.
 * @author Alex
 *
 */
public class FlinkeFuesseSkill extends PassiveSkill {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 7446234137672323691L;

	/**
	 * Konstruktor. Initialisiert die effects-boolean.
	 */
	public FlinkeFuesseSkill() {
		// Der Skill heisst unzaehmbarer Krieger.
			super("Flinke F\u00fcsse");
	}
	
	@Override
	public void execute(final NonStatic executer) {	
		new FlinkeFuesseEffect(executer, executer.getSkillLevel(this)).ready();	
	}
	
	@Override
	public AttributeValueList getEffectsAttributeValueList(final NonStatic executer) {
		return new AttributeValueList();
	}

}
