package com.messages;

/**
 * Nachricht die zu Begin des Spiels gesendet wird.
 * @author Tristan
 *
 */
public class MatchTimeMessage extends Message {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 8360046813646876777L;
	
	/**
	 * Millisekunden seit Match laeuft.
	 */
	private long time;

	/**
	 * Konstrukor. Legt MessageType fest.
	 * @param time Millisekunden seit Match laeuft.
	 */
	public MatchTimeMessage(final long time) {
		super(MessageType.MATCHTIME);
		this.time = time;
	}

	/**
	 * @return Millisekunden seit Match laeuft.
	 */
	public long getTime() {
		return time;
	}

}
