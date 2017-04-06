package match;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;

import game.content.buildings.Tower;
import game.content.ki.arno.behavior.NeutralGroup;
import game.content.ki.hero.HeroKi;
import game.content.ki.tower.TowerKi;
import game.content.ki.vasallen.behavior.VasalGroup;
import game.content.ki.vasallen.behavior.VasalTypes;
import game.objects.Fraction;
import game.objects.Map;
import game.objects.Match;

/**
 * Handelt die Ki Operationen.
 * @author Marius
 *
 */
public class KiHandler {
	
	
	/**
	 * Maximale anzahl an Vasalengruppen.
	 */
	public static final int MAX_NUMBER_OF_VASALLENGROUPS = 6;
	
	/**
	 * Die Anzahl an Vasallen die in einer Gruppe sind.
	 */
	public static final int NUMBER_OF_VASSALEN_PER_GROUP = 6;
	
	/**
	 * Die Anzahl an neutralen Mobs die in einer Gruppe sind.
	 */
	public static final int NUMBER_OF_NEUTRALS_PER_GROUP = 3;
	
	/**
	 * Die Spawnpunkte der Neutralen mobs.
	 */
	private static LinkedList<ArrayList<Point>> spawnPointsNeutral = new LinkedList<ArrayList<Point>>();
	
	/**
	 * Liste aller Vasal Gruppen.
	 */
	private LinkedList<VasalGroup> vasalGroups = new LinkedList<VasalGroup>();
	
	/**
	 * Liste aller neutralen Gruppen.
	 */
	private LinkedList<NeutralGroup> neutralGroups = new LinkedList<NeutralGroup>();
	
	/**
	 * Eine Liste mit allen Tuermen die auf der Karte stehe.
	 */
	private static LinkedList<Tower> towers = new LinkedList<Tower>();
	
	/**
	 * Eine Liste mit allen Helden die von der Ki gesteuert werden.
	 */
	private static LinkedList<HeroKi> heroes = new LinkedList<HeroKi>();
	
	
	/**
	 * Konstruktor. Inhalt momentan nur zum Test.
	 * @param map Das Objekt der Map
	 */
	public KiHandler(final Map map) {
		spawnNewVasallenGroups();
		spawnNeutralGroups();
		assignTowerKi();
	}
	
	/**
	 * Fuegt den Tuerem die Ki Operationen hinzu.
	 */
	private void assignTowerKi() {
		for (Tower t : towers) {
			t.setTowerKi(new TowerKi(t));
		}
		
	}
	
	/**
	 * Plant die naechste Action jeder KI Group anhand des Graphomaten.
	 */
	private void planKiGroupActions() {
		planVasalGroupActions();
		planNeutralGroupActions();
		planTowerActions();
		planHeroActions();
	}
	
	/**
	 * Plant die naechsten Aktionen aller Vasalen.
	 */
	private void planVasalGroupActions() {
		for (VasalGroup group : vasalGroups) {
			if (!group.isGroupInFinalState()) {
				group.planKiGroupAction();
			}
		}
	}
	
	/**
	 * Plant die naechsten Aktionen aller Neutralen.
	 */
	private void planNeutralGroupActions() {
		for (NeutralGroup group : neutralGroups) {
			if (!group.isGroupInFinalState()) {
				group.planKiGroupAction();
			}
		}
	}
	
	/**
	 * Plant die naechsten Aktionen aller Neutralen.
	 */
	private void planTowerActions() {
		for (Tower t : towers) {
			if (!t.getTowerKi().isGroupInFinalState()) {
				t.getTowerKi().planKiGroupAction();
			}
		}
	}
	
	/**
	 * Plant die naechsten Aktionen aller Helden.
	 */
	private void planHeroActions() {
		for (HeroKi h : heroes) {
			if (!h.isGroupInFinalState()) {
				h.planKiGroupAction();
			}
		}
	}
	
	/**
	 * setzt Alle Vallengruppen neu auf die Karte.
	 */
	private void spawnNewVasallenGroups() {
		vasalGroups.add(new VasalGroup(VasalTypes.DEFAULT_TOP_PATH, NUMBER_OF_VASSALEN_PER_GROUP, Fraction.TeamA, Match.getMatch().getMap()));
		vasalGroups.add(new VasalGroup(VasalTypes.DEFAULT_TOP_PATH, NUMBER_OF_VASSALEN_PER_GROUP, Fraction.TeamB, Match.getMatch().getMap()));
		vasalGroups.add(new VasalGroup(VasalTypes.DEFAULT_BOTTOM_PATH, NUMBER_OF_VASSALEN_PER_GROUP, Fraction.TeamA,  Match.getMatch().getMap()));
		vasalGroups.add(new VasalGroup(VasalTypes.DEFAULT_BOTTOM_PATH, NUMBER_OF_VASSALEN_PER_GROUP, Fraction.TeamB,  Match.getMatch().getMap()));
		vasalGroups.add(new VasalGroup(VasalTypes.DEFAULT_MIDDLE_PATH, NUMBER_OF_VASSALEN_PER_GROUP, Fraction.TeamA,  Match.getMatch().getMap()));
		vasalGroups.add(new VasalGroup(VasalTypes.DEFAULT_MIDDLE_PATH, NUMBER_OF_VASSALEN_PER_GROUP, Fraction.TeamB,  Match.getMatch().getMap()));
	}
	
	/**
	 * setzt alle neutrallen Gruppen auf die Karte.
	 */
	private void spawnNeutralGroups() {
		for (ArrayList<Point> spawnArea : getSpawnPointsNeutral()) {
			neutralGroups.add(new NeutralGroup(NUMBER_OF_NEUTRALS_PER_GROUP, spawnArea, Match.getMatch().getMap()));
		}
	}
	
	/**
	 * Wechselt das verhalten der Helden Ki.
	 */
	private void changeHeroBehavior() {
		for (HeroKi h : heroes) {
			h.changeBehavior();
		}
	}
	

	
	/**
	 *  Fuehrt alle aktionen aus die die Ki betreffen.
	 *  (Ausser das das ausfuehren von geplanten aktionen)
	 * @param turn Die aktuelle Runde
	 */
	public void handleKi(final int turn) {
		planKiGroupActions();
		if (turn % 5000 == 0) {
			changeHeroBehavior();
		}
	}

	/**
	 * @return the towers
	 */
	public static LinkedList<Tower> getTowers() {
		return towers;
	}

	/**
	 * @param t the towers to set
	 */
	public static void setTowers(final LinkedList<Tower> t) {
		towers = t;
	}

	/**
	 * @return the spawnPointsNeutral
	 */
	public static LinkedList<ArrayList<Point>> getSpawnPointsNeutral() {
		return spawnPointsNeutral;
	}

	/**
	 * @param spawnPointsNeutral the spawnPointsNeutral to set
	 */
	public static void setSpawnPointsNeutral(final LinkedList<ArrayList<Point>> spawnPointsNeutral) {
		KiHandler.spawnPointsNeutral = spawnPointsNeutral;
	}

	/**
	 * @return the heroes
	 */
	public static LinkedList<HeroKi> getHeroes() {
		return heroes;
	}

	/**
	 * @param heroes the heroes to set
	 */
	public static void setHeroes(final LinkedList<HeroKi> heroes) {
		KiHandler.heroes = heroes;
	}
	
	
	
}
