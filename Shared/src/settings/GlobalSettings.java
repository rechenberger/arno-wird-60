package settings;

/**
 * Hier werden global die Einstellungen definiert.
 * @author Alle
 * CHECKSTYLE:OFF
 */
public final class GlobalSettings {
	
	///// Module SleepTime /////
	
	/**
	 * Server MatchModule SleepTime.
	 */
	public static int SERVER_MATCH_SLEEPTIME = 10;	

	/**
	 * Client MatchModule SleepTime.
	 */
	public static int CLIENT_MATCH_SLEEPTIME = 40;
	
	/**
	 * Server ComModule SleepTime.
	 */
	public static int SERVER_COM_SLEEPTIME = 20;
	
	/**
	 * Server ChatModule SleepTime.
	 */
	public static int SERVER_CHAT_SLEEPTIME = 20;
	
	/**
	 * Client ComModule SleepTime.
	 */
	public static int CLIENT_COM_SLEEPTIME = 10;
	
	/**
	 * Client GuiModule SleepTime.
	 */
	public static int CLIENT_GUI_SLEEPTIME = 40;
	

	/**
	 * Sleeptime der Stream-Threads.
	 */
	public static int SHARED_COM_STREAM_SLEEPTIME = 20;

	///// MATCH /////
	
	/**
	 * Ob Projektile erzeugt werden.
	 */
	public static boolean MATCH_GENERATE_PROJECTILS = true;
	
	/**
	 * Wie oft Passive Skills ausgefuehrt werden.
	 */
	public static int MATCH_PASSIVE_SKILLS_TICKER = 2 * 1000;
	
	/**
	 * Array aller Klassennamen zu denen Listen gespeichert werden sollen.
	 */
	public static String[] MATCH_GAMEOBJECTSLISTS = {"Player", "NonStatic", "WorldObject", "Hero", "Vasal", "Effect",  "DircetEffect", "Match", "Action", "ActiveEffect" , "PermanentEffect", "Building", "PassiveSkill" };

	
	/**
	 * MillisekundenTimer der Lobby.
	 * Wird erst Aktiviert wenn alle Bereit.
	 */
	public static int MATCH_LOBBY_TIME = 4 * 1000;
	
	/**
	 * Nach wievielen Runden die KI neu berechnet wird.
	 */
	public static int MATCH_KI_TURNS_NOT_CALCULATING = 10;
	
	///// COM /////
	
	/**
	 * Port des Servers.
	 */
	public static int COM_PORT = 6006;
	
	/**
	 * Host des Servers. Varianten: --> "proglab.informatik.uni-koeln.de" -->
	 * "localhost"
	 */
	
	public static String[] COM_HOSTS = {
		"localhost",
		"proglab.informatik.uni-koeln.de", 
		
	};
	
	/**
	 * Anzahl der Verbindungen.
	 */
	public static int COM_MAX_CONNECTIONS = 10;
	
	/**
	 * Wie oft der Output-Stream geloescht wird.
	 */
	public static int COM_OUTPUT_RESET_RATE = 300;
	
	///// GUI /////
	
	/**
	 * Ob Skills gezeigt werden, die shownInGui() == false haben.
	 */
	public static boolean GUI_SHOW_HIDDEN_SKILLS = false;

	/**
	 * Ob Skills gezeigt werden, die shownInGui() == false haben.
	 */
	public static boolean GUI_SHOW_POS_IN_NONSTATIC_PANEL = false;
	
	/**
	 * Ob die Effekt-Animationen angezeigt werden.
	 */
	public static boolean GUI_SHOW_PULSING_ANIMATION = true;
	
	/**
	 * Wie oft die MiniMap aktualisiert wird.
	 */
	public static int GUI_MINI_MAP_REFRESH_RATE = 50;
	
	///// Util /////
	
	/**
	 * Privater Konstrukor, da Utility-Class.
	 */
	private GlobalSettings() {
		
	}
	
}
