package util.graphomat;


import game.objects.Fightable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

import util.graphomat.Actions.Action;
import util.graphomat.Conditions.Condition;

/**
 * Eine Vertex, die beim Betretten eine Action ausfuehrt.
 * @author Marius
 *
 * @param <T>
 */
public class ActionVertex<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8061815209865333956L;

	/**
	 * Die Id dieser Vertex.
	 */
	private int id;
	
	/**
	 * Alle Arcs die von dieser Vertex ausgehen.
	 */
	private ArrayList<ConditionalArc<T>> arcs = new ArrayList<ConditionalArc<T>>();
	
	
	/**
	 * Die Aktion die mit diesem Zustand verbunden ist.
	 */
	private Action action;
	
	/**
	 * Ob dies ein Endzustand ist aus dem nichtmehr entkommen werden kann.
	 */
	private boolean isFinalState = false;
	
	/**
	 * Der Wert dieser Vertex.
	 * Nicht zwingend notwendig fuer den Graphomaten, jedoch hilfreich beim Debuggen.
	 */
	protected T value = null;
	
	/**
	 * Ob diese Vertex einen arc hat.
	 */
	protected boolean hasArc = false;
	
	/**
	 * Konstruktor.
	 * @param value Die Value
	 * @param id Die Id
	 * @param a Die Action die ueber diese Vertex definiert ist.
	 */
	public ActionVertex(final T value, final int id, final Action a) {
		

		this.value = value;
		this.action = a;
		this.setId(id);
		
	}
	
	/**
	 * fuegt einen Arc von dieser Vertex aus zu einer andern hinzu.
	 * @param next Die Naechste Vertex
	 * @param c Die Kondition die uber den Arc denfiniert wird
	 * @param id die Id
	 */
	public void addArc(final ActionVertex<T> next, final Condition c, final int id) {
		
		ConditionalArc<T> arc = new ConditionalArc<T>(this, next, c, id);
		this.arcs.add(arc);
		this.hasArc = true;
		
	}
	
	/**
	 * 
	 * @return ob diese Vertex einen Arc hat.
	 */
	public boolean hasArc() {
		return this.hasArc;
	}
	
	/**
	 * 
	 * @return die Vaulue
	 */
	public T getValue() {
		return this.value;
	}
	
	/**
	 * 
	 * @return Alle Arcs dieser Vertex als Arraylist
	 */
	public ArrayList<ConditionalArc<T>> getArcs() {
		return this.arcs;
	}

	/**
	 * 
	 * @return Die Id dieser Vertex
	 */
	public int getId() {
		return id;
	}

	/**
	 * setzt die Id dieser Vertex.
	 * @param id die Id
	 */
	public void setId(final int id) {
		this.id = id;
	}
	
	/**
	 * Gibt das Actionobject zurueck die ueber diese Vertex definiert ist.
	 * @return Die Action
	 */
	public Action getAction() {
		return this.action;
	}
	
	/**
	 * fuehrt die Action dieser Vertex fuer alle uebergebenen Kis aus.
	 * @param kis Die Kis die die Action ausfuehren solen.
	 */
	public void doAction(final LinkedList<Fightable> kis) {
		action.planAction(kis);
	}

	/**
	 * @return the isFinalState
	 */
	public boolean isFinalState() {
		return isFinalState;
	}

	/**
	 * @param isFinalState the isFinalState to set
	 */
	public void setFinalState(final boolean isFinalState) {
		this.isFinalState = isFinalState;
	}
}
