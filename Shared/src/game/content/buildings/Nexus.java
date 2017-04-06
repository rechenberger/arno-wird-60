package game.content.buildings;

import java.awt.Point;

import game.objects.Building;

/**
 * Hauptgebaeude. Wird es zerstoert, ist das Spiel fuer die Angreifer gewonnen.
 * @author Tristan
 *
 */
public class Nexus extends Building {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 2625377023473535248L;
	
	/**
	 * Groesse des Gebaeudes.
	 */
	public static final Point SIZE = new Point(8, 8);

	/**
	 * Konstruktor.
	 */
	public Nexus() {
		this.size = SIZE;
		this.setImageURL("match", "nexus", "nexus");
	}
}
