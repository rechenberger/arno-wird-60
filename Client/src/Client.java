import settings.GlobalSettings;
import module.ModuleHandler;
/**
 * @author Tristan, Sean
 *
 */
public final class Client {
	
	/**
	 * Private, da nicht instanziert werden muss.
	 */
	private Client() {
		
	}

	/**
	 * Startet den Client, indem es das Match-, Com- und Gui- Module in jeweils einem Thread startet.
	 * @param args Kommandozeilenparameter
	 */
		public static void main(final String[] args) {
			
			ModuleHandler.GUI.setSleepTime(GlobalSettings.CLIENT_GUI_SLEEPTIME);
			ModuleHandler.MATCH.setSleepTime(GlobalSettings.CLIENT_MATCH_SLEEPTIME);
			ModuleHandler.COM.setSleepTime(GlobalSettings.CLIENT_COM_SLEEPTIME);

			new Thread(ModuleHandler.COM).start();
			new Thread(ModuleHandler.MATCH).start();
			new Thread(ModuleHandler.GUI).start();
			
		}
}
