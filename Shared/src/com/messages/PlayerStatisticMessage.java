package com.messages;

import game.objects.Player;

/**
 * Nachricht zum Aendern der Spielerstatistik.
 * @author Tristan
 */
public class PlayerStatisticMessage extends Message {

	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 2535034158059940774L;

	/**
	 * Id des betroffenen Spielers.
	 */
	private final int playerId;
	
	/**
	 * Name der Statistik.
	 */
	private final String statName;
	
	/**
	 * Wert.
	 */
	private final int value;
	
	/**
	 * @param playerId Id des betroffenen Spielers.
	 * @param statName Name der Statistik.
	 * @param value Wert.
	 */
	public PlayerStatisticMessage(final int playerId, final String statName, final int value) {
		super(MessageType.PLAYERSTATISTIC);
		this.playerId = playerId;
		this.statName = statName;
		this.value = value;
	}
	
	/**
	 * Fuehrt Aenderungen aus.
	 */
	public void execute() {
		Player player = Player.getById(playerId);
		if (player != null) {
			player.setStatisticByMessage(statName, value);
		}
	}
	
}
