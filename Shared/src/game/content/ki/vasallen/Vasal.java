package game.content.ki.vasallen;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;

import game.attributes.Attribute;
import game.content.ki.Ki;

import game.objects.GameObject;

/**
 * Die Oberklasse der Vasallen.
 * @author Marius
 *
 */
public class Vasal extends Ki {
	
	/**
	 * Hier startet der Vasal neu.
	 */
	private ArrayList<Point> spawnPoints;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3801463778805850946L;

	/**
	 * Ein Vasal.
	 */
	public Vasal() {
		this.getAttributeValueList().setAttribute(Attribute.movementSpeed, 25);
		this.getAttributeValueList().setAttribute(Attribute.maxHealth, 35);
		this.getAttributeValueList().setAttribute(Attribute.damage, 3);
		this.setImageURL("match", "vasal", "sheep-white");
	}

	/**
	 * Gibt alle Vasallen zurueck.
	 * @return Liste aller Helden.
	 */
	public static LinkedList<Vasal> getAllVasalen() {
		LinkedList<Vasal> ll = GameObject.getGameObjectsByClassName("Vasal");
		return ll;
	}

	/**
	 * @return the spawnPoint
	 */
	public Point getSpawnPoint() {
		if (this.spawnPoints == null) {
			return super.getSpawnPoint();
		}
		return matchModule.getMap().getEmptyPointInArea(spawnPoints);
	}

	/**
	 * @param spawnPoint the spawnPoint to set
	 */
	public void setSpawnPoints(final ArrayList<Point> spawnPoint) {
		this.spawnPoints = spawnPoint;
	}
}
