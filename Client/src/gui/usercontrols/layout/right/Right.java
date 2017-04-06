package gui.usercontrols.layout.right;

import gui.usercontrols.chat.ChatPanel;

import javax.swing.JPanel;

/**
 * Rechte Seite der UserControls.
 * @author Christian
 *
 */
public class Right extends JPanel {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Speichert das ChatPanel.
	 */
	private ChatPanel chatPanel = new ChatPanel();

	/**
	 * Konstruktor.
	 */
	public Right() {
		this.setOpaque(false);
		this.add(chatPanel);
	}
	
	/**
	 * @return Gibt das ChatPanel zurueck.
	 */
	public ChatPanel getChatPanel() {
		return chatPanel;
	}
}
