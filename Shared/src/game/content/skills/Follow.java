package game.content.skills;

import game.attributes.Attribute;
import game.attributes.AttributeValueList;
import game.content.effects.active.Following;
import game.objects.NonStatic;
import game.skills.AimedOnNonStaticSkill;

/**
 * Skill um anderen NonStatics zu folgen.
 * @author Tristan
 *
 */
public final class Follow extends AimedOnNonStaticSkill {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 7084210930239558997L;
	/**
	 * Instanz.
	 */
	private static Follow instance = new Follow();

	/**
	 * 
	 * @return Instanz.
	 */
	public static Follow getInstance() {
		return instance;
	}
	
	/**
	 * Konstruktor.
	 */
	private Follow() {
		super("Folgen");
		this.shownInGui = false;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(final NonStatic executer, final NonStatic target) {
		new Following(executer, target, 0);
		
	}
	

	@Override
	public int getCooldown(final NonStatic executer) {
		return executer.getAttributeValueList().getValue(Attribute.movementSpeed);
	}

	@Override
	public AttributeValueList getEffectsAttributeValueList(final NonStatic executer) {
		AttributeValueList av = new AttributeValueList();
		return av;
	}

}
