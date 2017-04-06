package mapgenerator.elemente;

import mapgenerator.elemente.pathelemente.Tower;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import util.geometrie.Points;
import util.geometrie.RectangularArea;

import mapgenerator.Mapgenerator;
import mapgenerator.elemente.abstractclasses.Path;

/**
 * 
 * @author Marius
 *
 */
public class MiddlePath extends Path {
	
	/**
	 * Die Anzahl der Tuerme auf diesem Pfad.
	 */
	public static final int NUMBER_OF_TOWERS = 2;
	
	/**
	 * Beschreibt wie weit der startpunkt des Pfades in das Gebiet der Basis reinreicht.
	 */
	private static final double ONE_POINT_FIVE = 1.5;
	
	/**
	 * Beschreibt wie weit der startpunkt des Pfades in das Gebiet der Basis reinreicht.
	 */
	private static final double O_POINT_FIVE = 0.5;
	
	/**
	 * alle Tower und ihre Positionen.
	 */
	private HashMap<Point, Tower> towers = new HashMap<Point, Tower>();
	
	/**
	 * @param mapg das Mapgenerator Objekt
	 */
	public MiddlePath(final Mapgenerator mapg) {
		
		mg = mapg;
		size = mg.getSize().height / PROPOTION_MAP_PATH;
		//valuesInMapNotToOverwirte.add(-1);
		valuesInMapNotToOverwirte.add(Base.TILE_ID);

		setTopLeft(new Point(mg.getSize().width - (mg.getBase().getSize().width - (int) (getSize() * ONE_POINT_FIVE) - 1),
				mg.getBase().getSize().height - getSize()));
		
		
		setTopRight(new Point(mg.getSize().width - (mg.getBase().getSize().width - (int) (getSize() * O_POINT_FIVE) - 1),
				mg.getBase().getSize().height - getSize()));

		
		setBottomLeft(new Point(mg.getBase().getSize().width - (int) (getSize() * ONE_POINT_FIVE) - 1, 
						mg.getSize().height - (mg.getBase().getSize().height - getSize())));

		
		setBottomRight(new Point(mg.getBase().getSize().width - (int) (getSize() * O_POINT_FIVE), 
						mg.getSize().height - (mg.getBase().getSize().height - getSize())));
		
		
		this.calculteCoords();
		this.setTowers();
	}
	
	/**
	 * 
	 */
	private void setTowers() {
		
		LinkedList<Point> towerPos = new LinkedList<Point>();
		Point middle = new Point((getBottomLeft().x + getBottomRight().x) / 2, (getBottomLeft().y + getBottomRight().y) / 2);
		Point firstTower = new Point(middle.x + getSize(), middle.y - getSize());
		this.findTowerPos(firstTower, towerPos);
		
		this.setTowersOnPath(towerPos);
		Tower t1 = new Tower(towerPos.get(0), new Dimension(game.content.buildings.Tower.SIZE.x, game.content.buildings.Tower.SIZE.y), mg);
		this.towers.put(towerPos.get(0), t1);
		towerPos.removeFirst();
		
		Tower t2 = new Tower(towerPos.getLast(), new Dimension(game.content.buildings.Tower.SIZE.x, game.content.buildings.Tower.SIZE.y), mg);
		this.towers.put(towerPos.get(0), t2);
		towerPos.removeLast();
		
	}
	
	/**
	 * Sucht nach weiteren Positionen einen Turm auf dem mittleren Weg zu platzieren.
	 * @param currentPos Die aktuelle Position
	 * @param allPos Alle gefundenen Positionen.
	 */
	private void findTowerPos(final Point currentPos, final LinkedList<Point> allPos) {
		if (currentPos.x + getSize() < (mg.getSize().width / 2) && currentPos.y - getSize() > (mg.getSize().height / 2)) {
			Point newPos = new Point(currentPos.x + getSize(), currentPos.y - getSize()); 
			allPos.add(newPos);
			this.findTowerPos(newPos, allPos);
		}
	}
	
	/**
	 * Setzt Die Tower auf diesen Pfad.
	 * @param posTowers alle moeglichen Positionen auf diesem pfad
	 */
	private void setTowersOnPath(final LinkedList<Point> posTowers) {
		for (int i = 0; i < NUMBER_OF_TOWERS; i++) {
			int rand = (int) (Math.random() * posTowers.size());
			if (!this.towers.containsKey(posTowers.get(rand))) {
				Tower t = new Tower(posTowers.get(rand), new Dimension(game.content.buildings.Tower.SIZE.x, game.content.buildings.Tower.SIZE.y), mg);
				this.towers.put(posTowers.get(rand), t);
			} else {
				i--;
			}
		}
	}
	
	@Override
	public final void calculteCoords() {
		
		int counterX = 0;
		int counterY = 0;
		int toGo =  getTopLeft().x - getTopRight().x; //die breite des weges
		
		//starte unter in der Map und gehe in -y/+x Richtung
		for (int i = getBottomLeft().y; i >= 0; i--) {
			
			counterY++;
			for (int j = getBottomLeft().x; j < mg.getSize().width; j++) {
				//ueberprueft ob die breite des Weges schon gegangen wurde und ob schon angefangen werden soll zu gehen 
				if (counterX < toGo && j - getBottomLeft().x >= counterY) {
					counterX++;
					
					if (!valuesInMapNotToOverwirte.contains(mg.getHalbeMap()[j][i])) {	
						add(new Point(j, i));
					}
				}
			}
			counterX = 0;
		}
		
		this.coordsOnMap(mg.getHalbeMap(), TILE_ID);
	}
	
	/**
	 * Gibt den Eintrittspunkt auf den Weg von Basis A zurueck.
	 * @return Den Eintrittspunkt
	 */
	public ArrayList<Point> getEntreeTeamA() {
		ArrayList<Point> entreeTeamA = new ArrayList<Point>();
		int xPos = getBottomRight().x - (getSize() / 2);
		entreeTeamA.add(new Point(xPos, getBottomLeft().y - (getSize() / 2)));
		return entreeTeamA;
	}
	
	/**
	 * 	
	 * Eine Menge an Punkten die sich um TOPPATH_ENTREE_TEAM_A befinden,
	 * sodass diese Punkte ein Viereck bilden, 
	 * dessen Seitenlaenge der breite des Pfades entspricht.
	 * @return Den Eintrittspunkt
	 */
	public ArrayList<Point> getEntreeTeamAArea() {
		RectangularArea a = new RectangularArea(getBottomLeft(), new Dimension(getSize(), getSize()));
		return a.getCoords();
	}
	
	/**
	 * Gibt den Eintrittspunkt auf den Weg von Basis B zurueck.
	 * @return Den Eintrittspunkt
	 */
	public ArrayList<Point> getEntreeTeamB() {
		return reflectCoordsOnRiver(getEntreeTeamA());
	}
	
	/**
	 * Eine Menge an Punkten die sich um TOPPATH_ENTREE_TEAM_B befinden,
	 * sodass diese Punkte ein Viereck bilden, 
	 * dessen Seitenlaenge der breite des Pfades entspricht.
	 * @return Den Eintrittspunkt
	 */
	public ArrayList<Point> getEntreeTeamBArea() {
		return reflectCoordsOnRiver(getEntreeTeamAArea());
	}
	
	/**
	 * Mittelpunkt des oberen Weges.
	 * @return Der Mittelpunkt
	 */
	public ArrayList<Point> getCenterPoint() {
		ArrayList<Point> centerPoint = new ArrayList<Point>();
		int metricDist = Points.distBetweenTwoPoints(getBottomLeft(), getTopRight());
		int toGoInBothDir = (metricDist / 2) / 2;
		int startX = getBottomRight().x - getSize() / 2;
		int startY = getBottomLeft().y;

		
		centerPoint.add(new Point(startX + toGoInBothDir, startY - toGoInBothDir));
		return centerPoint;
		
	}
	
	/**
	 * Eine Menge an Punkten die sich um TOPPATH_CENTER_POINT befinden,
	 * sodass diese Punkte ein Viereck bilden, 
	 * dessen Seitenlaenge der breite des Pfades entspricht.
	 *
	 * @return Die Menge an Punkten
	 */
	public ArrayList<Point> getCenterPointArea() {
		
		Dimension d = new Dimension(getSize(), getSize());
		Point cp = getCenterPoint().get(0);
		
		// Da man diesen Weg nicht in Reckecke aufteilen kann muss man hier etwas anders vorgehen
		// Zuerst wird vom Mittelpunkt aus in -x +y mit der Wertigkeit der groesse gegangen
		// Da dieser Weg aber einem verschobenen Trapez aehnelt, liegt das Rechteck, was man erhaelt, 
		// Wenn man ein neues RectangularArea daraus macht, nur zur haelfte auf dem Weg.
		// Deswegen werden 2 Rechtecke draus gemacht und beide dann zusammengefuehrt.
		Point bottomLeftOfCenterArea = new Point(cp.x - getSize(), cp.y + getSize() / 2); 
		Point bottomRightOfCenterArea = new Point(cp.x, cp.y + getSize() / 2);
		RectangularArea a1 = new RectangularArea(bottomLeftOfCenterArea, d);
		RectangularArea a2 = new RectangularArea(bottomRightOfCenterArea, d);
		
		ArrayList<Point> centerPointArea = new ArrayList<Point>();
		
		for (Point p : a1.getCoords()) {
			if (this.contains(p)) {
				centerPointArea.add(p);
			}
		}
		
		for (Point p : a2.getCoords()) {
			if (this.contains(p)) {
				centerPointArea.add(p);
			}
		}
		
		return centerPointArea;
	}
	
}
