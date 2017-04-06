package gui.usercontrols.chat;

import gui.Colors;

import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.messages.ChatMessage;
import com.messages.MessageType;

import module.ModuleHandler;

/**
 * Zeigt eine Box zum eingeben von Chatnachrichten an.
 * @author Sean
 *
 */
public class ChatGlobalEnterBox extends JPanel {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -8524916712858246263L;
	
	/**
	 * Speichert das Textfeld zum Zugriff aus dem KeyListener.
	 */
	JTextField text;
	
	/**
	 * Speichert den MessageTyp, der versand werden soll.
	 */
	protected MessageType chatType = MessageType.GLOBAL_CHAT;
	
	
	/**
	 * Speichert den Text des TextFields, das angezeigt wird.
	 */
	protected String textFieldText = "An Alle senden";
	
	/**
	 * Erstellt eine neue ChatEnterBox.
	 */
	public ChatGlobalEnterBox() {
		
	}
	
	/**
	 * Initialisiert die ChatEnterBox.
	 * @return Gibt sich selbst zurueck um es zu einem Panel hinzufuegen zu koennen.
	 */
	public Component init() {
		this.setOpaque(false);
		text = new JTextField(this.textFieldText, 20);
		text.addKeyListener(new ChatEnterBoxKeyListener());
		text.addFocusListener(new ChatEnterBoxFocusListener());
		text.setBackground(Colors.WHITE);
		text.setForeground(Colors.GREY2);
		text.setBorder(BorderFactory.createLineBorder(Colors.WHITE, 3));
		
		this.add(text);
		this.setVisible(true);
		
		return this;
	}
	
	/**
	 * Horcht im Textfeld auf das Druecken von Enter und sendet dann die Nachricht.
	 * Danach wird der Focus auf das ContentPane gelegt um die KeyBindings wieder zu aktivieren.
	 * @author Sean
	 *
	 */
	class ChatEnterBoxKeyListener extends KeyAdapter {

		@Override
		public void keyPressed(final KeyEvent arg0) {
			if (arg0.getKeyCode() == 10) {
				//Enter
				JTextField text = (JTextField) arg0.getComponent();
				ModuleHandler.COM.pushMessage(new ChatMessage(chatType, ModuleHandler.MATCH.getMe().getName(),
						text.getText()));
				text.setText(textFieldText);
				ModuleHandler.GUI.getContentPane().requestFocus();
			}
		}		
	}
	
	/**
	 * Setzt den Text des Feldes, wenn das Feld den Focus verliert bzw. entfernt den
	 * Inhalt des Feldes, wenn das Feld den Focus bekommt.
	 * @author Sean
	 *
	 */
	class ChatEnterBoxFocusListener implements FocusListener {

		@Override
		public void focusGained(final FocusEvent arg0) {
			JTextField text = (JTextField) arg0.getComponent();
			text.setText("");
		}

		@Override
		public void focusLost(final FocusEvent arg0) {
			JTextField text = (JTextField) arg0.getComponent();
			text.setText(textFieldText);
		}
		
	}
}
