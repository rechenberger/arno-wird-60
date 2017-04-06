package util.graphomat.Actions;

import game.actions.MoveAction;

import game.objects.Fightable;

import java.awt.Point;
import java.util.LinkedList;

import util.Graphomat;

/**
 * Die Klasse spiegelt einen Befehl an die Ki wieder, die dieser Singnalisiert sich zu bewegen.
 * @author Marius
 *
 */
public class MovingToOnePointAction extends Action {

	/**
	 * das Ziel der Bewegung.
	 */
	private Point goal;
	
	/**
	 * Konstruktor.
	 * @param gr der Graphomat
	 * @param g Das Ziel der Bewegung
	 */
	public MovingToOnePointAction(final Graphomat gr,  final Point g) {
		super(gr);
		
		goal = g;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void planAction(final LinkedList<Fightable> kis) {
		for (Fightable member : kis) {
			new MoveAction(member, getGoal()).plan();
		}
	}

//
//	@Override
//	public void planAction(LinkedList<Ki> kis) {
//		// TODO Auto-generated method stub
//		
//	}


	/**
	 * @return the goal
	 */
	public Point getGoal() {
		return goal;
	}


	/**
	 * @param g the goal to set
	 */
	public void setGoal(final Point g) {
		this.goal = g;
	}

}
