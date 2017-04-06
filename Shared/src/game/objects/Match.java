package game.objects;

import java.util.LinkedList;

import settings.GlobalSettings;


/**
 * 
 * @author Tristan
 *
 */
public class Match extends GameObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1429986935799529972L;
	/**
	 * Karte.
	 */
	private Map map;
	
	/**
	 * Millisekunden bis Match startet.
	 */
	private int startingIn = GlobalSettings.MATCH_LOBBY_TIME;
	
	/**
	 */
	public Match() {
		map = new Map();
	}

	/**
	 * 
	 * @return Map
	 */
	public Map getMap() {
		return this.map;
	}
	
	/**
	 * @return Gibt das Match aus der Liste aller GameObjects.
	 */
	public static Match getMatch() {
		LinkedList<Match> list = GameObject.getGameObjectsByClassName("Match");
		return list.getFirst();
	}

	/**
	 * @return Millisekunden bis Match startet.
	 */
	public int getStartingIn() {
		return startingIn;
	}

	/**
	 * @param setStartingIn Millisekunden bis Match startet.
	 */
	public void setStartingIn(final int setStartingIn) {
		this.startingIn = setStartingIn;
	}
	
	/**
	 * @param decStartingIn Millisekunden die abgezogen werden.
	 */
	public void decStartingIn(final int decStartingIn) {
		this.startingIn -= decStartingIn;
	}
	
	

}
