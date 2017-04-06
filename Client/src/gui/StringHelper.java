package gui;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 * Hilfsklasse um Strings mit br-Tags zu zeichnen.
 * @author Sean
 */
public final class StringHelper {
	/**
	 * Benannte Variable fuer mehr Intuitivitaet.
	 */
	public static final int CENTER = 1;
	
	/**
	 * Leerer Konstruktor, da alle anderen Methoden statisch sind.
	 */
	private StringHelper() { }
	
	/**
	 * Gibt die Groesse eines zu zeichnenden Strings zurueck. Der String kann auch br Tags beinhalten,
	 * an denen der Text umgebrochen wird.
	 * @param g2 Graphics2D Objekt, dass zeichnen soll
	 * @param text Zu zeichnender String, dessen Groesse benoetigt wird
	 * @return Rechteck mit der Breite und Hoehe des Strings
	 */
	public static Rectangle2D stringSize(final Graphics2D g2, final String text) {
		double width = 0;
		double height = 0;
		for (String line : text.split("<br>")) {
			Rectangle2D rect = g2.getFontMetrics().getStringBounds(line, g2);
			width = Math.max(rect.getWidth(), width);
			height += g2.getFontMetrics().getAscent();
		}
		
		return new Rectangle2D.Double(0, 0, width, height);
	}
	
	/**
	 * Zeichnet einen String an eine bestimmte Position und bricht ihn an den br Tags um.
	 * @param g2 Graphics2D Objekt, dass zeichnen soll
	 * @param text String, der gezeichnet werden soll
	 * @param startX X-Koordinate, von der aus gezeichnet werden soll
	 * @param startY Y-Koordinate, von der aus gezeichnet werden soll
	 */
	public static void drawString(final Graphics2D g2, final String text, final int startX, final int startY) {
		int x = startX;
		int y = startY;
		
		for (String line : text.split("<br>")) {
			y += g2.getFontMetrics().getAscent();
			g2.drawString(line, x, y);
		}
	}
	
	/**
	 * Zeichnet einen String an eine bestimmte Position und bricht ihn an den br Tags um.
	 * @param g2 Graphics2D Objekt, dass zeichnen soll
	 * @param text String, der gezeichnet werden soll
	 * @param startX X-Koordinate, von der aus gezeichnet werden soll
	 * @param startY Y-Koordinate, von der aus gezeichnet werden soll
	 * @param style StringHelper.CENTER um auf X-Achse zu zentrieren
	 */
	public static void drawString(final Graphics2D g2, final String text, final int startX, 
			final int startY, final int style) {
		int x = startX;
		int y = startY;
		
		for (String line : text.split("<br>")) {
			if (style == CENTER) {
				Rectangle2D rect = g2.getFontMetrics().getStringBounds(line, g2);
				x = (int) (startX - rect.getWidth() / 2);
			}
			y += g2.getFontMetrics().getAscent();
			g2.drawString(line, x, y);
		}
	}
}
