package com;

import java.io.IOException;
import java.net.Socket;
import com.messages.Message;

/**
 * Klasse fuer eingehende Verbindung.
 * 
 * @author Christian Westhoff
 * 
 */
public class InputConnectionClient extends InputConnectionShared {

	/**
	 * Konstruktor.
	 * 
	 * @param socket
	 *            Socket
	 */
	public InputConnectionClient(final Socket socket) {
		super(socket);
		System.out.println("Verbunden mit: " + socket.getLocalAddress());
	}

	/**
	 * run-Methode des Threads: Solange eine Verbindung verfuegbar ist koennen
	 * Nachrichten empfangen werden. Ausserdem wird hier die Aktivitaet des
	 * Klienten ueberprueft. Wenn die Verbindung abbricht wird das Match-Modul
	 * benachrichtigt
	 */
	@Override
	public void run() {

		this.openStream();

		
		while (this.running) {
			try {
				Message msg = (Message) objIn.readObject();
				MessageHandlerClient.handle(msg);
			} catch (IOException e) {
				System.out.println("Inputstream abgebrochen");
				this.running = false;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

	}

}
