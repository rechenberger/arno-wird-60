package gui.usercontrols.knobs;

import gui.Colors;
import gui.StringHelper;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

/**
 * Zeichnet einen Knopf, der eine prozentuale Skala hat.
 * @author Sean
 *
 */
public class Knob extends JPanel {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 9046768271755437593L;

	/**
	 * Die Farbe der Skala.
	 */
	Color arcColor;
	
	/**
	 * Das Bild des Knopfes.
	 */
	Image icon;
	
	/**
	 * Das Label des Knopfen.
	 */
	String label;
	
	/**
	 * Das zentrale Grafikobjekt.
	 */
	Graphics2D g2;
	
	/**
	 * Der Mittelpunkt der Zeichenflaeche.
	 */
	Point center;
	
	/**
	 * Soll die Auswulstung (Anker genannt) gezeichnet werden.
	 */
	private boolean showAnchor = true;
	
	/**
	 * Auf welcher Seite der Anker gezeichnet werden soll.
	 */
	private boolean anchorRight = true;
	
	/**
	 * Duenner Strich.
	 */
	BasicStroke stroke = new BasicStroke(0.5f);
	
	/**
	 * Etwas dickerer Strich.
	 */
	BasicStroke boldStroke = new BasicStroke(1.5f);
	
	/**
	 * Ein richtig dicker Strich.
	 */
	BasicStroke fatStroke = new BasicStroke(2.5f);
	
	/**
	 * Der Strich, der zum Zeichnen der Skala benutzt wird.
	 */
	BasicStroke arcStroke = new BasicStroke(6);
	
	/**
	 * Der Winkel der Skala.
	 */
	private int arcAngle = 0;
	
	/**
	 * Tauscht die Farben von Skala und Hintergrund.
	 */
	protected boolean arcInverse = false;
	
	
	/**
	 * Ob Knob klein ist.
	 */
	protected boolean small = true;
	
	/**
	 * Zeigt an, ob der Knob gerade animiert wird.
	 */
	protected boolean animating = false;
	
	/**
	 * Minimale Groesse des ersten Kreises.
	 */
	protected int minSize = 18;
	
	/**
	 * Maximale Groesse des ersten Kreises.
	 */
	protected int maxSize = 28;
	
	/**
	 * Groesse des ersten Kreises.
	 */
	protected int circleTranslation = minSize;
	
	/**
	 * Konstruktor.
	 * @param arcColor Setzt die Farbe der Skala
	 * @param icon Das Bild, das angezeigt werden soll
	 * @param listener Gibt an, ob der Knob einen Listener hat
	 */
	public Knob(final Color arcColor, final Image icon, final boolean listener) {
		this.setOpaque(false);
		this.arcColor = arcColor;
		this.icon = icon;
	}

	/**
	 * Legt fest, ob der Knob klein oder gross dargestellt werden soll.
	 * @param small True, falls er klein sein soll
	 */
	public void setSmall(final boolean small) {
		if (this.small != small) {
			this.small = small;
			this.animating = true;
		}
	}
	
	/**
	 * Setzt die Groesse des ersten Kreises bei der Animation.
	 */
	protected void animateChangeSize() {
		if (this.animating) {
			if (this.small) {
				this.circleTranslation -= 2;
			} else {
				this.circleTranslation += 4;
			}
			if ((this.circleTranslation >= this.maxSize && !this.small) || (this.circleTranslation <= this.minSize && this.small)) {
				this.animating = false;
			}
		}
	}
	
	/**
	 * Zeichnet einen, vom Mittelpunkt ausgehenden (center)), konzentrischen Kreis.
	 * @param translate Abweichung vom Mittelpunkt
	 */
	protected void drawConcentricCircle(final int translate) {
		this.g2.drawOval(this.center.x - translate, this.center.y - translate, 2 * translate,  2 * translate);
	}
	
	/**
	 * Zeichnet einen, vom Mittelpunkt ausgehenden (center)), konzentrischen Bogen.
	 * @param translate Abweichung vom Mittelpunkt
	 * @param startAngle Gleicher Paramter wie bei drawArc
	 * @param arcAngle Gleicher Paramter wie bei drawArc
	 */
	protected void drawConcentricArc(final int translate, final int startAngle, final int arcAngle) {
		this.g2.drawArc(this.center.x - translate, this.center.y - translate, 2 * translate, 2 * translate, startAngle, arcAngle);
	}
	
	/**
	 * Zeichnet ein, vom Mittelpunkt ausgehendes (center), Bild.
	 * @param image Bild
	 */
	protected void drawConcentricImage(final Image image) {
		int width = image.getWidth(null);
		int height = image.getHeight(null);
		this.g2.drawImage(image, this.center.x - width / 2, this.center.y - height / 2, width, height, null);
	}
	
	/**
	 * Zeichnet den Knopf.
	 */
	public void draw() {
		
		int currentTranslation = this.circleTranslation;
		
//		Color ultraDarkGrey = Colors.GREY2;
//		Color darkGrey = Colors.GREY4;
		Color brightGrey = Colors.GREY6;
		
		if (this.small || this.animating) {
			// Zeichnet Icon
			if (this.icon != null) {
				this.drawConcentricImage(this.icon);
			}
		} else {
			this.g2.setFont(this.g2.getFont().deriveFont(Font.BOLD));
			this.g2.setColor(Colors.GREY8);
			Rectangle2D nameRect = StringHelper.stringSize(g2, this.getName());
			Rectangle2D valueRect = StringHelper.stringSize(g2, this.getValueText());
			StringHelper.drawString(g2, this.getName(), (int) (this.center.x), 
					(int) (this.center.y - (nameRect.getHeight() + valueRect.getHeight()) / 2), StringHelper.CENTER);
		
			this.g2.setColor(this.arcColor);
			
			StringHelper.drawString(g2, this.getValueText(), (int) (this.center.x - (valueRect.getWidth() / 2)), 
					(int) (this.center.y + (nameRect.getHeight() - valueRect.getHeight()) / 2));
		
		
		}
		
		this.g2.setColor(Colors.GREY8);
		this.g2.setStroke(boldStroke);
		this.drawConcentricCircle(currentTranslation);
		
		currentTranslation += 2;
		this.g2.setStroke(stroke);
		this.drawConcentricCircle(currentTranslation);
		
		//Hintergrund der Skala
		currentTranslation += 5;
		if (!this.arcInverse) {
			this.g2.setColor(brightGrey);
		} else {
			this.g2.setColor(this.arcColor);
		}
		this.g2.setStroke(arcStroke);
		this.drawConcentricCircle(currentTranslation);
		
		//Skala
		if (!this.arcInverse) {
			this.g2.setColor(this.arcColor);
		} else {
			this.g2.setColor(brightGrey);
		}
		this.drawConcentricArc(currentTranslation, 90, -this.getArcAngle());
		
		currentTranslation += 5;
		this.g2.setColor(Colors.GREY8);
		this.g2.setStroke(stroke);
		this.drawConcentricCircle(currentTranslation);
		
		currentTranslation += 2;
		this.g2.setStroke(boldStroke);
		this.drawConcentricCircle(currentTranslation);
		
		currentTranslation += 1;
		this.drawAnchor(currentTranslation);
		
	}
	
	/**
	 * @return Text unter Name.
	 */
	protected String getValueText() {
		return "100 / 100";
	}

	/**
	 * Zeichnet den Anker.
	 * @param translate Abweichung vom Mittelpunkt
	 */
	protected void drawAnchor(final int translate) {
		if (isShowAnchor()) {
			this.g2.setStroke(fatStroke);
			if (isAnchorRight()) {
				this.drawConcentricArc(translate, 0, -45);
			} else {
				this.drawConcentricArc(translate, 180, +45);
			}
		}
	}

	/**
	 * @return Gibt zurueck, on der Anker gezeichnet wird.
	 */
	public boolean isShowAnchor() {
		return showAnchor;
	}

	/**
	 * @param showAnchor Soll der Anker gezeichnet werden?
	 */
	public void setShowAnchor(final boolean showAnchor) {
		this.showAnchor = showAnchor;
	}

	/**
	 * @return Gibt zurueck, ob der Anker auf der rechten Seite gezeichnet werden soll.
	 */
	public boolean isAnchorRight() {
		return anchorRight;
	}

	/**
	 * Legt fest, ob der Anker auf der rechten Seite gezeichnet werden soll.
	 * @param anchorRight Anker auf der rechten Seite zeichnen?
	 */
	public void setAnchorRight(final boolean anchorRight) {
		this.anchorRight = anchorRight;
	}

	/**
	 * @return Gibt die Gradzahl der Skala zurueck.
	 */
	public int getArcAngle() {
		return arcAngle;
	}
	
	/**
	 * Setzt aus einer Zahl zwischen 0 und 1 die Gradzahl der Skala.
	 * @param arcAngle Zahl zwischen 0 und 1
	 */
	public void setArcAngle(final float arcAngle) {
		this.arcAngle = (int) (arcAngle * 360);
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(100, 100);
	}
	
	@Override
	public Dimension getMinimumSize() {
		return new Dimension(100, 100);
	}
	
	@Override
	public void paintComponent(final Graphics g) {
		super.paintComponent(g);
		this.g2 = (Graphics2D) g;
		this.g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		this.center = new Point((this.getWidth()) / 2, (this.getHeight()) / 2);
		this.animateChangeSize();
		this.draw();
	}

}
