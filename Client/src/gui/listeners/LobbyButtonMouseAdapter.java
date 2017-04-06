package gui.listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import com.messages.UserReadyMessage;

import module.ModuleHandler;

/**
 * Listener, der in der Lobby auf den Buttons lauscht.
 * @author Alex
 *
 */
public class LobbyButtonMouseAdapter extends MouseAdapter {

	@Override
	public void mouseClicked(final MouseEvent arg0) {
		if (arg0.getComponent() instanceof JButton) {
			JButton button = (JButton) arg0.getComponent();
			if (button.getName().equals("ready")) {
				ModuleHandler.MATCH.sendMessage(new UserReadyMessage(ModuleHandler.MATCH.getMe().getId(), true));
			}
		} 
	}
}
