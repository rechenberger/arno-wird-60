package game.content.ki.hero.behavior.movement;

import java.util.LinkedList;

import util.Graphomat;
import util.graphomat.Actions.Action;
import game.actions.MoveAction;
import game.content.heros.Hero;
import game.content.ki.arno.Neutral;
import game.objects.Fightable;
import game.objects.KiPlayer;
import game.objects.Map;
import game.objects.NonStatic;

/**
 * Findet einen Monster im Jungle und laeuft zu diesem hin.
 * @author Marius
 *
 */
public final class FindJungleEnemy {
	
	/**
	 * In welcher Entfernung sich ein Junglemonster befinden muss.
	 * Befindet sich keins in dieser Entfernung wird die Verhaltensweise <i>BEHAVE_LIKE_A_VASAL</i>
	 * angewandt.
	 */
	private static final int MAX_RANGE_TO_LOOK_FOR_NEUTRAL = 100;
	
	/**
	 * Wie weit der Suchradius erhoeht werden soll, wenn im aktuellen Radius kein gegner gefunden wurde.
	 */
	private static final int RANGE_INCREMENTAL = 10;
	
	/**
	 * Utilityclass.
	 */
	private FindJungleEnemy() {
		
	}
	
	
	
	/**
	 * 
	 * @param n Das Nonstatic das einen Feid sucht.
	 * @param radius Der Suchradius
	 * @return ein JungleMonster
	 */
	private static Neutral findEnemyinJungle(final NonStatic n, final int radius) {
		if (radius >= MAX_RANGE_TO_LOOK_FOR_NEUTRAL) {
			return null;
		}
		
		int r = radius;
		for (NonStatic otherNonS : Map.getMatchMap().getFightablesInCircle(n.getCoord(), radius)) {
			if (otherNonS instanceof Neutral) {
				return (Neutral) otherNonS;
			}
		}
		r += RANGE_INCREMENTAL;
		return findEnemyinJungle(n, r);
	}
	
	
	/**
	 * 
	 * @param g Der Graphomat
	 */
	public static void getGraphomat(final Graphomat g) {
		
		Action moveToJungle = new Action(g) {
			/**
			 * 
			 */
			private static final long serialVersionUID = -3645564187993828884L;

			@Override
			public void planAction(final LinkedList<Fightable> kis) {
				for (Fightable member : kis) {
					Neutral jungleEnemy = findEnemyinJungle(member, RANGE_INCREMENTAL);
					if (jungleEnemy == null) {
						if (member instanceof Hero) {
							if (((Hero) member).getPlayer() instanceof KiPlayer) {
								((KiPlayer) ((Hero) member).getPlayer()).getKi().changeBehavior();
							}
						}
					} else {
						new MoveAction(member, jungleEnemy.getCoord()).plan();
					}
				}
			}
		};
		
		g.addVertex(0, moveToJungle);
		g.setCurrentState(g.getVertex(0));
	}
}
