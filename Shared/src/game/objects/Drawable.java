package game.objects;
/**
 * Alle Gameobjects, die gezeichnet werden koennen bzw. Bilder haben.
 * @author Christian Westhoff
 *
 */
public abstract class Drawable extends GameObject {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -579130425394649427L;
	
	/**
	 * Pfad zum Bild.
	 * [0]: base (static/movable/etc.)
	 * [1]: ImageSet (floor/tree/etc.)
	 * [2]: ImageName (ground/gras/etc.)
	 */
	protected String[] imageURL = {"empty", "empty", "empty"};
	
	/**
	 * Konstruktor.
	 */
	public Drawable() {
	}
	
	/**
	 * Setzt die ImageURL.
	 * @param base static / nonstatic
	 * @param set Uebergeordnetes ImageSet
	 * @param image Das passende Bild
	 */
	public void setImageURL(final String base, final String set, final String image) {
		this.imageURL[0] = base;
		this.imageURL[1] = set;
		this.imageURL[2] = image;
	}
	
	/**
	 * 
	 * @return Pfad zum Bild
	 */
	public String getImageURL() {
		return this.imageURL[0] + "/" + this.imageURL[1] + "/" + this.imageURL[2];
	}
	
	/**
	 * @return Erster Parameter der ImageURL.
	 */
	public String getImageBase() {
		return this.imageURL[0];
	}
	
	/**
	 * @return Zweiter Parameter der ImageURL.
	 */
	public String getImageSet() {
		return this.imageURL[1];
	}
	
	/**
	 * @return Dritter Parameter der ImageURL.
	 */
	public String getImage() {
		return this.imageURL[2];
	}
	
}
