package game.content.effects.direct;

import game.attributes.Attribute;
import game.objects.NonStatic;

/**
 * Wiederbelebt. Geronimo-Style.
 * @author Tristan
 *
 */
public class Revive extends game.effects.DirectEffect {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -3410140755368258195L;

	/**
	 * Konstruktor.
	 * @param effects Betroffener
	 */
	public Revive(final NonStatic effects) {
		super(effects);
	}
	
	@Override
	public void execute() {
		this.getEffects().getAttributeValueList().setAttribute(Attribute.alive, 1);
	}

}
