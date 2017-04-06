package mapgenerator.elemente.baseelemente;

import java.awt.Dimension;
import java.awt.Point;

import util.geometrie.RectangularArea;

import mapgenerator.Mapgenerator;
import mapgenerator.elemente.Base;

/**
 * 
 * @author Marius
 *
 */
public class Shop extends RectangularArea {
	
	
	/**
	 * Unten Links von der Base minus die Basegroese geteilt durch diese Zahl ergibt bL vom Shop.
	 */
	public static final int PROPOTION_BASE_TO_BL = 3;
	
	/**
	 * Die TileId des Shop der Fraction A.
	 */
	public static final int FRACTION_A_TILE_ID = 12;

	/**
	 * Die TileId des Shop der Fraction B.
	 */
	public static final int FRACTION_B_TILE_ID = 13;
	
	
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
	public Shop(final Point bL, final Dimension s, final Mapgenerator mapg) {
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
