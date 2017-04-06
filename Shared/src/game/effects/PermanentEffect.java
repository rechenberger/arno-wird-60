package game.effects;

import java.util.LinkedList;

import game.objects.GameObject;
import game.objects.NonStatic;

/**
 * permanente Effekte veraendern NonStatics ueber eine Zeitspanne.
 * @author Tristan
 */
public class PermanentEffect extends Effect {

	/**
	 * serialVersionUID.
	 * @author Tristan
	 */
	private static final long serialVersionUID = -76338083877306945L;

	/**
	 * Runden die dieser Effekt noch existiert.
	 */
	protected int turnsToExist = 0;
	
	/**
	 * Zeitpunkt zu dem der Effekt startet.
	 */
	protected long timeStart;
	
	/**
	 * Zeitpunkt zu dem der Effekte beendet wird.
	 */
	protected long timeEnd;
	
	/**
	 * Ob Effekt automatisch nach timeEnd endet.
	 */
	protected boolean autoEnd = false;
	
	/**
	 * Konstruktor.
	 * Speichert auf die Id des betroffenen NonStatics.
	 * Fuegt dem betroffenem NonStatic den Effekt zu.
	 * @param effects betroffenes NonStatics.
	 */
	public PermanentEffect(final NonStatic effects) {
		super(effects);
	}
	

	/**
	 * Speichert wie lange der permanente Effekt noch wirkt.
	 * Setzt autoEnd auf wahr.
	 * @param millisecond in Milisekunden
	 */
	protected void setExistenceTimer(final int millisecond) {
		this.autoEnd = true;
		this.timeStart = matchModule.getTime();
		this.timeEnd = this.timeStart + millisecond;
		this.turnsToExist = (int) (millisecond / matchModule.getSleepTime());
	}
	

	/**
	 * Fuehrt alle Aktiven Effekte aus deren sleepTimer<=0, indem es die execute() Methode aufruft.
	 * Wird einmal pro turn() ausgefuehrt.
	 */
	public static void updateAllTimer() {
//		for (GameObject go : GameObject.allGameObjects.values()) {
		LinkedList<PermanentEffect> list = GameObject.getGameObjectsByClassName("PermanentEffect");
		for (PermanentEffect pe : list) {
			if (pe.autoEnd) {
				if (pe.getTimeDurationLeft() <= 0) {
					pe.end();
				}
//				
//				if (pe.turnsToExist <= 0) {
//					pe.end();
//				} else {
//					pe.turnsToExist--;
//				}
			}
		}
	}
	
	/**
	 * 
	 * @return Ob Effekt automatisch nach timeEnd endet.
	 */
	public boolean isAutoEnding() {
		return this.autoEnd;
	}
	
	/**
	 * @return Millisekunden in denen der Effekt endet.
	 */
	public long getTimeDurationLeft() {
		return this.timeEnd - matchModule.getTime();
	}

	/**
	 * @return Milliseknden die Effekt insgesamt anhaelt.
	 */
	public float getTimeDuration() {
		return this.timeEnd - this.timeStart;
	}
	
	/**
	 * @return getTimeDurationLeft / getTimeDuration
	 */
	public float getTimeDurationLeftRatio() {
		if (!this.autoEnd || this.getTimeDuration() == 0) {
			return 1;
		} else {
			float timeDurationRatio =  ((float) this.getTimeDurationLeft())  / ((float) this.getTimeDuration());
			if (timeDurationRatio > 1) {
				timeDurationRatio = 1;
			} else if (timeDurationRatio < 0) {
				timeDurationRatio = 0;
			}
			return timeDurationRatio;
		}
	}
	


}
