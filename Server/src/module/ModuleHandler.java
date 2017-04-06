package module;

/**
 * Hier werden zentral die Teil-Module des Servers verwaltet.
 * @author Tristan
 *
 */
public final class ModuleHandler {
	
	/**
	 * Das Chat-Modul.
	 * Verwaltet die Text-Nachrichten
	 */
	public static final ChatModule CHAT = new ChatModule();
	
	/**
	 * Das Match-Modul.
	 * Verwaltet das Spiel.
	 */
	public static final MatchModuleServer MATCH = new MatchModuleServer();
	
	/**
	 * Das Kommunikations-Modul.
	 * Verwaltet die Kommunikation zwischen Server und den Clienten.
	 */
	public static final ComModuleServer COM = new ComModuleServer();
	
	/**
	 * Wird nicht initialisiert.
	 */
	private ModuleHandler() {
		
	}
}
