package game.effects;

import game.objects.NonStatic;

/**
 * permanente Effekte veraendern NonStatics ueber eine bestimmte, definierte Zeitspanne.
 * @author Tristan
 */
public class DurationEffect extends PermanentEffect {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -6219689037707328812L;

	/**
	 * Konstruktor.
	 * Speichert auf die Id des betroffenen NonStatics.
	 * @param effects betroffenes NonStatics.
	 * @param millisecounds Wie lange der Effekt wirken wird.
	 */
	public DurationEffect(final NonStatic effects, final int millisecounds) {
		super(effects);
		this.autoEnd = true;
		this.setExistenceTimer(millisecounds);
	}

}
