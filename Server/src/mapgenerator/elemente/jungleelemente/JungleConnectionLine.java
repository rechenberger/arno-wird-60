package mapgenerator.elemente.jungleelemente;


import java.awt.Point;

import util.geometrie.Area;
import util.geometrie.Points;

/**
 * Diese Klasse ist fuer <b>die Verbindung zweier JungleSectoren</b> zustaendig.
 * Zwei benachbarte JungleSectoren teilen sich eine instanz dieser Klasse.
 * <br>
 * Durch eine ConnectionLine wird nur ein Weg berechnet, 
 * wenn diese Verbindung auch im berechneten MST
 * vorhanden ist. 
 * <br>
 * Falls dies nicht der Fall ist wird die Variable 
 * <i>JungleSector.print\<Direction\>Connection</i>
 * auf <i>false</i> gesetzt und und die Methode <i>calculateConnectionLine()</i> wird nicht aufgerufen.
 * 
 * @author Marius
 *
 */
public class JungleConnectionLine extends Area {
	
	/**
	 * Verhaeltniss zwischen JungleSector groesse und minimale Entfernung von einer ConnectionLine zum Eckpunkt.
	 */
	protected static final int PROPOTION_DIST_ADJACENTCONNECTION_JUNGLESECTOR_EDGE_POINT = 45;
	
	/**
	 * TileId fuer ConnectionLine.
	 */
	public static final int TILE_ID = 50;

	/**
	 * Das Objekt des JungleSektors in welchem die ConnectionLine instanziert werden soll.
	 */
	private JungleSector js;
	
	/**
	 * die Mindestdistanz zwischen einer Ecke des JungleSectors und der ConnectionLine.
	 * <br>
	 * berechnet sich im Konstruktor durch die Proportion <b>PROPOTION_DIST_ADJACENTCONNECTION_JUNGLESECTOR_POINT</b> und der JungleSectorgroesse.
	 */
	private int distBetweenEndOfSectorAndConnection;
	
	/**
	 * Ob die ConnectionLine ausgegeben werden soll.
	 */
	private boolean printConnectionLine = true;
	
	/**
	 * Anders als bei den anderen Klassen wird hier <b>NICHT</b> bei der instanzierung die Berechnung der 
	 * Koordinaten vorgenommen.
	 * <br>
	 * Dies muss durch die Methode <i>calculateConnectionLine()</i> angestossen werden.
	 * @param jungleS Das Objekt des JungleSektors.
	 */
	public JungleConnectionLine(final JungleSector jungleS) {
		js = jungleS;
		distBetweenEndOfSectorAndConnection = js.getSize().height / PROPOTION_DIST_ADJACENTCONNECTION_JUNGLESECTOR_EDGE_POINT;
	}
	
	/**
	 * 	/**
	 * Speichert die Koordinaten die auf einer Linie zwischen start und Goal liegen.
	 * <br>
	 * <b>Setzt diese Pubkte nur auf die Karte wenn WRITE_CONNECTION_ON_MAP_WHEN_INSTANTIATED == true ist.</b>
	 * <br>
	 * wird dazu verwendet die ConnectionLine zwischen zwei JungleSectoren zu berechnen.
	 * @param start anfang der ConnectionLine
	 * @param goal ende der ConnectionLine
	 */
	private void calculateConnectionLinesCoords(final Point start, final Point goal) {
		int xDist = start.x - goal.x;
		int yDist = start.y - goal.y;
		
		int x = 0;
		int y = 0;
		for (int i = 0; i < Math.max(Math.abs(xDist), Math.abs(yDist)); i++) {
			this.add(new Point(start.x + x, start.y + y));
			if (x != xDist) {
				if (xDist < 0) {
					x++;
				} else {
					x--;
				}
			}
			if (y != yDist) {
				if (yDist < 0) {
					y++;
				} else {
					y--;
				}
			}
		}
	}

	/**
	 * Berechnet die Punkte der ConnectionLine und setzt diese auf die Karte.
	 * @param p1 Ein Eckpunkt des JungleSektors
	 * @param p2 Ein weiterer Eckpubkt des JungleSektors
	 */
	public void calculateConnectionLine(final Point p1, final Point p2) {
			
		//Zufaelliger Wert zwischen 0 und der laenge der ConnectionLine
		int conLineLength = js.getSize().width / JungleSectorPath.PROPOTION_JUNGLESECTOR_PATH;
		int rand = ((int) Math.rint(Math.random() * conLineLength + 1));
		Point direction = new Point(0, 0);
		Point toGo = new Point(0, 0);
		
		if (p1.x != p2.x) {
			toGo = new Point(conLineLength, 0);
			if (p1.x < p2.x) {
				direction = new Point(1 * (rand + distBetweenEndOfSectorAndConnection), 0);
			} else {
				direction = new Point(-1 * (rand + distBetweenEndOfSectorAndConnection), 0);
			}
		} else {
			toGo = new Point(0, conLineLength);
			if (p1.y < p2.y) {
				direction = new Point(0, 1 * (rand + distBetweenEndOfSectorAndConnection));
			} else {
				direction = new Point(0, -1 * (rand + distBetweenEndOfSectorAndConnection));
			}
		}

		
		if (this.isEmpty()) {
			
			Point connectionStartPoint = new Point(p1.x + direction.x, p1.y + direction.y);
			if (Points.getPointWhichIsFarestAwayOfTwoPoints(connectionStartPoint, p1, p2) == p1) {
				toGo = new Point(toGo.x * (-1), toGo.y * (-1));
				if (printConnectionLine) {
					calculateConnectionLinesCoords(connectionStartPoint, new Point(connectionStartPoint.x + toGo.x, connectionStartPoint.y + toGo.y));
				}
			} else {
				if (printConnectionLine) {
					calculateConnectionLinesCoords(connectionStartPoint, new Point(connectionStartPoint.x + toGo.x, connectionStartPoint.y + toGo.y));
				}
			}
		}
	}
	
	/**
	 * Ruft die Methode CoordsOnMap fuer alle Mapelemente auf die sich in dem aktuellen Element befinden.
	 * Wenn das aktuelle Element auf die Karte geschrieben wird, wird das auch hier gemacht.
	 */
	public void coordsOnMap() {
		coordsOnMap(js.mg.getHalbeMap(), TILE_ID);
	}
	
	/**
	 * Ruft die Methode coordsOnMapReflectedByMiddlePath fuer alle Mapelemente auf die sich in dem aktuellen Element befinden.
	 * Wenn das aktuelle Element auf die Karte geschrieben wird, wird das auch hier gemacht.
	 * <br>
	 * Hier werden allerdings die Elemente gespiegelt auf die Karte gesetzt.
	 */
	public void coordsOnMapReflectedByMiddlePath() {
		coordsOnMapReflectedByMiddlePath(js.mg.getHalbeMap(), TILE_ID);
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////GETTER UND SETTER//////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * @return the printConnectionLine
	 */
	public final boolean isPrintConnectionLine() {
		return printConnectionLine;
	}
	/**
	 * @param printConLine the printConnectionLine to set
	 */
	public void setPrintConnectionLine(final boolean printConLine) {
		this.printConnectionLine = printConLine;
	}
}
