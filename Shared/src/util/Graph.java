package util;
import java.util.ArrayList;

import util.graph.Arc;
import util.graph.Vertex;

/**
 * Ein Graph.
 *  
 * Quelle: Im Rahmen der Info II Veranstalltung von Prof. Speckenmeyer von mir selbst entwickelt.
 * @author Marius
 *
 * @param <T> Welchen Typ die Value in der Vertex annehmen darf.
 */
public class Graph<T> {
	
	/**
	 * Der Vertex Zaehler.
	 */
	protected int counterIdVertex = 0; 
	/**
	 * Der Arc Zaehler.
	 */
	protected int counterIdArc = 0;
	/**
	 * Liste aller Vertices.
	 */
	protected ArrayList<Vertex<T>> vertexArr = new ArrayList<Vertex<T>>();
	
	/**
	 * Fuegt dem Graphen eine neue Vertex hinzu.
	 * @param value Den Wert der Vertex.
	 * @return Die Id der Vertex.
	 */
	public int addVertex(final T value) {
		
		Vertex<T> vertex = new Vertex<T>(value, this.counterIdVertex);
		vertexArr.add(counterIdVertex, vertex);
		return this.counterIdVertex++;
	
	}
	
	/**
	 * Gibt die Vertex mit der gegebenen Id zurueck.
	 * @param id Die Id der gewuenschten Vertex.
	 * @return Die Vertex
	 */
	public Vertex<T> getVertex(final int id) {
		
		return this.vertexArr.get(id);
		
	}
	
	/**
	 * Fuegt eine neue Arc hinzu, welche zwei Vertices verbindet.
	 * @param start StartId der Vertex
	 * @param end EndId der Vertex
	 * @param weight Die Kosten dieses Arcs
	 */
	public void addArc(final int start, final int end, final int weight) {
		
		Vertex<T> startVertex = this.getVertex(start);
		Vertex<T> endVertex = this.getVertex(end);
		startVertex.addArc(endVertex, weight, this.counterIdArc);
		this.counterIdArc++;
		
	}
	
	/**
	 * Fuegt eine Arc zwischen 2 Vertices hinzu.
	 * @param start Startid der Vertex
	 * @param end endif der Vertex
	 */
	public void addArc(final int start, final int end) {
		
		Vertex<T> startVertex = this.getVertex(start);
		Vertex<T> endVertex = this.getVertex(end);
		startVertex.addArc(endVertex, 1, this.counterIdArc);
		this.counterIdArc++;
		
	}
	
	/**
	 * Fuegt eine Arc hinzu die in beide Richtungen begehbar ist.
	 * @param start Das Id
	 * @param end EndId
	 * @param weightForward Gewichtung des Arcs in die eine Richtung
	 * @param weightBackward Gewichtung des Arcs in die andere Richtung 
	 */
	public void addUndirectedArc(final int start, final int end, final int weightForward, final int weightBackward) {
		
		this.addArc(start, end, weightForward);
		this.addArc(end, start, weightBackward);
		
	}
	
	/**
	 * Fuegt eine Arc hinzu die in beide Richtungen begehbar ist. Ohne Kosten
	 * @param start StartId der Vertex
	 * @param end Endid Der Vertex
	 */
	public void addUndirectedArc(final int start, final int end) {
		
		this.addArc(start, end, 1);
		this.addArc(end, start, 1);
		
	}
	
	/**
	 * 
	 * Gibt den Graphen aus.
	 * Wird zu debugzwecken genuzt.
	 */
	public void print() {
		//CHECKSTYLE:OFF
		for (Vertex<T> v : this.vertexArr) {
			
			ArrayList<Arc<T>> arcs = v.getArcs();
			
			for (Arc<T> a : arcs) {
				Vertex<T> nextVertex = a.getNextVertex();
				System.out.print(v.getValue() + " -> " + nextVertex.getValue()  + " ( " + v.getArcWeight(nextVertex) + " ) \n");
			}
		}
	}
	

	
	
	
}