package util.geometrie;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Set;

/**
 * Ein Area ist eine Menge an Punkten, die in einer ArrayList gespeichert sind.
 * Die Meisten Operationen, die man auf Collections ausfuehren kann, kann man auch auf diese Klasse ausfuehren.
 * @author Marius
 *
 */
public abstract class Area {
	
	/**
	 * Die Koordinaten, die dieses Area ausmachen.
	 */
	private ArrayList<Point> coords = new ArrayList<Point>();

	/**
	 * Fuegt eine neue Koordinate hinzu.
	 * @param p die Koordinate
	 */
	public void add(final Point p) {
		coords.add(p);
	}
	
	/**
	 * Fuegt eine neue Koordinate hinzu.
	 * @param p die Koordinate
	 * @param key an welcher stelle in der ArrayList
	 */
	public void add(final int key, final Point p) {
		coords.add(key, p);
	}
	
	/**
	 * Ob das Area Koordinaten beinhaltet.
	 * @return  ja/nein
	 */
	public boolean isEmpty() {
		return coords.isEmpty();
	}
	
	/**
	 * Ob das Area den gegebenen Punkt beinhaltet.
	 * @param p Der Punkt
	 * @return ob er drin ist.
	 */
	public boolean contains(final Point p) {
		return coords.contains(p);
	}
	
	/**
	 * Gibt den Punkt zurueck an der stelle index.
	 * @param index Des Punktes
	 * @return Der Punkt
	 */
	public Point get(final int index) {
		return coords.get(index);
	}
	
	/**
	 * Liefert alle Punkte des Areas zuruck.
	 * @return Die Punkte
	 */
	public ArrayList<Point> getCoords() {
		return coords;
	}
	
	/**
	 * Setzt die Punkte.
	 * @param co die Punkte
	 */
	public void setCoords(final ArrayList<Point> co) {
		coords = co;
	}
	
	/**
	 * Schreibt die Koordinaten in die uebergebene Map.
	 * @param map Die Integer Map.
	 * @param tileId welcher Wert in die Map geschribene werden soll.
	 * @param toIgnore Hier sind alle Werte gespeichert die durch diese Methode in der Integer Map nicht ueberschrieben werden sollen.
	 */
	public void coordsOnMap(final int[][] map, final int tileId, final Set<Integer> toIgnore) {
		for (Point p : coords) {
			if (!toIgnore.contains(map[p.x][p.y])) {
				map[p.x][p.y] = tileId;
			}
		}
	}
	
	/**
	 * Schreibt die Koordinaten in die uebergebene Map.
	 * @param map Die Integer Map.
	 * @param tileId welcher Wert in die Map geschribene werden soll.
	 */
	public void coordsOnMap(final int[][] map, final int tileId) {
		for (Point p : coords) {
			map[p.x][p.y] = tileId;
		}
	}
	
	/**
	 * Schreibt die Koordinaten gespiegelt an der Diagonalen von untenlinks nach oben rechts auf die Karet in die uebergebene Map.
	 * @param map Die Integer Map.
	 * @param tileId welcher Wert in die Map geschribene werden soll.
	 * @param toIgnore Hier sind alle Werte gespeichert die durch diese Methode in der Integer Map nicht ueberschrieben werden sollen.
	 */
	public void coordsOnMapReflectedByMiddlePath(final int[][] map, final int tileId, final Set<Integer> toIgnore) {
		for (Point p : coords) {
			if (!toIgnore.contains(map[p.x][p.y])) {
				map[map.length - p.y][map[0].length - p.x - 1] = tileId;
			}
		}
	}
	
	/**
	 * Schreibt die Koordinaten gespiegelt an der Diagonalen von untenlinks nach oben rechts auf die Karet in die uebergebene Map.
	 * @param map Die Integer Map.
	 * @param tileId welcher Wert in die Map geschribene werden soll.
	 **/
	public void coordsOnMapReflectedByMiddlePath(final int[][] map, final int tileId) {
		for (Point p : coords) {
			map[map.length - p.y][map[0].length - p.x - 1] = tileId;
		}
	}
	
	/**
	 * Reflectiert die gegebenen Punkte ueber die Imaginaere Linie von oben Links der Karte bis unten Rechts.
	 * 
	 * @param points zu reflektieren.
	 * @return reflektiert
	 */
	public ArrayList<Point> reflectCoordsOnRiver(final ArrayList<Point> points) {
		ArrayList<Point> reflectedPoints = new ArrayList<Point>();
		
		for (Point p : points) {
			reflectedPoints.add(new Point(p.y, p.x));
		}
		
		return reflectedPoints;
	}
	
	/**
	 * Reflectiert die gegebenen Punkte ueber den Mittleren Weg.
	 * 
	 * @param points zu reflektieren.
	 * @param mapSize die groesse der Map
	 * @return reflektiert
	 */	
	public ArrayList<Point> reflectCoordsOnMiddlePath(final ArrayList<Point> points, final Dimension mapSize) {
		ArrayList<Point> reflectedPoints = new ArrayList<Point>();
		
		for (Point p : points) {
			reflectedPoints.add(new Point(mapSize.width - p.y, mapSize.height - p.x - 1));
		}
		
		return reflectedPoints;
		
	}
}
