package gui.loader;

import java.awt.BorderLayout;

import gui.Colors;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Zeichnet die Anzeige, dass das Spiel geladen wird.
 * @author Sean
 */
public class Loader extends JPanel {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -1432777346608433120L;

	/**
	 * Zeichnet die Anzeige, dass das Spiel geladen wird.
	 * Bild stammt von http://www.ajaxload.info/
	 */
	public Loader() {
		this.setBackground(Colors.WHITE);
		this.setLayout(new BorderLayout());
		//TODO NAJA
		this.add(new JLabel(new ImageIcon("images/usercontrols/loader/ajax-loader.gif")), BorderLayout.CENTER);
		this.setVisible(true);
	}
}
