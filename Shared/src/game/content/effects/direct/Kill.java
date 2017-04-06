package game.content.effects.direct;

import game.attributes.Attribute;
import game.objects.NonStatic;

/**
 * Ziemlich toetlicher Effekt.
 * @author Tristan
 *
 */
public class Kill extends game.effects.DirectEffect {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -3410140355368258195L;
	
	/**
	 * Konstruktor.
	 * @param effects Betroffener
	 */
	public Kill(final NonStatic effects) {
		super(effects);
	}
	
	@Override
	public void execute() {
		this.getEffects().getAttributeValueList().setAttribute(Attribute.alive, 0);
		matchModule.getMap().removeWorldObject(getEffects());
	}

}
