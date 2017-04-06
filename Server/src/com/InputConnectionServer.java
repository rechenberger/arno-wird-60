package com;

import game.objects.Player;

import java.io.IOException;
import java.net.Socket;

import com.messages.Message;

/**
 * Klasse fuer eingehende Verbindung. Baut diese auf und wieder ab. Erbt von der
 * Shared-Klasse
 * 
 * @author Christian Westhoff
 * 
 */
public class InputConnectionServer extends InputConnectionShared {

	/**
	 * Benutzer-ID.
	 */
	private int userID;
	
	/**
	 * Speichert die Zeit der letzten Aktivitaet des Nutzers.
	 */
	private boolean activity;
	
	/**
	 * Konstruktor.
	 * @param socket Socket
	 * @param userID User-ID
	 */
	public InputConnectionServer(final Socket socket, final int userID) {
		super(socket);
		this.userID = userID;
		this.activity = false;
	}
	
	/**
	 * run-Methode des Threads: Solange eine Verbindung verfuegbar ist koennen Nachrichten empfangen werden.
	 * Ausserdem wird hier die Aktivitaet des Klienten ueberprueft. Wenn die Verbindung abbricht wird das
	 * Match-Modul benachrichtigt
	 */
	@Override
	public void run() {

		this.openStream();

		while (running && this.socket.isConnected()) {
			
			try {
				Message msg = (Message) objIn.readObject();
				this.activity = true;
				msg.setUserID(this.userID);
				MessageHandlerServer.handle(msg);
			} catch (IOException e) {
				if (Player.getPlayerByUserId(this.userID) != null) {
					Player.getPlayerByUserId(this.userID).leave();
				}
				this.running = false;
				System.out.println("Inputstream abgebrochen");
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Methode, die die Benutzer-ID zurueckgibt.
	 * @return Benutzer-ID
	 */
	public int getUserID() {
		return userID;
	}
	
	/**
	 * Liefert die Zeit der letzten Aktivitaet zurueck.
	 * @return Zeit der letzten Aktivitaet.
	 */
	public boolean hasActivity() {
		return this.activity;
	}

	/**
	 * Setzt den Aktivitaetsstatus auf FALSE.
	 */
	public void setActivityToFalse() {
		this.activity = false;
	}
}
