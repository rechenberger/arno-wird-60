package game.content.ki.hero.behavior;

import game.content.ki.hero.behavior.attack.AttackEnemy;
import game.content.ki.hero.behavior.attack.AttackEverything;
import game.content.ki.hero.behavior.movement.FindJungleEnemy;
import game.content.ki.vasallen.behavior.routen.FractionABottomPath;
import game.content.ki.vasallen.behavior.routen.FractionAMiddlePath;
import game.content.ki.vasallen.behavior.routen.FractionATopPath;
import game.content.ki.vasallen.behavior.routen.FractionBBottomPath;
import game.content.ki.vasallen.behavior.routen.FractionBMiddlePath;
import game.content.ki.vasallen.behavior.routen.FractionBTopPath;
import game.objects.Fraction;
import util.Graphomat;

/**
 * Hier werden alle Moeglichen Verhaltensweisen eines Helden zugewiesen.
 * @author Marius
 *
 */
public enum PossibleBehaviors {
	
	/**
	 * Geh in den Jungle um zu Leveln.
	 */
	GO_TO_JUNGLE() {
		
		/**
		 * Der Name dieser Verhaltenweise.
		 */
		@SuppressWarnings("unused")
		public static final String NAME = "Jungle";

		@Override
		public Graphomat getGraphomat(final Fraction f) {
			Graphomat g = new Graphomat();
			FindJungleEnemy.getGraphomat(g);
			AttackEverything.addAttackStatesToGraph(g);
			return g;
		}
		
	}, 
	
	/**
	 * Laeuft wie ein Vasal einen der Drei Wege entlang.
	 */
	BEHAVE_LIKE_A_VASAL() {
		
//		/**
//		 * Der Name dieser Verhaltenweise.
//		 */
//		public final String name() = "Vorstoss";

		@Override
		public Graphomat getGraphomat(final Fraction f) {
			double rand = Math.random();
			Graphomat g = null;
			if (rand < 0.33) {
				if (f == Fraction.TeamA) {
					g = FractionATopPath.getRouteAsGraphomat();
					AttackEnemy.addAttackStatesToGraph(g);
				} else {
					g = FractionBTopPath.getRouteAsGraphomat();
					AttackEnemy.addAttackStatesToGraph(g);
				}
			} else if (rand < 0.66) {
				if (f == Fraction.TeamA) {
					g = FractionAMiddlePath.getRouteAsGraphomat();
					AttackEnemy.addAttackStatesToGraph(g);
				} else {
					g = FractionBMiddlePath.getRouteAsGraphomat();
					AttackEnemy.addAttackStatesToGraph(g);
				}				
			} else {
				if (f == Fraction.TeamA) {
					g = FractionABottomPath.getRouteAsGraphomat();
					AttackEnemy.addAttackStatesToGraph(g);
				} else {
					g = FractionBBottomPath.getRouteAsGraphomat();
					AttackEnemy.addAttackStatesToGraph(g);
				}
			}
			
			return g;
		}
		
	};
	
	/**
	 * Gibt den Graphomaten dieser Verhaltensweise zurueck.
	 * @param f Die Fraction
	 * @return den Graphomaten.
	 */
	public abstract Graphomat getGraphomat(Fraction f);

}
