package gui;

import com.messages.MessageType;
import com.messages.UserDataMessage;

import module.ModuleHandler;

import gui.usercontrols.modal.Modal;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * Einfaches JFrame zum Login.
 * 
 * @author Melanie Kuntze
 * 
 */
public class Login extends JPanel {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -5663538353893905953L;
	/**
	 * Panel.
	 */
	private JPanel panel;
	/**
	 * Label Name.
	 */
	private JLabel labelName;
	/**
	 * Label Password.
	 */
	private JLabel labelPassword;
	/**
	 * Feld Benutzername.
	 */
	private JTextField usernameField;
	/**
	 * Feld Password.
	 */
	private JTextField passwordField;
	/**
	 * Login-Button.
	 */
	private JButton login;
	/**
	 * Register-Button.
	 */
	private JButton register;
	/**
	 * Benutzername.
	 */
	private String username;
	/**
	 * Passwort.
	 */
	private String password;
	/**
	 * info.
	 */
	private JLabel info;

	/**
	 * Konstruktor.
	 */
	public Login() {
		panel = new JPanel();
		panel.setOpaque(false);
		panel.setLayout(new GridLayout(9, 1));

		labelName = new JLabel("Benutzername");
		labelName.setForeground(Colors.WHITE);
		panel.add(labelName);

		usernameField = new JTextField(20);
		usernameField.setBackground(Colors.WHITE);
		usernameField.setForeground(Colors.GREY2);
		usernameField
				.setBorder(BorderFactory.createLineBorder(Colors.WHITE, 5));
		usernameField.setText("Schluppi");
		username = usernameField.getText();
		panel.add(usernameField);

		labelPassword = new JLabel("Passwort");
		labelPassword.setForeground(Colors.WHITE);
		panel.add(labelPassword);

		passwordField = new JPasswordField(20);
		passwordField.setBackground(Colors.WHITE);
		passwordField.setForeground(Colors.GREY2);
		passwordField
				.setBorder(BorderFactory.createLineBorder(Colors.WHITE, 5));
		password = passwordField.getText();
		passwordField.setText("Schluppi");
		panel.add(passwordField);

		panel.add(new JLabel(""));

		login = new JButton("Login");
		login.setBackground(Colors.ORANGE);
		login.setForeground(Colors.WHITE);
		login.setBorder(BorderFactory.createLineBorder(Colors.ORANGE, 5));
		login.addActionListener(new ClickListener());
		panel.add(login);

		panel.add(new JLabel(""));

		register = new JButton("Register");
		register.addActionListener(new ClickListener());
		register.setBackground(Colors.ORANGE);
		register.setForeground(Colors.WHITE);
		register.setBorder(BorderFactory.createLineBorder(Colors.ORANGE, 5));
		panel.add(register);

		info = new JLabel();
		panel.add(info);

		this.setBackground(Colors.BLACK_08);
		this.add(panel);
		this.setVisible(true);
	}

	/**
	 * Setzt die Buttons auf ENABLED(TRUE).
	 */
	public void resetLogin() {
		login.setEnabled(true);
		register.setEnabled(true);
	}
	
	/**
	 * Click Listener.
	 * 
	 * @author Christian Westhoff
	 * 
	 */
	public class ClickListener implements ActionListener {
		/**
		 * action Performed.
		 * 
		 * @param e
		 *            ActionEvent.
		 */
		public void actionPerformed(final ActionEvent e) {

			ModuleHandler.COM.notifyLogin();
			
			if (e.getSource() == login) {
				
				username = usernameField.getText();
				password = passwordField.getText();

				if (!username.equals("System")) {
					if (username.length() > 0 && password.length() > 0) {
						login.setEnabled(false);
						register.setEnabled(false);
						ModuleHandler.COM.pushMessage(new UserDataMessage(
								MessageType.USER_LOGIN, username, password));

					} else {
						new Modal("Ihrer ist zu kurz",
								"Ihr Benutzername bzw. Passwort muss mindestens ein Zeichen lang sein.")
								.show();
					}
				} else {
					new Modal("Unerlaubter Benutzername",
							"Diesen Namen d\u00fcrfen Sie nicht nutzen.")
							.show();
				}

			} else if (e.getSource() == register) {
				
				username = usernameField.getText();
				password = passwordField.getText();

				if (!username.equals("System")) {
					if (username.length() > 0 && password.length() > 0) {
						login.setEnabled(false);
						register.setEnabled(false);
						ModuleHandler.COM.pushMessage(new UserDataMessage(
								MessageType.USER_REGISTER, username, password));

						
					} else {
						new Modal("Ihrer ist zu kurz",
								"Ihr Benutzername bzw. Passwort muss mindestens ein Zeichen lang sein.")
								.show();
					}
				} else {
					new Modal("Unerlaubter Benutzername",
							"Diesen Namen d\u00fcrfen Sie nicht nutzen.")
							.show();
				}
			}
			

			
		}
	}
}
