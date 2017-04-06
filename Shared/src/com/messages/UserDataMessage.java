package com.messages;

import com.Security;

/**
 * Abstrakte Klasse zum Uebermitteln von Benutzerdaten.
 * @author Christian Westhoff
 *
 */
public class UserDataMessage extends Message {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -4524034267953651224L;
	/**
	 * Benutzername.
	 */
	private String username;
	/**
	 * Passwort.
	 */
	private String password;
	/**
	 * Konstruktor.
	 * @param type Nachrichtentyp
	 * @param username Benutzername
	 * @param password Passwort
	 */
	public UserDataMessage(final MessageType type, final String username, final String password) {
		super(type);
		this.username = username;
		this.password = Security.getSHAValue(password);
	}
	/**
	 * Gibt den Benutzernamen zurueck.
	 * @return Benutzername
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * Gibt das Passwort zurueck.
	 * @return Passwort
	 */
	public String getPassword() {
		return password;
	}

}
