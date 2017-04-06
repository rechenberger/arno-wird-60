package util.graph;


/**
 * 
 * @author Marius
 * @param <T> Die Parameterisierung der VertexValue
 *
 */
public class Arc<T> {
	
	/**
	 * Die Id dieses Arcs.
	 */
	private int id = 0;
	
	/**
	 * Der Zeiger auf die Naechste Vertex.
	 */
	private Vertex<T> nextVertex = null; // endnode
	/**
	 * Der Zeiger auf die Vorherige Vertex.
	 */
	private Vertex<T> prevVertex = null; // startnode
	/**
	 * Die GEwichtung oder die Kosten.
	 */
	private int weight = 0;
	
	/**
	 * Konstruktor.
	 * @param prevVertex Zeiger auf vorherige Vertex
	 * @param nextVertex Zeiger auf naehcste Vertex
	 * @param weight Gewichtung 
	 * @param id id
	 */
	public Arc(final Vertex<T>  prevVertex, final Vertex<T>  nextVertex, final int weight, final int id) {
		
		this.setNextVertex(prevVertex, nextVertex, weight, id);
		
	}
	
	/**
	 * 
	 * @return Die naechste Vertex im Arc
	 */
	public Vertex<T> getNextVertex() {
		return this.nextVertex;
	}
	
	/**
	 * 
	 * @return Die vorherige Vertex im Arc
	 */
	public Vertex<T> getPrevVertex() {
		return this.prevVertex;
	}
	
	/**
	 * Initialisiert den Arc.
	 * @param prevVertex  Der Zeiger auf die Vorherige Vertex
	 * @param nextVertex Der Zeiger auf die naechste Vertex
	 * @param weight Die Gewichtung bzw. die Kosten.
	 * @param id Die Id des Arcs
	 */
	private void setNextVertex(final Vertex<T>  prevVertex, final Vertex<T>  nextVertex, final int weight, final int id) {
		
		this.prevVertex = prevVertex;
		this.nextVertex = nextVertex;
		this.id = id;
		this.weight = weight;
	
	}
	
	/**
	 * 
	 * @return Die Gewichtung oder die Kosten.
	 */
	public int getWeight() {
		return this.weight;
	}
	
	/**
	 * @return Die id
	 */
	public int getId() {
		return this.id;
	}
}
