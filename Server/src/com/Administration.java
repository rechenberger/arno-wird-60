package com;

import game.objects.GameObject;
import game.objects.Player;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

import module.ModuleHandler;

import com.messages.Message;
import com.messages.MessageType;
import com.messages.UserDataMessage;
import com.messages.UserAuthMessage;

/**
 * Utility-Klasse zur Verwaltung von Benutzern.
 * 
 * @author Christian Westhoff
 * 
 */
public class Administration {

	/**
	 * Konstruktor fuer Utility-Klasse.
	 */
	protected Administration() {
	}

	/**
	 * Datenbank.
	 */
	private static final Database DATABASE = new Database();

	/**
	 * Methode wird genutzt um Nachrichten zu uebergeben.
	 * 
	 * @param msg
	 *            Nachricht
	 */
	public static void pushMessage(final Message msg) {
		switch (msg.getType()) {
		case USER_LOGIN:
			checkLogin((UserDataMessage) msg);
			break;
		case USER_REGISTER:
			checkRegistration((UserDataMessage) msg);
			break;
		case USER_LOGOFF:
			Player player = Player.getPlayerByUserId(msg.getUserID());
			player.getHero().die();
			player.unregisterGameObject();
			player.leave();
			ModuleHandler.COM.pushMessage(new Message(MessageType.USER_EXIT), msg.getUserID());
			break;
		default:
			break;
		}
	}

	/**
	 * Loggt einen Benutzer ein: Prueft, ob die Logindaten existieren und ob der
	 * Benutzer bereits eigeloggt ist, benachrichtigt das Match ueber neuen
	 * Nutzer.
	 * 
	 * @param msg
	 *            Benutzerdaten
	 */
	private static void checkLogin(final UserDataMessage msg) {
		try {
			// Benutzer einloggen
			if (!DATABASE.queryLookup(msg.getUsername(), msg.getPassword())) {

				ModuleHandler.COM.pushMessage(new UserAuthMessage(
						MessageType.USER_LOGIN_ANSWER, false), msg.getUserID());
				return;
			}
		} catch (SQLException e) {
			// Wenn die Datenbank nicht verfuegbar ist, koennen die Benutzer so
			// spielen, wenn das Spiel nicht laueft
			System.out.println(e.getClass().getName()
					+ ": Fehler beim Lesen der Userdaten in die DB");
		}
		doLogin(msg.getUsername(), msg.getUserID());
	}

	/**
	 * Registriert den Benutzer und loggt ihn ein, benachrichtigt das Match
	 * ueber neuen Nutzer.
	 * 
	 * @param msg
	 *            Nachricht
	 */
	private static void checkRegistration(final UserDataMessage msg) {
		try {
			if (!DATABASE.queryRegister(msg.getUsername(), msg.getPassword())) {

				// Benutzername bzw. Passwort vergeben
				ModuleHandler.COM.pushMessage(new UserAuthMessage(
						MessageType.USER_REGISTER_ANSWER, false), msg
						.getUserID());
				return;
			}
		} catch (SQLException e) {
			System.out.println(e.getClass().getName()
					+ ": Fehler beim Schreiben der Userdaten in die DB");
		}
		doLogin(msg.getUsername(), msg.getUserID());
	}

	/**
	 * Loggt den Benutzer ein.
	 * 
	 * @param name
	 *            Name
	 * @param id
	 *            ID
	 */
	private static void doLogin(final String name, final int id) {

		Player player = Player.getPlayerByName(name);

			if (player == null) {

				// Der Spieler ist nicht eingeloggt
				if (!ModuleHandler.MATCH.isMatchRunning()
						&& !Player.allPlayersReady()) {
					ModuleHandler.COM.pushMessage(new UserAuthMessage(
							MessageType.USER_LOGIN_ANSWER, true), id);
					ModuleHandler.MATCH.newUser(id, name);
				} else {
					ModuleHandler.COM.pushMessage(new Message(MessageType.GAME_IS_RUNNING), id);
				}
			} else {
				// Der Spieler war schon mal eingeloggt, aber ist nicht mehr
				// online
				if (!player.isOnline() && ModuleHandler.MATCH.isMatchRunning()) {

					ModuleHandler.COM.pushMessage(new Message(
							MessageType.USER_BACK), id);
					player.setUserId(id);
					player.setOnlineState(true);
					player.getHero().spawn();
					ModuleHandler.COM.pushMessage(
							GameObject.getAllGameObjectsMessage(), id);
				} else {
					ModuleHandler.COM
							.pushMessage(new Message(MessageType.USER_ALREADY_IN), id);
				}
			}
	}

	/**
	 * Uebergibt die Statistik aus der Datenbank.
	 * 
	 * @param username
	 *            Benutzername
	 * @return Statistik als ConcurrentHashMap<String, Integer>, der String ist
	 *         der Name des Fehldes , der Integer der Wert
	 */
	public static ConcurrentHashMap<String, Integer> loadStatistics(
			final String username) {
		try {
			return DATABASE.queryLoadStatistics(username);
		} catch (SQLException e) {
			System.out.println(e.getClass().getName()
					+ ": Fehler beim Lesen der Statistik in die DB");
		}
		return null;
	}

	/**
	 * Schreibt die aktuelle Statistik in die Datenbank.
	 * 
	 * @param username
	 *            Benutzername
	 * @param statistic
	 *            Statistik als ConcurrentHashMap<String, Integer>, der String
	 *            ist der Name des Fehldes , der Integer der Wert
	 */
	public static void saveStatistics(final String username,
			final ConcurrentHashMap<String, Integer> statistic) {
		try {
			DATABASE.querySaveStatistics(username, statistic);
		} catch (SQLException e) {
			System.out.println(e.getClass().getName()
					+ ": Fehler beim Schreiben der Statistik in die DB");
		}
	}
}
