package com;

import java.io.IOException;
import java.net.Socket;

import settings.GlobalSettings;
import module.Sleeper;
/**
 * Klasse fuer ausgehende Verbindungen.
 * @author Christian Westhoff
 */
public class OutputConnectionServer extends OutputConnectionShared {

	/**
	 * User-ID.
	 */
	private int userID;
	
	/**
	 * Konstruktor.
	 * @param socket Socket
	 * @param userID Benutzer-ID
	 * @param sleepTime Zeit, die der Thread zwischen zwei Schleifendurchlaeufen schlafen soll
	 */
	public OutputConnectionServer(final Socket socket, final int sleepTime, final int userID) {
		super(socket, sleepTime);
		this.userID = userID;
	}
	
	/**
	 * run-Methode des Threads: Solange eine Verbindung verfuegbar ist koennen Nachrichten gesendet werden.
	 * Ausserdem wird hier die Aktivitaet des Klienten ueberprueft. Wenn die Verbindung abbricht wird das
	 * Match-Modul benachrichtigt
	 */
	@Override
	public void run() {
		
		this.openStream();
		int count = 0;
		while (this.running && this.socket.isConnected()) {

			try {
				while (!this.outQueue.isEmpty()) {
					this.objOut.writeObject(outQueue.poll());
					if (count++ % GlobalSettings.COM_OUTPUT_RESET_RATE == 0) {
						this.objOut.flush();
						this.objOut.reset();
					}
				}
			} catch (IOException e) {
				
				System.out.println("Outputstream abgebrochen");
				this.running = false;
				
			}
			Sleeper.sleep(this.sleepTime);
		}
	}
	
	/**
	 * Methode, die die Benutzer-ID zurueckgibt.
	 * @return Benutzer-ID
	 */
	public int getUserID() {
		return this.userID;
	}
}
