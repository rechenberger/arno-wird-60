package module;

import java.io.IOException;
import java.net.Socket;

import settings.GlobalSettings;

import com.Administration;
import com.InputConnectionClient;
import com.OutputConnectionClient;
import com.messages.Message;
import com.messages.MessageType;

/**
 * Das ist das Kommunikationsmodul des Servers.
 * 
 * @author Christian Westhoff
 * 
 */
public class ComModuleClient extends Module {
	

	/**
	 * Runnable fuer die eingehende Verbindung.
	 */
	private InputConnectionClient inCon;
	/**
	 * Runnable fuer die ausgehende Verbindung.
	 */
	private OutputConnectionClient outCon;
	/**
	 * Port des Servers.
	 */
	private static final int PORT = GlobalSettings.COM_PORT;

	/**
	 * Hosts des Servers.
	 */
	private static final String[] HOSTS = GlobalSettings.COM_HOSTS;

	/**
	 * Speichert den zuletzt benuzten Host.
	 */
	private int lastHost;
	/**
	 * Gibt an, ob ein Benutzer verbunden ist.
	 */
	private boolean connected;
	/**
	 * Gibt an, ob der Spieler schon auf Login geklickt hat.
	 */
	private boolean waitForLogin;
	/**
	 * Timeout der Klientverbindung.
	 */
	private static final int CLIENTCONNECTIONTIMEOUT = 17000;

	/**
	 * Konstruktor.
	 */
	public ComModuleClient() {
		super(ModuleType.COMMUNICATION);
		this.lastHost = 1;
		this.waitForLogin = true;
		this.attemptTime = 0;
	}

	/**
	 * Gibt die Zeit an, wie lange der Client sich schon versucht zu verbinden.
	 */
	private int attemptTime;
	/**
	 * Run-Methode des Threads. Methode, um auf Serververbindung zu pruefen.
	 */
	@Override
	public void run() {

		while (this.running) {
			
			// Das Modul laeuft
			if (!this.waitForLogin) {
				
				// Der Client hat sich eingeloggt
				if (!this.connected) {
					
					// Der Client hat noch keine Verbindung zum Server
					// --> Verbindung aufbauen
					
					if (this.attemptTime < 2000) {
						
						// Es gibt noch keinen Timeout
						// --> Versuchen zu Verbinden
						
						this.setHost();
						
						this.tryToEstablishConnection();
						
						this.attemptTime += this.getSleepTime();
						
					} else {
						
						System.out.println("Timeout");
						// Timeout
						// --> Fehler melden
						
						this.attemptTime = 0;
						Administration.notifyFailure();
						this.waitForLogin = true;
						
					}
					
				} else {
					
					// Der Client hat eine Verbindung zum Server
					// --> "Ich bin noch da"-Nachricht senden
					this.pushSignOfLive();
				}
				
			}
			this.sleepForSleepTime();
		}
	}

	/**
	 * Sende das Sign Of Live.
	 */
	private void pushSignOfLive() {
		this.pushMessage(new Message(MessageType.ACTIVITY));
		Sleeper.sleep(CLIENTCONNECTIONTIMEOUT - this.sleepTime);
	}
	
	/**
	 * Diese Methode versucht die Verbindung aufzubauen.
	 */
	private void tryToEstablishConnection() {
		
		Socket clientSocket = null;

		try {
			
			clientSocket = new Socket(HOSTS[this.lastHost], PORT);
			

			inCon = new InputConnectionClient(clientSocket);
			new Thread(inCon).start();

			outCon = new OutputConnectionClient(clientSocket,
					this.sleepTime);
			new Thread(outCon).start();
			this.connected = true;

		} catch (IOException e) {
			e.printStackTrace();
			this.connected = false;
		}
		
	}
	/**
	 * Liefert zurueck, ob der Benutzer noch auf das Login wartet.
	 * 
	 * @return ob der Benutzer noch auf das Login wartet
	 */
	public boolean isWaitForLogin() {
		return waitForLogin;
	}

	/**
	 * Methode zum Wechsel durchgehen der Hosts in der Liste.
	 */
	private void setHost() {
		this.lastHost++;
		if (this.lastHost >= HOSTS.length) {
			this.lastHost = 0;
		}
	}
	
	/**
	 * Methode, um Nachrichten an den Server zu senden.
	 * 
	 * @param msg
	 *            Nachricht
	 */
	@Override
	public void pushMessage(final Message msg) {

		while (this.outCon == null) {
			this.sleepForSleepTime();
		}
		
		if (this.outCon != null) {
			this.outCon.addMessage(msg);
		}
	}

	/**
	 * @return Gibt zurueck, ob die Verbindung aufgebaut wurde
	 */
	public boolean isConnected() {
		return this.connected;
	}

	/**
	 * Diese Methode setzt die Verbindung zureck. Die run-Methode sucht nach dem
	 * zuruecksetzten wieder nach neuen Hosts.
	 */
	public void resetConnection() {
		this.waitForLogin = true;
		this.connected = false;
		if (this.inCon != null) {
			this.inCon.closeStream();
		}
		if (this.outCon != null) {
			this.outCon.closeStream();
		}

		this.inCon = null;
		this.outCon = null;
	}
	
	/**
	 * Beendet das Kommunikationsmodul und zeigt ein Modal an, dass der Server
	 * beendet wurde.
	 */
	public void closeConnection() {
		this.running = false;
		Administration.notifyFailure();
	}

	/**
	 * Methode, um die Kommunikation ueber das Login zu informieren.
	 */
	public void notifyLogin() {
		this.waitForLogin = false;
	}
}
