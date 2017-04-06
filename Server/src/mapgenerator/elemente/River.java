package mapgenerator.elemente;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

import mapgenerator.Mapgenerator;
import mapgenerator.elemente.abstractclasses.Path;

import util.geometrie.Area;


/**
 * Der Fluss der sich von oben links ueber die Diagonale durch die gesamte Ma zieht.
 * @author Marius
 *
 */
public class River extends Area {
	
	/**
	 * Das Objekt des Mapgenerators.
	 */
	protected Mapgenerator mg;
	
	/**
	 * Die TileId des Flusses.
	 */
	public static final int RIVER_TILE_ID = 20;
	
	/**
	 * Die TileId einer Bruecke.
	 */
	public static final int BRIGDE_TILE_ID = 21;
	
	/**
	 * 
	 */
	private int size;
	
	/**
	 * Das Verhaeltniss zwischen Map und Basis wenn Map 1000 mal 1000 gross ist, dann ist das Element 1000 / PROPOTION_MAP_BASE Gross.
	 */
	public static final int PROPOTION_MAP_RIVER = 30;
	
	/**
	 * Die Werte die dieses Objekt auf der Karte nicht ueberschreiben darf.
	 */
	protected Set<Integer> valuesInMapNotToOverwirte = new HashSet<Integer>();
	
	/**
	 * oben Links.
	 */
	private Point topLeft;
	
	/**
	 * unten Links.
	 */
	private Point bottomLeft;
	
	/**
	 * oben Rechts.
	 */
	private Point topRight;
	
	/**
	 * unten Rechts.
	 */
	private Point bottomRight;
	
	
	/**
	 * Konstruktor.
	 * @param mapg das Objekt des Mapgenerators.
	 */
	public River(final Mapgenerator mapg) {
		mg = mapg;
		setSize(mg.getSize().height / PROPOTION_MAP_RIVER);
		//valuesInMapNotToOverwirte.add(Path.TILE_ID);
		//valuesInMapNotToOverwirte.add(-1);

		setTopLeft(new Point((int) (0
				- (0.5 * getSize())), (int) (0
						- (0.5 * getSize()))));

		setTopRight(new Point((int) (0
				+ (0.5 * getSize())),  (int) (0
						- (0.5 * getSize()))));

		this.calculateCoords();

		coordsOnMap();
		
		
	}


	/**
	 * berechnet die Koordinaten, auf welchen sich der Fluss befindet.
	 */
	private void calculateCoords() {
		
		int counterX = 0;
		int counterY = -5;
		int toGo =  Math.abs(getTopLeft().x - getTopRight().x); //die breite des weges
		
		//starte unter in der Map und gehe in -y/+x Richtung
		for (int i = getTopLeft().y; i < mg.getSize().height; i++) {
			
			counterY++;
			for (int j = getTopLeft().x; j < mg.getSize().width; j++) {
				//ueberprueft ob die breite des Weges schon gegangen wurde und ob schon angefangen werden soll zu gehen 
				if (counterX < toGo && j - getTopLeft().x >= counterY) {
					counterX++;
					
					if (!(i < 0 || j < 0) && !valuesInMapNotToOverwirte.contains(mg.getHalbeMap()[j][i])) {	
						add(new Point(j, i));
					}
				}
			}
			counterX = 0;
		}
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
	 * @return the size
	 */
	public int getSize() {
		return size;
	}


	/**
	 * @param size the size to set
	 */
	public void setSize(final int size) {
		this.size = size;
	}
	
	/**
	 * setzt die Koords auf die Map.
	 */
	public void coordsOnMap() {
		for (Point p : getCoords()) {
			if (mg.getHalbeMap()[p.x][p.y] != Path.TILE_ID) {
				mg.getHalbeMap()[p.x][p.y] = RIVER_TILE_ID;
			} else {
				mg.getHalbeMap()[p.x][p.y] = BRIGDE_TILE_ID;
			}
			
		}
	}

}
