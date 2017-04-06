package util.graph;

import java.util.ArrayList;

/**
 * Eine Vertex die maximal 4 Arcs, in Richtung oben, unten, links, rechtes haben kann.
 * @author Marius
 *
 * @param <T> Der Typ der Value
 */
public class NeighbourVertex<T> extends Vertex<T> {



	/**
	 * EIn Arc nach oben hin.
	 */
	private Arc<T> topArc;
	/**
	 * Ein Arc nach unten hin.
	 */
	private Arc<T> bottomArc;
	/**
	 * Ein Arc nach links hin.
	 */
	private Arc<T> leftArc;
	/**
	 * Ein Arc nach Rechts hin.
	 */
	private Arc<T> rightArc;
	
	/**
	 * DIe Value.
	 */
	private T value = null;
	
	/**
	 * Ob diese Vertex Arcs hat.
	 */
	protected boolean hasArc = false;
	
	/**
	 * Konstruktor.
	 * @param value Der Wert
	 * @param id Die id
	 */
	public NeighbourVertex(final T value, final int id) {
		super(value, id);
		this.value = value;
	}
	
	/**
	 * Fuegt eine Arc nach oben hin zu einer anderen Vertex hinzu.
	 * @param next Die naechste Vertex
	 * @param weight Die Gewichtung, bzw die Kosten
	 * @param id Die id der Arc.
	 */
	public void addTopArc(final Vertex<T> next, final int weight, final int id) {
		
		Arc<T> arc = new Arc<T>(this, next, weight, id);
		topArc = arc;
		this.hasArc = true;
	}
	
	/**
	 * Fuegt eine Arc nach unten hin zu einer anderen Vertex hinzu.
	 * @param next Die naechste Vertex
	 * @param weight Die Gewichtung, bzw die Kosten
	 * @param id Die id der Arc.
	 */
	public void addBottomArc(final Vertex<T> next, final int weight, final int id) {
		Arc<T> arc = new Arc<T>(this, next, weight, id);
		bottomArc = arc;
		this.hasArc = true;
	}
	
	/**
	 * Fuegt eine Arc nach links hin zu einer anderen Vertex hinzu.
	 * @param next Die naechste Vertex
	 * @param weight Die Gewichtung, bzw die Kosten
	 * @param id Die id der Arc.
	 */
	public void addLeftArc(final Vertex<T> next, final int weight, final int id) {
		Arc<T> arc = new Arc<T>(this, next, weight, id);
		leftArc = arc;
		this.hasArc = true;
	}
	
	/**
	 * Fuegt eine Arc nach rechts hin zu einer anderen Vertex hinzu.
	 * @param next Die naechste Vertex
	 * @param weight Die Gewichtung, bzw die Kosten
	 * @param id Die id der Arc.
	 */
	public void addRightArc(final Vertex<T> next, final int weight, final int id) {
		Arc<T> arc = new Arc<T>(this, next, weight, id);
		rightArc = arc;
		this.hasArc = true;
	}

	
	/**
	 * 
	 * @return Die Top Arc
	 */
	public Arc<T> getTopArc() {
		return topArc;
	}

	/**
	 * 
	 * @return Die Bottom Arc
	 */
	public Arc<T> getBottomArc() {
		return bottomArc;
	}

	/**
	 * 
	 * @return Die Left Arc
	 */
	public Arc<T> getLeftArc() {
		return leftArc;
	}

	/**
	 * 
	 * @return Der Right Arc
	 */
	public Arc<T> getRightArc() {
		return rightArc;
	}
	
	@Override
	public ArrayList<Arc<T>> getArcs() {
		ArrayList<Arc<T>> arcs = new ArrayList<Arc<T>>();
		if (getTopArc() != null) {
			arcs.add(getTopArc());
		}
		if (getBottomArc() != null) {
			arcs.add(getBottomArc());
		}
		if (getLeftArc() != null) {
			arcs.add(getLeftArc());
		}
		if (getRightArc() != null) {
			arcs.add(getRightArc());
		}
		if (!arcs.isEmpty()) {
			return arcs;
		}
		return null;
	}
	
	@Override
	public int getArcWeight(final Vertex<T> v) {
		
		for (Arc<T> arc : getArcs()) {
			
			if (arc.getNextVertex() == v) {
				return arc.getWeight();
			}
			
		}
		return -1;
	}

	/**
	 * 
	 * @return Den Wert der Vertex
	 */
	public T getValue() {
		return value;
	}


}
