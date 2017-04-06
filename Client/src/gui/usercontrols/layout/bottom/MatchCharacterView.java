package gui.usercontrols.layout.bottom;

import gui.listeners.CharacterViewMouseAdapter;
import gui.usercontrols.knobs.CharacterView;

/**
 * Klasse fuer die MatchCharacter.
 * @author Christian Westhoff
 *
 */
public class MatchCharacterView extends CharacterView {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 3067975240683544631L;

	/**
	 * Konstruktor.
	 */
	public MatchCharacterView() {
		this.addMouseListener(new CharacterViewMouseAdapter());
	}
}
