package util;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;

/**
 * Dieses Interface ermoeglicht der implementiertenden Klasse den Astaralgorithmus zu benutzen.
 * @author Marius
 *
 */
public interface AStarFieldSetAble {
	
	/**
	 * Standartkosten fuer ein feld.
	 * Kann benutzt werden wenn die unteren Methoden implementiert werden.
	 */
	int COSTS_FOR_FIELD = 1;
	/**
	 * Hohe Kosten fuer ein Feld.
	 * Kann benutzt werden wenn die unteren Methoden implementiert werden.
	 */
	int HIGH_COSTS_FOR_FIELD = 100;
	/**
	 * Kosten die Signalisieren, das dieses Feld nicht begehbar ist.
	 * Kann benutzt werden wenn die unteren Methoden implementiert werden.
	 */
	int NOT_MOVEABLE = -1;
	
	/**
	 * Diese Methode ist dazu da die begehbaren Felder fuer den Astar Algorithmus zu verfuegung zu stellen.
	 * <br>
	 * Die Value des Fields spiegelt dabei die Kosten wieder es zu betreten. Bei <b>-1 ist dieses Feld nicht begehbar.</b>
	 * <br>
	 * Alle felder die zar in der Angegebenen Areadimension liegen, aber nicht in MoveableFields sind <b>automatisch nicht begehbar</b>.
	 * @return die begehbaren Felder.
	 */
	ArrayList<Field<Integer>> getMovableFields();
	
	/**
	 * Die Dimension des Gebietes in dem der Weg gesucht werden soll.
	 * @return Die Dimension des weges
	 */
	Dimension getAreaDimension();
	
	/**
	 * Der Startpunkt oder die Menge an Punken von dem aus das Ziel gesucht wird.
	 * <br>
	 * Falls eine Menge an Punkten ein Startfieldset makieren, muss <b>diese menge Recheckig</b> sein.
	 * Alles andere ist nicht getestet und wir vermutlich zu <b>Fehlern</b> fuehren.
	 * Wenn eine Menge an Punkten ein Startfieldset makieren soll, dann muss <b>getPathSize der laenge einer Kante in Rechteck entsprechen.</b>
	 * @return das StartFieldSet
	 */
	ArrayList<Point> getStartFieldSet();
	
	/**
	 * 
	 * @return Der Zielpunkt zu dem ein Weg gefunden werden soll.
	 */
	Point getGoal();
	
	/**
	 * 
	 * @return Die Breite des Pfades.
	 */
	Dimension getPathSize();
	

}
