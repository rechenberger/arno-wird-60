package com.messages;

/**
 * Message zum zurueckgeben der Authentifizierung am Server.
 * @author Christian Westhoff
 *
 */
public class UserAuthMessage extends Message {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 9175276945057669665L;
	
	/**
	 * Gibt an, ob Authentifizierung erfolgreich.
	 */
	private boolean status;
	
	/**
	 * Konstruktor.
	 * @param type Type
	 * @param status Status
	 */
	public UserAuthMessage(final MessageType type, final boolean status) {
		super(type);
		this.status = status;
	}
	
	/**
	 * Gibt den Status der Authentifizierung zurueck.
	 * @return Status
	 */
	public boolean getAuthStatus() {
		return this.status;
	}
}
