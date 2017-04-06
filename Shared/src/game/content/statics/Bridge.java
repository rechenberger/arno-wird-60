package game.content.statics;

/**
 * Eine Bruecke.
 * @author Marius
 *
 */
public class Bridge extends game.objects.Static {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 7166627718191301306L;

	/**
	 * Konstruktor.
	 */
	public Bridge() {
		this.movementSpeedFactor = 1.5f;
		this.setImageURL("match", "ground", "bridge");
	}
	
}
