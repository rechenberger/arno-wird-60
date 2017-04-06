package game.content.ki.vasallen.behavior.routen;


import game.content.ki.vasallen.behavior.attack.AttackNexus;
import game.content.statics.WayPoints;
import game.objects.Fightable;
import game.objects.Map;


import java.util.LinkedList;



import util.Graphomat;
import util.graphomat.Actions.Action;
import util.graphomat.Actions.MovingToOnePointAction;
import util.graphomat.Conditions.Condition;
import util.graphomat.Conditions.IsInAreaCondition;

/**
 * Vasalen gehen von Basis A zu Basis B ueber den BottomPath.
 * @author Marius
 *
 */
public final class FractionABottomPath {

	/**
	 * darf nicht instanziert werden.
	 */
	private FractionABottomPath() {
		
	}
	
	/**
	 * Gibt die Ki Operatinen zurueck die es benoetigt diesen Weg zu gehen.
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
		// In die Mitte vom oberen Weg gehen
		g.addVertex(1, new MovingToOnePointAction(g, Map.getMatchMap().getImportantPositions().get(
				WayPoints.BOTTOMPATH_CENTER_POINT).get(0)));

		// zum Ausgangspunkt vom Toppath
		g.addVertex(2, new MovingToOnePointAction(g, Map.getMatchMap().getImportantPositions().get(
				WayPoints.BOTTOMPATH_ENTREE_TEAM_B).get(0)));
		// zu gegnerischen Basis
		
		g.addVertex(3, new MovingToOnePointAction(g, Map.getMatchMap().getImportantPositions().get(
				WayPoints.BASE_TEAM_B_CENTER_POINT).get(0)));

		
		// hier passiert nichs mehr
		g.addVertex(4, AttackNexus.getAttackNexus());
	}
	
	/**
	 * Setzt die Arcs im Graphomaten, welcher Die Ki Operationen beschreibt.
	 * @param g Der Graphomat.
	 */
	private static void setArcs(final Graphomat g) {
		// aus startzustand in den ersten Zustand
		g.addArc(0, 1, new Condition(g) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean checkConditaion(final LinkedList<Fightable> kis) {
				return true;
			}

		});

		// Wenn sich alle Gruppen Mitglieder in einem bestimmten berech um den
		// Mittelpunkt des Weges befinden
		// change state to zu gegnerischen basis gehen
		g.addArc(
				1,
				2,
				new IsInAreaCondition(Map.getMatchMap().getImportantPositions()
						.get(WayPoints.BOTTOMPATH_CENTER_POINT_AREA), g));

		// Wenn bei der Gegnerischen Basis angelangt, dann wechsele zu nicht tun
		g.addArc(
				2,
				3,
				new IsInAreaCondition(Map.getMatchMap().getImportantPositions()
						.get(WayPoints.BOTTOMPATH_ENTREE_TEAM_B_AREA), g));


		// Wenn bei der Gegnerischen Basis angelangt, dann wechsele zu nicht tun
		g.addArc(
				3,
				4,
				new IsInAreaCondition(Map.getMatchMap().getImportantPositions()
						.get(WayPoints.BASE_TEAM_B_CENTER_POINT_AREA), g));
	}
}
