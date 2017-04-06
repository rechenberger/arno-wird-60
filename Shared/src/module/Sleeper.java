package module;

import settings.DebugSettings;

/**
 * Utility-Klasse, die das Schlafen eines Threads ermoeglicht.
 * Diese wird eingesetzt, da die Methode Thread.sleep() auf dem ProgPrak-Server
 * nicht korrekt funktioniert.
 * @author Tristan
 */
public final class Sleeper {
	
	/**
	 * Zaehlt wie oft falsch geschlafen wurde.
	 */
	private static int sleptWrong = 0;
	/**
	 * Zaehlt wie oft richtig geschlafen wurde.
	 */
	private static int sleptRight = 0;
	
	/**
	 * Ob Mit threadSleep() oder wakeNsleep() geschlafen wird.
	 */
	private static boolean sleepWithThread = true;
	
	/**
	 * Ob bereits getestet wurde.
	 */
	private static boolean sleepWithThreadTested = false;
	
	/**
	 * Konstruktor.
	 */
	protected Sleeper() {	
	}
	
	/**
	 * Schalfe fuer sleepMs Sekunden.
	 * @param sleepMs Sekunden die geschlafen werden.
	 */
	public static void sleep(final int sleepMs) {
		
		if (sleepMs <= 0) {
			return;
		}

		long timeBefore = System.currentTimeMillis();
		long timeAfter = timeBefore + sleepMs;
		
		
		if (sleepWithThread) {
			threadSleep(sleepMs);
			
			if	(!sleepWithThreadTested) {
				if (System.currentTimeMillis() >= timeAfter + 100) {
					sleptWrong++;
				} else {
					sleptRight++;
				}
				
				if (sleptWrong >= 3) {
					sleepWithThread = false;
					if (DebugSettings.DEBUG && DebugSettings.PRINT_SLEEP_TEST) {
						System.out.println("Thread.sleep() funktioniert nicht ordentlich.");
						System.out.println("Wechsel auf wakeNsleep().");
					}
				} else if (sleptRight >= 100) {
					sleepWithThreadTested = true;
					if (DebugSettings.DEBUG && DebugSettings.PRINT_SLEEP_TEST) {
						System.out.println("Thread.sleep() funktioniert tadellos.");
					}
				}
			}
			
		} else {
			wakeNsleep(sleepMs);
		}
		
		
	}
	
	/**
	 * Schlaeft mit Thread.sleep().
	 * @param sleepMs Millisekunden die geschlafen werden soll.
	 */
	private static void threadSleep(final int sleepMs) {
		try {
			Thread.sleep(sleepMs);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Schlaeft mit while(true) bis System.currentTimeMillis >= timeAfter.
	 * @param sleepMs Millisekunden die geschlafen werden soll.
	 */
	private static void wakeNsleep(final int sleepMs) {
		long timeAfter = System.currentTimeMillis() + sleepMs;
		while (true) {
			if (System.currentTimeMillis() >= timeAfter) {
				return;
			}
		}
	}
	

}
