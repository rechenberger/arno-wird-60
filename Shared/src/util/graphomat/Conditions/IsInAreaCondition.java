package util.graphomat.Conditions;


import game.objects.Fightable;

import java.awt.Dimension;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

import util.Graphomat;
import util.geometrie.RectangularArea;

/**
 * Diese Klasse ist eine spezielle Form der Condition.
 * Sie beschreibt Konditionen die erfuellt sind, wenn alle Kis einer Gruppe in einem 
 * bestimmten Gebiet befinden.
 * @author Marius
 *
 */
public class IsInAreaCondition extends Condition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6173155052993037776L;

	/**
	 * Das Gebiet in dem sich Die Ki befinden muessen oder eben nicht befinden duerfen,
	 * je nach definition der <i>checkCondition()</i> Methode.
	 */
	private ArrayList<Point> coords;
	
	/**
	 * HilfsObject, momentan nicht gebrauch aber ich wollte es nicht loeschen und dann nochmal hinschreiben.
	 * Kann benutzt werden wenn man an die Eckpunkte kommen will.
	 */
	private RectangularArea area;
	
	/**
	 * Der Konstruktor, hier wird das Gebiet ueber 4 Punkte definiert.
	 * @param bL unten links
	 * @param bR unten rechts 
	 * @param tL oben links
	 * @param tR oben rechts
	 * @param gr Das Objekt der Graphomaten
	 */
	public IsInAreaCondition(final Point bL, final Point bR, final Point tL, final Point tR, final Graphomat gr) {
		super(gr);
		int height = bL.y - tL.y;
		int width = bR.x - bL.x;
		
		area = new RectangularArea(bL, new Dimension(width, height));
		setCoords(area.getCoords());
	}
	
	/**
	 * 
	 * Der Konstruktor, hier wird das Gebiet ueber 4 Punkte definiert.
	 * @param points Das Gebiet als ArrayList
	 * @param gr Das Objekt der Graphomaten
	 */
	public IsInAreaCondition(final ArrayList<Point> points, final Graphomat gr) {
		super(gr);
		setCoords(points);
	}
	
	/**
	 * ueberprueft ob ein Punkt im Gebiet liegt.
	 * @param p Der Punkt der im Gebiet liegen soll
	 * @return ob der Pubkt im Gebiet liegt
	 */
	public boolean contains(final Point p) {
		return getCoords().contains(p);
	}

	/**
	 * @return the coords
	 */
	public ArrayList<Point> getCoords() {
		return coords;
	}

	/**
	 * @param co the coords to set
	 */
	public void setCoords(final ArrayList<Point> co) {
		this.coords = co;
	}
	
	@Override
	public boolean checkConditaion(final LinkedList<Fightable> kis) {
		for (Fightable member : kis) {
			if (!this.contains(member.getCoord())) {
				return false;
			}
		}
		return true;
	}



}
