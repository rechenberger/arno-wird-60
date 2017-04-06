package gui.usercontrols.layout.bottom;

import game.content.statics.BaseGround;
import game.content.statics.Bridge;
import game.content.statics.Gras;
import game.content.statics.Path;
import game.content.statics.Tree;
import game.content.statics.Water;
import game.objects.Building;
import game.objects.Map;
import game.objects.NonStatic;
import game.objects.Static;
import game.objects.WorldObject;
import gui.Colors;
import gui.listeners.MiniMapMouseAdapter;
import gui.match.MatchPanel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import settings.GlobalSettings;

import module.ModuleHandler;

/**
 * Panel, das eine MiniMap darstellt.
 * 
 * @author Alex, Tristan, Christian
 * 
 */
public class MiniMap extends JPanel {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 834221657422188554L;

	/**
	 * Bild der MiniMap, das gezeichnet wird.
	 */
	private BufferedImage miniMap;

	/**
	 * Speichert den Hintergrund in einem Array.
	 */
	private int[][] background;

	/**
	 * Zaehlt die Runden.
	 */
	private int turn = 99;

	/**
	 * Konstruktor.
	 */
	public MiniMap() {
		this.setBackground(Colors.BLACK_04);
		Dimension size = new Dimension(290, 290);
		this.setPreferredSize(size);
		this.setMinimumSize(size);
		this.setOpaque(true);
		this.setBackground();
		miniMap = new BufferedImage((int) Map.DIMENSION.getX(),
				(int) Map.DIMENSION.getY(), BufferedImage.TYPE_INT_RGB);
		this.addMouseListener(new MiniMapMouseAdapter());
		this.setVisible(true);
	}

	@Override
	public void paintComponent(final Graphics g) {
		super.paintComponent(g);


		if (turn++ % GlobalSettings.GUI_MINI_MAP_REFRESH_RATE == 0) {
			 this.update();
		}

		// MiniMap drehen
		// Transformation erstellen
		AffineTransform at = new AffineTransform();
		// Drehpunkt festsetzen: Mittelpunkt des Bildes
		at.translate(getWidth() / 2, getHeight() / 2);
		// Um wie viel Grad soll gedreht werden? 225 Grad
		at.rotate(Math.toRadians(45.0));
		at.translate(-miniMap.getWidth() / 2, -miniMap.getHeight() / 2);

		// Zeichnen
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(miniMap, at, null);

		Point centerCoordPixel = ModuleHandler.GUI.getPanel(MatchPanel.class)
				.getCenterCoord();
		centerCoordPixel = calcCoordToPixel(centerCoordPixel);
		Point rectSize = ModuleHandler.GUI.getPanel(MatchPanel.class)
				.getCoordSize();
		rectSize = new Point((int) (rectSize.x * Math.sqrt(2)),
				(int) (rectSize.y * Math.sqrt(2)));

		g2d.setColor(Colors.CYAN);
		g2d.drawRect(centerCoordPixel.x - rectSize.x / 2, centerCoordPixel.y
				- rectSize.y / 2, rectSize.x, rectSize.y);

	}

	/**
	 * setzt den Background ins Array.
	 */
	private void setBackground() {
		background = new int[(int) Map.DIMENSION.getX()][(int) Map.DIMENSION
				.getY()];

		for (int x = 0; x < (int) Map.DIMENSION.getX(); x++) {
			for (int y = 0; y < (int) Map.DIMENSION.getY(); y++) {
				int index = ModuleHandler.MATCH.getMatch().getMap()
						.getList(x, y).size();
				WorldObject wo;
				do {
					index--;
					wo = ModuleHandler.MATCH.getMatch().getMap().getList(x, y)
							.get(index);
				} while (wo instanceof NonStatic);

				if (wo instanceof Static) {
					if (wo instanceof BaseGround) {
						background[x][y] = 0;
					} else if (wo instanceof Bridge) {
						background[x][y] = 1;
					} else if (wo instanceof Gras) {
						background[x][y] = 2;
					} else if (wo instanceof Tree) {
						background[x][y] = 3;
					} else if (wo instanceof Water) {
						background[x][y] = 4;
					} else if (wo instanceof Path) {
						background[x][y] = 5;
					}
				}
			}
		}
	}

	/**
	 * Updatet die NonStatics auf der Karte.
	 */
	private void update() {
		Graphics g = miniMap.getGraphics();
		boolean drawFog = ModuleHandler.GUI.ifDrawFog();
		for (int x = 0; x < (int) Map.DIMENSION.getX(); x++) {
			for (int y = 0; y < (int) Map.DIMENSION.getY(); y++) {

				WorldObject wo = ModuleHandler.MATCH.getMatch().getMap()
						.getList(x, y).getLast();
				
				boolean inViewRange = ModuleHandler.GUI.getPanel(MatchPanel.class)
						.getPointsInViewRange().contains(new Point(x, y));
				
				boolean done = false;
				
				if (wo instanceof NonStatic) {
					NonStatic ns = (NonStatic) wo;
					if (ns.getFraction() == ModuleHandler.MATCH.getMyHero()
							.getFraction()) {
						g.setColor(Colors.BLUE);
						done = true;
						
					} else if (inViewRange || ns instanceof Building) {
						g.setColor(Colors.RED2);
						done = true;
						
					}
				}
				if (!done) {
					if (background[x][y] == 0) {
						g.setColor(Colors.BROWN1);
					} else if (background[x][y] == 1) {
						g.setColor(Colors.GREY1);
					} else if (background[x][y] == 2) {
						g.setColor(Colors.GREEN1);
					} else if (background[x][y] == 3) {
						g.setColor(Colors.GREEN2);
					} else if (background[x][y] == 4) {
						g.setColor(Colors.CYAN);
					} else if (background[x][y] == 5) {
						g.setColor(Colors.BROWN2);
					}
				}

				if (drawFog && !inViewRange) {
					g.setColor(g.getColor().darker().darker());
				}

				g.drawRect(x, /* (int) Map.DIMENSION.getY() - */ y, 1, 1);
			}
		}
	}

	/**
	 * Berechnet aus einer Pixelkoordinate die entsprechende Kartenkoordinate.
	 * 
	 * @param pixelPoint
	 *            Pixelkoordinate
	 * @return Kartenkoordinate
	 */
	public Point inverseCalcCoordToPixel(final Point pixelPoint) {

		float tileWidth = (float) Math.sqrt(2);
		float tileHeight = (float) Math.sqrt(2);

		Point centerCoord = new Point(Map.DIMENSION.x / 2, Map.DIMENSION.y / 2);
		Point centerPixel = new Point(this.getWidth() / 2, this.getHeight() / 2);

		float a = -(tileWidth / 2);
		float b = +(tileWidth / 2);
		float c = -(tileHeight / 2);
		float d = -(tileHeight / 2);

		float determinante = (float) a * d - b * c;

		float pixelX1x = d / determinante;
		float pixelX1y = -c / determinante;
		float pixelY1x = -b / determinante;
		float pixelY1y = a / determinante;

		int coordX = (int) (((float) centerCoord.x)
				+ (pixelX1x * (((float) centerPixel.x) - ((float) pixelPoint.x))) + (pixelY1x * (((float) centerPixel.y) - ((float) pixelPoint.y))));
		int coordY = (int) (((float) centerCoord.y)
				+ (pixelX1y * (((float) centerPixel.x) - ((float) pixelPoint.x))) + (pixelY1y * (((float) centerPixel.y) - ((float) pixelPoint.y))));
		return new Point(coordX, coordY);
	}

	/**
	 * Berechnet zu einer Kartenkoordinaten die entsprechende Pixelkoordiate.
	 * 
	 * @param coord
	 *            Kartenkoordinate
	 * @return Pixelkoordinate
	 */
	public Point calcCoordToPixel(final Point coord) {

		float tileWidth = (float) Math.sqrt(2);
		float tileHeight = (float) Math.sqrt(2);

		Point centerCoord = new Point(Map.DIMENSION.x / 2, Map.DIMENSION.y / 2);
		Point centerPixel = new Point(this.getWidth() / 2, this.getHeight() / 2);

		float cX1x = -(tileWidth / 2);
		float cX1y = -(tileHeight / 2);
		float cY1x = +(tileWidth / 2);
		float cY1y = -(tileHeight / 2);

		int pixelPointx = (int) (centerPixel.x
				+ (cX1x * (centerCoord.x - coord.x)) + (cY1x * (centerCoord.y - coord.y)));
		int pixelPointy = (int) (centerPixel.y
				+ (cX1y * (centerCoord.x - coord.x)) + (cY1y * (centerCoord.y - coord.y)));

		Point pixelPoint = new Point(pixelPointx, pixelPointy);

		return pixelPoint;
	}
}
