package module;

import game.objects.Player;

import java.util.LinkedList;
import java.util.Queue;

import com.messages.ChatMessage;
import com.messages.Message;

/**
 * Das ChatModul verwaltet Chatnachrichten und versendet diese.
 * @author Christian, Sean
 *
 */
public class ChatModule extends Module {
	
	/**
	 * Queue, die die Nachrichten speichert.
	 */
	private Queue<ChatMessage> inQueue;
	
	
	/**
	 * Das ChatModul erstellt eine Queue mit Nachrichten.
	 */
	protected ChatModule() {
		super(ModuleType.CHAT);
		inQueue = new LinkedList<ChatMessage>();
	}

	@Override
	public void run() {
		while (running) {
			while (!inQueue.isEmpty()) {
				handleMsg(inQueue.poll());
			}
			Sleeper.sleep(this.sleepTime);
		}
	}

	@Override
	public synchronized void pushMessage(final Message msg) {
		inQueue.add((ChatMessage) msg);
	}
	
	/**
	 * Verwaltet die Messages und schickt diese weiter.
	 * @param msg Nachricht, die bearbeitet werden soll
	 */
	private void handleMsg(final ChatMessage msg) {
		switch (msg.getType()) {
		case SYSTEM_CHAT:
			ModuleHandler.COM.pushMessage(msg);
			break;
		case GLOBAL_CHAT:
			ModuleHandler.COM.pushMessage(msg);
			break;
		case GROUP_CHAT:
			int[] idList = new int[5];
			Player msgPlayer = Player.getPlayerByUserId(msg.getUserID());
			int i = 0;
			for (Player player : Player.getAllPlayers()) {
				if (player.getHero().getFraction().equals(msgPlayer.getHero().getFraction())) {
					idList[i] = player.getUserId();
					i++;
				}
			}
			ModuleHandler.COM.pushMessage(msg, idList);
			break;
		default:
			break;
		}
	}

}
