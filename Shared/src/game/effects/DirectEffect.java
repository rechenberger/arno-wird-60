package game.effects;

import java.util.LinkedList;

import game.objects.GameObject;
import game.objects.NonStatic;

/**
 * direkte Effekte veraendern das betroffende NonStatic direkt und werden dann geloescht.
 * @author Tristan
 */
public class DirectEffect extends Effect {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -3515982615519065602L;
	
	/**
	 * Konstruktor.
	 * @param effects betroffendes NonStatic
	 */
	public DirectEffect(final NonStatic effects) {
		super(effects);
	}
	
	/**
	 *  Fuehrt alle direkten Effekte aus.
	 */
	public static void endAll() {
		LinkedList<DirectEffect> list = GameObject.getGameObjectsByClassName("DirectEffect");
		for (DirectEffect de : list) {
			de.end();		
		}
	}
	
	@Override
	public void ready() {
		super.ready();
		this.execute();
		this.end();
	}
	
	/**
	 * Fuehrt den direkten Effekt aus, falls noetig.
	 */
	public void execute() {
		// Normalerweise passiert hier garnichts.
	}
}
