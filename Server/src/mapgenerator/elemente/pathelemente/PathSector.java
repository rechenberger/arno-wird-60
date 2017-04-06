package mapgenerator.elemente.pathelemente;

import java.awt.Dimension;
import java.awt.Point;

import mapgenerator.Mapgenerator;

import util.geometrie.RectangularArea;

/**
 * 
 * @author Marius
 *
 */
public class PathSector extends RectangularArea {

	/**
	 * Das Object des Mapgenerators.
	 */
	protected Mapgenerator mg;
	
	/**
	 * Das Object des Turms.
	 */
	private Tower tower = null;
	
	/**
	 * Ob dieser Sektor mit sicherheit einen Turm haben soll.
	 */
	private boolean hasTower = false;
	
	/**
	 * Konstruktor.
	 * @param bL Der linke untere Punkt
	 * @param s Die Groesse
	 * @param mapg Das Objekt des Mapgenerators
	 */
	public PathSector(final Point bL, final Dimension s, final Mapgenerator mapg) {
		super(bL, s);
		mg = mapg;
		
		if (Math.random() < Tower.CHANGE_OF_HAVING_TOWER || hasTower) {
			//this.calculateTower();
		}
	}
	
	/**
	 * Erstellt einen neuen Tower.
	 */
	public void calculateTower() {
		Dimension towerSize = new Dimension(game.content.buildings.Tower.SIZE.x, game.content.buildings.Tower.SIZE.y);
		
		setTower(new Tower(new Point(getBottomLeft().x + (getSize().width / 2) - (towerSize.width / 2), getBottomLeft().y - (getSize().height / 2) - (towerSize.height / 2)), towerSize, mg));
	}

	/**
	 * @return the tower
	 */
	public Tower getTower() {
		return tower;
	}

	/**
	 * @param tower the tower to set
	 */
	public void setTower(final Tower tower) {
		this.tower = tower;
	}

	/**
	 * @return the hasTower
	 */
	public boolean isHasTower() {
		return hasTower;
	}

	/**
	 * @param hasTower the hasTower to set
	 */
	public void setHasTower(final boolean hasTower) {
		this.hasTower = hasTower;
	}

}
