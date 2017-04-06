package gui.listeners.actions;

import gui.match.MatchPanel;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;

import module.ModuleHandler;

/**
 * Wird ausgefuehrt, wenn man die Leertaste / Pfeiltasten benutzt um die Karte zu verschieben
 * bzw. auf den Helden zu zentrieren
 * @author Sean
 *
 */
public class MoveMapCenterPoint extends AbstractAction {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -6022929690152980537L;
	
	/**
	 * Speichert den KeyCode, damit spaeter eine Zuordnung moeglich ist.
	 */
	private int keyCode;
	
	/**
	 * Setzt den KeyCode der festlegt, in welche Richtung gegangen werden soll.
	 * @param keyCode Integerwert des KeyCode, der auch beim KeyListener verwendet wird
	 */
	public MoveMapCenterPoint(final int keyCode) {
		this.setKeyCode(keyCode);
	}

	@Override
	public void actionPerformed(final ActionEvent arg0) {
		switch(this.getKeyCode()) {
		case KeyEvent.VK_SPACE:
			//Leertaste
			ModuleHandler.GUI.getPanel(MatchPanel.class).setFollowMyHero(true);
			break;
		case KeyEvent.VK_LEFT:
			//Linke Pfeiltaste
			moveCenterCoord(-1, 1);
			break;
		case KeyEvent.VK_UP:
			//Obere Pfeiltaste
			moveCenterCoord(-1, -1);
			break;
		case KeyEvent.VK_RIGHT:
			//Rechte Pfeiltaste
			moveCenterCoord(1, -1);
			break;
		case KeyEvent.VK_DOWN:
			//Untere Pfeiltaste
			moveCenterCoord(1, 1);
			break;
		case 90:
			//z
			ModuleHandler.GUI.getPanel(MatchPanel.class).setZoom(0.5f);
		default:
			break;
		}
	}
	
	/**
	 * Veraendert die Mitte des Bildschirmausschnitts um die angegebenen Einheiten.
	 * @param translateX Veraenderung in X Richtung
	 * @param translateY Veraenderung in Y Richtung
	 */
	private void moveCenterCoord(final int translateX, final int translateY) {
		MatchPanel mp = ModuleHandler.GUI.getPanel(MatchPanel.class);
		Point centerCoord = (Point) mp.getCenterCoord().clone();
		
		centerCoord.translate(translateX, translateY);
		mp.setCenterCoord(centerCoord);
		
		ModuleHandler.GUI.getPanel(MatchPanel.class).setFollowMyHero(false);
	}

	/**
	 * @return Liefert den KeyCode zurueck, auf den die Action reagiert.
	 */
	public int getKeyCode() {
		return keyCode;
	}

	/**
	 * @param keyCode Setzt den KeyCode, auf den die Action reagiert.
	 */
	public void setKeyCode(final int keyCode) {
		this.keyCode = keyCode;
	}

}
