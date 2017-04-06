package gui.lobby;

import game.content.heros.Hero;
import game.objects.Fraction;
import gui.Colors;
import gui.listeners.LobbyButtonMouseAdapter;
import gui.listeners.LobbyMouseAdapter;
import gui.usercontrols.knobs.CharacterView;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.util.LinkedList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import settings.GlobalSettings;

import module.ModuleHandler;

/**
 * Zeichnet die Lobby.
 * @author Sean
 *
 */
public class Lobby extends JPanel {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -1140840645271352897L;
	
	/**
	 * Speichert die playerLabels um sie spaeter neu zeichnen zu koennen.
	 */
	private LinkedList<LobbyLabel> playerLabels = new LinkedList<LobbyLabel>();
	
	/**
	 * Speichert das Label, dass die Zeit anzeigt um sie spaeter neu zeichnen zu koennen.
	 */
	private JLabel time = new JLabel();
	
	/**
	 * Zeichnet die Lobby.
	 */
	public Lobby() {
		JPanel teamA = new JPanel();
		JPanel teamB = new JPanel();
		JPanel timePanel = new JPanel();
		
		teamA.setOpaque(false);
		teamB.setOpaque(false);
		timePanel.setOpaque(false);
		
		time.setForeground(Colors.WHITE);
		timePanel.add(time);

		
		for (Hero hero : Hero.getAllHerosSorted()) {
			JPanel heroView = new JPanel();
			LobbyLabel playerLabel;
			JLabel heroLabel = new JLabel(hero.getName(), JLabel.CENTER);
			if (hero.getPlayer() != null) {
				playerLabel = new LobbyLabel(hero.getPlayer().getName(), JLabel.CENTER, hero);
			} else {
				playerLabel = new LobbyLabel(" ", JLabel.CENTER, hero);
			}
			CharacterView charView = new LobbyCharacterView(hero);
			
			playerLabel.setForeground(Colors.WHITE);
			heroLabel.setForeground(Colors.WHITE);
			playerLabels.add(playerLabel);
			
			heroView.setLayout(new BorderLayout());
			
			heroView.add(charView, BorderLayout.PAGE_START);
			heroView.add(heroLabel, BorderLayout.CENTER);
			heroView.add(playerLabel, BorderLayout.PAGE_END);
			heroView.setOpaque(false);
			
			charView.addMouseListener(new LobbyMouseAdapter());
			
			if (hero.getFraction() == Fraction.TeamA) {
				teamA.add(heroView);
			} else if (hero.getFraction() == Fraction.TeamB) {
				teamB.add(heroView);
			}
		}
		
		JButton ready = new JButton();
		ready.setName("ready");
		ready.setText("Bereit");
		ready.addMouseListener(new LobbyButtonMouseAdapter());
		timePanel.add(ready);
		

		JLabel labelA = new JLabel("Team A");
		labelA.setAlignmentX(CENTER_ALIGNMENT);
		labelA.setFont(getFont().deriveFont(32f));
		labelA.setForeground(Colors.WHITE);
		this.add(labelA);
		this.add(teamA);
		JLabel labelB = new JLabel("Team B");
		labelB.setAlignmentX(CENTER_ALIGNMENT);
		labelB.setFont(getFont().deriveFont(32f));
		labelB.setForeground(Colors.WHITE);
		this.add(labelB);
		this.add(teamB);
		this.add(timePanel);
		this.setBackground(Colors.BLACK);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}
	
	@Override
	public void paintComponent(final Graphics g) {
		super.paintComponent(g);
	
		if (ModuleHandler.MATCH.getMatch().getStartingIn() == GlobalSettings.MATCH_LOBBY_TIME) {
			time.setText("Warte auf alle Spieler");
		} else {
			time.setText("Spiel startet in: " + (ModuleHandler.MATCH.getMatch().getStartingIn() / 1000) + " Sekunden");
		}
	}
}
