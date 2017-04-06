package com;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

import settings.DebugSettings;

import com.messages.Message;

/**
 * Klasse fuer ausgehende Verbindungen.
 * 
 * @author Christian Westhoff
 * 
 */
public abstract class OutputConnectionShared implements Runnable {
	
	/**
	 * Verbindungs-Socket.
	 */
	protected Socket socket;
	
	/**
	 * Sende-Schlange.
	 */
	protected ConcurrentLinkedQueue<Message> outQueue;
	
	/**
	 * Bestimmt, ob der Thread gerade laueft.
	 */
	protected boolean running;
	
	/**
	 * Output-Stream.
	 */
	protected ObjectOutputStream objOut;
	
	/**
	 * Zeit die der Thread zwischen zwei Schleifenaufrufen schlaeft.
	 */
	protected int sleepTime;

	/**
	 * Konstruktor.
	 * 
	 * @param socket
	 *            Socket
	 * @param sleepTime
	 *            Zeit, die der Thread zwischen zwei Schleifendurchlaeufen
	 *            schlafen soll
	 */
	public OutputConnectionShared(final Socket socket, final int sleepTime) {
		this.socket = socket;
		this.sleepTime = sleepTime;
		this.outQueue = new ConcurrentLinkedQueue<Message>();
	}

	/**
	 * Methode, die den Stream oeffnet.
	 */
	protected void openStream() {
		try {
			objOut = new ObjectOutputStream(socket.getOutputStream());
			this.running = true;
			if (DebugSettings.DEBUG && DebugSettings.COM_PRINT_STREAM_READY) {
				System.out.println("Input ready!");
			}
		} catch (IOException e) {
			// .getOutputStream()
			e.printStackTrace();
		}
	}

	/**
	 * Methode, die eine Nachricht in die Schlange schreibt.
	 * 
	 * @param msg
	 *            Nachricht
	 */
	public void addMessage(final Message msg) {
		outQueue.add(msg);
	}

	/**
	 * Methode, die den Stream schliesst.
	 */
	public void closeStream() {
		try {
			this.objOut.close();
			this.running = false;
		} catch (IOException e) {
			// .close()
			System.out.println("Stream bereits geschlossen");
		}
	}
	@Override
	public void run() {
		
	}
	/**
	 * @return Gibt zurueck, ob der ObjectOutputStream aufgebaut wurde
	 */
	public boolean isRunning() {
		return this.running;
	}
}
