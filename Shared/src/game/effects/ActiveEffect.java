package game.effects;

import java.util.LinkedList;

import game.objects.GameObject;
import game.objects.NonStatic;

/**
 * Aktiver Effekt.
 * @author Tristan
 *
 */
public class ActiveEffect extends PermanentEffect {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -5791466055030673236L;
	
	/**
	 * Speichert wie lange der Aktive Effekt noch warten muss bis er ausgefuehrt werden kann.
	 */
	private int turnsToSleep = 0;
	
	/**
	 * Konstruktor.
	 * @param effects betroffenes NonStatic
	 * @see Effect
	 */
	public ActiveEffect(final NonStatic effects) {
		super(effects);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Fuehrt alle Aktiven Effekte aus deren sleepTimer<=0, indem es die execute() Methode aufruft.
	 * Wird einmal pro turn() ausgefuehrt.
	 */
	public static void executeAll() {
//		for (GameObject go : GameObject.allGameObjects.values()) {
		LinkedList<ActiveEffect> list = GameObject.getGameObjectsByClassName("ActiveEffect");
		for (ActiveEffect ae : list) {
			if (ae.turnsToSleep <= 0) {
				ae.execute();
			} else {
				ae.turnsToSleep--;
			}
		}
	}
	
	/**
	 * Fuehrt den aktiven Effekt aus.
	 */
	public void execute() {
		
	}
	
	/**
	 * Speichert wie lange der Aktive Effekt noch warten muss bis er ausgefuehrt werden kann.
	 * @param millisecond in Milisekunden
	 */
	protected void setSleepTimer(final int millisecond) {
		this.turnsToSleep = (int) (millisecond / matchModule.getSleepTime());
	}
}
