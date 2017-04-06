package util;

import java.util.ArrayList;

import util.graph.Arc;
import util.graph.NeighbourVertex;

/**
 * Ein Graph dessen Vertices maximal 4 Arcs in 4 verschiedene Richtungen haben.
 * @author Marius
 *
 * @param <T>
 */
public class NeighbourGraph<T> extends Graph<T> {

	/**
	 * Liste aller Vertices.
	 */
	private ArrayList<NeighbourVertex<T>> neighbourVertexArr = new ArrayList<NeighbourVertex<T>>();
	
	/**
	 * Fuegt eine neue Vertex dem Graphen hinzu.
	 * @param value Wert dieser Vertex
	 * @return die id dieser Vertex
	 */
	public int addNeighbourVertex(final T value) {
		

		NeighbourVertex<T> vertex = new NeighbourVertex<T>(value, this.counterIdVertex);
		getNeighbourVertexArr().add(counterIdVertex, vertex);
		return this.counterIdVertex++;
	
	}
	
	/**
	 * Fuget eine Top Arc von der StartVertex zu der EndVertex hinzu.
	 * Bei der EndVertex wird also eine BottomArc hinzugefuegt.
	 * @param start Die StartVertex
	 * @param end Die Endvertex
	 * @param weight die Gewichtung
	 */
	public void addUndirectedTopArc(final int start, final int end, final int weight) {
		NeighbourVertex<T> startV = getNeighbourVertexArr().get(start);
		NeighbourVertex<T> endV = getNeighbourVertexArr().get(end);
		startV.addTopArc(endV, weight, this.counterIdArc++);
		endV.addBottomArc(startV, weight, this.counterIdArc++);
	}
	
	/**
	 * Fuget eine Bottom Arc von der StartVertex zu der EndVertex hinzu.
	 * Bei der EndVertex wird also eine TopArc hinzugefuegt.
	 * @param start Die StartVertex
	 * @param end Die Endvertex
	 * @param weight die Gewichtung
	 */
	public void addUndirectedBottomArc(final int start, final int end, final int weight) {
		NeighbourVertex<T> startV = getNeighbourVertexArr().get(start);
		NeighbourVertex<T> endV = getNeighbourVertexArr().get(end);
		startV.addBottomArc(endV, weight, this.counterIdArc++);
		endV.addTopArc(startV, weight, this.counterIdArc++);
	}
	
	/**
	 * Fuget eine Left Arc von der StartVertex zu der EndVertex hinzu.
	 * Bei der EndVertex wird also eine RightArc hinzugefuegt.
	 * @param start Die StartVertex
	 * @param end Die Endvertex
	 * @param weight die Gewichtung
	 */
	public void addUndirectedLeftArc(final int start, final int end, final int weight) {
		NeighbourVertex<T> startV = getNeighbourVertexArr().get(start);
		NeighbourVertex<T> endV = getNeighbourVertexArr().get(end);
		startV.addLeftArc(endV, weight, this.counterIdArc++);
		endV.addRightArc(startV, weight, this.counterIdArc++);
	}
	
	/**
	 * Fuget eine Right Arc von der StartVertex zu der EndVertex hinzu.
	 * Bei der EndVertex wird also eine LeftArc hinzugefuegt.
	 * @param start Die StartVertex
	 * @param end Die Endvertex
	 * @param weight die Gewichtung
	 */
	public void addUndirectedRightArc(final int start, final int end, final int weight) {
		NeighbourVertex<T> startV = getNeighbourVertexArr().get(start);
		NeighbourVertex<T> endV = getNeighbourVertexArr().get(end);
		startV.addRightArc(endV, weight, this.counterIdArc++);
		endV.addLeftArc(startV, weight, this.counterIdArc++);
	}
	
	/**
	 * Gibt eine Neighbourvertex zuruck mit der uebergebenen Id.
	 * @param id Die Id der Gesuchten Vertex
	 * @return Die Vertex
	 */
	public NeighbourVertex<T> getNeighbourVertex(final int id) {
		
		return getNeighbourVertexArr().get(id);
		
	}
	
	/**
	 * @return Die Menge aller Vertices
	 */
	public int size() {
		return this.counterIdVertex;
	}
	
	/**
	 * 
	 * @return Die Menge aller Arcs
	 */
	public int arcSize() {
		return this.counterIdArc;
	}
	
	/**
	 * Zu Debugzwecken.
	 */
	public void print() {
		int counter = 0;
		for (NeighbourVertex<T> v : this.getNeighbourVertexArr()) {
			
			ArrayList<Arc<T>> arcs = v.getArcs();
			if (arcs == null ||  arcs.isEmpty()) {
				continue;
			}
			
			for (Arc<T> a : arcs) {
				NeighbourVertex<T> nextVertex = (NeighbourVertex<T>) a.getNextVertex();
				System.out.print(v.getValue() + " (" + v.getId() + ") -> " + nextVertex.getValue()  + " (" + nextVertex.getId() + ") ( " + v.getArcWeight(nextVertex) + " ) \n");
				counter++;
			}
		}
		
		System.out.println(counter);
	}

	/**
	 * 
	 * @return Eine ArrayList aller Neighbourvertices
	 */
	public ArrayList<NeighbourVertex<T>> getNeighbourVertexArr() {
		return neighbourVertexArr;
	}
}
