package gui.usercontrols.chat;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import game.objects.Player;
import gui.Colors;
import gui.StringHelper;
import gui.usercontrols.UserControls;

import javax.swing.JPanel;

import com.messages.ChatMessage;
import com.messages.MessageType;

import module.ModuleHandler;

/**
 * Zeichnet eine Box in der eine Chatnachricht angezeigt wird.
 * @author Sean
 *
 */
public class ChatViewBox extends JPanel {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -4339337911625533974L;

	/**
	 * Speichert das Graphic-Objekt zentral.
	 */
	private Graphics2D g2;
	
	/**
	 * Speichert den Benutzernamen der Chatnachricht.
	 */
	private String username;
	
	/**
	 * Speichert den Text der Chatnachricht.
	 */
	private String text;
	
	/**
	 * Speichert den Typen der Chatnachricht.
	 */
	private String type;
	
	/**
	 * Erstellt eine neue Box um eine Chatnachricht anzuzeigen, die sich selbststaendig
	 * in die GUI einfuegt und anzeigt, falls die UserControls schon geladen sind.
	 * @param msg Die Chatnachricht
	 */
	public ChatViewBox(final ChatMessage msg) {
		this.username = msg.getUsername();
		this.text = msg.getText();
		if (msg.getType() == MessageType.SYSTEM_CHAT) {
			this.type = "";
		} else if (msg.getType() == MessageType.GROUP_CHAT) {
			this.type = " [Team] ";
		} else if (msg.getType() == MessageType.GLOBAL_CHAT) {
			this.type = " [Global] ";
		}
		this.setOpaque(false);
		if (ModuleHandler.GUI.getPanel(UserControls.class) != null) {
			ModuleHandler.GUI.getPanel(UserControls.class).getChatPanel().addChatMessage(this);
		}
	}
	
	@Override
	public void paintComponent(final Graphics g) {
		super.paintComponent(g);
		this.g2 = (Graphics2D) g;
		this.g2.setColor(Colors.WHITE);
		
		String text = this.username + this.type + ": " + this.text;
		
		if (this.username.equals("System")) {
			this.g2.setColor(Colors.ORANGE);
		} else if (this.username.equals(ModuleHandler.MATCH.getMe().getName())) {
			this.g2.setColor(Colors.CYAN);
		} else if (Player.getPlayerByName(this.username).getHero().getFraction() 
				== ModuleHandler.MATCH.getMe().getHero().getFraction()) {
			this.g2.setColor(Colors.GREEN1);
		} else {
			this.g2.setColor(Colors.RED1);
		}
		
		String newText = "";
		double textLength = 0;
		
		for (String word : text.split(" ")) {
			Rectangle2D rect = g2.getFontMetrics().getStringBounds(word, g2);
			if (rect.getWidth() + textLength + 1 < 300) {
				newText += " " + word;
				textLength += rect.getWidth() + 1;
			} else {
				newText += "<br>" + word;
				textLength = 0;
			}
		}
		
		Rectangle2D rect = StringHelper.stringSize(g2, newText);
		this.setPreferredSize(new Dimension((int) rect.getWidth(), (int) rect.getHeight() + g2.getFontMetrics().getAscent()));
		StringHelper.drawString(g2, newText, 0, 0);
		this.revalidate();
	}
 }
