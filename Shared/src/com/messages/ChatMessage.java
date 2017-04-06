package com.messages;
/**
 * Klasse zum Versenden von Nachrichten verschiedener Typen.
 * @author Christian Westhoff
 *
 */
public class ChatMessage extends Message {
/**
 * serialVersionUID.
 */
	private static final long serialVersionUID = -8102161110672190796L;
	/**
	 * Enthaelt den Text der Nachricht.
	 */
	private String text;
	/**
	 * Enthaelt den Namen des Klienten (bzw. die des Servers), der die Nachricht sendet.
	 */
	private String username;
	
	/**
	 * Konstruktor fuer globale und gruppenspezifische Nachrichten.
	 * @param type Messagetyp
	 * @param text Messageinhalt als String
	 * @param username Benutzername
	 */
	public ChatMessage(final MessageType type, final String username, final String text) {
		super(type);
		this.text = text;
		this.username = username;
	}
	
	/**
	 * Konstruktor fuer Systemnachrichten.
	 * @param text Messageinhalt als String
	 */
	public ChatMessage(final String text) {

		super(MessageType.SYSTEM_CHAT);
		this.text = text;
		this.username = "System";
	}
	/**
	 * Gibt den Inhalt der Message zurueck.
	 * @return Messageinhalt.
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * Gibt den Namen des Klienten zurueck.
	 * @return Benutzername
	 */
	public String getUsername() {
		return username;
	}
}
