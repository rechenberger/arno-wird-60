package mapgenerator.elemente.baseelemente;

import java.awt.Dimension;
import java.awt.Point;

import util.geometrie.RectangularArea;

import mapgenerator.Mapgenerator;
import mapgenerator.elemente.Base;

/**
 * Diese Klasse beschreibt die groesse des Nexus und seine Position.
 * @author Marius
 *
 */
public class Nexus extends RectangularArea {

	/**
	 * Die TileId des Nexus der Fraction A.
	 */
	public static final int FRACTION_A_TILE_ID = 14;
	
	/**
	 * Die TileId des Nexus der Fraction B.
	 */
	public static final int FRACTION_B_TILE_ID = 15;

	
	/**
	 * Das Object des Mapgenerators.
	 */
	protected Mapgenerator mg;

	
	/**
	 * Konstruktor.
	 * @param bL Der linke untere Punkt
	 * @param s Die Groesse
	 * @param mapg Das Objekt des Mapgenerators
	 */
	public Nexus(final Point bL, final Dimension s, final Mapgenerator mapg) {
		super(bL, s);
		mg = mapg;
		coordsOnMap(mg.getHalbeMap(), FRACTION_A_TILE_ID);
	}
	
	@Override
	public void coordsOnMap(final int[][] map, final int tileId) {
		super.coordsOnMap(map, Base.TILE_ID);
		map[getBottomRight().x][getBottomRight().y] = tileId;
	}
		
	


}
