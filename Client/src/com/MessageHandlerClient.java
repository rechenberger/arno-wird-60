package com;

import gui.usercontrols.chat.ChatViewBox;

import com.messages.ChatMessage;
import com.messages.Message;

import module.ModuleHandler;

/**
 * Klasse, die einkommende Nachrichten behandelt.
 * @author Christian Westhoff
 *
 */
public class MessageHandlerClient extends MessageHandlerShared {
	/**
	 * Konstruktor fuer Utility-Klasse.
	 */
	protected MessageHandlerClient() {
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
			new ChatViewBox((ChatMessage) msg);
			break;
		case MATCH:
			ModuleHandler.MATCH.pushMessage(msg);
			break;
		default:
			System.out.println("Mit " + msg.toString() + " weiss ich nichts anzufangen");
			break;
		}
	}
}
