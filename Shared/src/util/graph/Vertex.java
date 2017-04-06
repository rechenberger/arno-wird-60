package util.graph;

import java.util.ArrayList;

/**
 * 
 * @author Marius
 *
 * @param <T> Der Wert der Value
 */
public class Vertex<T> {

	/**
	 * Die Id dieser Vertex.
	 */
	private int id;
	
	/**
	 * Eine Liste aller arcs, von dieser Vertex aus begehbar sind.
	 */
	private ArrayList<Arc<T>> arcs = new ArrayList<Arc<T>>();
	/**
	 * Der Wert der Vertex.
	 */
	protected T value = null;
	/**
	 * Ob die vertex Arcs hat.
	 */
	protected boolean hasArc = false;
	
	/**
	 * 
	 * @param value der Wert der Vertex
	 * @param id Die Id der Vertex
	 */
	public Vertex(final T value, final int id) {
		
		this.value = value;
		this.setId(id);
		
	}
	
	/**
	 * Fuegt eine Neue Arc von dieser Vertex aus hinzu.
	 * @param next Die Naechste Vertex, das Ziel des Arcs
	 * @param weight Die Gewichtung des Arcs
	 * @param id Die Id de Arc
	 */
	public void addArc(final Vertex<T> next, final int weight, final int id) {
		
		Arc<T> arc = new Arc<T>(this, next, weight, id);
		this.arcs.add(arc);
		this.hasArc = true;
		
	}

	/**
	 * Gibt die Gewichtung von dieser Vertex zu einer anderen Vertex zuruck.
	 * @param v Die andere Vertex
	 * @return Die Kosten des Arcs
	 */
	public int getArcWeight(final Vertex<T> v) {
		
		for (Arc<T> arc : this.arcs) {
			
			if (arc.getNextVertex() == v) {
				return arc.getWeight();
			}
			
		}
		return -1;
	}
	
	/**
	 * 
	 * @return Ob diese Vertex arcs hat.
	 */
	public boolean hasArc() {
		return this.hasArc;
	}
	
	/**
	 * 
	 * @return Die value
	 */
	public T getValue() {
		return this.value;
	}
	
	/**
	 * 
	 * @return Die Liste aller Arcs von dieser Vertex aus.
	 */
	public ArrayList<Arc<T>> getArcs() {
		return this.arcs;
	}

	/**
	 * 
	 * @return die Id
	 */
	public int getId() {
		return id;
	}

	/**
	 * 
	 * @param id Die id
	 */
	public void setId(final int id) {
		this.id = id;
	}
}
