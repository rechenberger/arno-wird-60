package mapgenerator.elemente.abstractclasses;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

import util.geometrie.Area;

import mapgenerator.Mapgenerator;

/**
 * @see Mapgenerator
 * @author Marius
 *
 */
public abstract class Path extends Area {
	
	/**
	 * 
	 */
	public static final int TILE_ID = 1;
	
	/**
	 * Das Verhaeltniss zwischen Map und Basis wenn Map 1000 mal 1000 gross ist, dann ist das Element 1000 / PROPOTION_MAP_BASE Gross.
	 */
	public static final int PROPOTION_MAP_PATH = 20;
	
	/**
	 * Das Objekt des Mapgenerators.
	 */
	protected Mapgenerator mg;
	
	/**
	 * 
	 */
	private Point topLeft;
	
	/**
	 * 
	 */
	private Point bottomLeft;
	/**
	 * 
	 */
	private Point topRight;
	/**
	 * 
	 */
	private Point bottomRight;
	
	/**
	 * 
	 */
	protected Set<Integer> valuesInMapNotToOverwirte = new HashSet<Integer>();
	
	/**
	 * 
	 */
	protected int size;
	
	/**
	 * Berechnet die Koordinaten welche Dieses Element auf der Map einnimmt und speichert diese in der Map, sowie in der Variable coords.
	 */
	public void calculteCoords() {
		
		for (int i = getTopLeft().y; i < getBottomLeft().y; i++) {
			for (int j = getTopLeft().x; j < getTopRight().x; j++) {
				if (!valuesInMapNotToOverwirte.contains(mg.getHalbeMap()[j][i])) {
					add(new Point(j, i));
				}
			}
		}
		this.coordsOnMap(mg.getHalbeMap(), TILE_ID);
	}
	
	
	/**
	 * Berechnet die Koordinaten welche spaeter durch die Spiegelung auf die Karte gesetzt werden.
	 * Wird nur fuer die Wegpunkte gebraucht.
	 */
	public void calculateCoordsReflectedByRiver() {
		for (int i = getTopLeft().y; i < getBottomLeft().y; i++) {
			for (int j = getTopLeft().x; j < getTopRight().x; j++) {
				if (!valuesInMapNotToOverwirte.contains(mg.getHalbeMap()[j][i])) {
					add(new Point(i, mg.getSize().height - j));
				}
			}
		}
	}
	
	/**
	 * 
	 * @return Die breite oder hoehes der Weges
	 */
	public int getSize() {
		return size;
	}


	/**
	 * @return the topLeft
	 */
	public Point getTopLeft() {
		return topLeft;
	}


	/**
	 * @param topLeft the topLeft to set
	 */
	public void setTopLeft(final Point topLeft) {
		this.topLeft = topLeft;
	}


	/**
	 * @return the bottomLeft
	 */
	public Point getBottomLeft() {
		return bottomLeft;
	}


	/**
	 * @param bottomLeft the bottomLeft to set
	 */
	public void setBottomLeft(final Point bottomLeft) {
		this.bottomLeft = bottomLeft;
	}


	/**
	 * @return the topRight
	 */
	public Point getTopRight() {
		return topRight;
	}


	/**
	 * @param topRight the topRight to set
	 */
	public void setTopRight(final Point topRight) {
		this.topRight = topRight;
	}


	/**
	 * @return the bottomRight
	 */
	public Point getBottomRight() {
		return bottomRight;
	}


	/**
	 * @param bottomRight the bottomRight to set
	 */
	public void setBottomRight(final Point bottomRight) {
		this.bottomRight = bottomRight;
	}

	
	
	
}
