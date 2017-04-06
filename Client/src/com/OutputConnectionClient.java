package com;

import java.io.IOException;
import java.net.Socket;

import settings.GlobalSettings;

import module.Sleeper;

/**
 * Klasse fuer ausgehende Verbindungen.
 * 
 * @author Christian Westhoff
 * 
 */
public class OutputConnectionClient extends OutputConnectionShared {
	
	/**
	 * Konstruktor.
	 * 
	 * @param socket
	 *            Socket
	 * @param sleepTime
	 *            Zeit, die der Thread zwischen zwei Schleifendurchlaeufen
	 *            schlafen soll
	 */
	public OutputConnectionClient(final Socket socket, final int sleepTime) {
		super(socket, sleepTime);
	}

	/**
	 * run-Methode des Threads: Solange eine Verbindung verfuegbar ist koennen
	 * Nachrichten gesendet werden. Ausserdem wird hier die Aktivitaet des
	 * Klienten ueberprueft. Wenn die Verbindung abbricht wird das Match-Modul
	 * benachrichtigt
	 */
	
	@Override
	public void run() {

		this.openStream();
		
		int count = 0;
		while (this.running) {
			try {	
				while (!outQueue.isEmpty()) {
					objOut.writeObject(outQueue.poll());
					if (++count % GlobalSettings.COM_OUTPUT_RESET_RATE == 0) {
						this.objOut.flush();
						this.objOut.reset();
					}
				}
			} catch (IOException e) {
				System.out.println("Outputstream abgebrochen");
			}
			
			Sleeper.sleep(this.sleepTime);
		}
	}

}
