package util.graphomat.Actions;

import game.objects.Fightable;

import java.io.Serializable;
import java.util.LinkedList;

import util.Graphomat;

/**
 * Eine Action, die auf einen State im Graphomat definiert ist.
 * @author Marius
 *
 */
public abstract class Action implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5905591425788025975L;
	/**
	 * Das Objekt des Graphomaten.
	 */
	private Graphomat g;
	
	/**
	 * Der Name der Action.
	 */
	private String name;
	
	/**
	 * Der Konstruktor.
	 * @param gr Das Objekt des Graphomaten.
	 */
	public Action(final Graphomat gr) {
		setG(gr);
	}
	
	/**
	 * Setzt den Graphomaten.
	 * @param gr Der Graphomat
	 */
	public void setGraphomat(final Graphomat gr) {
		setG(gr);
	}
	
	/**
	 * Beschreibt wie ide Action ausgefuehrt werden soll.
	 * @param kis auf welche Kis die Action ausgefuehrt werden soll.
	 */
	public abstract void planAction(LinkedList<Fightable> kis);

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
	 * @return the g
	 */
	public Graphomat getG() {
		return g;
	}

	/**
	 * @param g the graphomat to set
	 */
	public void setG(final Graphomat g) {
		this.g = g;
	}

}
