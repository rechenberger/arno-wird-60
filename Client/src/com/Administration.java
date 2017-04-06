package com;

import com.messages.Message;
import com.messages.UserAuthMessage;

import gui.Login;
import gui.usercontrols.modal.Modal;
import module.ModuleHandler;

/**
 * Utility-Klasse zur Administration.
 * 
 * @author Christian Westhoff
 * 
 */
public class Administration {

	/**
	 * privater Konstruktor.
	 */
	protected Administration() {
	}

	/**
	 * Methode, die Administrationsnachrichten bearbeitet.
	 * 
	 * @param msg
	 *            Nachricht vom Type ADMINISTRATION
	 */
	public static void pushMessage(final Message msg) {
		switch (msg.getType()) {
		case USER_LOGIN_ANSWER:
			if (((UserAuthMessage) msg).getAuthStatus()) {
				ModuleHandler.GUI.showNextView();
			} else {
				new Modal("Login", "Benutzername bzw. Passwort nicht korrekt")
						.show();
				if (ModuleHandler.GUI.getPanel(Login.class) != null) {
					ModuleHandler.GUI.getPanel(Login.class).resetLogin();
				}
			}
			break;
		case USER_REGISTER_ANSWER:
			if (((UserAuthMessage) msg).getAuthStatus()) {
				ModuleHandler.GUI.showNextView();
				new Modal("Registrierung",
						"Herzlich Willkommen. Wir freuen uns, dass Sie unser Spiel spielen wollen")
						.show();
			} else {
				new Modal("Registrierung",
						"Der Benutzername ist bereits vergeben").show();
				if (ModuleHandler.GUI.getPanel(Login.class) != null) {
					ModuleHandler.GUI.getPanel(Login.class).resetLogin();
				}
			}

			break;
		case GAME_IS_RUNNING:
			ModuleHandler.COM.resetConnection();
			ModuleHandler.GUI.getPanel(Login.class).resetLogin();
			new Modal("Login", "Spiel l\u00e4uft bereits.").show();

			break;
		case USER_ALREADY_IN:
			ModuleHandler.COM.resetConnection();
			ModuleHandler.GUI.getPanel(Login.class).resetLogin();
			new Modal("Login", "Spieler ist bereits im Spiel.").show();

			break;
		case USER_LESS_MONEY:
			new Modal("Shop", "Zu wenig Geld fuer dieses Item.").show();
			break;
		case USER_HERO_TAKEN:
			new Modal("Lobby", "Dieser Held ist bereits belegt.").show();
			break;
		case USER_BACK:
			ModuleHandler.GUI.showNextView();
			ModuleHandler.MATCH.setMatchRunning(true);
			new Modal("Login", "Sch\u00f6n, dass sie wieder im Spiel sind")
					.show();
			break;
		case USER_EXIT:
			System.exit(0);
		default:
			break;
		}
	}

	/**
	 * Klasse zur Verwaltung von Fehlern in der Kommunikation. Wenn der Benutzer
	 * sich versucht einzuloggen und der Server nich verfuegbar ist, dann
	 * 
	 */
	public static void notifyFailure() {
		new Modal("Fehler", "Kein Server verf\u00fcgbar.").show();
		if (ModuleHandler.GUI.getPanel(Login.class) != null) {
			ModuleHandler.GUI.getPanel(Login.class).resetLogin();
		}
	}
}
