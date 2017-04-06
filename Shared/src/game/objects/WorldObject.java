package game.objects;

import java.awt.Point;
import java.util.LinkedList;
/**
 * Weltobjekte sind jene Spielobjekte, die auf der Spielkarte zu sehen sind.
 * 
 * @author Tristan
 *
 */
public class WorldObject extends Drawable {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -4402617737877932686L;
	/**
	 * Die Dimension des Objekts in Feldern.
	 */
	protected Point size = new Point(1, 1);
	/**
	 * Koordinaten auf der Karte.
	 */
	protected Point coord;
	
	/**
	 * Faktor um den ein NonStatic beschleunigt wird, wenn es ueber dieses Weltobjekt laeuft.
	 */
	protected float movementSpeedFactor = 0;
	
	/**
	 * Konstruktor.
	 */
	public WorldObject() {
		super();
	}
	
	/**
	 * Setzt die Koordinaten neu und aktualisiert die Karte.
	 * @param newCoord neue Koordinaten (unten rechts)
	 */
	public void setCoord(final Point newCoord) {
		Point oldCoord = this.coord;
		this.coord = newCoord;
		matchModule.getMap().updateCoord(this, newCoord, oldCoord);
	}
	
	/**
	 * 
	 * @return Koordinaten
	 */
	public Point getCoord() {
		return this.coord;
	}
	
	/**
	 * Plaziert das Objekt auf einer Karte.
	 * @param setMap Karte
	 * @param setCoord Koordinaten
	 */
	public void placeOnMap(final Map setMap, final Point setCoord) {
		this.coord = setCoord;
		matchModule.getMap().place(this);
	}
	
	/**
	 * 
	 * @return Dimension
	 */
	public Point getSize() {
		return this.size;
	}
	
	/**
	 * 
	 * @return Faktor um den ein NonStatic beschleunigt wird, wenn es ueber dieses Weltobjekt laeuft.
	 */
	public float getMovementSpeedFactor() {
		return this.movementSpeedFactor;
	}
	
	/**
	 * @return Liste aller Punkte auf denen das WorldObject steht.
	 */
	public LinkedList<Point> getCoords() {
		LinkedList<Point> coords = new LinkedList<Point>();
		for (int x = this.getCoord().x; x > this.getCoord().x - this.getSize().x; x--) {
			for (int y = this.getCoord().y; y > this.getCoord().y - this.getSize().y; y--) {
				coords.add(new Point(x, y));
			}
		}
		return coords;
	}
	
	/**
	 * @return Punkt
	 */
	public Point getCoordBottomLeft() {
		return new Point(this.coord.x - (this.size.x - 1), this.coord.y);
	}
	
	@Override
	public void unregisterGameObject() {
		super.unregisterGameObject();
		this.coord = null;
		this.size = null;
	}
	
//	/**
//	 * Gibt alle Weltobjekte zurueck.
//	 * @return Liste aller Weltobjekte.
//	 */
//	public static LinkedList<WorldObject> getAll() {
//		LinkedList<WorldObject> ll = new LinkedList<WorldObject>();
//		for (GameObject go : GameObject.getGameObjectsByClassName("WorldObject")) {
//			if (go instanceof WorldObject) {
//				ll.add((WorldObject) go);
//			}
//		}
//		return ll;
//	}
	
}
