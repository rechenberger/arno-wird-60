package mapgenerator.elemente.jungleelemente;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;

import mapgenerator.elemente.abstractclasses.Path;
import util.AStarFieldSetAble;
import util.Field;

/**
 * In dieser Klasse wird die <b>Verbindung</b> eines JungleSectors zum <b>Hauptweg</b> berechnet.
 * <br>
 * Der Weg wird durch den Algorithmus <b>AStarFieldSet</b> berechnet.
 * <br>
 * Hierbei wird zuerst in dieser Klasse ein Zielpunkt gesucht der sich Nahe dem der uebergebenen Connectionline,
 * aber auf dem Hauptweg befindet. (Dies geschiet in der durch das Interface definierten Methode getGoal).
 * <br>
 * Danach wird in der Vaterklasse der AStar Algoritmmus ausgefuehrt und der Berechnete Weg gespeichert.
 * 
 * @author Marius
 *
 */
public class ConnectionJungleSectorMainPath extends JungleSectorPath implements AStarFieldSetAble {

	/**
	 * Das Objekt des JungleSectors.
	 */
	private JungleSector js;
	
	/**
	 * Die Richtung in die sich der Weg von der ConnectionLine aus bewegen soll.
	 */
	private Dimension dir;
	
	/**
	 * Der Weg zwischen JungleSector und Hauptweg wird berechnet wird initialisiert.
	 * 
	 * @param conLine Das Objekt der Connectionline von der aus ein Weg gesucht werden soll.
	 * @param jungleSector Das Objekt des JungleSectors.
	 * @param direction Die Richtung in die sich der Weg von der ConnectionLine aus bewegen soll.
	 */
	public ConnectionJungleSectorMainPath(final JungleConnectionLine conLine,
			final JungleSector jungleSector, final Dimension direction) {
		
		super(conLine, jungleSector);
		js = jungleSector;
		dir = direction;
	}
	

	@Override
	public ArrayList<Field<Integer>> getMovableFields() {
		
		ArrayList<Field<Integer>> moveableFieds = new ArrayList<Field<Integer>>();
		
		for (Field<Integer> f : js.mg.getHalbeMapAsArrayList()) {
			if (f.getValue() == -1) {
				continue;
			} else if (f.getValue() == TILE_ID) {
				f.setValue(HIGH_COSTS_FOR_FIELD);
				moveableFieds.add(f);
			} else {
				f.setValue(COSTS_FOR_FIELD);
				moveableFieds.add(f);
			}
		}

		return moveableFieds;
	}

	@Override
	public Dimension getAreaDimension() {
		return super.getAreaDimension();
	}

	@Override
	public ArrayList<Point> getStartFieldSet() {
		
		Point min = null;
		Point max = null;
		int inXDir = 0;
		int inYDir = 0;
		
		//ArrayList in normalen array Umwandeln
		Point[] sl = new Point[connectionLine.getCoords().size()];
		for (int i = 0; i < connectionLine.getCoords().size(); i++) {
			sl[i] = connectionLine.get(i);
		}
		
		//Wenn die Y Koordinate der ersten Punktes gleich der des letzten ist. veraendert sich nur die X Koordinate der ConnectionLine.
		//Daher es ist entweder eine Top oder eine Bottom Connection
		if (sl[0].y == sl[sl.length - 1].y) {
			int minX = Integer.MAX_VALUE; // der Punkt mit dem kleinsten X wert
			int maxX = Integer.MIN_VALUE; // der Punkt mit dem groessten X wert
			inXDir = 1;
			for (Point p : sl) {
				if (p.x < minX) {
					minX = p.x;
					min = p;
				}
				if (p.x > maxX) {
					maxX = p.x;
					max = p;
				}
			}
		} else { //Hier geht er rein Wenn es eien Left oder Right connection ist.
			inYDir = 1;
			int minY = Integer.MAX_VALUE; // der Punkt mit dem kleinsten Y wert
			int maxY = Integer.MIN_VALUE; // der Punkt mit dem groessten Y wert
			for (Point p : sl) {
				if (p.y < minY) {
					minY = p.y;
					min = p;
				}
				if (p.y > maxY) {
					maxY = p.y;
					max = p;
				}
			}
		}
		
		ArrayList<Point> fieldSet = new ArrayList<Point>();
		Point startAt = new Point(0, 0);
		
		// fuer jede Moegliche Richtung ein FiedSet aufzuspannen
		directions:
		for (Point direct : directionToCreateFieldSet) {
			
			fieldSet.clear(); //alte Daten aus FieldSet raus
			if (inXDir == 1 && direct.x < 0 || inYDir == 1 && direct.y < 0)  {
				startAt = max; // Wenn wir in minus x oder Y gehen muessen wir beim Punkt mit dem groessten Wert anfangen
			} else if (inXDir == 1 && direct.x > 0 || inYDir == 1 && direct.y > 0) {
				startAt = min; // Wenn wir in plus x oder Y gehen muessen wir beim Punkt mit dem kleinsten Wert anfangen
			}
			
			//Spanne FieldSet auf
			for (int i = startAt.y; 
							(i < startAt.y + getPathSize().height)
							&& ((i > startAt.y + (direct.y * getPathSize().height)) 
							|| direct.y != -1);
					i += 1 * direct.y) {
				for (int j = startAt.x; 
								(j < startAt.x + getPathSize().width) 
								&& ((j > startAt.x + (direct.x * getPathSize().width)) 
								|| direct.x != -1);
						j += 1 * direct.x) {
					//ist Koordinate im JungleSector, dann nehme naechte Richtung.
					if (js.getCoords().contains(new Point(j, i)) && !connectionLine.contains(new Point(j, i))) {
						continue directions;
					}
					fieldSet.add(new Point(j, i));
				}
			}
			break directions;
		}

		return fieldSet;
	}

	@Override
	public final Point getGoal() {
		boolean allPointsOnMainPath = true;
		int counter = 1;
		do {
			for (Point p : this.getConnectionLine().getCoords()) {
				allPointsOnMainPath = true;
				if (js.mg.getHalbeMap()[p.x + dir.width * counter][p.y + dir.height * counter] != Path.TILE_ID) {
					allPointsOnMainPath = false;
					continue;
				}
			}
			counter++;
		}
		while(!allPointsOnMainPath);
		
		int goalX = this.getConnectionLine().getCoords().get(0).x + counter * dir.width;
		goalX += dir.width * 1;
		int goalY = this.getConnectionLine().getCoords().get(0).y + counter * dir.height;
		goalY += dir.height * 1;
		
		Point goal = new Point(goalX, goalY);
		return goal;
	}

	@Override
	public final Dimension getPathSize() {
		return super.getPathSize();
	}
}
