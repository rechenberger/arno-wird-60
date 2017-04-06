package module;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import settings.GlobalSettings;

import com.User;
import com.messages.Message;

/**
 * Das ist das Kommunikationsmodul des Servers.
 * 
 * @author Christian Westhoff
 * 
 */
public class ComModuleServer extends Module {

	/**
	 * Anzahl der Verbindungen.
	 */
	private static int maxConnection = GlobalSettings.COM_MAX_CONNECTIONS;

	/**
	 * Port des Servers.
	 */
	private static final int PORT = GlobalSettings.COM_PORT;

	/**
	 * Konstruktor.
	 */
	public ComModuleServer() {
		super(ModuleType.COMMUNICATION);
		User.setSleepTime(GlobalSettings.SHARED_COM_STREAM_SLEEPTIME);
		this.waitForMatch = true;
	}

	/**
	 * Methode, um Nachrichten an alles Nutzer zu senden.
	 * 
	 * @param msg
	 *            Nachricht
	 */
	@Override
	public void pushMessage(final Message msg) {
		for (User user : User.getConnectedUsers()) {
			user.getOutCon().addMessage(msg);
		}
	}

	/**
	 * Methode, um Nachrichten an bestimmte Nutzer zu senden.
	 * 
	 * @param msg
	 *            Nachricht
	 * @param idList
	 *            Benutzer-ID-Liste
	 */
	public void pushMessage(final Message msg, final int[] idList) {
		for (User user : User.getConnectedUsers()) {
			for (int id : idList) {
				if (user.getOutCon().getUserID() == id) {
					user.getOutCon().addMessage(msg);
				}
			}
		}
	}

	/**
	 * Methode, um Nachrichten an einen Benutzer zu senden.
	 * 
	 * @param msg
	 *            Nachricht
	 * @param id
	 *            Benutzer-ID
	 */
	public void pushMessage(final Message msg, final int id) {
		msg.setUserID(id);
		for (User user : User.getConnectedUsers()) {
			if (user.getOutCon().getUserID() == id) {
				user.getOutCon().addMessage(msg);
			}
		}
	}

	/**
	 * Boolean, die dafuer sorgt, dass die Kommunikation auf das Match wartet.
	 */
	private boolean waitForMatch;

	/**
	 * Run-Methode des Threads. Hier wird die Verbindung aufgebaut und auf neue
	 * Benutzer gewartet, wenn das Match bereit ist und die Verbindungen der Benutzer
	 * unter den maximalen Verbindungen liegt.
	 */
	public void run() {

		System.out
				.println("Server-Connection is running ... waiting for Clients");

		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		while (this.running) {

			if (!this.waitForMatch) {

				if (User.getConnectedUsers().size() < maxConnection) {

					try {

						Socket socket = serverSocket.accept();
						new User(socket);
						System.out.println("Neuer User, Socket angenommen!");

					} catch (IOException e) {
						e.printStackTrace();
					} catch (NullPointerException e) {
						// Wird verursacht, wenn der Port schon belegt ist.
						System.out
								.println("Server is already running! Closing...");
						this.closeConnection();
						System.exit(0);

					}
				} else {
					System.out.println("Keine Spieler frei");
				}

			} 

			this.sleepForSleepTime();

		}
	}

	/**
	 * Mit dieser Methode wird die Kommunikation benachrichtigt, dass das
	 * Matchmodul fertig mit initalsieren ist.
	 */
	public void notifyWaitForMatch() {
		this.waitForMatch = false;
	}

	/**
	 * Setzt saemtliche Verbindungen zurueck. Beendet saemtliche Input- und
	 * Outputthreads und loescht die Nutzer.
	 */
	public void resetConnection() {
		for (User user : User.getConnectedUsers()) {
			user.closeConnection();
		}
	}

	/**
	 * Schliesst saemtliche Verbindungen. Beendet saemtliche Input- und
	 * Outputthreads und loescht die Nutzer.
	 */
	public void closeConnection() {
		this.resetConnection();
		this.running = false;
	}
}
