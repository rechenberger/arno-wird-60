package module;

import com.messages.Message;
/**
 *  Das MatchModule verwaltet das Match und fuehrt die Runden aus.
 * @author Tristan
 */

public class Module implements Runnable {
	
	/**
	 * Laufzeitboolean fuer den Thread.
	 */
	protected boolean running;
	/**
	 * setSleepTime Zeit die der Thread zwischen zwei Schleifenaufrufen schlaeft.
	 * @author Tristan
	 */
	protected int sleepTime = 0;
	
	/**
	 * Zaehlt die Sleeps.
	 */
	private int sleepCounter = 0;
	
	/**
	 * @param setSleepTime Zeit die der Thread zwischen zwei Schleifenaufrufen schlaeft.
	 * @author Tristan
	 */
	public void setSleepTime(final int setSleepTime) {
		this.sleepTime = setSleepTime;
	}
	
	/**
	 * @return Zeit die der Thread zwischen zwei Schleifenaufrufen schlaeft.
	 * @author Tristan
	 */
	public int getSleepTime() {
		return this.sleepTime;
	}
	
	
	/**
	 * Speichert den Modultypen zur schnellen identifizierung.
	 * @author Tristan
	 */
	protected ModuleType mType;
	
	/**
	 * Enumeration der Modultypen.
	 * @author Tristan
	 */
	public enum mType { };

	/**
	 * Konstruktor.
	 * @param setMType Modultyp
	 * @author Tristan
	 */
	protected Module(final ModuleType setMType) {
		this.mType = setMType;
		this.running = true;
	}


	/**
	 * ModulTyp-Getter.
	 * @return mType Modultyp
	 * @author Tristan
	 */
	public ModuleType getModuleType() {
		return this.mType;
	}
	
	
	/**
	 * Methode um von aussen in die Queue zu schreiben.
	 * @param msg zu uebergebende Nachricht
	 * @author Tristan
	 */
	public void pushMessage(final Message msg) {
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * @return Ob der Thread laueft.
	 */
	public boolean isRunning() {
		return running;
	}

	/**
	 * Gibt zurueck, ob der Thread laueft.
	 * @param running 
	 */
	public void setRunning(final boolean running) {
		this.running = running;
	}

	/**
	 * Schlaeft fuer eingestellte Zeit.
	 */
	public void sleepForSleepTime() {
		Sleeper.sleep(this.getSleepTime());
		this.incSleepCounter();
	}
	
	/**
	 * @return Zaehlt die Sleeps.
	 */
	public int getSleepCounter() {
		return this.sleepCounter;
	}
	
	/**
	 * Erhoeht Sleep-Zaehler um eins.
	 */
	protected void incSleepCounter() {
		this.sleepCounter++;
	}
}
