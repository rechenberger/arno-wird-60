package game.objects;

import java.awt.Point;

/**
 * Interface fuer eine Route, die die Spiellogik von der Map braucht.
 * @author Tristan
 *
 */
public interface RouteInterface {
	
	/**
	 * Gibt den naechsten Schritt zurueck und merk sich intern die neue aktuelle Position.
	 * @return Koordinaten, des naechsten Schritts.
	 */
	Point doNextStep();
	

	/**
	 * 
	 * Gibt den naechsten Schritt zurueck .
	 * @return Koordinaten, des naechsten Schritts.
	 */
	Point getNextStep();
	
	/**
	 * Setzt eine Neue Route auf.
	 * @param setCurrentCoord Ausfuehrer
	 * @param destination Ziel der Route.
	 */
	void setupNewRoute(final Point setCurrentCoord, final Point destination);
	
	/**
	 * 
	 * @param setCurrentCoord Aufenthaltsort
	 */
	void setCurrentCoord(final Point setCurrentCoord);
	

	/**
	 * 
	 * @return Point Aufenthaltsort
	 */
	Point getCurrentCoord();
	
	/**
	 * 
	 * @param destination Ziel
	 */
	void setDestination(final Point destination);
	
	/**
	 * 
	 * @return Ziel
	 */
	Point getDestination();
	
	/**
	 * 
	 * @return Distanz bis zum Ziel.
	 */
	int getDistanceToGo();
	
	/**
	 * @see game.objects.GameObject
	 */
	void unregisterGameObject();
}
