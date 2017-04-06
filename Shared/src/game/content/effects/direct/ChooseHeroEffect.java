package game.content.effects.direct;

import game.content.heros.Hero;
import game.objects.Player;

/**
 * Effekt um Held einem Speiler zuzuteilen.
 * @author Tristan
 *
 */
public class ChooseHeroEffect extends game.effects.DirectEffect {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 8269687787590626200L;
	
	/**
	 * Id des Spielers.
	 */
	private int playerId = 0;

	/**
	 * 
	 * @param hero Held
	 * @param player Spieler
	 */
	public ChooseHeroEffect(final Player player, final Hero hero) {
		super(hero);
		if (player != null) {
			this.playerId = player.getId();
		}
	}
	
	@Override
	public void execute() {
//		System.out.println(this.getPlayer() + " -> " + this.getHero());
		this.getHero().setPlayer(this.getPlayer());
	}
	
	/**
	 * @return Spieler
	 */
	public Player getPlayer() {
		Player player = Player.getById(playerId);
		return player;
	}
	
	/**
	 * @return Held.
	 */
	public Hero getHero() {
		Hero hero = (Hero) this.getEffects();
		return hero;
	}
}
