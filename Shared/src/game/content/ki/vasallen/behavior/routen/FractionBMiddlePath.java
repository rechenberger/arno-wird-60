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
 * Vasalen gehen von Basis B zu Basis A ueber den MiddlePath.
 * @author Marius
 *
 */
public final class FractionBMiddlePath {

	/**
	 * darf nicht instanziert werden.
	 */
	private FractionBMiddlePath() {
		
	}
	
	/**
	 * Gibt die Ki Operatinen zurueck die es benoetigt diesen Weg zu gehen.
	 * @return Ki Operationen in Form vom Graphomat
	 */
	public static Graphomat getRouteAsGraphomat() {

		Graphomat g = new Graphomat();
		g.setName("FractionBMiddlePath");
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
			private static final long serialVersionUID = -8379485835336401202L;

			@Override
			public void planAction(final LinkedList<Fightable> kis) {
			}

		});

		g.setCurrentState(g.getVertex(0));

		// In die Mitte vom oberen Weg gehen
		g.addVertex(1, new MovingToOnePointAction(g, Map.getMatchMap().getImportantPositions().get(
				WayPoints.MIDDLEPATH_CENTER_POINT).get(0)));


		// zum Ausgangspunkt vom Toppath
		g.addVertex(2, new MovingToOnePointAction(g, Map.getMatchMap().getImportantPositions().get(
				WayPoints.MIDDLEPATH_ENTREE_TEAM_A).get(0)));


		// zu gegnerischen Basis
		g.addVertex(3, new MovingToOnePointAction(g, Map.getMatchMap().getImportantPositions().get(
				WayPoints.BASE_TEAM_A_CENTER_POINT).get(0)));


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
			private static final long serialVersionUID = 2898577877498601062L;

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
						.get(WayPoints.MIDDLEPATH_CENTER_POINT_AREA), g));

		// Wenn bei der Gegnerischen Basis angelangt, dann wechsele zu nicht tun
		g.addArc(
				2,
				3,
				new IsInAreaCondition(Map.getMatchMap().getImportantPositions()
						.get(WayPoints.MIDDLEPATH_ENTREE_TEAM_A_AREA), g));

		// Wenn bei der Gegnerischen Basis angelangt, dann wechsele zu nicht tun
		g.addArc(
				3,
				4,
				new IsInAreaCondition(Map.getMatchMap().getImportantPositions()
						.get(WayPoints.BASE_TEAM_A_CENTER_POINT_AREA), g));

	}
}
