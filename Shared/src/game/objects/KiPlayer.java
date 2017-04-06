package game.objects;

import game.content.ki.hero.HeroKi;

/**
 * Ein Ki Spieler.
 * @author Marius
 *
 */
public class KiPlayer extends Player {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5839430383940716711L;
	/**
	 * Die Number der KiHelden.
	 */
	private static int numberofKiPlayers = 0;
	
	/**
	 * Die Klasse die den Graphomaten verwaltet.
	 */
	private HeroKi ki;
	
	/**
	 * Konstruktor.
	 */
	public KiPlayer() {
		super(0, "E.V.E (" + numberofKiPlayers++ + ")");
		this.setReadyToPlay(true);
	}

	/**
	 * @return the ki
	 */
	public HeroKi getKi() {
		return ki;
	}

	/**
	 * @param ki the ki to set
	 */
	public void setKi(final HeroKi ki) {
		this.ki = ki;
	}
	
	@Override
	public void afterRecieve()  {
		this.setReadyToPlay(true);
		super.afterRecieve();
	}

}
