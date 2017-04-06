package gui.lobby;

import java.awt.Graphics;
import java.awt.Graphics2D;

import module.ModuleHandler;
import game.content.heros.Hero;
import gui.Colors;
import gui.usercontrols.knobs.CharacterView;

/**
 * Zeichnet die Charaktere in der Lobby.
 * @author Sean
 *
 */
public class LobbyCharacterView extends CharacterView {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -2596961300701511978L;
	
	/**
	 * Speichert den Helden, damit der Listener darauf zugreifen kann.
	 */
	protected Hero hero;
	
	/**
	 * Zeichnet die CharacterView eines Helden.
	 * @param hero Held, dessen CharacterView gezeichnet werden soll
	 */
	public LobbyCharacterView(final Hero hero) {
		this.setOpaque(false);
		
		this.hero = hero;
		
		this.arc1Pos = (int) (Math.random() * 360);
		this.arc2Pos = (int) (Math.random() * 360);
	}
	
	/**
	 * @return Held.
	 */
	public Hero getHero() {
		return this.hero;
	}
	
	@Override
	protected java.awt.Image getHeroImage() {
		return ModuleHandler.GUI.getImageSet().getImage(PATH + hero.getImage());
	}
	
	@Override
	public void paintComponent(final Graphics g) {
		super.paintComponent(g);
		g2 = (Graphics2D) g;
		arc1Pos += 4;
        arc2Pos += 8;

        if (hero.equals(ModuleHandler.MATCH.getMyHero())) {
        	arcColor = Colors.CYAN;
        } else if (hero.getFraction().equals(ModuleHandler.MATCH.getMyHero().getFraction())) {
        	arcColor = Colors.GREEN1;
        } else {
        	arcColor = Colors.RED1;
        }
        
        if (hero.getPlayer() != null) {
			if (hero.getPlayer().isReadyToPlay()) {
				setArc(360);
			} else {
				setArc(270);
			}
		} else {
			setArc(0);
		}
        this.drawCharacterView();
	}
}
