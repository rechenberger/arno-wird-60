package game.objects;

import game.content.statics.WayPoints;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Die Karte.
 * @author Tristan
 */
public class Map extends GameObject {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1588736118945793421L;
	
	/**
	 * Dimenison.
	 */
	public static final Point DIMENSION = new Point(200, 200);
	
	/**
	 * Definiert fuer das Spiel relevante Wegpunkte.
	 */
	private HashMap<WayPoints, ArrayList<Point>> importantPositions = new HashMap<WayPoints, ArrayList<Point>>();
	
	
	
	/**
	 * Feld der Karte.
	 * @author Tristan
	 *
	 */
	public class Field implements Serializable {
		/**
		 * serialVersionUID.
		 */
		private static final long serialVersionUID = 7954361189597418972L;
		/**
		 * Liste aller WorldObjects auf diesem Feld.
		 */
		LinkedList<WorldObject> worldObjects;
		/**
		 * Konstruktor.
		 * Initialisiert die Liste als leere LinkedList
		 */
		public Field() {
			worldObjects = new LinkedList<WorldObject>();
		}
		/**
		 * 
		 * @return Liste aller WorldObjects auf diesem Feld. 
		 */
		public LinkedList<WorldObject> getList() {
//			LinkedList<WorldObject> list = new LinkedList<WorldObject>();
//			for (WorldObject wo : worldObjects) {
//				if (!(wo instanceof NonStatic) || ((NonStatic) wo).isAlive()) {
//					list.add(wo);
//				}
//			}
			
//			return list;
			return worldObjects;
		}
		/**
		 * Fuegt WorldObject zum Feld hinzu.
		 * @param worldObject hinzuzufuegendes WorldObject
		 */
		public void add(final WorldObject worldObject) {
			this.worldObjects.add(worldObject);
		}
		/**
		 * Entfernt WorldObject vom Feld.
		 * @param worldObject zu entfernendes WorldObject
		 */
		public void remove(final WorldObject worldObject) {
			this.worldObjects.remove(worldObject);
		}
		
		/**
		 * @return movementSpeedFactor
		 */
		public float getMovementSpeedFactor() {
			if (this.getList().isEmpty()) {
				return 0;
			}
			
			float movementSpeedFactor = 1;
			
			if (this.getList() == null) {
				return 0;
			}
			
			for (WorldObject wo : this.getList()) {
				try {
					movementSpeedFactor *= wo.getMovementSpeedFactor();
					
				} catch (NullPointerException e) {
					movementSpeedFactor *= 0;
				}
			}
			return movementSpeedFactor;

		}
		
		/**
		 * 
		 * @return Ob Feld begehbar ist.
		 */
		public boolean ifWalkable() {
			return this.getMovementSpeedFactor() != 0;
		}
	}
	
	/**
	 * Array aller Felder.
	 */
	private Field[][] fieldMap;
	
	/**
	 * Konstruktor.
	 */
	public Map() {
		this.fieldMap = new Field[DIMENSION.x][DIMENSION.y];
		for (int x = 0; x < DIMENSION.x; x++) {
			for (int y = 0; y < DIMENSION.y; y++) {
				fieldMap[x][y] = new Field();
			}
		}
	}
	
	/**
	 * @return Gibt die Map aus der Liste aller GameObjects.
	 */
//	public static Map getMap() {
//		for (GameObject go : GameObject.allGameObjects.values()) {
//			if (go instanceof Map) {
//				return (Map) go;
//			}
//		}
//		return null;
//	}
	/**
	 * Aktualisiert die Koordinaten eines Weltobjekt.
	 * @param worldObject Weltobjekt
	 * @param newCoord neue Koordinaten
	 * @param oldCoord alte Koordinaten
	 */
	public void updateCoord(final WorldObject worldObject, final Point newCoord, final Point oldCoord) {
		if (!worldObject.getSize().equals(new Point(1, 1))) {
			System.out.println("Ein so grosses Weltobject darf sich nicht bewegen!!!");
		}
		if (oldCoord != null) {
			this.getField(oldCoord).remove(worldObject);
		}
		place(worldObject);
	}
	
	/**
	 * Plaziert ein Weltobjekt auf der Karte.
	 * @param worldObject Weltobjekt
	 */
	public void place(final WorldObject worldObject) {
		for (Point coord : worldObject.getCoords()) {
			this.getField(coord).add(worldObject);
		}
	}
	
	/**
	 * Entfernt WeltObjekt von der Karte.
	 * @param worldObject zu entferndenes WeltObjekt.
	 */
	public void removeWorldObject(final WorldObject worldObject) {
		for (Point coord : worldObject.getCoords()) {
			this.getField(coord).remove(worldObject);
		}
	}
	
	/**
	 * 
	 * @param coord Koordinaten
	 * @return Feld
	 */
	private Field getField(final Point coord) {
		if (coord == null) {
			return null;
		}
		return this.getField(coord.x, coord.y);
	}
	
	/**
	 * 
	 * @param x X-Koordinate
	 * @param y Y-Koordinate
	 * @return Feld
	 */
	private Field getField(final int x, final int y) {
		if (x >= fieldMap.length || x < 0 || y < 0 || y >= fieldMap[x].length) {
			return new Field();
		}
		return this.fieldMap[x][y];
	}

	/**
	 * Gibt Liste mit allen Weltobjekten auf einer Koordinate zurueck.
	 * @param x X-Koordinate
	 * @param y Y-Koordinate
	 * @return Liste mit allen Weltobjekten auf diesem Feld
	 */
	public LinkedList<WorldObject> getList(final int x, final int y) {
		return this.getField(x, y).getList();
	}

	/**
	 * Gibt Liste mit allen Weltobjekten auf einer Koordinate zurueck.
	 * @param coord Koordinaten
	 * @return Liste mit allen Weltobjekten auf diesem Feld
	 */
	public LinkedList<WorldObject> getList(final Point coord) {
		return this.getField(coord).getList();
	}
	
	/**
	 * 
	 * @param coord Koordinaten des Feldes
	 * @return Ob Feld begehbar ist.
	 */
	public boolean ifWalkable(final Point coord) {
		try {
			return this.getField(coord).ifWalkable();
		} catch (java.lang.NullPointerException e) {
			return false;
		}
	}
	

	/**
	 * 
	 * @param coord Koordinaten des Feldes
	 * @return Kosten des Feldes
	 */
	public float getMovementSpeedFactor(final Point coord) {
		return this.getField(coord).getMovementSpeedFactor();
	}
	
	/**
	 * 
	 * @param a Punkt A
	 * @param b Punkt B
	 * @return Distanz zwischen den beiden Punkten.
	 */
	public static int getDistance(final Point a, final Point b) {
		return (int) Math.sqrt((Math.pow((a.x - b.x), 2) + Math.pow((a.y - b.y), 2)));
	}

	/**
	 * 
	 * @param coord Koordinaten des Mittelpunkts des Kreises
	 * @param radius Radius des Kreises
	 * @return Liste aller Weltobjekte in diesem Kreis.
	 */
	public LinkedList<WorldObject> getWorldObjectsInCircle(final Point coord, final int radius) {
		LinkedList<WorldObject> list = new LinkedList<WorldObject>();
		for (Point currentCoord : getPointsInCircle(coord, radius)) {
			for (WorldObject wo : this.getList(currentCoord)) {
				if (!list.contains(wo)) {
					list.add(wo);
				}
			}
		}
		return list;
	}
	
	/**
	 * 
	 * @param coord Koordinaten des Mittelpunkts des Kreises
	 * @param radius Radius des Kreises
	 * @return Liste aller Punkte in diesem Kreis.
	 */
	public static LinkedList<Point> getPointsInCircle(final Point coord, final int radius) {
		LinkedList<Point> list = new LinkedList<Point>();
		Point currentCoord;
		for (int x = coord.x - radius; x <= coord.x + radius; x++) {
			for (int y = coord.y - radius; y <= coord.y + radius; y++) {
				currentCoord = new Point(x, y);
				if (getDistance(coord, currentCoord) <= radius) {
					list.add(currentCoord);
				}
			}
		}
		return list;
	}
	
	/**
	 * 
	 * @param coord Koordinaten des Mittelpunkts des Kreises
	 * @param radius Radius des Kreises
	 * @return Liste aller Punkte in diesem Kreis.
	 */
	public static LinkedList<Point> getPointsOnCircle(final Point coord, final int radius) {
		LinkedList<Point> list = new LinkedList<Point>();
		Point currentCoord;
		for (int x = coord.x - radius; x <= coord.x + radius; x++) {
			for (int y = coord.y - radius; y <= coord.y + radius; y++) {
				currentCoord = new Point(x, y);
				if (getDistance(coord, currentCoord) == radius) {
					list.add(currentCoord);
				}
			}
		}
		return list;
	}
	
	/**
	 * Das Quadrat ist im Verhaeltnis zum Koordinaten System um 45 Grad gedreht.
	 * @param coord Koordinaten des Mittelpunkts des Quadrats
	 * @param radius Radius des Kreises
	 * @return Liste aller Punkte in diesem Quadrat.
	 */
	public static LinkedList<Point> getPointsInSquare(final Point coord, final int radius) {
		LinkedList<Point> pointsInSquare = new LinkedList<Point>();
		Point start = new Point(coord.x - radius, coord.y);
		Point current = start;
		for (int row = 1; row <= (radius * 2) - 1; row++) {
			start.translate(row % 2, (row + 1) % 2);
			current = (Point) start.clone();
			for (int collum = 1; collum <= radius + (row % 2); collum++) {
				pointsInSquare.add((Point) current.clone());
				current.translate(1, -1);
//				((Point) current.clone()).translate(1, -1);
			}
		}
		
		
//		for (int dy = -radius; dy <= radius; dy++) {
//			for (int dx = Math.abs(dy) - radius; dx <= radius - Math.abs(dy); dx++) {
//				pointsInSquare.add(new Point(coord.x + dx, coord.y + dy));
//			}
//		}
		return pointsInSquare;
	}
	

//	public static LinkedList<Point> getPointsInSquare(final Point coord, final int radius) {
//		LinkedList<Point> pointsInSquare = new LinkedList<Point>();
//		for (int dy = -radius; dy <= radius; dy++) {
//			for (int dx = Math.abs(dy) - radius; dx <= radius - Math.abs(dy); dx++) {
//				pointsInSquare.add(new Point(coord.x + dx, coord.y + dy));
//			}
//		}
//		return pointsInSquare;
//	}
//	
	/**
	 * 
	 * @param coord Koordinaten des Mittelpunkts des Kreises
	 * @param radius Radius des Kreises
	 * @return Liste aller Weltobjekte in diesem Kreis.
	 */
	public LinkedList<NonStatic> getFightablesInCircle(final Point coord, final int radius) {
		LinkedList<NonStatic> list = new LinkedList<NonStatic>();
		for (WorldObject wo : this.getWorldObjectsInCircle(coord, radius)) {
			if (wo instanceof Fightable) {
				list.add(((NonStatic) wo));
			}
		}
		return list;
	}
	
	/**
	 * Das Quadrat ist im Verhaeltnis zum Koordinaten System um 45 Grad gedreht.
	 * @param coord Koordinaten des Mittelpunkts des Quadrats
	 * @param radius Radius des Kreises
	 * @return Liste aller Weltobjekte in diesem Quadrat.
	 */
	public LinkedList<WorldObject> getWorldObjectsInSquare(final Point coord, final int radius) {
		LinkedList<WorldObject> list = new LinkedList<WorldObject>();
		for (Point currentCoord : getPointsInSquare(coord, radius)) {
			for (WorldObject wo : this.getList(currentCoord)) {
				if (wo.getCoordBottomLeft().equals(currentCoord)) {
					list.add(wo);
				}
			}
		}
		return list;
	}
	
	/**
	 * Das Quadrat ist im Verhaeltnis zum Koordinaten System um 45 Grad gedreht.
	 * @param coord Koordinaten des Mittelpunkts des Quadrats
	 * @param radius Radius des Kreises
	 * @return Liste aller Weltobjekte in diesem Quadrat.
	 */
	public LinkedList<WorldObject> getNonFloorInSquare(final Point coord, final int radius) {
		LinkedList<WorldObject> list = new LinkedList<WorldObject>();
		for (Point currentCoord : getPointsInSquare(coord, radius)) {
			LinkedList<WorldObject> innerList = this.getList(currentCoord); 
			for (WorldObject wo : innerList) {
				if (innerList.indexOf(wo) != 0 && currentCoord.equals(wo.getCoordBottomLeft())) {
					list.add(wo);
				}
			}
		}
		return list;
	}
	/**
	 * Liefert alle FloorObjekte im Quadrat zurueck.
	 * @param coord Koordinaten des Mittelpunkts des Quadrats
	 * @param radius Radius des Kreises
	 * @return Liste aller Weltobjekte in diesem Quadrat.
	 */
	public LinkedList<WorldObject> getFloorInSquare(final Point coord, final int radius) {
		LinkedList<WorldObject> list = new LinkedList<WorldObject>();
		for (Point currentCoord : getPointsInSquare(coord, radius)) {
			if (!this.getList(currentCoord).isEmpty()) {
				list.add(this.getList(currentCoord).getFirst());
			}
		}
		return list;
	}
	/**
	 * @return Liste aller Bodenweltobjekte
	 */
	public LinkedList<WorldObject> getFloor() {
		LinkedList<WorldObject> floor = new LinkedList<WorldObject>();

		for (int x = 0; x < DIMENSION.x; x++) {
			for (int y = 0; y < DIMENSION.y; y++) {
				floor.add(this.getList(x, y).getFirst());
			}
		}
		
		return floor;
	}
	
	/**
	 * Gibt den ersten begehbahren Punkt in einem Area zurueck.
	 * @param list Das Area
	 * @return Der Punkt
	 */
	public Point getEmptyPointInArea(final ArrayList<Point> list) {
		for (Point p : list) {
			if (ifWalkable(p)) {
				return p;
			}
		}
		
		return null;
		
	}
	
	/**
	 * @param centerPoint Punkt von dem aus gesehen werden soll.
	 * @param viewRange Reichweite in der gesehen werden soll.
	 * @return Punkte die gesehen werden.
	 */
	public HashSet<Point> getPointsInViewRange(final Point centerPoint, final int viewRange) {
		LinkedList<Point> toCheck = new LinkedList<Point>();
		toCheck.addAll(getPointsOnCircle(centerPoint, viewRange));
		toCheck.addAll(getPointsInCircle(centerPoint, viewRange - 1));
		HashSet<Point> list = new HashSet<Point>();
		HashSet<Point> checked = new HashSet<Point>();
		list.add(centerPoint);
		
		// Geht Punkte auf Kreis und im Kreis durch.
		for (Point currentPoint : toCheck) {
			boolean inViewRange = true;
			if (!checked.contains(currentPoint)) {
					
				// Geht Alle Punkte auf Luftlinie von Innen nach Aussen durch.
				for (Point pointOnBeeline : getPointsOnBeeline(centerPoint, currentPoint)) {
					
					checked.add(pointOnBeeline);
					if (inViewRange) {
						list.add(pointOnBeeline);
					}
					
					if (!this.ifWalkable(pointOnBeeline)) {
						inViewRange = false;
					}
				}
			}
		}
		return list;
	}
	
	
	
	/**
	 * 
	 * @param a Punkt A
	 * @param b Punkt B
	 * @return Punkte auf Luftlinie (exkl. A, inkl. B)
	 */
	public static LinkedList<Point> getPointsOnBeeline(final Point a, final Point b) {
		LinkedList<Point> list = new LinkedList<Point>();
		
		if (a.equals(b)) {
			return list;
		}
		
		float dx;
		float dy;
		if (Math.abs(b.y - a.y) > Math.abs(b.x - a.x)) {
			if (a.y < b.y) {
				dy = 1;
			} else {
				dy = -1;
			}
			dx = dy * ((float) (b.x - a.x)) / ((float) (b.y - a.y));
		} else {
			if (a.x < b.x) {
				dx = 1;
			} else {
				dx = -1;
			}
			dy = dx * ((float) (b.y - a.y)) / ((float) (b.x - a.x));
		}
		
		float x = a.x;
		float y = a.y;
		Point c;
		do {
			x += dx;
			y += dy;
			c = new Point(Math.round(x), Math.round(y));
			list.add(c);
		} while (!c.equals(b));
		list.removeLast();
		
		
		return list;
	}
	
	/**
	 * @return Definiert fuer das Spiel relevante Wegpunkte.
	 */
	public HashMap<WayPoints, ArrayList<Point>> getImportantPositions() {
		return importantPositions;
	}

	/**
	 * @param setImportantPositions Definiert fuer das Spiel relevante Wegpunkte.
	 */
	public void setImportantPositions(final HashMap<WayPoints, ArrayList<Point>> setImportantPositions) {
		this.importantPositions = setImportantPositions;
	}
	
	/**
	 * Gibt die im Matchmodul gespeicherte Map zurueck.
	 * @return Die Map
	 */
	public static Map getMatchMap() {
		return matchModule.getMap();
	}
	
	/**
	 * @param direction Richtung
	 * @return Winkel
	 */
	public static int directionToAngle(final Point direction) {
		
		int angle;
		if (direction.y == 0) {
			if (direction.x < 0) {
				angle = 90;
			} else {
				angle = 270;
			}
		} else {
			angle = (int) Math.toDegrees(Math.atan(-direction.x  / direction.y));
			if (direction.y < 0) {
				angle += 180;
			}
				angle = angle % 360;
				if (angle < 0) {
					angle += 360;
				}
		}
		return (int) angle;
	}
	
	/**
	 * @param current Startpunkt
	 * @param destination Zielpunkt
	 * @return Winkel dazwischen.
	 */
	public static int directionToAngle(final Point current, final Point destination) {
		return directionToAngle(new Point(destination.x - current.x, destination.y - current.y));
	}
	
}
