package game.objects;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;

import util.AStarFieldSet;
import util.AStarFieldSetAble;
import util.Field;
/**
 * 
 * @author Tristan
 *
 */
public class Route extends GameObject implements RouteInterface, AStarFieldSetAble {

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

	
	////////// AStarFieldSetAble //////////
	

	/**
	 * 
	 */
	public void calculateRoute() {
		AStarFieldSet a = new AStarFieldSet(this);
		this.route = a.run();
		while (!route.isEmpty() && route.get(route.size() - 1).equals(this.currentCoord)) {
			route.remove(route.size() - 1);
		}
	}
	
	@Override
	public ArrayList<Field<Integer>> getMovableFields() {
		ArrayList<Field<Integer>> list = new ArrayList<Field<Integer>>();
		for (int x = 0; x < Map.DIMENSION.x; x++) {
			for (int y = 0; y < Map.DIMENSION.y; y++) {
					Point coord = new Point(x, y);
					Map map = matchModule.getMatch().getMap();
					float factor = map.getMovementSpeedFactor(coord);
					
					// Unsauber, ich weiss.
					if (coord.equals(this.destination)) {
						factor = 1;
					}
						
					if (factor != 0) {
						int costs = (int) (1000000 / factor);
						list.add(new Field<Integer>(coord, costs));
					}
			}
		}
		
		return list;
	}

	@Override
	public Dimension getAreaDimension() {
		return new Dimension(Map.DIMENSION.x, Map.DIMENSION.y);
	}

	@Override
	public ArrayList<Point> getStartFieldSet() {
		// TODO Auto-generated method stub
		ArrayList<Point> list = new ArrayList<Point>();
		list.add(this.getCurrentCoord());
		return list;
	}

	@Override
	public Point getGoal() {
		return this.getDestination();
	}

	@Override
	public Dimension getPathSize() {
		return new Dimension(1, 1);
	}

}
