package gui.usercontrols.knobs;

import game.attributes.Attribute;
import gui.Colors;
import gui.StringHelper;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import module.ModuleHandler;

/**
 * Stellt den Charakter und seine Erfahrungspunkt dar.
 * @author Sean
 */
public abstract class CharacterView extends JPanel {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1727715164246762150L;
	
	/**
	 * Pfad zu den Character Bildern.
	 */
	protected static final String PATH = "usercontrols/character/";
	
	/**
	 * Position in Grad des ersten umlaufenden Arcs.
	 */
	protected int arc1Pos = 0;
	
	/**
	 * Position in Grad des zweiten umlaufenden Arcs.
	 */
	protected int arc2Pos = 50;
	
	/**
	 * Groesse des Erfahrungsbogens.
	 */
	protected int arc;
	
	/**
	 * Farbe des Erfahrungsbogens.
	 */
	protected Color arcColor = Colors.CYAN;
	
	/**
	 * Zentraler Zugriff auf das Graphics Objekt.
	 */
	protected Graphics2D g2;

	/**
	 * Konstruktor.
	 */
	public CharacterView() {
		this.setOpaque(false);
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(191, 191);
	}
	
	@Override
	public Dimension getMinimumSize() {
		return new Dimension(191, 191);
	}
	
	@Override
	public Dimension getMaximumSize() {
		return new Dimension(191, 191);
	}
	
//	@Override
//	public int getWidth() {
//		return 191;
//	}
//	
//	@Override
//	public int getHeight() {
//		return 191;
//	}
	
	/**
	 * Zeichnet die CharacterView.
	 */
	public void drawCharacterView() {
		int x = 5;
		int y = 5;
		int width = this.getWidth() - x - 1;
		int height = this.getHeight() - y - 1;
		int ruler =  8;
		int gap = 1;
		int strokeSize = 1;
		int doubleStrokeSize = 2;
		int trippleStrokeSize = 3;
		int experienceStrokeSize = 6;
		
		BasicStroke stroke = new BasicStroke(strokeSize);
		BasicStroke doubleStroke = new BasicStroke(doubleStrokeSize);
		BasicStroke trippleStroke = new BasicStroke(trippleStrokeSize);
		BasicStroke experienceStroke = new BasicStroke(experienceStrokeSize);
		
		Color lightGrey = Colors.GREY8;
		Color grey = Colors.GREY6;
		Color arcColor = this.arcColor;
		
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		//Aeusserer Kreis
		x += ruler;
		y += ruler;
		g2.setColor(lightGrey);
		g2.setStroke(stroke);
		g2.drawOval(x, y, width - (2 * (x - 5)), height - (2 * (y  - 5)));
		
		//Hintergrund Erfahrung
		x += experienceStrokeSize;
		y += experienceStrokeSize;
		g2.setColor(grey);
		g2.setStroke(experienceStroke);
		g2.drawOval(x, y, width - (2 * (x - 5)), height - (2 * (y  - 5)));
		
		//Erfahrung
		g2.setColor(arcColor);
		g2.drawArc(x, y, width - (2 * (x - 5)), height - (2 * (y  - 5)), 90, -arc);
		
		//Innerer Kreis
		x += 4 * gap + doubleStrokeSize;
		y += 4 * gap + doubleStrokeSize;
		g2.setColor(lightGrey);
		g2.setStroke(stroke);
		g2.drawOval(x, y, width - (2 * (x - 5)), height - (2 * (y  - 5)));
		
		//Bild des Helden
		x += 5 * gap;
		y += 5 * gap;
		if (this.getHeroImage() != null) {
			g2.drawImage(this.getHeroImage(), x, y, null);
		}
		//Umrundende Arcs
		g2.setStroke(trippleStroke);
		g2.drawArc(5, 5, width, height, arc1Pos, 40);
		g2.setStroke(doubleStroke);
		g2.drawArc(5 + 4, 5 + 4, width - (2 * 4), height - (2 * 4), arc2Pos, 60);
	}
	
	/**
	 * Zeichnet das Level des Spielers.
	 */
	protected void drawLevel() {
		this.g2.setColor(Colors.CYAN);
		this.g2.setFont(this.g2.getFont().deriveFont(Font.BOLD));
		String text = "Level: " + ModuleHandler.MATCH.getMyHero().getLevel();
		Rectangle2D rect = StringHelper.stringSize(g2, text);
		StringHelper.drawString(g2, text, (this.getWidth() / 2), 
				(int) (this.getHeight() - 35 - rect.getHeight()), StringHelper.CENTER);
	}
	
	/**
	 * Zeichnet das Level des Spielers.
	 */
	protected void drawMoney() {
		this.g2.setColor(Colors.ORANGE);
		this.g2.setFont(this.g2.getFont().deriveFont(Font.BOLD));
		String text = "Geld: " + ModuleHandler.MATCH.getMyHero().getAttributeValue(Attribute.money);
		Rectangle2D rect = StringHelper.stringSize(g2, text);
		StringHelper.drawString(g2, text, (this.getWidth() / 2), 
				(int) (this.getHeight() - 50 - rect.getHeight()), StringHelper.CENTER);
	}
	
	@Override
	public void paintComponent(final Graphics g) {		
		super.paintComponent(g);
		g2 = (Graphics2D) g;
		arc1Pos += 4;
        arc2Pos += 8;
        arc = (int) (ModuleHandler.MATCH.getMyHero().getLevelProgress() * 360);
		this.drawCharacterView();
		this.drawLevel();
		this.drawMoney();
	}
	
	/**
	 * @param arc Setzt die Groesse des Erfahrungsbogens (0 - 360).
	 */
	public void setArc(final int arc) {
		this.arc = arc;
	}
	
	/**
	 * @return Bild des Helden.
	 */
	protected Image getHeroImage() {
		return ModuleHandler.GUI.getImageSet().getImage(PATH + ModuleHandler.MATCH.getMyHero().getImage());
	}
}
