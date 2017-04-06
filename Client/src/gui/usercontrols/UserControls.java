package gui.usercontrols;


import gui.usercontrols.chat.ChatPanel;
import gui.usercontrols.layout.bottom.Bottom;
import gui.usercontrols.layout.left.Left;
import gui.usercontrols.layout.right.Right;

import java.awt.BorderLayout;

import javax.swing.JPanel;

/**
 * Zeichnet und behandelt die Bedienelemente des Benutzers. 
 * @author Sean
 *
 */
@SuppressWarnings("serial")
public class UserControls extends JPanel {
	
	/**
	 * Der rechte Teil des BorderLayouts.
	 */
	Right right = new Right();
	
	/**
	 * Der untere Teil des BorderLayouts.
	 */
	Bottom bottom = new Bottom();
	
	/**
	 * Der linke Teil des BorderLayouts.
	 */
	Left left = new Left();
	
	/**
	 * Konstruktor.
	 */
	public UserControls() {
		setLayout(new BorderLayout());
		setOpaque(false);
		this.add(bottom, BorderLayout.SOUTH);
		this.add(right, BorderLayout.EAST);
		this.add(left, BorderLayout.WEST);
	}
	
	/**
	 * Setzt den anzuzeigenden NonStatic.
	 * @param gameObject NonStatic der angezeigt werden soll.
	 */
	public void setNonStaticInfoPanel(final game.objects.GameObject gameObject) {
		left.setGameObjectOfInfoPanel(gameObject);
	}
	
	/**
	 * @return Gibt das ChatPanel zurueck.
	 */
	public ChatPanel getChatPanel() {
		return this.right.getChatPanel();
	}
}
