package gui.usercontrols.chat;

import com.messages.MessageType;

/**
 * Zeigt eine Box zum eingeben von Chatnachrichten an.
 * @author Sean
 *
 */
public class ChatGroupEnterBox extends ChatGlobalEnterBox {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -8524916712858246263L;
	
	/**
	 * Erstellt eine neue ChatGroupEnterBox.
	 */
	public ChatGroupEnterBox() {
		this.chatType = MessageType.GROUP_CHAT;
		this.textFieldText = "An Team senden";
	}
}
