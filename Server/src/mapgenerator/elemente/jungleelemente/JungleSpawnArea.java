package mapgenerator.elemente.jungleelemente;

import java.awt.Dimension;
import java.awt.Point;

import util.geometrie.RectangularArea;


/**
 * 
 * In dieser Klasse wird das SpawnArea berechnet.
 * <br>
 * Das <b>SpawnArea</b> ist ist das Gebiet in welchem <b>neutrale Monster</b> erscheinen.
 * <br>
 * Dieses Gebiet ist viereckig und sollte ein bisschen Groesser sein als die breite Eines Weges.
 * 
 * @author Marius
 *
 */
public class JungleSpawnArea extends RectangularArea {
	
	/**
	 * TileId fuer SpawnPoint.
	 */
	public static final int TILE_ID = 51;
	
	/**
	 * Verhaeltniss zwischen JungleSector groesse und SPAWNAREA grosse.
	 */
	public static final int PROPOTION_JUNGLESECTOR_SPAWNAREA = 3;
	
	/**
	 * Die Wahrscheinlichkeit das ein JungleSector einen SpawnPoint hat.
	 */
	protected static final double CHANGE_OF_HAVING_SPAWNAREA = 0.5;
	
	/**
	 * Das Objekt des JungleSektors in welchem die ConnectionLine instanziert werden soll.
	 */
	private JungleSector js;
	
	/**
	 * JungleSpawnArea wird initialisiert.
	 * <br>
	 * Die Eckpunkte werden berechnet und die Kordinaten werden gespeichert.
	 * 
	 * @param jungleS das JungleSector Object
	 * @param bl der Startpunkt des JungleSectors(die untere linke Ecke)
	 * @param d Die Groesse des SpawnAreas
	 */
	public JungleSpawnArea(final Point bl, final Dimension d, final JungleSector jungleS) {
		super(bl, d);
		js = jungleS;
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

}
