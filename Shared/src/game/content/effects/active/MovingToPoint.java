package game.content.effects.active;

import game.effects.Effect;
import game.objects.Map;
import game.objects.NonStatic;

import java.awt.Point;
/**
 * Aktiver Effekt.
 * Das betroffene Nonstatic bewegt sich momentan auf einen Punkt zu.
 * @author Tristan
 *
 */
public class MovingToPoint extends Moving {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 8962606274941684863L;
	
	/**
	 * Wie lange versucht werden soll einen freien platz in der Naehe von Ziel zu finden.
	 */
	private static final int MAX_TRIES_TO_FIND_EMPTY_POINT = 10;
	
	/**
	 * Die Koordinaten des Zielpunkts des Bewegungablauf.
	 * 
	 */
	private Point destination;
	

	/**
	 * Konstruktor.
	 * Seichert die Parameter in den Attributen des Objekts.
	 * @param effects NonStatic deren Bewegung beschrieben wird.
	 * @param setDestination Der Zielpunkt
	 * @param setRange Die Entfernung die am Ende des Bewegungsablaufs zum Ziel unterschritten werden soll.
	 */
	public MovingToPoint(final NonStatic effects, final Point setDestination, final int setRange) {
		super(effects, setRange);
		this.checkAndSetDestination(setDestination, 1);
		this.route.setupNewRoute(this.getEffects().getCoord(), this.getDestination());
	}
	
	/**
	 * Ueberprueft ob sich vom angegebenen Ziel in einem bestimmten Radius eine Freie Position befinden, wenn nicht wird der Radius um 1 erhoeht.
	 * @param dest Das gewuenschte Ziel
	 * @param radius Der Radius
	 */
	private void checkAndSetDestination(final Point dest, final int radius) {
		int r = radius;
		if (!Map.getMatchMap().ifWalkable(dest) || radius > MAX_TRIES_TO_FIND_EMPTY_POINT) {
			for (Point p : Map.getPointsInCircle(dest, radius)) {
				if (Map.getMatchMap().ifWalkable(p)) {
					this.destination = p;
					return;
				}
			}
		} else {
			this.destination = dest;
			return;
		}
		System.out.println("ziel belegt, suche neues");
		this.checkAndSetDestination(dest, ++r);
	}
	
	/**
	 * 
	 * @param setDestination Der Zielpunkt
	 */
//	private void setDestination(final Point setDestination) {
//		this.destination = setDestination;
//		this.route.setDestination(setDestination);
//	}

	/**
	 * Konstruktor.
	 * Seichert die Parameter in den Attributen des Objekts.
	 * @param effects NonStatic deren Bewegung beschrieben wird.
	 * @param setTarget Der Zielpunkt
	 */
	public MovingToPoint(final NonStatic effects, final Point setTarget) {
		this(effects, setTarget, 0);
	}

	@Override
	public Point getDestination() {
		return this.destination;
	}
	
	/**
	 * @param nonStatic ein Nonstatic
	 * @return ob das NonStatic diesen Effekt hat.
	 */
	public static boolean hasEffect(final NonStatic nonStatic) {
		for (Effect effect : nonStatic.getEffects()) {
			if (effect instanceof MovingToPoint) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param effects ein Nonstatic
	 * @param targetPoint Koordinaten eines Feldes.
	 * @return ob das NonStatic zu diesem Punkt geht.
	 */
	public static boolean hasEffect(final NonStatic effects, final Point targetPoint) {
		for (Effect effect : effects.getEffects()) {
			if (effect instanceof MovingToPoint) {
				if (((MovingToPoint) effect).getDestination().equals(targetPoint)) {
					return true;
				}
			}
		}
		return false;
	}
}
