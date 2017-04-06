package game.content.ki.arno;

import java.awt.Point;

import game.content.ki.Ki;

/**
 * Oberklasse fuer alle Neutralen Mobs.
 * @author Marius
 *
 */
public class Neutral extends Ki {
	
	/**
	 * 
	 */
	private Point spawnPoint;

	/**
	 * 
	 */
	private static final long serialVersionUID = 2172222052570583142L;
	
	/**
	 * @return der SpawnPoint
	 */
	public Point getSpawnPoint() {
		return spawnPoint;
		
	}

	/**
	 * @param spawnPoint the spawnPoint to set
	 */
	public void setSpawnPoint(final Point spawnPoint) {
		this.spawnPoint = spawnPoint;
	}
}
