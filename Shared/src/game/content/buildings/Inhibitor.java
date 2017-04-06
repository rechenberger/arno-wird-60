package game.content.buildings;

import java.awt.Point;

import com.messages.ChatMessage;

import game.content.effects.duration.Weak;
import game.content.ki.vasallen.Vasal;
import game.objects.Building;
import game.objects.GameObject;

/**
 * 
 * @author Marius
 *
 */
public class Inhibitor extends Building {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4575621305203234159L;
	
	
	/**
	 * Groesse des Gebaeudes.
	 */
	public static final Point SIZE = new Point(4, 4);

	/**
	 * Konstruktor.
	 */
	public Inhibitor() {
		this.name = "Inhibitor";
		this.size = SIZE;
		this.setImageURL("match", "inhibitor", "inhibitor");
		
	}

	
	/**
	 * Schwaecht die Vasalen des eigenen Teams.
	 */
	public void die() {
		super.die();
		
		for (GameObject v: getGameObjectsByClassName("Vasal")) {
			if (v instanceof Vasal && ((Vasal) v).getFraction() == getFraction()) {
				new Weak(((Vasal) v), Integer.MAX_VALUE).ready();
			}
		}
		
		String text = getFraction() + ": " + getName() + " wurde Zerst\u00F6rt, Vasalen sind nun schw\u00E4cher ";
		matchModule.sendMessage(new ChatMessage(text));
		
		
	}
}
