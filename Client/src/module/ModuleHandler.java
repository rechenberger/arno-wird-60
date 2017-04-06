package module;

/**
 * Hier werden zentral die Teil-Module des Clienten verwaltet.
 * @author Tristan
 */
public final class ModuleHandler {
	
	/**
	 * Das Match-Modul.
	 * Verwaltet das Spiel.
	 */
	public static final MatchModuleClient MATCH = new MatchModuleClient();
	
	/**
	 * Das Kommunikations-Modul.
	 * Verwaltet die Kommunikation zwischen Server und den Clienten.
	 */
	public static final ComModuleClient COM = new ComModuleClient();

	/**
	 * Das GUI-Modul.
	 * Verwaltet die Benutzeroberflaeche und die Darstellung der Spielinhalte.
	 */
	public static final GuiModuleClient GUI = new GuiModuleClient();
	
	/**
	 * Wird nicht initialisiert.
	 */
	private ModuleHandler() {
		
	}
	
}
