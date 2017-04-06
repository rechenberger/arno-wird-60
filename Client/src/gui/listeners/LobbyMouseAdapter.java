package gui.listeners;

import game.actions.ChooseHeroAction;
import game.content.heros.Hero;
import game.objects.KiPlayer;
import game.objects.Player;
import gui.lobby.Lobby;
import gui.lobby.LobbyCharacterView;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.messages.NewKiMessage;

import module.ModuleHandler;

/**
 * Listener, der in der Lobby auf den CharacterViews lauscht.
 * 
 * @author Sean
 * 
 */
public class LobbyMouseAdapter extends MouseAdapter {

	@Override
	public void mouseClicked(final MouseEvent arg0) {
		
		Hero hero = ((LobbyCharacterView) arg0.getComponent()).getHero();
		Player player = hero.getPlayer();
		
		if (arg0.getButton() == MouseEvent.BUTTON1) {
			if (player == null) {
				new ChooseHeroAction(ModuleHandler.MATCH.getMe(), hero).plan();
				ModuleHandler.GUI.getPanel(Lobby.class).repaint();
			}
		} else if (arg0.getButton() == MouseEvent.BUTTON3) {
			if (player != null && !(player instanceof KiPlayer)) {
				System.err.println("Held bereits von " + player.getName() + " belegt.");
				return;
			} else {
				ModuleHandler.COM.pushMessage(new NewKiMessage(((LobbyCharacterView) arg0.getComponent()).getHero(), (player == null)));
			}
			
		}
	}
	
}
