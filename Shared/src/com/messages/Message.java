package com.messages;

import java.io.Serializable;
/**
 * Allgemeiner Nachrichtentyp.
 * @author Christian Westhoff
 *
 */
public class Message implements Serializable {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -7310818601638170563L;
	/**
	 * Definiert den Nachrichtentyp.
	 */
	private MessageType type;
	/**
	 * Definiert die UserID auf dem Server, durch die die Zuweisung zu den User-Threads passiert.
	 */
	private int userID;
	/**
	 * Konstruktor.
	 * @param type 
	 */
	public Message(final MessageType type) {
		this.type = type;
		this.userID = 0;
	}
	/**
	 * Gibt den Messagetypen zurueck.
	 * @return Messagetyp
	 */
	public MessageType getType() {
		return type;
	}
	/**
	 * Gibt die UserID der Message zurueck.
	 * @return UserID des Users
	 */
	public int getUserID() {
		return userID;
	}
	/**
	 * Setzt die UserID der Message.
	 * @param userID 
	 */
	public void setUserID(final int userID) {
		this.userID = userID;
	}
}
