package com.messages;

import game.objects.GameObject;

/**
 * Nachricht mit Spielobjekt.
 * @author Tristan
 *
 */
public class GameContentMessage extends Message {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -8314864365210775996L;
	
	/**
	 * Das zu uebertragende Spielobjekt.
	 */
	private GameObject gameObject;
	
	/**
	 * @param setGameObject Das zu uebertragende Spielobjekt.
	 */
	public GameContentMessage(final GameObject setGameObject) {
		super(MessageType.GAMECONTENT);
		this.gameObject = setGameObject;
	}
	
	/**
	 * @return Das zu uebertragende Spielobjekt.
	 */
	public GameObject getContent() {
		return gameObject;
	}
	
	/**
	 * Loescht das GameObject.
	 */
	public void removeContent() {
		this.gameObject = null;
	}
	
}
