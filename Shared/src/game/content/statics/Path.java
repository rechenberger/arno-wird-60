package game.content.statics;

/**
 * Ein Pfad.
 * @author Tristan
 *
 */
public class Path extends game.objects.Static {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 7166627718191301306L;

	/**
	 * Konstruktor.
	 */
	public Path() {
		this.movementSpeedFactor = 1.5f;
		this.setImageURL("match", "ground", "earth");
	}
}
