package util.graphomat.Conditions;


import game.objects.Fightable;

import java.io.Serializable;
import java.util.LinkedList;

import util.Graphomat;

/**
 * Diese Klasse soll die Kondition des ConditionalArc beschreiben.
 * Beim Instanzieren muss diese Kondition genau definiert werden.
 * @author Marius
 *
 */
public abstract class Condition implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4646997175438569388L;
	
	/**
	 * Das Objekt des Graphomaten.
	 */
	private Graphomat g;
	
	/**
	 * Der name der Bedigung.
	 */
	private String name;
	
	/**
	 * Konstruktor.
	 * @param gr Das Objekt des Graphomaten.
	 */
	public Condition(final Graphomat gr) {
		g = gr;
	}
	
	/**
	 * 
	 * @return Den Graphomaten.
	 */
	public Graphomat getGraphomat() {
		return g;
	}
	
	/**
	 * 
	 * @param gr Der Graphomat
	 */
	public void setGraphomat(final Graphomat gr) {
		g = gr;
	}
	
	/**
	 * Hier kommt die Logik rein, Die jede Ki einer Gruppe erfuellen muss
	 * damit der State, ueber den die akutelle Aktion definiert ist,
	 * sich aendert.
	 * @param kis Die Kis
	 * @return ob die Kondition erfuellt ist und der State sich aendern muss
	 */
	public abstract boolean checkConditaion(LinkedList<Fightable> kis);

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


}
