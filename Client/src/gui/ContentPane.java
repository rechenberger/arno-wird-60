package gui;

import gui.interfaces.Resizable;
import gui.listeners.ResizeComponentAdapter;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.LinkedList;

import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

/**
 * Ist das ContentPane fuer das Hauptfenster.
 * Es legt die einzelnen Panels/Components in einem LayeredPane ab, sodass
 * sie uebereinander gezeichnet werden koennen.
 * @author Sean
 *
 */
@SuppressWarnings("serial")
public class ContentPane extends JPanel implements Resizable {
	/**
	 * Speichert das layeredPane, damit im Nachhinein Panels hinzugefuegt werden koennen.
	 */
	private JLayeredPane layeredPane;
	
	/**
	 * Speichert die Componenten, die zum layeredPane hinzugefuegt werden.
	 * Die Reihenfolge enspricht der Position im Array. Liegt eine Component an stelle 0
	 * so wird sie von einer Component, die an Stelle 1 liegt, ueberzeichnet.
	 */	
	private LinkedList<JComponent> components = new LinkedList<JComponent>();
	
	/**
	 * Konstruktor. Fuegt die einzelnen Panels/Components in ein LayeredPane ein.
	 * @param dim Dimension des Fensters
	 */
	public ContentPane(final Dimension dim) {
		layeredPane = new JLayeredPane();
		
        //Grundeinstellungen:
		setLayout(new BorderLayout());
		setPreferredSize(dim);
		setOpaque(true);
		
		//Listener:
		addComponentListener(new ResizeComponentAdapter());
		
		int i = 0;
		for (JComponent comp : components) {
			comp.setBounds(0, 0, (int) dim.getWidth(), (int) dim.getHeight());
			comp.setPreferredSize(dim);
			layeredPane.add(comp, new Integer(i));
			i++;
		}
        
        add(layeredPane);
	}
	
	
	/**
	 * Passt die Komponenten bei Veraenderung der Fenstergroesse an.
	 */
	@Override
	public void resized() {
		for (JComponent comp : components) {
			comp.setPreferredSize(this.getSize());
			comp.setMaximumSize(this.getSize());
			comp.setSize(this.getSize());
			comp.revalidate();
			comp.repaint();
		}
	}
	
	/**
	 * Fuegt eine JComponent dem Fenster an oberster Stelle hinzu und zeichnet diese.
	 * @param component JComponent, dass hinzugefuegt werden soll
	 */
	public void addComponent(final JComponent component) {
		layeredPane.add(component, new Integer(this.components.size()));
		this.components.add(component);
		component.setPreferredSize(this.getSize());
		component.setMaximumSize(this.getSize());
		component.setSize(this.getSize());
		component.revalidate();
		component.requestFocusInWindow();
		component.repaint(); 
	}
	
	/**
	 * Entfernt eine bestimmte JComponent aus dem Fenster.
	 * @param component JComponent, die entfernt werden soll
	 */
	public void removeComponent(final JComponent component) {
		layeredPane.remove(component);
		this.components.remove(component);
		this.repaint();
	}
	
	/**
	 * Entfernt alle JComponents aus dem Fenster.
	 */
	public void removeAll() {
		layeredPane.removeAll();
		this.components = new LinkedList<JComponent>();
		this.repaint();
	}
}
