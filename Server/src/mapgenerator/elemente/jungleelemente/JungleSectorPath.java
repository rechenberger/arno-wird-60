package mapgenerator.elemente.jungleelemente;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import mapgenerator.elemente.abstractclasses.Path;

import util.AStarFieldSet;
import util.AStarFieldSetAble;
import util.Field;
import util.geometrie.Area;

/**
 * 
 * Hier wird ein <b>Weg</b> in einem JungleSector berechnet.
 * <br>
 * Dieser Weg fuehrt immer von einer ConnectionLine zur Mitte des SpawnPoints oder,
 * <br>
 * falls dieser nicht vorhanden ist,
 * <br>
 * zu der Mitte des JungleSectors.
 * <br>
 * Dieser Weg wird ueber <b>AStarFieldSet</b> berechnet.
 * <br>
 * Diese Klasse wird fuer jede ConnectionLine in einem Jungle einmal instanziert.
 * 
 * @author Marius
 *
 */
public class JungleSectorPath extends Area implements AStarFieldSetAble {

	/**
	 * Die TileId des JungleSectors.
	 */
	public static final int TILE_ID = 51;
	
	/**
	 * Verhaeltniss zwischen JungleSector groesse und Jungle Path groesse.
	 */
	public static final int PROPOTION_JUNGLESECTOR_PATH = 4;

	/**
	 * Die Bewegungen die AStar zusaetzlich zu den standardbewegungen ausfuehren darf.
	 */
	protected Point[] directionToCreateFieldSet = {new Point(1, 1), new Point(-1, 1), new Point(-1, -1), new Point(1, -1)};
	
	
	/**
	 * Das Object des JungleSectors.
	 */
	protected JungleSector js;
	
	/**
	 * Die Verbindungslinie von der aus ein Weg berechnet wird.
	 */
	protected JungleConnectionLine connectionLine;
	
	/**
	 * Konstruktor.
	 * @param conLine Das Objekt der ConnectionLine.
	 * @param jungleSector Das Objekt des JungleSectors.
	 */
	public JungleSectorPath(final JungleConnectionLine conLine, final JungleSector jungleSector) {
		super();
		js = jungleSector;
		connectionLine = conLine;
	}
	
	/**
	 * Fuehrt AStar aus, welcher einen Weg vom Start zum Zielpunkt findet.
	 */
	public void calculatePath() {
		AStarFieldSet a = new AStarFieldSet(this);
		
		//AStar bewegt sich standartmaesig nur pro schritt in eine Richtung +x oder +y
		Point[] oldLegalMovements =  a.getLegalMovements();
		Point[] newLegalMovements = new Point[oldLegalMovements.length + directionToCreateFieldSet.length];
		//Hier werden auch noch die Bewegungen +x UND +y hinzugefuegt.
		for (int i = 0; i < oldLegalMovements.length; i++) {
			newLegalMovements[i] = oldLegalMovements[i];
		}
		int oldMovementsLenght = oldLegalMovements.length;
		newLegalMovements[oldMovementsLenght] = new Point(1, 1);
		oldMovementsLenght++;
		newLegalMovements[oldMovementsLenght] = new Point(1, -1);
		oldMovementsLenght++;
		newLegalMovements[oldMovementsLenght] = new Point(-1, 1);
		oldMovementsLenght++;
		newLegalMovements[oldMovementsLenght] = new Point(-1, -1);
		a.setLegalMovements(newLegalMovements);
		
		this.setCoords(a.run());
		//coordsOnMap();
	}

	
	@Override
	public ArrayList<Field<Integer>> getMovableFields() {
		
		ArrayList<Field<Integer>> moveableFieds = new ArrayList<Field<Integer>>();
		
		coordsLoop:
		for (Point p : js.getCoords()) {
			for (JungleSectorPath jsp : js.getPaths()) {
				if (jsp.contains(p)) {
					moveableFieds.add(new Field<Integer>(p , HIGH_COSTS_FOR_FIELD));
					continue coordsLoop;
				} 
				for (Point dir : directionToCreateFieldSet) {
					if (js.getCoords().contains(new Point(p.x + dir.x, p.y + dir.y)) && jsp.contains(new Point(p.x + dir.x, p.y + dir.y))) {
						moveableFieds.add(new Field<Integer>(p , HIGH_COSTS_FOR_FIELD));	
					}
				}
			}
			
			if (!js.getDistBetweenEndOfSectorAndSpawnAreaCoords().contains(p)) {
				moveableFieds.add(new Field<Integer>(p , COSTS_FOR_FIELD));
			} else {
				moveableFieds.add(new Field<Integer>(p , HIGH_COSTS_FOR_FIELD));
			}
		}
		
		return moveableFieds;
	}

	@Override
	public Dimension getAreaDimension() {
		return js.mg.getSize();
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
		for (Point dir : directionToCreateFieldSet) {
			
			fieldSet.clear(); //alte Daten aus FieldSet raus
			if (inXDir == 1 && dir.x < 0 || inYDir == 1 && dir.y < 0)  {
				startAt = max; // Wenn wir in minus x oder Y gehen muessen wir beim Punkt mit dem groessten Wert anfangen
			} else if (inXDir == 1 && dir.x > 0 || inYDir == 1 && dir.y > 0) {
				startAt = min; // Wenn wir in plus x oder Y gehen muessen wir beim Punkt mit dem kleinsten Wert anfangen
			}
			
			//Spanne FieldSet auf
			for (int i = startAt.y;
							(i < startAt.y + getPathSize().height)
							&& ((i > startAt.y + (dir.y * getPathSize().height))
							|| dir.y != -1);
					i += 1 * dir.y) {
				
				for (int j = startAt.x;
								(j < startAt.x + getPathSize().width)
								&& ((j > startAt.x + (dir.x * getPathSize().width))
								|| dir.x != -1); 
						j += 1 * dir.x) {
					
					//ist Koordinate im JungleSector, dann ist sie gueltig
					if (!js.getCoords().contains(new Point(j, i))) {
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
	public Point getGoal() {
		Point goal = null;
		//Wenn es keinen SpawnArea gibt nimm die Mitte 
		if (js.getSpawnArea().isEmpty()) {
			
			goal = new Point(js.getTopLeft().x + (js.getSize().width / 2), 
							js.getTopLeft().y + (js.getSize().height / 2));
		
		} else { // Wenn es ein SpawnArea gibt nimm den MittelPunkt von Area
			
			Point minPoint = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
			for (Point p : js.getSpawnArea().getCoords()) {
				if (p.x <= minPoint.x && p.y <= minPoint.y) {
					minPoint = p;
				}
			}
			
			goal = new Point(minPoint.x + (js.getSpawnArea().getSize().width / 2), 
							minPoint.y + (js.getSpawnArea().getSize().height / 2));
		
		}
		return goal;
	}

	/**
	 * @return the jungleSector
	 */
	public JungleSector getJungleSector() {
		return js;
	}

	/**
	 * @param jungleS the jungleSector to set
	 */
	public void setJungleSector(final JungleSector jungleS) {
		this.js = jungleS;
	}


	@Override
	public Dimension getPathSize() {
		 return new Dimension(js.getSize().width / JungleSectorPath.PROPOTION_JUNGLESECTOR_PATH, js.getSize().height / JungleSectorPath.PROPOTION_JUNGLESECTOR_PATH); 
	}
	
	/**
	 * Ruft die Methode CoordsOnMap fuer alle Mapelemente auf die sich in dem aktuellen Element befinden.
	 * Wenn das aktuelle Element auf die Karte geschrieben wird, wird das auch hier gemacht.
	 */
	public void coordsOnMap() {
		
		Set<Integer> toIgnore = new HashSet<Integer>();
		toIgnore.add(Path.TILE_ID);
		coordsOnMap(js.mg.getHalbeMap(), TILE_ID, toIgnore);

	}
	
	/**
	 * Ruft die Methode coordsOnMapReflectedByMiddlePath fuer alle Mapelemente auf die sich in dem aktuellen Element befinden.
	 * Wenn das aktuelle Element auf die Karte geschrieben wird, wird das auch hier gemacht.
	 * <br>
	 * Hier werden allerdings die Elemente gespiegelt auf die Karte gesetzt.
	 */
	public void coordsOnMapReflectedByMiddlePath() {
		Set<Integer> toIgnore = new HashSet<Integer>();
		toIgnore.add(Path.TILE_ID);
		coordsOnMapReflectedByMiddlePath(js.mg.getHalbeMap(), TILE_ID, toIgnore);
	}


	/**
	 * @return the connectionLine
	 */
	protected JungleConnectionLine getConnectionLine() {
		return connectionLine;
	}

}
