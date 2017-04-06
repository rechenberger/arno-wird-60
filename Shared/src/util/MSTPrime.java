package util;

import java.util.Comparator;
import java.util.PriorityQueue;
import util.graph.Arc;
import util.graph.NeighbourVertex;


/**
 * Berechnet einen MST in einem NeighbourGraph.
 * Quelle http://algs4.cs.princeton.edu/43mst/PrimMST.java.html
 * @author Marius
 *
 * @param <T>
 */
public class MSTPrime<T> {

	/**
	 * Die Arc die zu dieser Vertex fuheren.
	 */
	private Arc<T>[] arcTo;
	/**
	 * 
	 */
	private Arc<T>[] backArcFrom;
	
	/**
	 * Der Neighbourgraph in dem der MST berechnet werden soll.
	 */
	private NeighbourGraph<T> g;
	/**
	 * Hier wird der Berechnete MST gespeichert.
	 */
	private NeighbourGraph<T> mstG;
	/**
	 * Die Kommulierten Kosten um zu der Vertex mit der gegebnen Id zu Kommen.
	 */
    private double[] distTo;      // distTo[v] = weight of shortest such edge
    /**
     * Ob die Vertex bereichts untersucht worden ist.
     */
    private boolean[] marked;
    
    /**
     * 
     */
    private PriorityQueue<NeighbourVertex<T>> pq = new PriorityQueue<NeighbourVertex<T>>(10, new Comparator<NeighbourVertex<T>>() {
		@Override
		public int compare(final NeighbourVertex<T> v1, final NeighbourVertex<T> v2) {
			if (distTo[v1.getId()] < distTo[v2.getId()]) {
				return -1;
			} else if (distTo[v1.getId()] > distTo[v2.getId()]) {
				return +1;
			}
			return 0;
		}
    });
    
    /**
     * Konstruktor.
     * @param graph Der Neighbourgraph in dem ein MST berechnet werden soll.
     */
    @SuppressWarnings("unchecked")
	public MSTPrime(final NeighbourGraph<T> graph) {
    	g = graph;
    	distTo = new double[g.size()];
    	marked = new boolean[g.size()];
        arcTo = new Arc[g.arcSize()];
        backArcFrom = new Arc[g.arcSize()];

        for (int v = 0; v < g.size(); v++) {
        	distTo[v] = Double.POSITIVE_INFINITY;
        	marked[v] = false;
        }
        

        
    }
    
    /**
     * Der Eigentliche Algorithmus.
     * @return Der MST
     */
    public NeighbourGraph<T> run() {
    	
        for (int v = 0; v < g.size(); v++) {     // run from each vertex to find
            if (!marked[v]) {
				prim(g, v);      // minimum spanning forest
			}
        }
        createMstG();
    	return mstG;
    }
    
    /**
     * Erstellt fuer jeden Vertex der noch nicht in einem MST vorhanden ist einen MST.
     * @param gs Der Graph ueber dem ein MST berechnet werden soll.
     * @param s Die aktuelle VertexId
     */
    private void prim(final NeighbourGraph<T> gs, final int s) {
        distTo[s] = 0.0;
        pq.add(g.getNeighbourVertex(s));
        while (!pq.isEmpty()) {
            int v = pq.remove().getId();
            scan(v);
        }
    }
    
    /**
     * Ueberprueft die MST Bedingungen fuer dieser Vertex.
     * @param v Die id der Vertex
     */
    private void scan(final int v) {
        marked[v] = true;
        for (Arc<T> a : g.getNeighbourVertex(v).getArcs()) {
        	if (a != null) {
        		int w = a.getNextVertex().getId();
		        if (!marked[w]) { // v-w is not obsolete edge
				
		        	if (a.getWeight() < distTo[w]) {
		        		
		        		distTo[w] = a.getWeight();
		        		arcTo[w] = a;
		        	
		        		if (((NeighbourVertex<T>) a.getPrevVertex()).getTopArc() == a) {
		        			backArcFrom[w] = g.getNeighbourVertex(w).getBottomArc();
		        		}
		        		if (((NeighbourVertex<T>) a.getPrevVertex()).getBottomArc() == a) {
		        			backArcFrom[w] = g.getNeighbourVertex(w).getTopArc();
		        		}
		        		
		        		if (((NeighbourVertex<T>) a.getPrevVertex()).getLeftArc() == a) {
		        			backArcFrom[w] = g.getNeighbourVertex(w).getRightArc();
		        		}
		        		if (((NeighbourVertex<T>) a.getPrevVertex()).getRightArc() == a) {
		        			backArcFrom[w] = g.getNeighbourVertex(w).getLeftArc();
		        		}
		        		//arcTo[v].add(a);
		        		if (!pq.contains(g.getNeighbourVertex(w))) {
		        			pq.add(g.getNeighbourVertex(w));
		        		}
		        	}
		    	}
	        }
        }
    }
    
    /**
     * Fuegt den berechneten MST in einnen NeighbourVertexGraph.
     */
    private void createMstG() {
    	
    	mstG = new NeighbourGraph<T>();
    	for (NeighbourVertex<T> v : g.getNeighbourVertexArr()) {
    		mstG.addNeighbourVertex(v.getValue());
    	}
    	
    	for (int i = 0; i < mstG.size(); i++) {
    		
    		Arc<T> a = arcTo[i];
    		Arc<T> backA = backArcFrom[i];
    		
    		if (a == null && backA == null) {
    			continue;
    		}
    		if (((NeighbourVertex<T>) a.getPrevVertex()).getTopArc() == a) {
    			mstG.addUndirectedTopArc(((NeighbourVertex<T>) a.getPrevVertex()).getId(), ((NeighbourVertex<T>) a.getNextVertex()).getId(), a.getWeight());
    			
    		}
    		if (((NeighbourVertex<T>) a.getPrevVertex()).getBottomArc() == a) {
    			mstG.addUndirectedBottomArc(((NeighbourVertex<T>) a.getPrevVertex()).getId(), ((NeighbourVertex<T>) a.getNextVertex()).getId(), a.getWeight());
    		}
    		
    		if (((NeighbourVertex<T>) a.getPrevVertex()).getLeftArc() == a) {
    			mstG.addUndirectedLeftArc(((NeighbourVertex<T>) a.getPrevVertex()).getId(), ((NeighbourVertex<T>) a.getNextVertex()).getId(), a.getWeight());
    		}
    		if (((NeighbourVertex<T>) a.getPrevVertex()).getRightArc() == a) {
    			mstG.addUndirectedRightArc(((NeighbourVertex<T>) a.getPrevVertex()).getId(), ((NeighbourVertex<T>) a.getNextVertex()).getId(), a.getWeight());
    		}
    	}
    }
}
