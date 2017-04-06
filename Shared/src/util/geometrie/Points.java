package util.geometrie;

import java.awt.Point;
import java.util.LinkedList;

/**
 * Klasse die Bestimmte Berechnungen mit die mit Punkten zusammenhaengen durchfuehrt.
 * @author Marius
 *
 */
public final class Points {
	
	/**
	 * Utility Klasse.
	 */
	private Points() {
		
	}
	
	/**
	 * Manhaten Metrik zwischen zwei Punkten.
	 * @param p1 Der Erste Punkt 
	 * @param p2 Der Zweite Punkt
	 * @return Die Distant
	 */
	public static int distBetweenTwoPoints(final Point p1, final Point p2) {
		int xDist = Math.abs(p1.x - p2.x);
		int yDist = Math.abs(p1.y - p2.y);
		
		return xDist + yDist;
	}
	
	/**
	 * Gibt den Punkt zurueck der am weitesten vom Starpunkt entfernt ist.
	 * @param start der Startpunkt
	 * @param goal1 ein Zielpunkt
	 * @param goal2 ein Zielpunkt
	 * @return der Punt der beiden Ziele welche am weitesten vom Start entfernt ist
	 */
	public static Point getPointWhichIsFarestAwayOfTwoPoints(final Point start, final Point goal1, final Point goal2) {
		int xDistBetweenStartAndGoal1 = Math.abs(start.x - goal1.x);
		int yDistBetweenStartAndGoal1 = Math.abs(start.y - goal1.y);
		
		int xDistBetweenStartAndGoal2 = Math.abs(start.x - goal2.x);
		int yDistBetweenStartAndGoal2 = Math.abs(start.y - goal2.y);
		
		if (xDistBetweenStartAndGoal2 + yDistBetweenStartAndGoal2 <= xDistBetweenStartAndGoal1 + yDistBetweenStartAndGoal1) {
			return goal1;
		}
		
		return goal2;
	}
	
	/**
	 * Der Punkt aus einer Menge an Punkten der einem festgelegen Punkt am naechsten ist.
	 * @param start Der festgelegte Punkt zu dem die anderen Punkte ueberprueft werden.
	 * @param otherPoints Die anderen Punkte welche ueberpruft werden.
	 * @return Der Punkt aus other Points der am Naechsten zu start ist.
	 */
	public static Point getNearestPointToAPoint(final Point start, final LinkedList<Point> otherPoints) {
		Point min = null;
		
		for (Point op : otherPoints) {
			if (distBetweenTwoPoints(start, min) > distBetweenTwoPoints(start, op)) {
				min = op;
			}
		}
		
		return min;
	}

}
