package mapgenerator.elemente.baseelemente;

import java.awt.Dimension;
import java.awt.Point;

import mapgenerator.Mapgenerator;
import mapgenerator.elemente.Base;

import util.geometrie.RectangularArea;

/**
 * Der Inhibitor, welcher auf der Karte platziert werden soll.
 * @author Marius
 *
 */
public class Inhibitor extends RectangularArea {
	
	
	/**
	 * Unten Links von der Base minus die Basegroese geteilt durch diese Zahl ergibt bL vom Shop.
	 */
	public static final double PROPOTION_BASE_TO_BL = 1.5;
	/**
	 * Die TileId des Inhibitors der Fraction A.
	 */
	public static final int FRACTION_A_TILE_ID = 17;

	/**
	 * Die TileId des Inhibitors der Fraction B.
	 */
	public static final int FRACTION_B_TILE_ID = 18;
	
	
	/**
	 * Das Object des Mapgenerators.
	 */
	protected Mapgenerator mg;

	/**
	 * Konstruktor.
	 * @param bL unten links vom RectangularArea
	 * @param s die Groesse
	 * @param mapg Das Objekt des Mapgenerators
	 */
	public Inhibitor(final Point bL, final Dimension s, final Mapgenerator mapg) {
		super(bL, s);
		mg = mapg;
		
		this.coordsOnMap(mg.getHalbeMap(), FRACTION_A_TILE_ID);
	}
	
	@Override
	public void coordsOnMap(final int[][] map, final int tileId) {
		super.coordsOnMap(map, Base.TILE_ID);
		map[getBottomRight().x][getBottomRight().y] = tileId;
	}

}
