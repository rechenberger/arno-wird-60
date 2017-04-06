package game.content.effects.direct;

import game.attributes.Attribute;
import game.content.heros.Hero;
import game.effects.DirectEffect;
import game.objects.Map;
import game.objects.NonStatic;

import java.awt.Point;

/**
 * Betroffener macht einen Schritt.
 * @author Tristan
 *
 */
public class Step extends DirectEffect {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -4363910034515378905L;
	
	/**
	 * Zielpunkt dieses Schritts.
	 */
	private Point destination;

	/**
	 * Konstruktor.
	 * @param setDestination Zielpunkt dieses Schritts
	 * @param effects Betroffenes NonStatic
	 */
	public Step(final NonStatic effects, final Point setDestination) {
		super(effects);
		this.destination = setDestination;
	}
	
	@Override
	public void execute() {
		if (this.getEffects().getCoord() != null && !(this.getEffects().getAttributeValue(Attribute.flying) == 1)) {
			this.getEffects().getAttributeValueList().setAttribute(Attribute.headingTo, Map.directionToAngle(this.getEffects().getCoord(), this.destination));
		}
		this.getEffects().setCoord(destination);
		
		if (!matchModule.isClient() && this.getEffects() instanceof game.content.heros.Hero && ((Hero) this.getEffects()).getPlayer() != null) {
			((Hero) this.getEffects()).getPlayer().incStatistic("Steps");
		}
	}
	
	@Override
	public void registerGameObject() {
		super.registerGameObject();
//		this.execute();
	}
}
