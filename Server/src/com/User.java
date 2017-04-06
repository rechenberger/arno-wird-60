package com;

import game.objects.Player;

import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;

import module.Sleeper;

/**
 * Datentyp fuer die Benutzer.
 * 
 * @author Christian Westhoff
 */
public class User implements Runnable {
	/**
	 * Liste zur Verwaltung der User. Die Klasse verwaltet sich selber.
	 */
	private static LinkedList<User> connectedUsers = new LinkedList<User>();
	/**
	 * sleepTime des Threads.
	 */
	private static int sleepTime;
	/**
	 * Runnable fuer die eingehende Verbindung.
	 */
	private InputConnectionServer inCon;
	/**
	 * Runnable fuer die ausgehende Verbindung.
	 */
	private OutputConnectionServer outCon;
	/**
	 * Socket.
	 */
	private Socket socket;
	/**
	 * TimeOut-Zeit.
	 */
	private static final int CLIENTCONNECTIONTIMEOUT = 18000;
	/**
	 * Bestimmt, ob der Thread gerade laufen soll.
	 */
	private boolean running;

	/**
	 * Konstruktor.
	 * 
	 * @param socket
	 *            Socket
	 */
	public User(final Socket socket) {

		System.out.println("New user is connecting...");
		int id = this.hashCode();
		this.socket = socket;
		this.running = true;

		inCon = new InputConnectionServer(socket, id);
		new Thread(inCon).start();
		outCon = new OutputConnectionServer(socket, sleepTime, id);
		new Thread(outCon).start();

		new Thread(this).start();

		connectedUsers.add(this);
	}

	/**
	 * Methode zum Setzen der Schlaf-Zeit der Threads.
	 * 
	 * @param time
	 *            Zeit
	 */
	public static void setSleepTime(final int time) {
		sleepTime = time;
	}

	/**
	 * Gibt eine Liste mit den verbundenen Clienten zurueck.
	 * 
	 * @return LinkedList mit Usern
	 */
	public static LinkedList<User> getConnectedUsers() {
		return connectedUsers;
	}

	/**
	 * Liefert das Runnable Objekt fuer die eingehende Verbindung.
	 * 
	 * @return InputConnection
	 */
	public InputConnectionServer getInCon() {
		return inCon;
	}

	/**
	 * Liefert das Runnable Objekt fuer die ausgehende Verbindung.
	 * 
	 * @return InputConnection
	 */
	public OutputConnectionServer getOutCon() {
		return outCon;
	}

	/**
	 * Schliesst die Streams und den Socket.
	 */
	public void closeConnection() {
		inCon.closeStream();
		outCon.closeStream();
		try {
			socket.close();
		} catch (IOException e) {
			// .close()
		}
	}

	/**
	 * Run-Methode: Hier wird die Aktivitaet des Users ueberprueft.
	 */
	@Override
	public void run() {

		while (running) {

			Sleeper.sleep(CLIENTCONNECTIONTIMEOUT);

			if (!this.inCon.hasActivity()) {
				if (Player.getPlayerByUserId(this.inCon.getUserID()) != null) {
					Player.getPlayerByUserId(this.inCon.getUserID()).leave();
				}
				this.running = false;
				this.closeConnection();
				connectedUsers.remove(this);
				System.out.println("Verbindung zu User "
						+ this.getInCon().getUserID()
						+ " beendet. Users.size = " + connectedUsers.size());
				this.inCon = null;
				this.outCon = null;

			} else {

				this.inCon.setActivityToFalse();

			}

		}

	}

	/**
	 * @return Gibt zurueck, ob die Verbindung aufgebaut wurde
	 */
	public boolean isRunning() {
		if (this.outCon == null || this.inCon == null) {
			return false;
		}
		return this.inCon.isRunning() && this.outCon.isRunning();
	}
}
