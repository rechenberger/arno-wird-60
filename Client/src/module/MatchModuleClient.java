package module;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import settings.DebugSettings;
import game.content.heros.Hero;
import game.objects.GameObject;
import game.objects.Match;
import game.objects.Player;
import gui.usercontrols.modal.Modal;

import com.messages.AllGameObjectsMessage;
import com.messages.MatchEndMessage;
import com.messages.MatchTimeMessage;
import com.messages.Message;




/**
 *  Das MatchModule der Client Seite.
 * @author Tristan
 */
public class MatchModuleClient extends MatchModuleShared {
	
	/**
	 * Auszulesende AllGameObjectsMessage.
	 */
	protected AllGameObjectsMessage allGameObjectsMessage;
	
	/**
	 * Der Spieler.
	 */
	protected Player me;
	
	/**
	 * Konstruktor.
	 */
	public MatchModuleClient() {
		super();
		this.isClient = true;
	}
	
	@Override
	protected void turn() {
		this.turnAllGameObjects();
		super.turn();
	}
	
	@Override
	public void run() {
		super.run();
	}

	@Override
	public void sendMessage(final Message msg) {
		ModuleHandler.COM.pushMessage(msg);
	}
	
	@Override
	public void sendMessage(final Message msg, final int userId) {
		System.out.println("Warnung: Die Methode sendMessage(msg, userId) sollte auf dem Client nicht aufgerufen werden. Sende sie an den Server.");
		this.sendMessage(msg);
	}
	
	@Override
	public void pushMessage(final Message msg) {
		if (msg instanceof AllGameObjectsMessage) {
			if (DebugSettings.DEBUG && DebugSettings.MATCH_PRINT_ALLGAMEOBJECTMESSAGE) {
				System.out.println("Habe AllGameObjectsMessage erhalten: size: " + ((AllGameObjectsMessage) msg).getAllGameObjects().size());
			}
			allGameObjectsMessage = (AllGameObjectsMessage) msg;
		} else if (msg instanceof MatchTimeMessage) {
			this.time = ((MatchTimeMessage) msg).getTime();
		} else if (msg instanceof MatchEndMessage) {
			String header = "";
			if (((MatchEndMessage) msg).getWinnerTeam() == ModuleHandler.MATCH.getMyHero().getFraction()) {
				header = "Sie haben GEWONNEN!";
			} else {
				header = "Sie haben VERLOREN!";
			}
			Modal matchEnd = new Modal(header, "Durch klicken auf WEITER gelangen Sie zu den Statistiken.");
			matchEnd.removeCancelButton();
			matchEnd.addButton("WEITER", new MouseListener() {
				@Override
				public void mouseClicked(final MouseEvent e) {
					ModuleHandler.GUI.showNextView();
				}
				@Override
				public void mouseEntered(final MouseEvent e) { }
				@Override
				public void mouseExited(final MouseEvent e) { }
				@Override
				public void mousePressed(final MouseEvent e) { }
				@Override
				public void mouseReleased(final MouseEvent e) { }
			});
			matchEnd.show();
		} else {
			super.pushMessage(msg);
		}
	}
	

	/**
	 * Setzt GameObject.allGameObjects wie in der gepeicherten erhalten Nachricht, falls diese vorhanden.
	 */
	private void turnAllGameObjects() {
		if (this.allGameObjectsMessage != null) {
			GameObject.setAllGameObjects(allGameObjectsMessage.getAllGameObjects());
			this.match = Match.getMatch();
			if (DebugSettings.DEBUG && DebugSettings.MATCH_PRINT_USERID) {
				System.out.println("Meine UserID = " + allGameObjectsMessage.getUserID());
			}
			this.me = Player.getPlayerByUserId(allGameObjectsMessage.getUserID());
			// TODO Koennen wir hier nicht irgendwie warten bis alle fertig sind
			if (this.getMyHero() == null) {
				System.out.println("Eigenen Helden nicht gefunden");
				System.out.println("Client shutting down...");
				System.exit(0);
			}
			this.allGameObjectsMessage = null;
			
			if (this.isMatchRunning()) {
				ModuleHandler.GUI.showCertainView(4);
			} else {
				ModuleHandler.GUI.showNextView();
			}
		}
	}
	/**
	 * @return Der vom Spieler gesteurte Held.
	 */
	public Hero getMyHero() {
		return this.me.getHero();
	}
	
	/**
	 * @return Der Spieler.
	 */
	public Player getMe() {
		return this.me;
	}
	
	@Override
	public void startRunning() {
		super.startRunning();
		ModuleHandler.GUI.showNextView();
	}
	
	@Override
	public void tellGuiOfNewEffect(final game.effects.Effect effect) {
		ModuleHandler.GUI.newEffect(effect);		
	}
}
