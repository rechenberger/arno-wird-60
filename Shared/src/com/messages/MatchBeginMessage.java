package com.messages;

/**
 * Nachricht die zu Begin des Spiels gesendet wird.
 * @author Tristan
 *
 */
public class MatchBeginMessage extends Message {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 8360046813646876777L;
	
	/**
	 * Millisekunden bis Match startet.
	 */
	private int startingIn;

	/**
	 * Konstrukor. Legt MessageType fest.
	 * @param startingIn Millisekunden bis Match startet.
	 */
	public MatchBeginMessage(final int startingIn) {
		super(MessageType.MATCHBEGIN);
		this.startingIn = startingIn;
	}
	
	/**
	 * @return Millisekunden bis Match startet.
	 */
	public int getStartingIn() {
		return startingIn;
	}

}
