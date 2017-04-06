package util.graphomat;

import java.io.Serializable;

import util.graphomat.Conditions.Condition;

/**
 * Ein Arc, der nur beschritten werden darf wenn eine bestimmte Bedingung erfuellt ist.
 * @author Marius
 * @param <T>
 *
 */
public class ConditionalArc<T> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7324798551104327098L;

	/**
	 * Die Id dieser Arc.
	 */
	private int id = 0;
	
	/**
	 * Die Vorherige ActionVertex, spiegelt den Vorherigen Zustand des Graphomaten wieder.
	 */
	private ActionVertex<T> nextVertex = null;
	/**
	 * Die Naechste ActionVertex, spiegelt den Nachsten Zustand des Graphomaten wieder.
	 */
	private ActionVertex<T> prevVertex = null;

	
	/**
	 * Die Kondition die erfuellt sein muss, damit dieser Arc beschritten werden darf.
	 */
	private Condition condition;
	
	/**
	 * 
	 * @param prevVertex Der Start dieser Arc
	 * @param nextVertex Das Ziel dieser Arc
	 * @param c Die Condition die erfuellt sein muss.
	 * @param id Die Id dieser Arc
	 */
	public ConditionalArc(final ActionVertex<T>  prevVertex, final ActionVertex<T>  nextVertex, final Condition c, final int id) {
		
		this.setArcData(prevVertex, nextVertex, id);
		this.setCondition(c);
		
	}
	


	/**
	 * 
	 * @return Die Zielvertex dieser Arc
	 */
	public ActionVertex<T> getNextVertex() {
		return this.nextVertex;
	}
	
	/**
	 * 
	 * @return Die STartVertex dieses Arcs
	 */
	public ActionVertex<T> getPrevVertex() {
		return this.prevVertex;
	}
	
	/**
	 * 
	 * @param <T>
	 * @param prevVertex Der Start dieser Arc
	 * @param nextVertex Das Ziel dieser Arc
	 * @param id Die Id dieser Arc
	 */
	private void setArcData(final ActionVertex<T>  prevVertex, final ActionVertex<T>  nextVertex, final int id) {
		
		this.prevVertex = prevVertex;
		this.nextVertex = nextVertex;
		this.id = id;
	}
	
	/**
	 * @return Die Id dieses Arcs
	 */
	public int getId() {
		return this.id;
	}



	/**
	 * @return the condition
	 */
	public Condition getCondition() {
		return condition;
	}



	/**
	 * @param condition the condition to set
	 */
	public void setCondition(final Condition condition) {
		this.condition = condition;
	}
}
