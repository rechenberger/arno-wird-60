package gui.usercontrols.modal;

import gui.Colors;
import gui.StringHelper;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.Border;

import module.ModuleHandler;

/**
 * Zeichnet ein Modal. Hintergrund wird verdunkelt und man kann erst weiter machen,
 * bis man das Modal beantwortet hat.
 * @author Christian
 */
public class ComponentModal extends JPanel {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 3944778516370986727L;
	
	/**
	 * Titel des Modals.
	 */
	protected String head;
	
	/**
	 * Zentrum des Fensters.
	 */
	protected Point center;
	
	/**
	 * Startpunkt von dem aus die graue Box gezeichnet wird.
	 */
	protected Point startBox;
	
	/**
	 * Startpunkt von dem aus die Buttons gezeichnet werden.
	 */
	protected Point startButtons;
	
	/**
	 * Startpunkt von dem aus der Header/Titel gezeichnet wird.
	 */
	protected Point startHeader;
	
	/**
	 * Zentrales Graphics Objekt.
	 */
	protected Graphics2D g2;
	
	/**
	 * Liste der Buttons, die angezeigt werden sollen.
	 */
	protected LinkedList<JButton> buttons;
	
	/**
	 * Verweis aus das Panel um aus dem MouseListener darauf zuzugreifen. 
	 */
	protected JPanel panel = this;
	
	/**
	 * Komponente.
	 */
	protected JComponent comp;
	/**
	 * Speichert Titel und Nachricht und setzt den Abbrechen Button.
	 * @param head Titel des Modals
	 * @param comp JComponent
	 */
	public ComponentModal(final String head, final JComponent comp) {
		this.head = head;
		
		this.setLayout(null);
		
		this.buttons = new LinkedList<JButton>();
		
		this.addButton("OKAY", new MouseListener() {
			@Override
			public void mouseClicked(final MouseEvent e) {
				ModuleHandler.GUI.getContentPane().removeComponent(panel);
				ModuleHandler.GUI.getContentPane().requestFocus();
			}
			@Override
			public void mouseEntered(final MouseEvent e) { }
			@Override
			public void mouseExited(final MouseEvent e) { }
			@Override
			public void mousePressed(final MouseEvent e) { }
			@Override
			public void mouseReleased(final MouseEvent e) { }
			
		});
		
		this.setOpaque(false);
		this.setVisible(true);
		
		this.comp = comp;
		this.comp.setOpaque(false);
		this.comp.setVisible(true);
		
		this.add(comp);
	}
	
	/**
	 * Fuegt das Modal in das ContentPane auf hoechster Ebene ein.
	 */
	public void show() {
		ModuleHandler.GUI.getContentPane().addComponent(this);
	}
	
	/**
	 * Entfernt das Modal.
	 */
	public void remove() {
		ModuleHandler.GUI.getContentPane().removeComponent(panel);
	}
	
	/**
	 * Fuegt einen Button dem Modal zu.
	 * @param label Label des Buttons
	 * @param clicked MouseListener, der dem Button hinzugefuegt wird. null, falls keiner eingebaut werden soll
	 */
	public void addButton(final String label, final MouseListener clicked) {
		JButton button = new JButton(label);
		button.setBackground(Colors.ORANGE);
		button.setForeground(Colors.WHITE);
		button.setOpaque(true);
		Border buttonBorder = BorderFactory.createLineBorder(Colors.GREY4);
		button.setBorder(buttonBorder);
		
		if (clicked != null) {
			button.addMouseListener(clicked);
		}
		
		this.buttons.add(button);
		this.add(button);
	}
	
	/**
	 * Entfernt den Standardbutton, der das Fenster schliesst.
	 */
	public void removeCancelButton() {
		int toRemove = 0;
		for (JButton button : this.buttons) {
			if (button.getText().equals("OKAY")) {
				toRemove = buttons.indexOf(button);
			}
		}
		this.remove(buttons.get(toRemove));
		this.buttons.remove(toRemove);
	}
	
	/**
	 * Benennt den Standardbutton, der das Fenster schliesst, um.
	 * @param text Neuer Text des Buttons
	 */
	public void renameCancelButton(final String text) {
		for (JButton button : this.buttons) {
			if (button.getText().equals("OKAY")) {
				button.setText(text);
			}
		}
	}
	
	/**
	 * Zeichnet den Header/Titel ausgehend von der uebergebenen Breite/Hoehe und dem Startpunkt,
	 * der in der Methode drawBox gesetzt wird. Deshalb muss drawBox vor drawHeader aufgerufen werden.
	 * @param startWidth Breite des Headers
	 * @param startHeight Hoehe des Headers
	 */
	protected void drawHeader(final double startWidth, final double startHeight) {
		GeneralPath path = new GeneralPath();
		double width = startWidth;
		//Ein bisschen mehr Platz fuer den Text
		double height = startHeight + 8;
		
		//Mindestbreite des Titels
		if (width < 350) {
			width = 350;
		}
		
		double x = this.startBox.x;
		//Abstand von der Box
		double y = this.startBox.y - height - 10;
		
		this.startHeader = new Point((int) x, (int) y);
		
		path.moveTo(x, y);
		x += width;
		path.lineTo(x, y);
		x += height;
		y += height;
		path.lineTo(x, y);
		x -= width + height;
		path.lineTo(x, y);
		path.closePath();
		
		this.g2.setColor(Colors.ORANGE);
		this.g2.fill(path);
		this.g2.setColor(Colors.GREY4);
		this.g2.draw(path);
		
		this.g2.setColor(Colors.WHITE);
		//Text wird ein wenig eingerueckt
		StringHelper.drawString(g2, this.head, (int) (this.startHeader.x + 10), (int) (this.startHeader.y + 2));
	}
	
	/**
	 * Zeichnet die Box mit der Nachricht.
	 * @param startWidth Breite der Box
	 * @param startHeight Hoehe der Box
	 */
	protected void drawBox(final double startWidth, final double startHeight) {
		GeneralPath path = new GeneralPath();
		double width = startWidth;
		double height = startHeight;
		
		//Mindestbreite der Box
		if (width < 400) {
			width = 400;
		}
		
		//Mindesthoehe der Box
		if (height < 160) {
			height = 160;
		}
		
		//Groesse wird um die Aussenraender 2 * 30 erweitert, damit man ein wenig
		//mehr Platz hat und es huebscher ist.
		width += 2 * 30;
		height += 2 * 30 + 30;
		
		double x = (double) this.center.x - (width / 2);
		double y = (double) this.center.y - (height / 2);
		
		this.startBox = new Point((int) x, (int) y);
		
		path.moveTo(x, y);
		x += 0.75 * width;
		path.lineTo(x, y);
		x += 10;
		y += 10;
		path.lineTo(x, y);
		x += 0.25 * width;
		path.lineTo(x, y);
		x += 15;
		y += 15;
		path.lineTo(x, y);
		y += 0.8625 * height;
		path.lineTo(x, y);
		x -= 0.5 * width;
		path.lineTo(x, y);
		x -= 10;
		y += 10;
		path.lineTo(x, y);
		x -= 0.5 * width;
		path.lineTo(x, y);
		x -= 15;
		y -= 15;
		path.lineTo(x, y);
		path.closePath();
		
		//Buttons sollen ein wenig Abstand zu den Raendern haben
		this.startButtons = new Point((int) x + 20, (int) y - 10);
		
		this.g2.setColor(Colors.GREY8);
		this.g2.setStroke(new BasicStroke(1));
		this.g2.fill(path);
		
		this.g2.setColor(Colors.GREY2);
		this.g2.draw(path);
		
		this.g2.setColor(Colors.BLACK);
		//Nachricht hat einen Abstand zu den Raendern der Box
//        StringHelper.drawString(g2, this.message, (int) (this.startBox.x + 20), (int) (this.startBox.y + 20));
	}
	
	@Override
    public void paintComponent(final Graphics g) {
		Rectangle2D rect;
		this.g2 = (Graphics2D) g;
		this.g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		this.center = new Point(this.getWidth() / 2, this.getHeight() / 2);
		
        this.g2.setColor(Colors.BLACK_08);
        Dimension dim = comp.getPreferredSize();
        this.g2.fillRect(0, 0, this.getWidth(), this.getHeight());
        
        this.drawBox(dim.getWidth(), dim.getHeight());
        
        rect = StringHelper.stringSize(g2, this.head);
        this.drawHeader(comp.getWidth(), rect.getHeight());
        
        comp.setBounds((int) (this.center.x - dim.getWidth() / 2), (int) (this.center.y - dim.getHeight() / 2) - 30, (int) dim.getWidth(), (int) dim.getHeight());
        int buttonX = this.startButtons.x;
        int buttonY = this.startButtons.y;
        for (JButton button : this.buttons) {
        	button.setBounds(buttonX, buttonY, button.getPreferredSize().width + 10, button.getPreferredSize().height + 2);
        	buttonX += button.getPreferredSize().width + 15;
        }
    }
}
