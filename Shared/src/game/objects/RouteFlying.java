package game.objects;

import java.awt.Point;
import java.util.ArrayList;

/**
 * 
 * @author Tristan
 *
 */
public class RouteFlying extends GameObject implements RouteInterface {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -5806578160564899534L;

	/**
	 * 
	 */
	ArrayList<Point> route;
	
	/**
	 * 
	 */
	Point destination;
	
	/**
	 * 
	 */
	Point currentCoord;
	

	////////// RouteInterface //////////
	
	/**
	 * 
	 * @return ob Route vorhanden und nicht leer ist.
	 */
	private boolean noRoute() {
		if (route == null) {
			return true;
		} else {
			return route.isEmpty();
		}
	}
	
	@Override
	public Point doNextStep() {
		if (this.noRoute()) {
			return null;
		}
		Point nextCoord = route.remove(route.size() - 1);
		this.currentCoord = nextCoord;
		return nextCoord;
	}
	
	@Override
	public Point getNextStep() {
		if (this.noRoute()) {
			return null;
		}
		Point nextCoord = route.get(route.size() - 1);
		return nextCoord;
	}

	@Override
	public void setupNewRoute(final Point setCurrentCoord, final Point setDestination) {
		this.currentCoord = setCurrentCoord;
		this.destination = setDestination;
		this.calculateRoute();
//		this.print();
//		System.out.println("Mein Ziel: " + setDestination.toString() + ", Ziel der Route: " + this.route.get(0).toString() );
	}
	
	/**
	 * Druckt die Route.
	 */
	public void print() {
		System.out.print("Route");
		for (Point p : this.route) {
			System.out.print(", (" + p.x + ", " + p.y + ")");
		}
		System.out.println();
	}

	@Override
	public void setCurrentCoord(final Point setCurrentCoord) {
		this.currentCoord = setCurrentCoord;
		this.calculateRoute();
		
	}

	@Override
	public Point getCurrentCoord() {
		return this.currentCoord;
	}

	@Override
	public void setDestination(final Point setDestination) {
		this.destination = setDestination;
		this.calculateRoute();
		
	}

	@Override
	public Point getDestination() {
		return this.destination;
	}

	@Override
	public int getDistanceToGo() {
		return Map.getDistance(this.currentCoord, this.destination);
	}
	
	/**
	 * Berechnet die Luftlininen-Route.
	 */
	private void calculateRoute() {
		this.route = new ArrayList<Point>();
		this.route.add(destination);
		this.route.addAll(Map.getPointsOnBeeline(destination, currentCoord));
		this.route.remove(currentCoord);
	}

}
