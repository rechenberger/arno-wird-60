package game.content.statics;

import game.objects.Static;

/**
 * Wasser.
 * @author Marius
 *
 */
public class Water extends Static {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 3696630145655997958L;
	
	/**
	 * Konstruktor.
	 */
	public Water() {
		this.setImageURL("match", "ground", "water");
		this.movementSpeedFactor = 0;
	}
}
