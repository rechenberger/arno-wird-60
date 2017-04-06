package game.content.ki.vasallen.behavior;

import java.awt.Point;
import java.util.ArrayList;

import game.content.ki.Ki;
import game.content.ki.vasallen.Melee;
import game.content.ki.vasallen.Ranged;
import game.content.ki.vasallen.VasalKinds;
import game.content.ki.vasallen.behavior.attack.AttackCharacter;
import game.content.ki.vasallen.behavior.routen.FractionABottomPath;
import game.content.ki.vasallen.behavior.routen.FractionAMiddlePath;
import game.content.ki.vasallen.behavior.routen.FractionATopPath;
import game.content.ki.vasallen.behavior.routen.FractionBBottomPath;
import game.content.ki.vasallen.behavior.routen.FractionBMiddlePath;
import game.content.ki.vasallen.behavior.routen.FractionBTopPath;
import game.content.statics.WayPoints;

import util.Graphomat;

import game.objects.Fraction;
import game.objects.Map;

/**
 * Alle Typen an KIs und ihre unterschiedlichen State Diagramme werden hier
 * definiert. Mit Typen sind nur die Verhaltenweisen gemeint.
 * Eine unterscheidung zwischen Nahkampf und Fernkampf findet in <b>VasalKinds</b> statt.
 * 
 * @see game.content.ki.vasallen.VasalKinds
 * @author Marius
 * 
 */
public enum VasalTypes {

	/**
	 * Der Default Ki Typ.
	 */
	DEFAULT_TOP_PATH() {

		/**
		 * Gibt einen neuen KI vom Typ Default zurueck, welcher zu einer
		 * Fraktion zugehoerig ist.
		 * 
		 * @param f
		 *            Die Fraktion
		 * @param memberOf
		 *            Die zugehoerigkeit zu einer kiGruppe
		 * @param kind Welche art von Vasal
		 * @return Das Neuerstellte Ki Object
		 */
		public Ki getNew(final Fraction f, final VasalGroup memberOf, final VasalKinds kind) {
			if (kind == VasalKinds.MELEE) {
				return new Melee(f/* , memberOf */);
			} else {
				return new Ranged(f/* , memberOf */);
			}
		}

		/**
		 * Hier wird der Graphomat erstellt, ueber den sich die Ki Operationen
		 * definieren fuer den oberen Pfad.
		 * 
		 * @param f
		 *            Die Fraction
		 * @return Das Objekt des Graphomats
		 */
		public Graphomat getGraphomat(final Fraction f) {

			if (f == Fraction.TeamA) {
				Graphomat g = FractionATopPath.getRouteAsGraphomat();
				AttackCharacter.addAttackStatesToGraph(g);
				return g;
			} else {
				Graphomat g = FractionBTopPath.getRouteAsGraphomat();
				AttackCharacter.addAttackStatesToGraph(g);
				return g;				
			}
		}

		@Override
		public ArrayList<Point> getSpawn(final Fraction f, final Map map) {
			if (f == Fraction.TeamA) {
				return map.getImportantPositions().get(WayPoints.TOPPATH_SPAWNPOINT_VASAL_TEAM_A_AREA);
			} else {
				return map.getImportantPositions().get(WayPoints.TOPPATH_SPAWNPOINT_VASAL_TEAM_B_AREA);
			}
		}
	},

	/**
	 * Der Default Ki Typ.
	 */
	DEFAULT_BOTTOM_PATH() {

		/**
		 * Gibt einen neuen KI vom Typ Default zurueck, welcher zu einer
		 * Fraktion zugehoerig ist.
		 * 
		 * @param f
		 *            Die Fraktion
		 * @param memberOf
		 *            Die zugehoerigkeit zu einer kiGruppe
		 * @param kind Welche art von Vasal
		 * @return Das Neuerstellte Ki Object
		 */
		public Ki getNew(final Fraction f, final VasalGroup memberOf, final VasalKinds kind) {
			if (kind == VasalKinds.MELEE) {
				return new Melee(f/* , memberOf */);
			} else {
				return new Ranged(f/* , memberOf */);
			}
		}

		/**
		 * Hier wird der Graphomat erstellt, ueber den sich die Ki Operationen
		 * definieren fuer den unteren Pfad.
		 * 
		 * @param f
		 *            Die Fraction
		 * @return Das Objekt des Graphomats
		 */
		public Graphomat getGraphomat(final Fraction f) {

			if (f == Fraction.TeamA) {
				
				Graphomat g = FractionABottomPath.getRouteAsGraphomat();
				AttackCharacter.addAttackStatesToGraph(g);
				return g;
			} else {
				Graphomat g = FractionBBottomPath.getRouteAsGraphomat();
				AttackCharacter.addAttackStatesToGraph(g);
				return g;
			}
		}

		@Override
		public ArrayList<Point> getSpawn(final Fraction f, final Map map) {
			if (f == Fraction.TeamA) {
				return map.getImportantPositions().get(WayPoints.BOTTOMPATH_SPAWNPOINT_VASAL_TEAM_A_AREA);
			} else {
				return map.getImportantPositions().get(WayPoints.BOTTOMPATH_SPAWNPOINT_VASAL_TEAM_B_AREA);
			}
		}

	},

	/**
	 * Der Default Ki Typ.
	 */
	DEFAULT_MIDDLE_PATH() {

		/**
		 * Gibt einen neuen KI vom Typ Default zurueck, welcher zu einer
		 * Fraktion zugehoerig ist.
		 * 
		 * @param f
		 *            Die Fraktion
		 * @param memberOf
		 *            Die zugehoerigkeit zu einer kiGruppe
		 * @param kind Welche art von Vasal
		 * @return Das Neuerstellte Ki Object
		 */
		public Ki getNew(final Fraction f, final VasalGroup memberOf, final VasalKinds kind) {
			if (kind == VasalKinds.MELEE) {
				return new Melee(f/* , memberOf */);
			} else {
				return new Ranged(f/* , memberOf */);
			}
		}

		/**
		 * Hier wird der Graphomat erstellt, ueber den sich die Ki Operationen
		 * definieren fuer den unteren Pfad.
		 * 
		 * @param f
		 *            Die Fraction
		 * @return Das Objekt des Graphomats
		 */
		public Graphomat getGraphomat(final Fraction f) {

			if (f == Fraction.TeamA) {
				Graphomat g = FractionAMiddlePath.getRouteAsGraphomat();
				AttackCharacter.addAttackStatesToGraph(g);
				return g;
			} else {
				Graphomat g = FractionBMiddlePath.getRouteAsGraphomat();
				AttackCharacter.addAttackStatesToGraph(g);
				return g;
			}
		}

		@Override
		public ArrayList<Point> getSpawn(final Fraction f, final Map map) {
			// TODO Auto-generated method stub
			return null;
		}

	};

	/**
	 * Gibt eine Neue Ki vom definierten Typ zuurueck.
	 * 
	 * @param f
	 *            Die Fraktion die diese Ki angehoert.
	 * @param memberOf
	 *            Die zugehoerigkeit zu einer kiGruppe
	 * @param kind Welche art von Vasal
	 * @return Das neue KI objekt.
	 */
	public abstract Ki getNew(Fraction f, VasalGroup memberOf, VasalKinds kind);

	/**
	 * Hier wird der Graphomat erstellt, ueber den sich die Ki Operationen
	 * definieren fuer den unteren Pfad.
	 * 
	 * @param f Die Fraktion
	 * 
	 * @return Das Objekt des Graphomats
	 */
	public abstract Graphomat getGraphomat(Fraction f);
	
	/**
	 * 
	 * @param f Die Fraktion
	 * @param map Das Objekt der Karte.
	 * @return das Gebiet in dem die Vasalen spawnen duerfen.
	 */
	public abstract ArrayList<Point> getSpawn(Fraction f, Map map);

}
