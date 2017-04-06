package mapgenerator.elemente.pathelemente;

import java.awt.Dimension;
import java.awt.Point;

import mapgenerator.Mapgenerator;

import mapgenerator.elemente.abstractclasses.Path;

import util.geometrie.RectangularArea;

/**
 * Ein Turm.
 * 
 * @author Marius
 * 
 */
public class Tower extends RectangularArea {

	/**
	 * Die TileId des Nexus der Fraction A.
	 */
	public static final int FRACTION_A_TILE_ID = 10;

	/**
	 * Die TileId des Nexus der Fraction B.
	 */
	public static final int FRACTION_B_TILE_ID = 11;

	/**
	 * Die Wahrscheinlichkeit das ein PathSector einen Tower hat.
	 */
	protected static final double CHANGE_OF_HAVING_TOWER = 0.4;
	
	/**
	 * Das Object des Mapgenerators.
	 */
	protected Mapgenerator mg;

	/**
	 * Konstruktor.
	 * 
	 * @param bL
	 *            Der linke untere Punkt
	 * @param s
	 *            Die Groesse
	 * @param mapg
	 *            Das Objekt des Mapgenerators
	 */
	public Tower(final Point bL, final Dimension s, final Mapgenerator mapg) {
		super(bL, s);
		mg = mapg;
		coordsOnMap(mg.getHalbeMap(), FRACTION_A_TILE_ID);
	}
	
	@Override
	public void coordsOnMap(final int[][] map, final int tileId) {
		super.coordsOnMap(map, Path.TILE_ID);
		map[getBottomRight().x][getBottomRight().y] = tileId;
	}

}
