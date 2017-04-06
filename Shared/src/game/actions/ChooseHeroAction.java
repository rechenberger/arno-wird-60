package game.actions;

import game.content.effects.direct.ChooseHeroEffect;
import game.content.heros.Hero;
import game.objects.Player;

import java.awt.Point;

import com.messages.Message;
import com.messages.MessageType;
/**
 * Aktion, um einen Helden zu waehlen.
 * @author Tristan
 *
 */
public class ChooseHeroAction extends Action {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -2473826622498588390L;
	
	/**
	 * Id des Spielers.
	 */
	private int playerId = 0;

	/**
	 * @param setPlayer Spieler
	 * @param setHero Held
	 */
	public ChooseHeroAction(final Player setPlayer, final Hero setHero) {
		super(setHero, null);
		if (setPlayer != null) {
			this.playerId = setPlayer.getId();
		}
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
		Hero hero = (Hero) this.getExecuter();
		return hero;
	}
	
	@Override
	public void execute() {
		if (this.getHero().getUserId() != 0) {
			matchModule.sendMessage(
					new Message(MessageType.USER_HERO_TAKEN), this.getPlayer().getUserId());
		} else {
			new ChooseHeroEffect(null, this.getPlayer().getHero()).ready();
			new ChooseHeroEffect(this.getPlayer(), this.getHero()).ready();
		}
		this.end();
	}

	@Override
	public Point getTargetPoint() {
		return null;
	}

	@Override
	public void executeSkill() {

	}

}
