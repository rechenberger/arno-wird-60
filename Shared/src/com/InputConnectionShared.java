package com;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import settings.DebugSettings;

/**
 * Klasse fuer eingehende Verbindung.
 * @author Christian Westhoff
 *
 */
public abstract class InputConnectionShared implements Runnable {
	/**
	 * Verbindungs-Socket.
	 */
	protected Socket socket;
	/**
	 * ObjectInputStream.
	 */
	protected ObjectInputStream objIn;
	/**
	 * Bestimmt, ob der Stream offen ist.
	 */
	protected boolean running;
	/**
	 * Konstruktor.
	 * @param socket Socket
	 */
	public InputConnectionShared(final Socket socket) {
		this.socket = socket;
	}
	/**
	 * Methode, die den Stream oeffnet.
	 */
	protected void openStream() {
		try {
			objIn = new ObjectInputStream(socket.getInputStream());
			this.running = true;
			if (DebugSettings.DEBUG && DebugSettings.COM_PRINT_STREAM_READY) {
				System.out.println("Output ready!");
			}
			
		} catch (IOException e) { 
			// .getInputStream()
			e.printStackTrace();
		}
	}
	/**
	 * Methode, die den Stream schliesst.
	 */
	public void closeStream() {
		try {
			objIn.close();
			this.running = false;
		} catch (IOException e) {
			// .close()
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		
	}
	/**
	 * @return Gibt zurueck, ob der ObjectInputStream aufgebaut wurde
	 */
	public boolean isRunning() {
		return this.running;
	}
}
