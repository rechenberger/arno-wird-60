package gui.usercontrols.layout.left;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import game.attributes.Attribute;
import game.content.heros.Hero;
import game.effects.Effect;
import game.objects.NonStatic;
import gui.Colors;

import javax.swing.JPanel;

import module.ModuleHandler;

import settings.GlobalSettings;
/**
 * Zeigt Informationen ueber ein angeklickes NonStatic an.
 * @author Alex
 *
 */
public class NonStaticInfoPanel extends JPanel {
	
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = -5161498229716507477L;
	/**
	 * NonStatic ueber den Informationen angezeigt werden.
	 */
	private NonStatic ns;
	
	/**
	 * Konstruktor.
	 */
	public NonStaticInfoPanel() {
		this.setBackground(Colors.BLACK_08);
		Dimension size; 
		
		if (GlobalSettings.GUI_SHOW_POS_IN_NONSTATIC_PANEL) {
			size = new Dimension(200, 165);
		} else {
			size = new Dimension(200, 150);	
		}
		
		
		this.setPreferredSize(size);
		this.setMinimumSize(size);
		this.setMaximumSize(size);
		this.setOpaque(true);
	}
	
	@Override
	public void paintComponent(final Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		super.paintComponent(g);
		
		g2.setColor(Colors.WHITE);
		
		if (ns != null) {
//			this.setOpaque(true);
				g2.drawString(" Schaden: " + ns.getAttributeValue(Attribute.damage), 10, 20);
				g2.drawString(" Angriffsgeschwindigkeit: " + ns.getAttributeValue(Attribute.fightingSpeed), 10, 35);
				g2.drawString(" Reichweite: " + ns.getAttributeValue(Attribute.fightingRange), 10, 50);
				g2.drawString(" Bewegungsgeschwindigkeit: " + ns.getAttributeValue(Attribute.movementSpeed), 10, 65);
				g2.drawString(" Sichtweite: " + ns.getAttributeValue(Attribute.viewRange), 10, 80);
				
				String tmp = "Effekte: ";
				for (Effect effect : ModuleHandler.MATCH.getMyHero().getEffects()) {
					if (!(effect instanceof game.effects.ItemEffect) && !(effect instanceof game.effects.CooldownEffect)) {
						tmp += effect.getName() + ", ";
					}
				}
				g2.drawString(tmp, 10, 155);
			if (ns instanceof Hero) {
				Hero hero = (Hero) ns;
				g2.drawString(" Level: " + hero.getLevel(), 10, 95);
				g2.drawString(" Held: " + hero.getName(), 10, 110);
				if (hero.getPlayer() != null) {
					g2.drawString(" Spieler: " + hero.getPlayer().getName(), 10, 125);
				}
				if (GlobalSettings.GUI_SHOW_POS_IN_NONSTATIC_PANEL) {
					g2.drawString(" Position: " + hero.getCoord(), 10, 140);
					for (Effect e : hero.getEffects()) {
						System.out.println(e.toString());
						System.out.println(e.getName());
					}
				}
			}
		} else {
			this.setOpaque(false);
		}

	}
	
	/**
	 * SetterMethode fuer den NonStatic.
	 * @param ns Anzuzeigendes NonStatic
	 */
	public void setNonStatic(final NonStatic ns) {
		this.ns = ns;
	}

}
