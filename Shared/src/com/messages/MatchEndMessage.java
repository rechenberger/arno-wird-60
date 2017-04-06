package com.messages;

import game.objects.Fraction;

/**
 * Nachricht die bei Ende des Spiels gesendet wird.
 * @author Alex
 *
 */
public class MatchEndMessage extends Message {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -9036420031643672741L;
	/**
	 * Sieger Team.
	 */
	private Fraction winnerTeam;
	
	/**
	 * Konstrukor. Legt MessageType fest.
	 * @param winnerTeam Team, das gewonnen hat.
	 */
	public MatchEndMessage(final Fraction winnerTeam) {
		super(MessageType.MATCHEND);
		this.winnerTeam = winnerTeam;
	}
	
	/**
	 * @return Siegreiches Team.
	 */
	public Fraction getWinnerTeam() {
		return this.winnerTeam;
	}
}
