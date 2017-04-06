import settings.GlobalSettings;
import module.ModuleHandler;


/**
 * @author Tristan
 *
 */
public final class Server {
	
	
	/**
	 * Private, da nicht instanziert werden muss.
	 */
	private Server() { }

	/**
	 * Startet den Server, indem es das Match- und Com- Module in jeweils einem Thread startet.
	 * @param args Kommandozeilenparameter
	 */
	public static void main(final String[] args) {
		ModuleHandler.MATCH.setSleepTime(GlobalSettings.SERVER_MATCH_SLEEPTIME);
		ModuleHandler.COM.setSleepTime(GlobalSettings.SERVER_COM_SLEEPTIME);
		ModuleHandler.CHAT.setSleepTime(GlobalSettings.SERVER_CHAT_SLEEPTIME);
		
		new Thread(ModuleHandler.MATCH).start();
		new Thread(ModuleHandler.COM).start();
		new Thread(ModuleHandler.CHAT).start();
		}
}
