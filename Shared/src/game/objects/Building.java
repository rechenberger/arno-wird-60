package game.objects;

/**
 * Ein Gebaude. Zeichnet sich durch seine sture Unbeweglichkeit aus.
 * @author Tristan
 *
 */
public class Building extends Fightable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 4818529096909261672L;
	
	/**
	 * Konstrukor. Mach Gebaeude undurchgehbar.
	 */
	public Building() {
		this.movementSpeedFactor = 0f;
	}
	
	@Override
	public String getImageURL() {
		if (this.getFraction() == Fraction.TeamA) {
			return super.getImageURL() + "-a";
		}
		return super.getImageURL() + "-b";
	}

}
