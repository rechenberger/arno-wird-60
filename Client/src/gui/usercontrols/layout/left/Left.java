package gui.usercontrols.layout.left;

import java.awt.BorderLayout;

import game.objects.GameObject;
import javax.swing.JPanel;

/**
 * Verwaltet die auf der linken Seite liegenden Panels.
 * @author Sean
 *
 */
public class Left extends JPanel {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -1956844644442804447L;
	
	
	/**
	 * Speichert das NonStaticInfoPanel.
	 */
	private GameObjectInfoPanel infoPanel = new GameObjectInfoPanel();

	/**
	 * Konstruktor.
	 */
	public Left() {
		this.setOpaque(false);
		this.setLayout(new BorderLayout());
		
		JPanel infoWrapper = new JPanel();
		infoWrapper.setOpaque(false);
		infoWrapper.add(infoPanel);
		
		this.add(infoWrapper, BorderLayout.NORTH);
		this.add(new EffectKnobPanel(), BorderLayout.WEST);
	}
	
	/**
	 * Setzt das anzuzeigende GameObject.
	 * @param gameObject GameObject das angezeigt werden soll.
	 */
	public void setGameObjectOfInfoPanel(final GameObject gameObject) {
		infoPanel.setGameObject(gameObject);
	}
}
