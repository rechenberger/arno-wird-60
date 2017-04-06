package game.content.statics;


/**
 * Baum.
 * @author Tristan
 *
 */
public class Tree extends game.objects.Static {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 3696630145655997958L;
	
	/**
	 * Konstruktor.
	 */
	public Tree() {
		int rand = (int) (Math.random() * 20 + 1);
		if (rand < 10) {
			this.setImageURL("match", "tree", "tree-0" + rand);
		} else {
			this.setImageURL("match", "tree", "tree-" + rand);
		}
		this.movementSpeedFactor = 0;
	}
}
