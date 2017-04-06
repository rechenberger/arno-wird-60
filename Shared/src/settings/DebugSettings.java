package settings;

/**
 * Einstellungen zum Debuggen.
 * @author Alle
 * CHECKSTYLE:OFF
 */
public final class DebugSettings {

	///// GLOBAL /////
	
	/**
	 * Ob generell Debug-Nachrichten angezeigt werden.
	 */
	public static boolean DEBUG = true;
	
	
	/**
	 * Ob Ergebnis des SleepTests in Slepper ausgegeben wird.
	 */
	public static boolean PRINT_SLEEP_TEST = DEBUG && true;
	
	///// MATCH ////
	
	public static boolean EXIT_WHEN_NO_USERS = DEBUG && false;
	/**
	 * Ob Groesse der Liste aller GameObjects ausgegeben wird.
	 */
	public static boolean MATCH_PRINT_GAMEOBJECTSIZE = DEBUG && false;
	
	/**
	 * Ob TurnTimePerformance auf dem Client ausgegeben wird.
	 */
	public static boolean MATCH_TURNTIME_CLIENT = DEBUG && false;
	
	/**
	 * Ob TurnTimePerformance auf dem Server ausgegeben wird.
	 */
	public static boolean MATCH_TURNTIME_SERVER = DEBUG && false;
	
	/**
	 * Der Durschnitt wievieler Runden ausgegeben wird.
	 */
	public static int MATCH_TURNTIME_TURNS_TO_MEASURE = 200;
	
	/**
	 * Ob "... hat versucht nen Toten anzugreifen, pfui" angezeigt wird.
	 */
	public static boolean MATCH_PRINT_TRIES_TO_ATTACK_DEAD_GUY = DEBUG && false;
	
	/**
	 * Ob Match startet in ... ausgegeben wird.
	 */
	public static boolean MATCH_PRINT_MATCH_STARTINGIN = DEBUG && false;

	/**
	 * Ob AllGameObjectMessage ausgegeben wird.
	 */
	public static boolean MATCH_PRINT_ALLGAMEOBJECTMESSAGE = DEBUG && false;
	
	/**
	 * Ob UserID zu Beginn ausgegeben wird.
	 */
	public static boolean MATCH_PRINT_USERID = DEBUG && false;
	
	/**
	 * Ob "Neuer Benutzer im Match: ..." ausgegeben wird.
	 */
	public static boolean MATCH_PRINT_NEWUSER = DEBUG && true;
	
	///// GUI /////
	

	/**
	 * Ob "Objekte an dieser Stelle: ..." ausgegeben wird.
	 */
	public static boolean GUI_PRINT_LEFTCLICK_OBJECTS_HERE = DEBUG && false;
	
	///// COM /////
	
	/**
	 * Ob "Input/Output ready!" ausgegeben wird.
	 */
	public static boolean COM_PRINT_STREAM_READY = DEBUG && false;
	
	///// LOGIN /////
	
	/**
	 * Ob "Login erfolgreich? ..." ausgegeben wird.
	 */
	public static boolean LOGIN_PRINT_SUCCSESS = DEBUG && false;
	
	///// CHAT /////
	
	/**
	 * Ob SystemChatNachrichten in der Konsole ausgegeben werden.
	 */
	public static boolean CHAT_PRINT_SYSTEMCHATMESSAGE = DEBUG && false;
	
	
	///// Util /////
	
	/**
	 * Privater Konstrukor, da Utility-Class.
	 */
	private DebugSettings() {
	}

}
