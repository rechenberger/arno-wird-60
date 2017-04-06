package game.content.buildings;

import game.content.items.Angriffskraft;
import game.content.items.Bewegungsgeschwindigkeit;
import game.content.items.Lebenspunkte;
import game.content.items.Lebensregeneration;
import game.objects.Item;
import java.awt.Point;
import java.util.LinkedList;

/**
 * Shop n Shop.
 * @author Tristan
 *
 */
public class Shop extends game.objects.Building {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 6314362891648929627L;
	
	/**
	 * Liste der zu verkaufenden Items.
	 */
	private LinkedList<Item> items = new LinkedList<Item>();

	/**
	 * Groesse des Gebaeudes.
	 */
	public static final Point SIZE = new Point(4, 4);
	
	/**
	 * Konstruktor. Plaziert die Items im Regal.
	 */
	public Shop() {
		this.items.add(new game.content.items.Ungerbutz());
		this.items.add(new game.content.items.Soeckchen());
		this.items.add(new game.content.items.useable.HealthPotion());
		this.items.add(new game.content.items.useable.ManaPotion());
		this.items.add(new Lebenspunkte());
		this.items.add(new Lebensregeneration());
		this.items.add(new Angriffskraft());
		this.items.add(new Bewegungsgeschwindigkeit());
		
		this.size = SIZE;
		this.setImageURL("match", "shop", "shop");
	}
	
	/**
	 * @return  zu verkaufende Items.
	 */
	public LinkedList<Item> getItems() {
		return this.items;
	}
	
}
