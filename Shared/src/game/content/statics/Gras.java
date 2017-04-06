package game.content.statics;

/**
 * Rasen.
 * @author Tristan
 *
 */
public class Gras extends game.objects.Static {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3696630145655997958L;
	
	/**
	 * Konstruktor.
	 */
	public Gras() {
		this.setImageURL("match", "ground", "gras");
		this.movementSpeedFactor = 1;
	}

}
