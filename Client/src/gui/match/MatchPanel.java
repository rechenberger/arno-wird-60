package gui.match;

import game.attributes.Attribute;
import game.objects.NonStatic;
import game.objects.WorldObject;
import game.skills.Skill;
import gui.Colors;
import gui.listeners.MatchPanelMouseAdapter;
import gui.listeners.MatchPanelMouseWheelListener;
import gui.listeners.actions.SkillAction;
import gui.listeners.actions.FogToggleCheatAction;
import gui.listeners.actions.MoveMapCenterPoint;
import gui.listeners.actions.ShowHideStatisticAction;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.LinkedList;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import settings.GlobalSettings;

import module.ModuleHandler;

/**
 * Zeichnet das Match.
 * @author Christian Westhoff, Sean Dieterle
 *
 */
public class MatchPanel extends JPanel {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -8727349226676128015L;

	/**
	 * Gibt die Groesse der Darstellung der Karte an. Je geringer desto mehr
	 * wird angezeigt.
	 */
	private float zoom = 0.5f;

	/**
	 * Breite eines einzelnen Tiles.
	 */
	private int tileWidth;

	/**
	 * Hoehe eines einzelnen Tiles.
	 */
	private int tileHeight;

	/**
	 * Speichert den Mittelpunkt des Bildausschnittes als Pixel.
	 */
	private Point centerPixel;

	/**
	 * Speichert den Mittelpunkt des Bildausschnittes als Kartenkoordinate.
	 */
	private Point centerCoord;

	/**
	 * Speichert das gezeichnete MatchPanel.
	 */


	private boolean followMyHero;

	/**
	 * Konstruktor setzt das MatchPanel auf visible.
	 * 
	 */
	public MatchPanel() {
		super();
		this.setBackground(Colors.BLACK);

		// MausListener
		this.addMouseListener(new MatchPanelMouseAdapter());
		this.addMouseWheelListener(new MatchPanelMouseWheelListener());

		// KeyBindings zur Tastensteuerung
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(' '), "Leertaste");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke("UP"), "Hoch");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke("DOWN"), "Runter");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke("LEFT"), "Links");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke("RIGHT"), "Rechts");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke('z'), "Zoom");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke('h'), "HealthCheat");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke('m'), "MoneyCheat");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke('e'), "ExperienceCheat");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke('t'), "TeleportCheat");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke('f'), "FogCheat");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke('s'), "InGameStatistic");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke('1'), "Skill1");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke('2'), "Skill2");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke('3'), "Skill3");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke('4'), "Skill4");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke('5'), "Skill5");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke('6'), "Skill6");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke('7'), "Skill7");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke('8'), "Skill8");
		this.getActionMap().put("Leertaste", new MoveMapCenterPoint(32));
		this.getActionMap().put("Hoch", new MoveMapCenterPoint(38));
		this.getActionMap().put("Runter", new MoveMapCenterPoint(40));
		this.getActionMap().put("Links", new MoveMapCenterPoint(37));
		this.getActionMap().put("Rechts", new MoveMapCenterPoint(39));
		this.getActionMap().put("Zoom", new MoveMapCenterPoint(90));
		this.getActionMap().put("HealthCheat", new SkillAction(game.content.skills.cheats.HealthCheat.getInstance()));
		this.getActionMap().put("MoneyCheat", new SkillAction(game.content.skills.cheats.MoneyCheat.getInstance()));
		this.getActionMap().put("ExperienceCheat", new SkillAction(game.content.skills.cheats.ExperienceCheat.getInstance()));
		this.getActionMap().put("TeleportCheat", new SkillAction(game.content.skills.cheats.Teleport.getInstance()));
		LinkedList<Skill> skills = ModuleHandler.MATCH.getMyHero().getSpecialSkills();
		this.getActionMap().put("Skill1", new SkillAction(skills.get(0)));
		this.getActionMap().put("Skill2", new SkillAction(skills.get(1)));
		this.getActionMap().put("Skill3", new SkillAction(skills.get(2)));
//		this.getActionMap().put("Skill4", new SkillAction(skills.get(3)));
//		this.getActionMap().put("Skill5", new SkillAction(skills.get(4)));
//		this.getActionMap().put("Skill6", new SkillAction(skills.get(5)));
//		this.getActionMap().put("Skill7", new SkillAction(skills.get(6)));
//		this.getActionMap().put("Skill8", new SkillAction(skills.get(7)));
		this.getActionMap().put("FogCheat", new FogToggleCheatAction());
		this.getActionMap().put("InGameStatistic", new ShowHideStatisticAction());

		this.setVisible(true);
		this.setOpaque(true);

		ModuleHandler.GUI.getImageSet().update(zoom);
		
		this.tileWidth = ModuleHandler.GUI.getImageSet().getTileWidth();
		this.tileHeight = ModuleHandler.GUI.getImageSet().getTileHeight();
	
		this.followMyHero = true;
	}

	/**
	 * Setzt den Zoom. Jedoch nur, wenn dieser groesser als 0 ist, da es sonst
	 * Fehler geben kann.
	 * 
	 * @param zoom
	 *            Zoomfaktor
	 */
	public void setZoom(final float zoom) {
		if (zoom > 0.01 && zoom <= 2) {
			this.zoom = zoom;
			ModuleHandler.GUI.getImageSet().update(zoom);
			this.tileWidth = ModuleHandler.GUI.getImageSet().getTileWidth();
			this.tileHeight = ModuleHandler.GUI.getImageSet().getTileHeight();
		}
	}
	
	/**
	 * Setzt, ob dem Spieler gefolgt wird.
	 * @param followMyHero Status
	 */
	public void setFollowMyHero(final boolean followMyHero) {
		this.followMyHero = followMyHero;
	}
	
	/**
	 * Veraendert den Zoom um den angegebenen Wert.
	 * 
	 * @param translation
	 *            Veraenderung des Zooms
	 */
	public void translateZoom(final float translation) {
		this.setZoom(this.zoom + translation);
	}	

	/**
	 * Der Backbuffer. Hier wird der Boden eingezeichnet.
	 */
	private BufferedImage backbuffer;
	/**
	 * Zeichnet den Hintergrund.
	 */
	private void drawBackground() {
		
		this.backbuffer = new BufferedImage(this.getWidth(), this.getHeight(),
				BufferedImage.TYPE_INT_ARGB); 
		
		LinkedList<WorldObject> worldObjects = 
				ModuleHandler.MATCH.getMatch().getMap().getFloorInSquare(this.centerCoord, this.calcRadius());
		
		Graphics g = this.backbuffer.getGraphics();
		
		boolean drawFog = ModuleHandler.GUI.ifDrawFog();
		
		for (WorldObject wo: worldObjects) {
			
			Image curImage = ModuleHandler.GUI.getImageSet().getImage(wo.getImageURL());
			
			Point curPosition = this.getCorrectPosition(curImage, this.calcCoordToPixel(wo.getCoord()));
			
			g.drawImage(curImage, curPosition.x,
					curPosition.y, null);
			
			if (!this.pointsInViewRange.contains(wo.getCoord()) && drawFog) {
				g.drawImage(ModuleHandler.GUI.getImageSet().getImage(
						"match/marker/fog"), 
						curPosition.x, curPosition.y, null);
			}
		}
	}
	/**
	 * Speichert alle Punkte, die in sichbarer Weite aller Charaktere des Teams liegen.
	 */
	private HashSet<Point> pointsInViewRange;
	/**
	 * Methode, die den Kriegsnebel fuer alle Objekt zusammenfuegt und in das HashSet fuegt.
	 */
	private void calcFog() {
		if (ModuleHandler.GUI.ifDrawFog()) {
			this.pointsInViewRange = ModuleHandler.MATCH.getMyHero().getFraction().getPointsInViewRange();
		}
	}
	/**
	 * Gibt die korrekte Position zurueck.
	 * @param curImage Image
	 * @param curPosition Position
	 * @return Position
	 */
	private Point getCorrectPosition(final Image curImage, final Point curPosition) {
		if (curImage != null) {
			int x = curPosition.x
					- ((curImage.getWidth(null) - this.tileWidth) / 2);
			int y = curPosition.y
					- (curImage.getHeight(null) - this.tileHeight);
			return new Point(x, y);
		} else {
			return new Point(0, 0);
		}
	}
	
	/**
	 * Der Frontbuffer. Hier werden alle Objekte eingezeichnet die im Vordergrund liegen, d.h. andere Objekte ueberdecken koennen.
	 */
	private BufferedImage frontbuffer;
	/**
	 * Zeichnet den Vordergrund.
	 */
	private void drawForeground() {
		
		this.frontbuffer = new BufferedImage(this.getWidth(), this.getHeight(),
				BufferedImage.TYPE_INT_ARGB);
		
		LinkedList<WorldObject> worldObjects = 
				ModuleHandler.MATCH.getMatch().getMap().getNonFloorInSquare(this.centerCoord, this.calcRadius());
		
		Graphics g = this.frontbuffer.getGraphics();
		boolean drawFog = ModuleHandler.GUI.ifDrawFog();
		
		for (WorldObject wo: worldObjects) {
			
			if (this.pointsInViewRange.contains(wo.getCoord()) || !drawFog || !(wo instanceof game.objects.Character)) {

				String url = wo.getImageURL();
				
				if (wo instanceof game.objects.Character || wo instanceof game.content.projectils.Projectil) {
					// Abrundungen durch die Benutzung von Integers werden hier in Kauf genommen, 
					// damit Objekt die nicht nur 8 Himmelsrichtungen haben, korrekt angezeigt werden koennen
					int angle = ((((NonStatic) wo).getAttributeValue(Attribute.headingTo) + 225) % 360 / 45) * 45;
					// Da der Null-Punkt des Koordinatensystems oben links liegt muss gespiegelt werden
					if (angle > 180) {
						angle -= Math.abs(180 - angle) * 2;
					} else if (angle > 0) {
						angle += Math.abs(180 - angle) * 2;
					}
					url += "/" + angle;
				}
					
				Image curImage = ModuleHandler.GUI.getImageSet().getImage(url);
				
				Point curPosition = null;
				
				// Da der Held eventl schon beim zeichnen nicht mehr auf dem Centerpixel sein kann, wird
				// er einfach dorthin gezeichnet
				if (this.followMyHero && wo == ModuleHandler.MATCH.getMyHero()) {
					curPosition = this.getCorrectPosition(curImage, this.centerPixel);
				} else {
					curPosition = this.getCorrectPosition(curImage, this.calcCoordToPixel(wo.getCoord()));
				}
				
				g.drawImage(curImage, curPosition.x,
						curPosition.y, null);

					if (wo instanceof game.objects.Fightable && GlobalSettings.GUI_SHOW_PULSING_ANIMATION) {
						Image pulsingEffectImage = PulsingEffectAnimation.animate(((NonStatic) wo), curImage);
						if (pulsingEffectImage != null) {
							g.drawImage(pulsingEffectImage, curPosition.x, curPosition.y, null);
						}
					}
				
				if (curImage != null) {
					this.drawHealthIndicator(g, wo, curImage.getWidth(null), curPosition.x, curPosition.y);
				}
			}
		}
		this.drawMarker(g);
	}
	
	/**
	 * Zeichnet die Makierungen auf dem Weg.
	 * @param g Graphikobjekt
	 */
	private void drawMarker(final Graphics g) {

		if (ModuleHandler.MATCH.getMyHero().getNextAction() != null
				&& ModuleHandler.MATCH.getMap() != null) {
			Point targetPoint = ModuleHandler.MATCH.getMyHero().getNextAction().getTargetPoint();
			if (targetPoint != null) {
				WorldObject top = null;
				for (WorldObject wo : ModuleHandler.MATCH.getMap().getList(targetPoint)) {
					if (wo != null) {
						top = wo;
						if (wo instanceof game.objects.Fightable) {
							break;
						}
					}
				}
				if (top != null) {
					
					String imagePath = "match/marker/neutral-" + top.getSize().x;
					
					if (top instanceof NonStatic) {
						
						if (((NonStatic) top).getFraction() != ModuleHandler.MATCH
								.getMyHero().getFraction()) {
							
							imagePath = "match/marker/enemy-" + top.getSize().x;
							
						}
					}
					
					Image curImage = ModuleHandler.GUI.getImageSet().getImage(imagePath);
					
					Point curPosition = this.getCorrectPosition(curImage, 
							this.calcCoordToPixel(top.getCoord()));
					
					g.drawImage(curImage, curPosition.x,
							curPosition.y, null);
				}
			}
		}
	}
	
	/**
	 * Gibt die Breite der Lebensanzeige an.
	 */
	private static final int HP_INDICATOR_WIDTH = 60;
	/**
	 * Gibt die Hoehe der Lebensanzeige an.
	 */
	private static final int HP_INDICATOR_HEIGHT = 16;
	
	
	/**
	 * Zeichnet fuer NonStatics einen HealthIndicator.
	 * 
	 * @param g
	 *            Graphics-Objekt
	 * @param wo
	 *            WorldObject
	 * @param x
	 *            x-Koordinate
	 * @param y
	 *            y-Koordinate
	 * @param imgWidth
	 * 			  Image-Breite
	 */
	private void drawHealthIndicator(final Graphics g, final WorldObject wo, final int imgWidth, final int x, final int y) {
		if (wo instanceof game.objects.Fightable) {
			int width = (int) (HP_INDICATOR_WIDTH * this.zoom);
			int height = (int) (HP_INDICATOR_HEIGHT * this.zoom);
			
			int x1 =  x + imgWidth / 2 - width / 2;
			
			double currentHealth = ((double) ((NonStatic) wo)
					.getAttributeValue(Attribute.currentHealth))
					/ ((double) ((NonStatic) wo)
							.getAttributeValue(Attribute.maxHealth));
			
			if (currentHealth >= 1) {
				currentHealth = 1;
			}
			
			g.setColor(Colors.BLACK);
			g.fillRect(x1, y, width, height);
			if (((NonStatic) wo).getFraction().equals(
					ModuleHandler.MATCH.getMyHero().getFraction())) {
				g.setColor(Colors.GREEN1);
			} else {
				g.setColor(Colors.RED1);
			}
			int widthOfHealth = (int) ((currentHealth) * (width - 2));
			g.fillRect(x1 + 1, y + 1, widthOfHealth, (height - 2));
			g.setColor(Colors.WHITE);
		}
	}

	/**
	 * Ruft die Repaint Methode auf.
	 */
	public void draw() {
		this.repaint();
	}

	/**
	 * Veraendert den Mittelpunkt auf den man blickt und zeichnet danach das
	 * Match neu.
	 * 
	 * @param center
	 *            Mittelpunkt des Bildausschnittes
	 */
	public void setCenterCoord(final Point center) {
		this.centerCoord = center;
	}

	/**
	 * Gibt den Mittelpunkt des Bildausschnittes zurueck.
	 * 
	 * @return Mittelpunkt des Bildausschnittes auf der Karte
	 */
	public Point getCenterCoord() {
		return this.centerCoord;
	}

	/**
	 * Zeichnet das MatchPanel.
	 * 
	 * @param g
	 *            Graphics-Objekt
	 */
	public void paintComponent(final Graphics g) {
		super.paintComponent(g);
		
		this.centerPixel = new Point(
				((this.getWidth() - this.tileWidth) / 2),
				((this.getHeight() - this.tileHeight) / 2));
		
		if (this.followMyHero) {
			this.setCenterCoord(ModuleHandler.MATCH.getMyHero().getCoord());
		}
		
		this.calcFog();
		
		this.drawBackground();
		g.drawImage(this.backbuffer, 0, 0, null);
		
		this.drawForeground();
		g.drawImage(this.frontbuffer, 0, 0, null);
	}

	/**
	 * Berechnet die Koordinate zu einem angeklickten Pixel.
	 * 
	 * @param pixelPoint
	 *            Pixelkoordinate
	 * @return Mapkoordinate
	 */
	public Point calcPixelToCoord(final Point pixelPoint) {
		float subWidth = this.tileWidth / 2f;
		float subHeight = this.tileHeight / 2f;
		float newX = (pixelPoint.x - this.centerPixel.x) / subWidth;
		float newY = (pixelPoint.y - this.centerPixel.y) / subHeight;
		Point point1;
		Point point2;
		Point pixelCoord;

		// Wenn abgerundetes newX + 1 gerade
		if ((Math.floor(newX) + 1) % 2 == 0) {
			if ((Math.floor(newY) + 1) % 2 == 0) {
				// Steigung der Kachel ist positiv
				point1 = new Point((int) (Math.floor(newX)),
						(int) (Math.floor(newY)));
				point2 = new Point((int) ((Math.floor(newX) - 1)),
						(int) ((Math.floor(newY) - 1)));
				pixelCoord = getPixelCoordFromSubTriangle(1, newX, newY,
						point1, point2);
			} else {
				// Steigung der Kachel ist negativ
				point1 = new Point((int) ((Math.floor(newX) - 1)),
						(int) (Math.floor(newY)));
				point2 = new Point((int) (Math.floor(newX)),
						(int) ((Math.floor(newY) - 1)));
				pixelCoord = getPixelCoordFromSubTriangle(2, newX, newY,
						point1, point2);
			}
		} else {
			if ((Math.floor(newY) + 1) % 2 == 0) {
				point1 = new Point((int) ((Math.floor(newX) - 1)),
						(int) (Math.floor(newY)));
				point2 = new Point((int) (Math.floor(newX)),
						(int) ((Math.floor(newY) - 1)));
				pixelCoord = getPixelCoordFromSubTriangle(2, newX, newY,
						point1, point2);
			} else {
				// Steigung der Kachel ist positiv
				point1 = new Point((int) (Math.floor(newX)),
						(int) (Math.floor(newY)));
				point2 = new Point((int) ((Math.floor(newX) - 1)),
						(int) ((Math.floor(newY) - 1)));
				pixelCoord = getPixelCoordFromSubTriangle(1, newX, newY,
						point1, point2);
			}
		}

		pixelCoord.setLocation((pixelCoord.x * this.tileWidth / 2)
				+ this.centerPixel.x, (pixelCoord.y * this.tileHeight / 2)
				+ this.centerPixel.y);
		Point coord = this.inverseCalcCoordToPixel(pixelCoord);

		return coord;
	}

	/**
	 * Berechnet, welcher von zwei moeglichen Koordinaten der korrekte ist. Dazu
	 * werden die Kacheln in 2x2 Teile verschnitten. Entweder haben sie eine
	 * positive Steigung in ihrer Mitte oder eine negative. Ebenfalls wird der
	 * angeklickte Pixel uebergeben, sowie die beiden moeglichen Koordinaten.
	 * 
	 * @param subNumber
	 *            Steigung der Subkachel (1 positiv; 2 negativ)
	 * @param x
	 *            X-Koordinate des Pixels
	 * @param y
	 *            Y-Koordinate des Pixels
	 * @param point1
	 *            Moegliche X-Koordinate der Map
	 * @param point2
	 *            Moegliche Y-Koordinate der Map
	 * @return Korrekte Map-Koordinate
	 */
	private Point getPixelCoordFromSubTriangle(final int subNumber,
			final float x, final float y, final Point point1, final Point point2) {
		float xMod1 = x % 1;
		float yMod1 = y % 1;

		if (xMod1 < 0) {
			xMod1 = 1 + xMod1;
		}

		if (yMod1 < 0) {
			yMod1 = 1 + yMod1;
		}

		switch (subNumber) {
		case 1:
			// Steigung der Subkachel ist positiv
			if (xMod1 + yMod1 <= 1) {
				// linke obere Kachel
				return point2;
			} else {
				// rechte untere Kachel
				return point1;
			}
		case 2:
			// Steigung der Subkachel ist negativ
			if (xMod1 - yMod1 <= 0) {
				// linke untere Kachel
				return point1;
			} else {
				// rechte obere Kachel
				return point2;
			}
		default:
			break;
		}
		return point1;
	}

	/**
	 * Berechnet aus Pixelkoordinaten die entsprechenden Kartenkoordinaten.
	 * @param pixelPoint Pixelkoordinaten
	 * @return Kartenkoordinaten
	 * @author Tristan
	 */
	public Point inverseCalcCoordToPixel(final Point pixelPoint) {

		// Wie viele Pixel verschieben, wenn coord.x um 1 groesser
			Point coordX1 = new Point(-(this.tileWidth / 2), -(this.tileHeight / 2));		

		// Wie viele Pixel verschieben, wenn coord.y um 1 groesser
			Point coordY1 = new Point(+(this.tileWidth / 2), -(this.tileHeight / 2));

		// Als Matrix:
		// a b
		// c d
			int a = coordX1.x;
			int b = coordY1.x;
			int c = coordX1.y;
			int d = coordY1.y;

		// Determinante dieser Matrix
			float determinante = (float) a * d - b * c;

		// Wieviele Felder in x-Richtung verschieben, wenn pixelPoint.x um 1 groesser
			float pixelX1x = d / determinante;
		// Wieviele Felder in y-Richtung verschieben, wenn pixelPoint.x um 1 groesser
			float pixelX1y = -c / determinante;
		// Wieviele Felder in x-Richtung verschieben, wenn pixelPoint.y um 1 groesser
			float pixelY1x = -b / determinante;
		// Wieviele Felder in y-Richtung verschieben, wenn pixelPoint.y um 1 groesser
			float pixelY1y = a / determinante;

		// Berechnung der Karten-Koordinate
			int coordX = (int) (((float) this.centerCoord.x)
					+ (pixelX1x * (((float) this.centerPixel.x) - ((float) pixelPoint.x))) + (pixelY1x * (((float) this.centerPixel.y) - ((float) pixelPoint.y))));
			int coordY = (int) (((float) this.centerCoord.y)
				+ (pixelX1y * (((float) this.centerPixel.x) - ((float) pixelPoint.x))) + (pixelY1y * (((float) this.centerPixel.y) - ((float) pixelPoint.y))));
		
		// Ausgabe der Kartenkoordinaten 
			return new Point(coordX, coordY);
	}

	/**
	 * Berechnet zu Kartenkoordinaten die entsprechenden Pixelkoordiaten.
	 * @param coord Kartenkoordinaten
	 * @return Pixelkoordinaten
	 * @author Tristan
	 */
	public Point calcCoordToPixel(final Point coord) {

		// Wie viele Pixel um x verschieben, wenn coord.x um 1 groesser
			Point coordX1 = new Point(-(this.tileWidth / 2), -(this.tileHeight / 2));

		// Wie viele Pixel um y verschieben, wenn coord.y um 1 groesser
		Point coordY1 = new Point(+(this.tileWidth / 2), -(this.tileHeight / 2));

		// Berechnung der Pixel-Koordinaten
			int pixelPointx = this.centerPixel.x
					+ (coordX1.x * (this.centerCoord.x - coord.x))
					+ (coordY1.x * (this.centerCoord.y - coord.y));
			int pixelPointy = this.centerPixel.y
					+ (coordX1.y * (this.centerCoord.x - coord.x))
					+ (coordY1.y * (this.centerCoord.y - coord.y));
		
		// Ausgabe der Pixel-Koordinaten
			return new Point(pixelPointx, pixelPointy);
	}
	
	/**
	 * Berechnet den Radius oder Durchmesser des Bildausschnitts, der angzeigt
	 * werden soll.
	 * 
	 * @return Radius oder Durchmesser
	 */
	public int calcRadius() {
		int erg = (int) Math.ceil((Math.sqrt(Math.pow(this.getWidth(), 2) + Math.pow(this.getHeight(), 2))
				/ Math.sqrt(Math.pow(this.tileWidth, 2) + Math.pow(this.tileHeight, 2))) * 2);
		return erg;
	}

	/**
	 * Gibt ein HashSet mit allen Punkten 
	 * in Sichtweite aller Fraktionsmitglieder.
	 * @return HashSet mit Points
	 */
	public HashSet<Point> getPointsInViewRange() {
		return this.pointsInViewRange;
	}

	/**
	 * Gibt den Center-Pixel zurueck.
	 * @return Center-Pixel
	 */
	public Point getCenterPixel() {
		return centerPixel;
	}
	
	/**
	 * Setzt den Center-Pixel.
	 * @param centerPixel Pixel
	 */
	public void setCenterPixel(final Point centerPixel) {
		this.centerPixel = centerPixel;
	}
	
	/**
	 * Gibt den Zoom-Faktor zurueck.
	 * @return Zoom-Faktor
	 */
	/**
	 * 
	 * @return Zoomfaktor.
	 */
	public float getZoom() {
		return zoom;
	}
	
	/**
	 * 
	 * @return Dimension der Felder (Breite und Hoehe).
	 */
	public Point getCoordSize() {
		return new Point(this.getWidth() / this.tileWidth, this.getHeight() / this.tileHeight);
	}
	
	
}