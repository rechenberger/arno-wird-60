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
public class BottomPath extends Path {

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
	public BottomPath(final Mapgenerator mapg) {
	
		mg = mapg;
		size = mg.getSize().height / PROPOTION_MAP_PATH;
		valuesInMapNotToOverwirte.add(-1);
		
		setTopLeft(new Point(mg.getBase().getSize().width + 1,
				  		mg.getSize().height - (Mapgenerator.DIST_BETWEEN_END_OF_MAP_AND_PATH + getSize())));
		
		setTopRight(new Point(mg.getSize().width - Mapgenerator.DIST_BETWEEN_END_OF_MAP_AND_PATH,
						mg.getSize().height - (Mapgenerator.DIST_BETWEEN_END_OF_MAP_AND_PATH + getSize())));
		
		setBottomLeft(new Point(mg.getBase().getSize().width + 1, 
						mg.getSize().height - Mapgenerator.DIST_BETWEEN_END_OF_MAP_AND_PATH));

		setBottomRight(new Point(mg.getSize().width - Mapgenerator.DIST_BETWEEN_END_OF_MAP_AND_PATH, 
						mg.getSize().height - Mapgenerator.DIST_BETWEEN_END_OF_MAP_AND_PATH));
		
		
		this.calculteCoords();
		this.calculateSectors();
		this.setTowersOnPathsector();
	}
	
	/**
	 * Berechnet die PathSektoren.
	 */
	private void calculateSectors() {
		this.getPathSectors().add(new PathSector(new Point(getBottomLeft().x + getSize(), getBottomLeft().y), new Dimension(getSize() - 1, getSize() - 1), mg));
		this.getPathSectors().get(0).calculateTower();
		searchForSectors(this.getPathSectors().get(0));
	}
	
	/**
	 * Sucht neue Pathsektoren.
	 * @param current von welchem Sektor aus gesucht werden soll
	 */
	private void searchForSectors(final PathSector current) {
		if (this.contains(new Point(current.getTopLeft().x + getSize(), current.getTopLeft().y))
				&& this.contains(new Point(current.getTopRight().x + getSize(), current.getTopRight().y))) {
			PathSector newSector = new PathSector(current.getBottomRight(), new Dimension(getSize() - 1, getSize() - 1), mg);
			this.getPathSectors().add(newSector);
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
		int xPos = getBottomLeft().x + (getSize() / 2);
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
		centerPoint.add(new Point(getTopRight().x - (getSize() / 2), getTopRight().y + (getSize() / 2)));
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
		Point bottomLeftOfCenterArea = new Point(getBottomRight().x - getSize(), getBottomRight().y);
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
