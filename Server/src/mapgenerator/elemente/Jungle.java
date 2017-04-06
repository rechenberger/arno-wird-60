package mapgenerator.elemente;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;


import util.MSTPrime;
import util.NeighbourGraph;
import util.graph.NeighbourVertex;

import mapgenerator.Mapgenerator;
import mapgenerator.elemente.jungleelemente.JungleSector;
import mapgenerator.elemente.jungleelemente.JungleSpawnArea;

/**
 * Diese Klasse ist fuer die Berechnung der JungleSectoren zustaendig.
 * <br>
 * Zuerst wird der Punkt berechnet, an dem der Erste JungleSector platziert werden darf.
 * Bei dieser Berechnung wird von der unteren Basis ausgegangen. D.h. der JungleSector welcher
 * am Naechsten zur unteren Basis ist wird als erstes platziert.
 * <br>
 * Von diesem ersten Sector wird nun in alle Vier Richtungen(oben, unten, links, rechts)
 * eine rekursive Abwandlung der <b>Breitensuche</b> durchgefuehrt.
 * <br>
 * Die Breitensuche wird durch die Methode <i>calculateSectors()</i> angestossen und dann Rekursiv
 * durch die Methode <i>getAdjacentJungleSectors()</i> fortgefuehrt.
 * <br>
 * Die Breitensuche guckt fuer jede Richtung ob dort noch ein JungleSector platziert werden darf, 
 * ohne das dieser sich mit dem Hauptpfad oder dem Ende der halben Karte 
 * ueberschneidet. 
 * <br>
 * Wenn diese Bedingungen erfuellt sind wird ein neues Junglesector Objekt instanziert
 * und gleichzeitig auch die Connectionlines berechnet.
 * <br>
 * Hierbei wird auch darauf geachtet ob der gefundene JungleSector bereits vorher schon gefunden wurde,
 * falls ja, wird in dem gefunden JungleSector nur die neue ConnectionLine berechnet.
 * <br>
 * Der <b>Junglesector</b> wird dann als <b>Vertex</b> in einem <b>Graphen</b> gespeichert
 * und die <b>ConnectionLine</b> als zugehoeriger <b>Arc</b>.
 * <br>
 * Dieser Arc wird dann mit zufaelligen Kosten versehen, welche fuer den Naechsten Schritt relevant sind.
 * <br>
 * Der Naechste Schritt besteht dann darin aus dem nun enstandenen Graphen einen Minimal Spanning tree zu berechnen.
 * <br>
 * Damit das ganze auch von Berechnung zu Berechnung unterschiedlich ist, 
 * werden fuer die Berechnung des <b>Minimal Spanning Trees</b> die zufaelling zugewiesenen Kosten benutzt.
 * 
 * 
 * 
 * @author Marius
 *
 */
public class Jungle {

	/**
	 * total unnoetig hier ne Konstante draus zu machen, aber sonst meckert Checkstyle.
	 * Der Wert ist eigentlich egal solange er ueber 2 liegt.
	 */
	private static final int UPPERBOUND_RANDOMNUMBER = 10;
	
	/**
	 * Das Object des Mapgenerator, in welchem der Jungle plaziert werden soll.
	 */
	private Mapgenerator mg;
	
	/**
	 * Diese Map schafft eine Realation zwischen dem <b>unteren linken Punkt</b> eines JungleSectors
	 * <br>
	 * und der VertexId welcher dieser Sektor im Graph hat.
	 * <br>
	 * ausserdem kann hier ueberprueft werden ob ein JungleSector schon besucht wurde.
	 */
	private HashMap<Point, Integer> jsContainer = new HashMap<Point, Integer>();
	
	/**
	 * Hier werden alle jungleSectoren als Vertex gespeichert.
	 * <br>
	 * Sektoren die benachbart sind haben ein Arc zueinander.
	 */
	private NeighbourGraph<JungleSector> g = new NeighbourGraph<JungleSector>();
	
	/**
	 * der Punkt ab welchem der Erste JungleSector plaziert werden kann.
	 * ist gleichzeitig die untere linke Ecke des Ersten Sectors.
	 * <br> Dieser JungleSector ist auch der welcher am Naechsten zu Basis ist.
	 */
	private Point start;
	/**
	 * Untere Ecke des Jungles.
	 */
	private Point jungleBottom;
	/**
	 * Oben Links vom Jungle.
	 */
	private Point jungleTopLeft;
	
	/**
	 * Oben Rechts vom Jungle
	 * TODO FIXME ist nicht wirklich die obere linke Ecke. Momentan wird der Jungle durch Bottom TopLeft und dem Ende der halben Map(-1 in Map) begrenzt.
	 * Funktioniert aber ganz gut so.
	 */
	@SuppressWarnings("unused")
	private Point jungleTopRight;

	/**
	 * Berechnet die Aussenpunkte des Jungels, dies sind die drei Eckpunkte.
	 * 
	 * @param mapg das Mapgenerator Objekt
	 */
	public Jungle(final Mapgenerator mapg) {
		this.mg = mapg;

		//ergibt sich aus der Bedingung das der Jungel eine bestimmte Distanz unterhalb des oberen Weges laufen muss
		int yJungleHasToBeBelow = mg.getTopPath().getTopLeft().y + mg.getTopPath().getSize() + Mapgenerator.DIST_BETWEEN_JUNGLE_AND_PATH;
		
		//ergibt sich aus der Bedingnung das der Jungel erst ab eiber bestimmten Distanz vom linken Weg anfangen darf
		int xJungleHasToBeOnTheRight = Mapgenerator.DIST_BETWEEN_JUNGLE_AND_PATH + mg.getTopPath().getBottomRight().x;
		
		//BR = BottomRight
		//Da der Mittlere Pfad immer x+1 y+1 geht, kann man aus diesem Wert die x Koordinate ableiten, welche der Mittlere Pfad bei auf der hoehe vom top Pfad hat.
		int ydistBetweenWaysAtBR = Math.abs(mg.getTopPath().getBottomRight().y - mg.getMiddlePath().getBottomLeft().y);
		//Die Distanz in X Richtung wenn nur die x Koordinate veraendert wird zwischen TopPathBottomRight and MiddlePath;
		int xDistBetweenWaysAtBR = Math.abs(mg.getMiddlePath().getBottomLeft().x + ydistBetweenWaysAtBR - mg.getTopPath().getBottomRight().x);
	
		//Diese Distanz muss auf gerader Linie(durch veraendern der x Koordinate) mindestens zwischen beiden Pfaden vorhanden sein fuer einen Sector.
		int leastDistBetweenPathsToPlaceSector = Mapgenerator.DIST_BETWEEN_JUNGLE_AND_PATH * 2 + JungleSector.getSize(mg.getSize()).width;
		
		//Diese Distanz muss vom BR Top Pfad noch nach oben gegangen werden um leastDistBetweenPathsToPlaceSector zu erfuellen
		int yToGoFromBRToPlaceFirstSector = Math.abs(leastDistBetweenPathsToPlaceSector - xDistBetweenWaysAtBR);
		
		//Die Koordinate wo leastDistBetweenPathsToPlaceSector erfuellt ist
		int yCoordToPlaceFirstSector = mg.getTopPath().getBottomRight().y - yToGoFromBRToPlaceFirstSector;

		//Wie viele Sectoren von Bottom to Topleft oder von Topleft to Topright plaziert werden koennen
		int howManySectorsCanExistInOneDirection = (int) Math.abs((yJungleHasToBeBelow - yCoordToPlaceFirstSector)) / JungleSector.getSize(mg.getSize()).height;
		
		
		start = new Point(xJungleHasToBeOnTheRight, yCoordToPlaceFirstSector);
		
		//Punkt der direkt an der Basis liegt, unterster Punkt im Jungle
		jungleBottom = new Point(xJungleHasToBeOnTheRight, yCoordToPlaceFirstSector + JungleSector.getSize(mg.getSize()).height);
		
		//Punkt der an der oberen linken Ecke linkt
		jungleTopLeft = new Point(xJungleHasToBeOnTheRight, 
					yCoordToPlaceFirstSector
					- (howManySectorsCanExistInOneDirection * JungleSector.getSize(mg.getSize()).height));
		
		//Punkt an de gegnerischen Basis
		jungleTopRight = new Point(xJungleHasToBeOnTheRight
					+ (JungleSector.getSize(mg.getSize()).width * howManySectorsCanExistInOneDirection),
					yCoordToPlaceFirstSector 
					- (howManySectorsCanExistInOneDirection * JungleSector.getSize(mg.getSize()).height));
		
		this.calculateSectors();
		this.updateConnectionLine();
	}
	
	/**
	 * ueberprueft ob ein gegebener Punkt im Jungle liegt(im durch dir Drei Punkte angegebenen Dreieck).
	 * @param p der Punkt den es zu ueberpruefen gilt
	 * @return ob der Punkt im FieldSet liegt
	 */
	private boolean isPointInJungle(final Point p) {
		
		if (p.y >= jungleTopLeft.y &&  p.y <= jungleBottom.y && p.x >= jungleTopLeft.x) {
			if (p.x <= getXValueOnLineBetweenTopRightAndBottomByY(p.y) && mg.getHalbeMap()[p.x][p.y] != -1) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Ueberprueft ob ein JungleSector im Jungel liegt.
	 * Ein JungleSector liegt nicht im Jungle, wenn er ausserhalb des Dreiecks liegt welche den Jungle definiert,
	 * oder wenn er innerhalb dieses Dreickecs liegt, aber einer der 4 Eckpunkte des Jungles auf der Karte den Wert -1 hat.
	 * @param j den zu ueberpruefenden JungleSector.
	 * @return true wenn der JungleSector im Jungle liegt
	 */
	private boolean isJungleSectorInJungle(final JungleSector j) {
		
		
		if (this.isPointInJungle(j.getBottomLeft()) && this.isPointInJungle(j.getBottomRight())) {
			if (this.isPointInJungle(j.getTopLeft()) && this.isPointInJungle(j.getTopRight())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Ueberprueft ob ein Viereck im Jungel liegt.
	 * Ein Viereck liegt nicht im Jungle, wenn er ausserhalb des Dreiecks liegt welche den Jungle definiert,
	 * oder wenn er innerhalb dieses Dreickecs liegt, aber einer der 4 Eckpunkte des Jungles auf der Karte den Wert -1 hat.

	 * @param bL  die untere linke Ecke des Vierecks
	 * @param direction die Hoehe und breite des Vierecks.
	 * @return true wenn das Viereck im Jungle liegt
	 */
	private boolean isRectangleInJungle(final Point bL, final Point direction) {
		if (isPointInJungle(bL) && isPointInJungle(new Point(bL.x, bL.y + direction.y))) {
			if (isPointInJungle(new Point(bL.x + direction.x, bL.y)) && isPointInJungle(new Point(bL.x + direction.x, bL.y + direction.y))) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Gibt den x Wert eines Punktes auf der Linie zwischen den Begrenzungspunkten des Jungels (oben links und unten).
	 * @param y der Y Wert des Punktes
	 * @return der X Wert des Punktes
	 */
	private int getXValueOnLineBetweenTopRightAndBottomByY(final int y) {
		int distBetweenBottomYAndY = jungleBottom.y - y;
		return jungleBottom.x + distBetweenBottomYAndY;
	}
	
	/**
	 * Berechnet alle Moeglichen <b>JungleSectoren</b> die im Jungle liegen ausgehend von der <b>unteren Ecke</b> des Jungle.
	 * <br>
	 * Hierzu wird in dieser Methode zuerst vom startPunkt der erste JungleSector erstellt, 
	 * <br>
	 * danach wird die Methode <i>getAdjacentJungleSectors()</i> aufgerufen,
	 * <br>
	 * welche zuerst alle benachbarten JungleSectoren ueberprueft und instanziert. 
	 * <br>
	 * Danach wird mit der Liste an neuen Nachbarn die Methode <i>Recursiv</i> erneut aufgerufen.
	 * <br>
	 * dies geschiet solange bis keine neuen Sectoren mehr gefunden werden. 
	 */
	private void calculateSectors() {
		
		
		JungleSector sector = new JungleSector(mg, start);
		int id = g.addNeighbourVertex(sector);
		this.isJungleSectorInJungle(sector);
		this.jsContainer.put(sector.getBottomLeft(), id);
		ArrayList<JungleSector> adjacencies = new ArrayList<JungleSector>();
		adjacencies.add(sector);
		this.getAdjacentJungleSectors(adjacencies);
		
		
	}
	
	/**
	 * Ueberprueft die uebergebene Liste an JungleSectoren auf weitere Nachbarn.
	 * <br>
	 * Ruft sich selbst Recursiv auf bis keine neuen Nachbarn mehr gefunden werden.
	 * @param j Die Liste an bisher gefunden JungleSectoren die noch auf Nachbarn ueberprueft werden muessen.
	 * @return eine Liste an neuen JungleSectoren welche benachbart zu der uebergebenen Liste sind und auch noch auf Nachbarn ueberprueft werden muessen.
	 */
	private ArrayList<JungleSector> getAdjacentJungleSectors(final ArrayList<JungleSector> j) {
		
		ArrayList<JungleSector> adjacencies = new ArrayList<JungleSector>();
		
		for (JungleSector adj : j) {
			
			JungleSector top = this.getTopAjdacentJungleSector(adj);
			if (top != null) {


				int id = g.addNeighbourVertex(top);
				g.addUndirectedTopArc(jsContainer.get(adj.getBottomLeft()), id, ((int) Math.rint(Math.random() * UPPERBOUND_RANDOMNUMBER) + 1));
				//js.add(top);
				this.jsContainer.put(top.getBottomLeft(), id);
				
				adjacencies.add(top);
			}
			
			JungleSector left = this.getLeftAjdacentJungleSector(adj);
			if (left != null) {
				

				int id = g.addNeighbourVertex(left);
				g.addUndirectedLeftArc(jsContainer.get(adj.getBottomLeft()), id, ((int) Math.rint(Math.random() * UPPERBOUND_RANDOMNUMBER) + 1));
				//js.add(left);
				this.jsContainer.put(left.getBottomLeft(), id);
				
				adjacencies.add(left);
			}
			
			JungleSector bottom = this.getBottomAjdacentJungleSector(adj);
			if (bottom != null) {
				


				int id = g.addNeighbourVertex(bottom);
				g.addUndirectedBottomArc(jsContainer.get(adj.getBottomLeft()), id, ((int) Math.rint(Math.random() * UPPERBOUND_RANDOMNUMBER) + 1));
				//js.add(bottom);
				this.jsContainer.put(bottom.getBottomLeft(), id);
				
				adjacencies.add(bottom);
			}
			
			JungleSector right = this.getRightAjdacentJungleSector(adj);
			if (right != null) {



				int id = g.addNeighbourVertex(right);
				g.addUndirectedRightArc(jsContainer.get(adj.getBottomLeft()), id,  ((int) Math.rint(Math.random() * UPPERBOUND_RANDOMNUMBER) + 1));
				//js.add(right);
				this.jsContainer.put(right.getBottomLeft(), id);
				
				adjacencies.add(right);
			}	
		}
		
		if (!adjacencies.isEmpty()) {
			adjacencies.addAll(this.getAdjacentJungleSectors(adjacencies));
		}
		return adjacencies;
	}
	
	/**
	 * Ueberprueft ob ein JungleSector einen Nachbarn nach oben hin hat.
	 * <br>
	 * Falls dies so ist wird die ConnectionLine zwischen diesem beiden Sectoren berechnet(falls die noch nicht geschehen ist)
	 * <br>
	 * <br>
	 * Falls der Nachbar bereits aus einer anderen Richtung besucht wurde,
	 * <br>
	 * wird nur die neue ConnectionLine zu beiden Jungel Sektoren hinzugefuegt.
	 * @param j der JungleSector welcher auf einen Nachbarn ueberprueft werden soll.
	 * @return Den Nachbarn(nur falls dieser noch nicht besucht wurde).
	 */
	private JungleSector getTopAjdacentJungleSector(final JungleSector j) {
		
		

		
		Point direction = new Point(JungleSector.getSize(mg.getSize()).width, JungleSector.getSize(mg.getSize()).width * -1);
		Point bottomLeftOfAdjacent = j.getTopLeft();
		
		//ueberprueft ob der Anwaerter im Jungle ist, wenn nicht gib null zurueck
		if (isRectangleInJungle(j.getTopLeft(), direction)) {			
			//Wenn die neue Sektor im Jungle liegt und schon existiert muessen nurnoch die ConnectionLines hinzugefuegt werden.
			if (jsContainer.containsKey(bottomLeftOfAdjacent)) {
				//Wenn der neue Sektor noch keine ConnectionLine zum alten hin hat, wird hier eine erstellt.
				if (g.getNeighbourVertex(jsContainer.get(bottomLeftOfAdjacent))
						.getValue()
						.getBottomConnection()
						.isEmpty()) {
					
					if (j.getTopConnection().isEmpty()) {
						j.calculateTopConnection();
					}
					
					g.getNeighbourVertex(jsContainer.get(bottomLeftOfAdjacent)).getValue().setBottomConnection(j.getTopConnection());
					g.addUndirectedTopArc(jsContainer.get(j.getBottomLeft()), jsContainer.get(bottomLeftOfAdjacent), ((int) Math.rint(Math.random() * UPPERBOUND_RANDOMNUMBER) + 1));
				}
			//Sektor existiert noch nicht, erstelle neuen und setzte ConnectionLine.
			} else {
			
			JungleSector topAdjacent = new JungleSector(mg, j.getTopLeft());
			j.calculateTopConnection();
			topAdjacent.setBottomConnection(j.getTopConnection());
			return topAdjacent;
			}
		}
		return null;
	}
	
	/**
	 * Ueberprueft ob ein JungleSector einen Nachbarn nach Links hin hat.
	 * <br>
	 * Falls dies so ist wird die ConnectionLine zwischen diesem beiden Sectoren berechnet(falls dies noch nicht geschehen ist)
	 * <br>
	 * <br>
	 * Falls der Nachbar bereits aus einer anderen Richtung besucht wurde,
	 * <br>
	 * wird nur die neue ConnectionLine zu beiden Jungel Sektoren hinzugefuegt.
	 * @param j der JungleSector welcher auf einen Nachbarn ueberprueft werden soll.
	 * @return Den Nachbarn(nur falls dieser noch nicht besucht wurde).
	 */
	private JungleSector getLeftAjdacentJungleSector(final JungleSector j) {
		
		Point direction = new Point(JungleSector.getSize(mg.getSize()).width, JungleSector.getSize(mg.getSize()).width * -1);
		Point bottomLeftOfAdjacent = new Point(j.getBottomLeft().x - JungleSector.getSize(mg.getSize()).width, j.getBottomLeft().y);
		//ueberprueft ob der Anwaerter im Jungle ist, wenn nicht gib null zurueck
		if (isRectangleInJungle(bottomLeftOfAdjacent, direction)) {
			//Wenn die neue Sektor im Jungle liegt und schon existiert muessen nurnoch die ConnectionLines hinzugefuegt werden.
			if (jsContainer.containsKey(bottomLeftOfAdjacent)) {
				//Wenn der neue Sektor noch keine ConnectionLine zum alten hin hat, wird hier eine erstellt.
				if (g.getNeighbourVertex(jsContainer.get(bottomLeftOfAdjacent)).getValue().getRightConnection().isEmpty()) {
					if (j.getLeftConnection().isEmpty()) {
						j.calculateLeftConnection();
					}
					g.getNeighbourVertex(jsContainer.get(bottomLeftOfAdjacent)).getValue().setRightConnection(j.getLeftConnection());
					g.addUndirectedLeftArc(jsContainer.get(j.getBottomLeft()), jsContainer.get(bottomLeftOfAdjacent), ((int) Math.rint(Math.random() * UPPERBOUND_RANDOMNUMBER) + 1));
				}
			//Sektor existiert noch nicht, erstelle neuen und setzte ConnectionLine.
			} else {
				JungleSector leftAdjacent = new JungleSector(mg, bottomLeftOfAdjacent);
				j.calculateLeftConnection();
				leftAdjacent.setRightConnection(j.getLeftConnection());
				return leftAdjacent;
			}
		}
		return null;
	}
	
	/**
	 * Ueberprueft ob ein JungleSector einen Nachbarn nach Unten hin hat.
	 * <br>
	 * Falls dies so ist wird die ConnectionLine zwischen diesem beiden Sectoren berechnet(falls dies noch nicht geschehen ist)
	 * <br>
	 * <br>
	 * Falls der Nachbar bereits aus einer anderen Richtung besucht wurde,
	 * <br>
	 * wird nur die neue ConnectionLine zu beiden Jungel Sektoren hinzugefuegt.
	 * @param j der JungleSector welcher auf einen Nachbarn ueberprueft werden soll.
	 * @return Den Nachbarn(nur falls dieser noch nicht besucht wurde).
	 */
	private JungleSector getBottomAjdacentJungleSector(final JungleSector j) {
		
		Point direction = new Point(JungleSector.getSize(mg.getSize()).width, JungleSector.getSize(mg.getSize()).width * -1);
		Point bottomLeftOfAdjacent = new Point(j.getBottomLeft().x, j.getBottomLeft().y + JungleSector.getSize(mg.getSize()).height);
		//ueberprueft ob der Anwaerter im Jungle ist, wenn nicht gib null zurueck
		if (isRectangleInJungle(bottomLeftOfAdjacent, direction)) {
			//Wenn die neue Sektor im Jungle liegt und schon existiert muessen nurnoch die ConnectionLines hinzugefuegt werden.
			if (jsContainer.containsKey(bottomLeftOfAdjacent)) {
				//Wenn der neue Sektor noch keine ConnectionLine zum alten hin hat, wird hier eine erstellt.
				if (g.getNeighbourVertex(jsContainer.get(bottomLeftOfAdjacent)).getValue().getTopConnection().isEmpty()) {
					if (j.getBottomConnection().isEmpty()) {
						j.calculateBottomConnection();
					}
					g.getNeighbourVertex(jsContainer.get(bottomLeftOfAdjacent)).getValue().setTopConnection(j.getBottomConnection());
					g.addUndirectedBottomArc(jsContainer.get(j.getBottomLeft()), jsContainer.get(bottomLeftOfAdjacent), ((int) Math.rint(Math.random() * UPPERBOUND_RANDOMNUMBER) + 1));
				}
			//Sektor existiert noch nicht, erstelle neuen und setzte ConnectionLine.
			} else {
				JungleSector bottomAdjacent = new JungleSector(mg, bottomLeftOfAdjacent);
				j.calculateBottomConnection();
				bottomAdjacent.setTopConnection(j.getBottomConnection());
				return bottomAdjacent;
			}
		}
		return null;
	}
	
	/**
	 * Ueberprueft ob ein JungleSector einen Nachbarn nach Unten hin hat.
	 * <br>
	 * Falls dies so ist wird die ConnectionLine zwischen diesem beiden Sectoren berechnet(falls dies noch nicht geschehen ist)
	 * <br>
	 * <br>
	 * Falls der Nachbar bereits aus einer anderen Richtung besucht wurde,
	 * <br>
	 * wird nur die neue ConnectionLine zu beiden Jungel Sektoren hinzugefuegt.
	 * @param j der JungleSector welcher auf einen Nachbarn ueberprueft werden soll.
	 * @return Den Nachbarn(nur falls dieser noch nicht besucht wurde).
	 */
	private JungleSector getRightAjdacentJungleSector(final JungleSector j) {
		
		Point direction = new Point(JungleSector.getSize(mg.getSize()).width, JungleSector.getSize(mg.getSize()).width * -1);
		Point bottomLeftOfAdjacent = j.getBottomRight();
		//ueberprueft ob der Anwaerter im Jungle ist, wenn nicht gib null zurueck
		if (isRectangleInJungle(j.getBottomRight(), direction)) {
			//Wenn die neue Sektor im Jungle liegt und schon existiert muessen nurnoch die ConnectionLines hinzugefuegt werden.
			if (jsContainer.containsKey(bottomLeftOfAdjacent)) {
				//Wenn der neue Sektor noch keine ConnectionLine zum alten hin hat, wird hier eine erstellt.
				if (g.getNeighbourVertex(jsContainer.get(bottomLeftOfAdjacent)).getValue().getLeftConnection().isEmpty()) {
					if (j.getRightConnection().isEmpty()) {
						j.calculateBottomConnection();
					}
					g.getNeighbourVertex(jsContainer.get(bottomLeftOfAdjacent)).getValue().setLeftConnection(j.getRightConnection());
					g.addUndirectedRightArc(jsContainer.get(j.getBottomLeft()), jsContainer.get(bottomLeftOfAdjacent), ((int) Math.rint(Math.random() * UPPERBOUND_RANDOMNUMBER) + 1));
				}
			//Sektor existiert noch nicht, erstelle neuen und setzte ConnectionLine.
			} else {
				JungleSector rightAdjacent = new JungleSector(mg, j.getBottomRight());
				j.calculateRightConnection();
				rightAdjacent.setLeftConnection(j.getRightConnection());
				return rightAdjacent;
			}
		}
		return null;
	}
	
	/**
	 * Bestimmt fuer jeden JungleSector welche seiner ConnectionLines wirklich auf die Karte geschrieben werden.
	 * Wird durch MST berechnung bestimmt.
	 */
	@SuppressWarnings("rawtypes")
	private void updateConnectionLine() {
		MSTPrime<JungleSector> mst = new MSTPrime<JungleSector>(g); 
		NeighbourGraph<JungleSector> g1 = mst.run();
		
		for (NeighbourVertex v :  g1.getNeighbourVertexArr()) {
			if (v.getTopArc() == null) {
				((JungleSector) v.getValue()).setPrintTopConnection(false);
			}
			if (v.getBottomArc() == null) {
				((JungleSector) v.getValue()).setPrintBottomConnection(false);
			}
			if (v.getRightArc() == null) {
				((JungleSector) v.getValue()).setPrintRightConnection(false);
			}
			if (v.getLeftArc() == null) {
				((JungleSector) v.getValue()).setPrintLeftConnection(false);
			}
			((JungleSector) v.getValue()).calculateJunglePath();
		}
	}
	
	/**
	 * Ruft die Methode CoordsOnMap fuer alle Mapelemente auf die sich in dem aktuellen Element befinden.
	 * Wenn das aktuelle Element auf die Karte geschrieben wird, wird das auch hier gemacht.
	 */
	public void coordsOnMap() {
		for (NeighbourVertex<JungleSector> v : g.getNeighbourVertexArr()) {
			v.getValue().coordsOnMap();
		}
	}
	
	/**
	 * Ruft die Methode coordsOnMapReflectedByMiddlePath fuer alle Mapelemente auf die sich in dem aktuellen Element befinden.
	 * Wenn das aktuelle Element auf die Karte geschrieben wird, wird das auch hier gemacht.
	 * <br>
	 * Hier werden allerdings die Elemente gespiegelt auf die Karte gesetzt.
	 */
	public void coordsOnMapReflectedByMiddlePath() {
		for (NeighbourVertex<JungleSector> v : g.getNeighbourVertexArr()) {
			v.getValue().coordsOnMapReflectedByMiddlePath();
		}
	}
	
	/**
	 * Gibt Alle SpawnAreas einer Junglehaelfte zurueck. 
	 * Sowohl die von Team A als auch Team B. Nicht den Jungle zwischen MittelPath und BottomPath.
	 * @return Die SpawnGebiete
	 */
	public LinkedList<JungleSpawnArea> getSpawnAreas() {
		
		LinkedList<JungleSpawnArea> allSpawns = new LinkedList<JungleSpawnArea>();
		
		for (NeighbourVertex<JungleSector> n : g.getNeighbourVertexArr()) {
			if (n.getValue().getSpawnArea() != null && !n.getValue().getSpawnArea().getCoords().isEmpty()) {
				allSpawns.add(n.getValue().getSpawnArea());
			}
		}
		return allSpawns;
	}
}
