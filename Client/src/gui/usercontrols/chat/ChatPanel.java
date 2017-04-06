package gui.usercontrols.chat;

import gui.Colors;

import java.util.LinkedList;
import java.util.Queue;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 * Verwaltet den Chat in der GUI.
 * @author Sean
 *
 */
public class ChatPanel extends JPanel {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -6762099429601140628L;
	
	/**
	 * Speichert die angezeigten Nachrichtem.
	 */
	Queue<ChatViewBox> viewBoxes = new LinkedList<ChatViewBox>();

	/**
	 * Erstellt ein neue ChatPanel und zeigt die Eingabefelder an.
	 */
	public ChatPanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBackground(Colors.BLACK_04);
		this.add(new ChatGlobalEnterBox().init());
		this.add(new ChatGroupEnterBox().init());
		this.setVisible(true);
	}
	
	/**
	 * Fuegt eine neue Box hinzu, die eine Chatnachricht enthaelt.
	 * @param chatBox Anzuzeigende Box
	 */
	public void addChatMessage(final ChatViewBox chatBox) {
		if (this.viewBoxes.size() >= 8) {
			this.remove(this.viewBoxes.poll());
		}
		this.add(chatBox);
		this.viewBoxes.add(chatBox);
		this.revalidate();
	}
}
