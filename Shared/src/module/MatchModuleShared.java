package module;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import settings.DebugSettings;

import com.messages.GameContentMessage;
import com.messages.MatchBeginMessage;
import com.messages.Message;
import com.messages.PlayerStatisticMessage;
import com.messages.SkillPointMessage;
import com.messages.UserReadyMessage;

import game.objects.GameObject;
import game.objects.Map;
import game.objects.Match;
/**
 *  Das MatchModule verwaltet das Match und fuehrt die Runden aus.
 * @author Tristan
 */
public abstract class MatchModuleShared extends Module {
	
	/**
	 * Ob Modul auf dem Client ausgefuehrt wird.
	 */
	protected boolean isClient;
	
	/**
	 * Ob Match laueft.
	 */
	protected boolean matchRunning = false;

	/**
	 * Zaehlt die Runden.
	 */
	protected int turn = 0;
	
	/**
	 * Zeahlt die Millisekunden.
	 */
	protected long time = 0;
	
	/**
	 * Das zugrundeliegende Match.
	 */
	protected Match match;

	/**
	 * speichert die GameContentMessages die von der Kommunikation kommen.
	 */
	protected Queue<GameContentMessage> inputQueue = new ConcurrentLinkedQueue<GameContentMessage>();

	/**
	 * Konstruktor.
	 */
	MatchModuleShared() {
		super(ModuleType.MATCH);
		GameObject.setMatchModule(this);
	}

	@Override
	public void run() {
		long timeBeforeTurn;
		long timeAfterTurn;
		int timeToSleep = 0;
		float avarageTurnTime = 0f;
		int turnsToMessure = DebugSettings.MATCH_TURNTIME_TURNS_TO_MEASURE;
		float turnTime;
		while (this.running) {
			
			timeBeforeTurn = System.currentTimeMillis();
			this.turn();
			timeAfterTurn = System.currentTimeMillis();
			
			turnTime = timeAfterTurn - timeBeforeTurn;
			
			
			timeToSleep = this.getSleepTime() - (int) turnTime;
			if (timeToSleep > 0) {
				Sleeper.sleep(timeToSleep);
			
			} 
			
			avarageTurnTime *= (turn % turnsToMessure);
			avarageTurnTime += turnTime;
			avarageTurnTime /= ((turn % turnsToMessure) + 1);

			
			if (DebugSettings.DEBUG && turn % turnsToMessure == 0 && ((this.isClient() && DebugSettings.MATCH_TURNTIME_CLIENT) || (!this.isClient() && DebugSettings.MATCH_TURNTIME_SERVER))) {
				System.out.println("Durchschnittliche Zugzeit: " + avarageTurnTime + "ms (" + this.getSleepTime() + "ms SleepTime)");
			}
		}
	}

	/**
	 * Eine Spielrunde.
	 */
	protected void turn() {
		this.turn++;
		this.incSleepCounter();
		if (this.getMatch() != null) {
			turnRegisterInputQueue();
		}

		
		if (this.turn % 100 == 0) {

//			for (Player p : Player.getAllPlayers()) {
//				p.printAllTimeStat();
//			}
			if (DebugSettings.MATCH_PRINT_GAMEOBJECTSIZE) {
				System.out.print("Runde " + this.turn + ", ");
				GameObject.printGameObjectSize();
	//			System.out.println(GameObject.getAllGameObjectsSize() + " GameObjects, ");
			}
		}
	}

	/**
	 * Nimmt alle GameContentMessage aus der InputQueue und registriert ihren Content.
	 */
	private void turnRegisterInputQueue() {
		while (!this.inputQueue.isEmpty()) {
			this.enqInputQueue().afterRecieve();
		}
	}

	/**
	 * Nimmt die naechste GameContentMessage aus der InputQueue und gibt ihren Content aus.
	 * @return GameObject (jener Content)
	 */
	private GameObject enqInputQueue() {
		GameContentMessage msg = inputQueue.remove();
		GameObject gameObject = msg.getContent();
		msg.removeContent();
		return gameObject;
	}

	/**
	 * @return Ob Modul auf dem Client ausgefuehrt wird.
	 */
	public boolean isClient() {
		return isClient;
	}

	/**
	 * Match-Getter.
	 * @return match
	 */
	public final Match getMatch() {
		return match;
	}

	/**
		 * Schreibt die empfangene Nachricht in die inputQueue.
		 * @param msg Zu empfangene Nachricht.
		 */
		public void pushMessage(final Message msg) {
			if (msg instanceof GameContentMessage) {
				this.inputQueue.add((GameContentMessage) msg);
			} else if (msg instanceof MatchBeginMessage) {
				if (this.getMatch() != null) {
					this.getMatch().setStartingIn(((MatchBeginMessage) msg).getStartingIn());
					if (DebugSettings.MATCH_PRINT_MATCH_STARTINGIN) {
						System.out.println("Match startet in: " + this.getMatch().getStartingIn() / 1000 + " Sekunden.");
					}
					if (this.getMatch().getStartingIn() <= 0) {
						this.startRunning();
					}
				}
			} else if (msg instanceof UserReadyMessage) {
				((UserReadyMessage) msg).execute();
				if (!this.isClient()) {
					this.sendMessage(msg);
				}
			} else if (msg instanceof PlayerStatisticMessage) {
				((PlayerStatisticMessage) msg).execute();
			} else if (msg instanceof SkillPointMessage) {
				((SkillPointMessage) msg).execute();
			} else {
				System.out.println("MatchModule hat falsche msg erhalten: " + msg.toString());
			}
		}

	/**
	 * Sendet die Nachricht ueber das Kommunikationsmodul an die anderen MatchModule.
	 * @param msg Die Nachricht.
	 */
	public abstract void sendMessage(final Message msg);
	
	/**
	 * Sendet Nachricht an einen bestimmten benutzer.
	 * @param msg Nachricht.
	 * @param userId Benutzerid.
	 */
	public abstract void sendMessage(final Message msg, final int userId);
	
	/**
	 * Setzt, ob das Match laeuft.
	 * @param matchRunning boolean
	 */
	public void setMatchRunning(final boolean matchRunning) {
		this.matchRunning = matchRunning;
	}
	
	/**
	 * 
	 * @return Akutelle Runde
	 */
	public int getTurn() {
		return this.turn;
	}

	/**
	 * Beendet das Match.
	 */
	public void end() {
		System.out.println("Match vorbei");
//		Player.addStatisticsCurrentGameToStatisticsAllTimeForAllPlayer();
		this.running = false;
		this.matchRunning = false;
	}

	/**
	 * @return Map.
	 */
	public Map getMap() {
		return this.getMatch().getMap();
	}
	
	/**
	 * @return Ob Spiel laueft.
	 */
	public boolean isMatchRunning() {
		return this.matchRunning;
	}
	
	/**
	 * Startet das Spiel.
	 */
	public void startRunning() {
		this.matchRunning = true;
		System.out.println("Lasset die Spiele beginnen.");
	}

	/**
	 * @return Millisekunden seit dem das Spiel laeuft.
	 */
	public long getTime() {
		return this.time;
	}
	
	/**
	 * Teilt der Gui von einem neuen Effekt mit.
	 * @param effect neuer Effekt
	 */
	public void tellGuiOfNewEffect(final game.effects.Effect effect) {
		
	}

}
