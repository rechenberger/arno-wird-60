package com;

import com.messages.Message;
import module.ModuleHandler;

/**
 * Klasse, die einkommende Nachrichten behandelt.
 * @author Christian Westhoff
 *
 */
public class MessageHandlerServer extends MessageHandlerShared {
	/**
	 * Konstruktor fuer Utility-Klasse.
	 */
	protected MessageHandlerServer() {
	}
	/**
	 * Methode, um einkommende Nachrichten zu behandelt und an die entsprechenden Module einfuegt.
	 * @param msg Nachricht
	 */
	public static void handle(final Message msg) {
		switch (msg.getType().getModule()) {
		case ADMINISTRATION:
			Administration.pushMessage(msg);
			break;
		case CHAT:
			ModuleHandler.CHAT.pushMessage(msg);
			break;
		case MATCH:
			ModuleHandler.MATCH.pushMessage(msg);
			break;
		default:
			break;
		}
	}
}
