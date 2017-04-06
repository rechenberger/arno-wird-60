package game.content.statics;

/**
 * Der Boden in der Basis.
 * @author Tristan
 *
 */
public class BaseGround extends game.objects.Static {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 3696630145655997958L;
	
	/**
	 * Konstruktor.
	 */
	public BaseGround() {
		this.setImageURL("match", "ground", "sand");
		this.movementSpeedFactor = 1.5f;
	}
}
