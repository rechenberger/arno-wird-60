package game.content.ki.arno.behavior;


import game.objects.Fightable;

import java.util.LinkedList;

import util.Graphomat;
import util.graphomat.Actions.Action;

import util.graphomat.Actions.MovingToSpawnPointAction;
import util.graphomat.Conditions.IsInAreaCondition;

/**
 * Fuegt dem Graphomaten der Neutralen Monster die Moeglichkeit hinzu zu Ihrem Spawnpoint zu laufen, falls die sich wo anders befinden.
 * 
 * @author Marius
 *
 */
public final class DefaultStates {
	
	/**
	 * darf nicht instanziert werden.
	 */
	private DefaultStates() {
		
	}

	/**
	 * Gibt die Ki Operatinen zurueck fuer neutrale mobs.
	 * @return Ki Operationen in Form vom Graphomat
	 */
	public static Graphomat getRouteAsGraphomat() {

		Graphomat g = new Graphomat();
		
		setVertices(g);
		setArcs(g);
		return g;
	}
	
	/**
	 * Speichert Die Vertices im Graphomaten.
	 * @param g Der Graphomat
	 */
	private static void setVertices(final Graphomat g) {
		// startState
		g.addVertex(0, new Action(g) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void planAction(final LinkedList<Fightable> kis) {
			}

		});

		g.setCurrentState(g.getVertex(0));
		
		g.addVertex(1, new MovingToSpawnPointAction(g));
	}
	
	/**
	 * Setzt die Arcs im Graphomaten, welcher Die Ki Operationen beschreibt.
	 * @param g Der Graphomat.
	 */
	private static void setArcs(final Graphomat g) {
		g.addArc(1, 0, new IsInAreaCondition(null, g) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 7041475823713634661L;
			
			@Override
			public boolean checkConditaion(final LinkedList<Fightable> kis) {
				for (Fightable member : kis) {
					if (member.getCoord() != member.getSpawnPoint()) {
						return false;
					}
				}
				return true;
			}
		});
		
	}
}
