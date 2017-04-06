package game.objects;

import java.awt.Point;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
/**
 * Fraktion eines NonStatic.
 * NonStatic gleicher Fraktion sind Verbuendete und kaempfen gemeinsam gegen die NonStatics anderer Fraktionen.
 * @author Tristan
 *
 */
public enum Fraction {
	/**
	 * Das erste Team.
	 */
	TeamA,
	/**
	 * Das zweite Team.
	 */
	TeamB,
	/**
	 * Arnos Schergen.
	 */
	Arno,
	
	/**
	 * Wirklich Neutral.
	 */
	Neutral;
	
	/**
	 * @return Punkte die von Fraktion gesehen werden.
	 */
	public HashSet<Point> getPointsInViewRange() {
		HashSet<Point> pointsInViewRange = new HashSet<Point>();
		for (NonStatic ns : NonStatic.getAllNonStatics()) {
			if (ns.getFraction().equals(this)) {
				try {
					pointsInViewRange.addAll(ns.getPointsInViewRange());
				} catch (ConcurrentModificationException e) {
					// TODO Exception werfen
					System.out.println(e + "beim Berechnen der Poins in View Range einer Fraktion.");
				}
			}
		}
		return pointsInViewRange;
	}
	
	/**
	 * @return Fraktion des gegnerischen Teams
	 */
	public Fraction getEnemyTeam() {
		if (this.equals(TeamA)) {
			return TeamB;
		} else if (this.equals(TeamB)) {
			return TeamA;
		} else {
			return Arno;
		}
	}
}
