package gui.lobby;

import java.awt.Graphics;

import game.content.heros.Hero;

import javax.swing.JLabel;

/**
 * Speichert den Hero im Label, damit der Name neu gezeichnet werden kann.
 * @author Sean
 *
 */
public class LobbyLabel extends JLabel {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 8972619797525074759L;
	
	/**
	 * Speichert den Helden.
	 */
	private Hero hero;
	
	/**
	 * Uebergibt den Text und den horizontalAlignment an JPanel und speichert den Helden.
	 * @param text Text, der angezeigt werden soll (Name des Spielers)
	 * @param horizontalAlignment Siehe JLabel
	 * @param hero Held, der zu dem Label gehoert
	 */
	public LobbyLabel(final String text, final int horizontalAlignment, final Hero hero) {
		super(text, horizontalAlignment);
		this.hero = hero;
	}
	
	@Override
	public void paintComponent(final Graphics g) {
		super.paintComponent(g);
		if (hero.getPlayer() != null) {
			setText(hero.getPlayer().getName());
		} else {
			setText(" ");
		}
	}
}
