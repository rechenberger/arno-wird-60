package game.objects;

/**
 * Statische Weltobjekte wie Wiese, Baeume, Wasser etc.
 * @author Tristan
 *
 */
public class Static extends WorldObject {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -1977660319654425721L;
	
	/**
	 * ueberprueft ob das entsprechende Objekt begehbar ist.
	 * @return ob das Entsprechende Feld begehbar ist.
	 */
	public boolean isWalkable() {
		if (this.getMovementSpeedFactor() != 0) {
			return true;
		}
		return false;
	}
	
	
}
