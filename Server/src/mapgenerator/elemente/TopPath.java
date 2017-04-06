package mapgenerator.elemente;


import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;

import util.geometrie.RectangularArea;

import mapgenerator.Mapgenerator;
import mapgenerator.elemente.abstractclasses.Path;
import mapgenerator.elemente.pathelemente.PathSector;

/**
 * 
 * @author Marius
 *
 */
public class TopPath extends Path {
	
	/**
	 * Die Anzahl der Tuerme auf diesem Pfad.
	 */
	public static final int NUMBER_OF_TOWERS = 2;
	
	/**
	 * Hier sind alle Pathsektoren dieses Weges gespeichert.
	 */
	private ArrayList<PathSector> pathSectors = new ArrayList<PathSector>();
	
	/**
	 * 
	 * @param mapg das Mapgenerator Objekt
	 */
	public TopPath(final Mapgenerator mapg) {
		
		mg = mapg;
		size = mg.getSize().width / PROPOTION_MAP_PATH;
		valuesInMapNotToOverwirte.add(-1);

		setTopLeft(new Point(Mapgenerator.DIST_BETWEEN_END_OF_MAP_AND_PATH,
						Mapgenerator.DIST_BETWEEN_END_OF_MAP_AND_PATH));
		
		setTopRight(new Point(Mapgenerator.DIST_BETWEEN_END_OF_MAP_AND_PATH + getSize(),
						Mapgenerator.DIST_BETWEEN_END_OF_MAP_AND_PATH));
		
		setBottomLeft(new Point(Mapgenerator.DIST_BETWEEN_END_OF_MAP_AND_PATH, 
						mg.getSize().height - mg.getBase().getSize().height - 1));

		setBottomRight(new Point(Mapgenerator.DIST_BETWEEN_END_OF_MAP_AND_PATH + getSize(), 
						mg.getSize().height - mg.getBase().getSize().height - 1));
		
		this.calculteCoords();
		this.calculateSectors();
		this.setTowersOnPathsector();
	}
	
	/**
	 * Berechnet die PathSektoren.
	 */
	private void calculateSectors() {
		this.pathSectors.add(new PathSector(new Point(getBottomLeft().x, getBottomLeft().y - getSize()), new Dimension(getSize() - 1, getSize() - 1), mg));
		this.pathSectors.get(0).calculateTower();
		searchForSectors(this.pathSectors.get(0));
	}
	
	/**
	 * Sucht neue Pathsektoren.
	 * @param current von welchem Sektor aus gesucht werden soll
	 */
	private void searchForSectors(final PathSector current) {
		if (this.contains(new Point(current.getTopLeft().x, current.getTopLeft().y - getSize()))
				&& this.contains(new Point(current.getTopRight().x , current.getTopRight().y - getSize()))) {
			PathSector newSector = new PathSector(current.getTopLeft(), new Dimension(getSize() - 1, getSize() - 1), mg);
			this.pathSectors.add(newSector);
			searchForSectors(newSector);
		} else {
			current.calculateTower();
		}
	}
	
	/**
	 * Setzt Die Tower auf diesen Pfad.
	 */
	private void setTowersOnPathsector() {
		for (int i = 0; i < NUMBER_OF_TOWERS; i++) {
			int rand = (int) (Math.random() * this.pathSectors.size());
			if (this.pathSectors.get(rand).getTower() == null) {
				this.pathSectors.get(rand).calculateTower();
			} else {
				i--;
			}
		}
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
		centerPoint.add(new Point(getTopLeft().x + (getSize() / 2), getTopLeft().y + (getSize() / 2)));
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
		Point bottomLeftOfCenterArea = new Point(getTopLeft().x, getTopLeft().y + getSize());
		RectangularArea a = new RectangularArea(bottomLeftOfCenterArea, d);
		return a.getCoords();
	}

	/**
	 * @return the pathSectors
	 */
	public ArrayList<PathSector> getPathSectors() {
		return pathSectors;
	}

	/**
	 * @param pathSectors the pathSectors to set
	 */
	public void setPathSectors(final ArrayList<PathSector> pathSectors) {
		this.pathSectors = pathSectors;
	}

}
