package gui.usercontrols.modal;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.messages.Message;
import com.messages.MessageType;

import module.ModuleHandler;

/**
 * Das Modal, das beim Schliessen des Fensters aufgerufen wird.
 * @author Sean
 */
public class FrameCloseModal extends Modal {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -1340308461128411812L;

	/**
	 * Konstrukter, in dem die Daten des Modals gesetzt werden.
	 */
	public FrameCloseModal() {
		super("SPIEL BEENDEN", "Sind Sie sicher, dass Sie das Spiel beenden m\u00F6chten?");
		this.renameCancelButton("ABBRECHEN");
		this.addButton("BEENDEN", new MouseListener() {
			@Override
			public void mouseClicked(final MouseEvent e) {
				if (ModuleHandler.MATCH.isMatchRunning() && ModuleHandler.COM.isRunning()) {
					ModuleHandler.COM.pushMessage(new Message(MessageType.USER_LOGOFF));
				} else {
					System.exit(0);
				}
			}
			@Override
			public void mouseEntered(final MouseEvent e) { }
			@Override
			public void mouseExited(final MouseEvent e) { }
			@Override
			public void mousePressed(final MouseEvent e) { }
			@Override
			public void mouseReleased(final MouseEvent e) { }
		});
	}
}
