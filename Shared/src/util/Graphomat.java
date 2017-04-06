package util;

import game.objects.Fightable;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

import util.graphomat.ConditionalArc;
import util.graphomat.ActionVertex;
import util.graphomat.Actions.Action;
import util.graphomat.Actions.AttackAction;
import util.graphomat.Conditions.Condition;


/**
 * 
 * @author Marius
 *
 */
public class Graphomat implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3410508412274330829L;
	
	/**
	 * Der Vertexzaehler.
	 */
	protected int counterIdVertex = 0; 

	/**
	 * Der id Zaehler.
	 */
	protected int counterIdArc = 0;
	
	/**
	 * Der Aktuelle Zustand des Graphomaten.
	 */
	private ActionVertex<Integer> currentState;
	
	/**
	 * der letzte Zustand des Graphomaten.
	 */
	private ActionVertex<Integer> lastState;
	/**
	 * Hier werden alle States in einem Stack gespeicehrt.
	 */
	private Stack<ActionVertex<Integer>> lastStatesStack = new Stack<ActionVertex<Integer>>();
	
	/**
	 * Der Anfangszustand.
	 */
	private ActionVertex<Integer> firstState;
	
	/**
	 * Hier sind alle Vertices gespeichert.
	 */
	protected ArrayList<ActionVertex<Integer>> vertexArr = new ArrayList<ActionVertex<Integer>>();
	
	/**
	 * Der name.
	 */
	private String name;
	
	/**
	 * fuegt eine Neue Vertex ein mit einer Value und eienr Action.
	 * @param value die Value, hier ist die Id gespeichert
	 * @param a die Action 
	 * @return Die id
	 */
	public int addVertex(final Integer value, final Action a) {
		
		ActionVertex<Integer> vertex = new ActionVertex<Integer>(value, this.counterIdVertex, a);
		vertexArr.add(counterIdVertex, vertex);
		return this.counterIdVertex++;
	
	}
	
	/**
	 * Gibt die Vertex mit der uebergebenen Id zurueck.
	 * @param id Die Id der Vertex.
	 * @return Die Vertex
	 */
	public ActionVertex<Integer> getVertex(final int id) {
		
		return this.vertexArr.get(id);
		
	}
	
	/**
	 * 
	 * @param start StartVertex
	 * @param end ZielVertex
	 * @param c Die Bedigung um diesen Arcbeschreiten zu duerfen.
	 */
	public void addArc(final int start, final int end, final Condition c) {
		
		ActionVertex<Integer> startVertex = this.getVertex(start);
		ActionVertex<Integer> endVertex = this.getVertex(end);
		startVertex.addArc(endVertex, c, this.counterIdArc);
		this.counterIdArc++;
		
	}
	
	/**
	 * Fuegt eine Verbindung von allen Vertexes zu einer bestimmten hinzu.
	 * @param end wohin die Arcs zeigen sollen.
	 * @param c Die Bedigung
	 */
	public void addArcFromAll(final int end, final Condition c) {
		for (ActionVertex<Integer> v : vertexArr) {
			if (v != getVertex(end)) {
				v.addArc(getVertex(end), c, counterIdArc++);
			}
		}
	}
	
	/**
	 * Fuegt eine Verbindung von einer Vertex zu allen anderen hinzu.
	 * @param start Von welcher Vertex aus.
	 * @param c Die Bedigung
	 */
	public void addArcToAll(final int start, final Condition c) {
		ActionVertex<Integer> startV = this.getVertex(start);
		for (ActionVertex<Integer> v : vertexArr) {
			if (v != getVertex(start)) {
				startV.addArc(v, c, counterIdArc++);
			}
		}
	}
	/**
	 * Suppres Waring weil es egal ist welcher Parameter in Vertex unter Value gespeichert ist.
	 * @param kis die in den Naechsten zustand welchseln koennten
	 */
	public void doNextAction(final LinkedList<Fightable> kis) {
		
		for (ConditionalArc<Integer> a: getCurrentState().getArcs()) {
			if (a.getCondition()
					.checkConditaion(kis)) {
				a.getNextVertex().doAction(kis);
				setCurrentState(a.getNextVertex());
			}
		}
	}
	
	/**
	 * Fuehr die aktuelle Aktion des Akutellen Statuses aus.
	 * @param kis Die Kis die dies ausfuehren sollen.
	 */
	public void doCurrentAction(final LinkedList<Fightable> kis) {
		currentState.doAction(kis);
	}
	/**
	 * Nur zum testen.
	 */
	public void print() {
		for (ActionVertex<Integer> v : this.vertexArr) {
			
			ArrayList<ConditionalArc<Integer>> arcs = v.getArcs();
			
			for (ConditionalArc<?> a : arcs) {
				ActionVertex<?> nextVertex = (ActionVertex<?>) a.getNextVertex();
				System.out.print(v.getValue() + " -> " + nextVertex.getValue()  + " \n");
			}
		}
	}

	/**
	 * @return the currentState
	 */
	public ActionVertex<Integer> getCurrentState() {
		return currentState;
	}

	/**
	 * @param nextState the currentState to set
	 */
	public void setCurrentState(final ActionVertex<Integer> nextState) {
		if (currentState == null) {
			setFirstState(nextState);
		}
		lastState = currentState;
		this.lastStatesStack.push(this.currentState);
		this.currentState = nextState;
	}
	
	/**
	 * Gibt den vorherigen Zustand des Graphomaten zurueck.
	 * @return Der Vorherige Zustand
	 */
	public ActionVertex<Integer> getLastState() {
		return lastState;
	}
	
	/**
	 * Gibt den Letzten Zustand zurueck der den Gegner nicht angreift.
	 * @return Den Zustand
	 */
	public ActionVertex<Integer> getLastNonAttackingState() {
		if (!(this.currentState.getAction() instanceof AttackAction)) {
			return this.currentState;
		}
		Stack<ActionVertex<Integer>> tmp = new Stack<ActionVertex<Integer>>();
		while (!this.lastStatesStack.isEmpty() && this.lastStatesStack.peek().getAction() instanceof AttackAction) {
			tmp.push(this.lastStatesStack.pop());
		}
		
		if (this.lastStatesStack.isEmpty()) {
			while (!tmp.isEmpty()) {
				this.lastStatesStack.push(tmp.pop());
			}	
			return null;
		} else {
			ActionVertex<Integer> toReturn = this.lastStatesStack.peek();
			while (!tmp.isEmpty()) {
				this.lastStatesStack.push(tmp.pop());
			}
			return toReturn;
		}
	}

	/**
	 * @return the counterIdVertex
	 */
	public int getCounterIdVertex() {
		return counterIdVertex;
	}

	/**
	 * @return the counterIdArc
	 */
	public int getCounterIdArc() {
		return counterIdArc;
	}

	/**
	 * @return the lastStatesStack
	 */
	public Stack<ActionVertex<Integer>> getLastStatesStack() {
		return lastStatesStack;
	}

	/**
	 * @param lastStatesStack the lastStatesStack to set
	 */
	public void setLastStatesStack(final Stack<ActionVertex<Integer>> lastStatesStack) {
		this.lastStatesStack = lastStatesStack;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the firstState
	 */
	public ActionVertex<Integer> getFirstState() {
		return firstState;
	}

	/**
	 * @param firstState the firstState to set
	 */
	public void setFirstState(final ActionVertex<Integer> firstState) {
		this.firstState = firstState;
	}
	
	/**
	 * Setzt den Graphomaten in den Anfangszustand zurueck.
	 */
	public void resetStates() {
		setCurrentState(getFirstState());
	}
}


