package com.messages;

import game.objects.Player;

/**
 * Nachricht zum Aendern des boolean isReady.
 * @author Alex
 *
 */
public class UserReadyMessage extends Message {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -3242221158675017618L;
	
	/**
	 * Id des betroffenen Spielers.
	 */
	private final int playerId;
	
	/**
	 * Ist der Spieler bereit?
	 */
	private boolean isReady;
	
	/**
	 * Konstruktor. Legt fest ob Spieler bereit ist.
	 * @param playerID Welcher Spieler?
	 * @param isReady Ist der Spieler bereit?
	 */
	public UserReadyMessage(final int playerID, final boolean isReady) {
		super(MessageType.PLAYER_READY);
		this.isReady = isReady;
		this.playerId = playerID;
	}
	
	/**
	 * Fuehrt die Veraenderung aus.
	 */
	public void execute() {
		Player player = Player.getById(playerId);
		if (player != null) {
			player.setReadyToPlay(isReady);
		}
	}
}
