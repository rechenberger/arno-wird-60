package util;

import java.awt.Point;

/**
 * Einziger Unterschied dieser Klasse zu einem Point ist das hier zusaetzlich noch ein wert eines beliebigen types gespeichert werden kann.
 * 
 * Zum Beispiel die Kosten die es mit sich bring eine Koordinate zu passieren.
 * @author Marius
 *
 * @param <T>
 */
public class Field<T> {

	/**
	 * die Koordinaten des Feldes.
	 */
	private Point coord;
	
	/**
	 * Der Wert des Feldes.
	 */
	private T value;
	
	
	/**
	 * Erstellt ein neues Feld und setzt Koordinaten und Wert.
	 * @param c Die Koordinaten
	 * @param v Der Wert des Feldes
	 */
	public Field(final Point c, final T v) {
		
		coord = c;
		value = v;
		
	}
	
	/**
	 * 
	 * @return der Wert des Feldes
	 */
	public T getValue() {
		return value;
	}
	
	/**
	 * 
	 * @param val Der Wert des Feldes
	 */
	public void setValue(final T val) {
		this.value = val;
	}
	
	/**
	 * 
	 * @return die Position des Feldes.
	 */
	public Point getCoord() {
		return coord;
	}
	
	/**
	 * 
	 * @param c Die Position des Feldes
	 */
	public void setCoord(final Point c) {
		this.coord = c;
	}
	
}
