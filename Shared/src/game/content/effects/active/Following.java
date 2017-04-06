package game.content.effects.active;

import java.awt.Point;

import game.effects.Effect;
import game.objects.GameObject;
import game.objects.NonStatic;
/**
 * Aktiver Effekt.
 * Das betroffene Nonstatic bewegt sich momentan aufein anderes NonStatic zu.
 * @author Tristan
 *
 */
public class Following extends Moving {


	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 6087079360108437786L;
	
	/**
	 * Die Id des zu folgendem Nonstatic.
	 */
	private int followId;
	

	/**
	 * Konstruktor.
	 * Seichert die Parameter in den Attributen des Objekts.
	 * @param effects NonStatic deren Bewegung beschrieben wird.
	 * @param setFollow Das NonStatic, dem es zu folgen gilt.
	 * @param setRange Die Entfernung die am Ende des Bewegungsablaufs zum Ziel unterschritten werden soll.
	 */
	public Following(final NonStatic effects, final NonStatic setFollow, final int setRange) {
		super(effects, setRange);
		this.followId = setFollow.getId();
		this.route.setupNewRoute(this.getEffects().getCoord(), this.getDestination());
	}

	/**
	 * 
	 * @return das NonStatic, dem gefolgt wird.
	 */
	public NonStatic getFollows() {
		NonStatic follows = GameObject.getById(this.followId);
		if (!follows.isAlive()) {
			this.end();
		}
		return follows;
	}

	@Override
	public Point getDestination() {
		return this.getFollows().getCoord();
	}
	
	@Override
	public void execute() {
		if (this.route.getDestination() != this.getDestination()) {
			this.route.setDestination(this.getDestination());
		}
		super.execute();
	}
	
	/**
	 * @param effects ein Nonstatic
	 * @return ob das NonStatic diesen Effekt hat.
	 */
	public static boolean hasEffect(final NonStatic effects) {
		for (Effect effect : effects.getEffects()) {
			if (effect instanceof Following) {
				return true;
			}
		}
		return false;
	}
	

	/**
	 * 
	 * @param effects dieses Nonstatic
	 * @param following jenes Nonstatic
	 * @return ob dieses NonStatic jenem folgt.
	 */
	public static boolean hasEffect(final NonStatic effects, final NonStatic following) {
		for (Effect effect : effects.getEffects()) {
			if (effect instanceof Following) {
				if (((Following) effect).getFollows() == following)  {
					return true;
				}
			}
		}
		return false;
	}
}
