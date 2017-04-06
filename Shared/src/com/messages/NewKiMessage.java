package com.messages;

import game.content.heros.Hero;

/**
 * Nachricht, um eine KI am Server anzumelden.
 * @author Christian Westhoff
 *
 */
public class NewKiMessage extends Message {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 6469445870383871577L;
	
	/**
	 * Held.
	 */
	private int heroId;
	
	/**
	 * Wahr, falls KI hinzugefuegt werden sollen
	 * Falsch, falls KI entfernt werden sollen.
	 */
	boolean addOrRemove;
	
	/**
	 * Konstruktor.
	 * @param hero Held
	 * @param addOrRemove Ob Ki hinzugefuegt oder entfernt wird.
	 */
	public NewKiMessage(final Hero hero, final boolean addOrRemove) {
		super(MessageType.ADD_NEW_KI);
		this.heroId = hero.getId();
		this.addOrRemove = addOrRemove;
	}

	/**
	 * Gibt den Helden zurueck.
	 * @return getHero
	 */
	public Hero getHero() {
		Hero hero = Hero.getById(heroId);
		return hero;
	}
	
	/**
	 * Wahr, falls KI hinzugefuegt werden sollen
	 * Falsch, falls KI entfernt werden sollen.
	 * @return Ob Ki hinzugefuegt oder entfernt wird.
	 */
	public boolean getAddOrRemove() {
		return this.addOrRemove;
	}
}
