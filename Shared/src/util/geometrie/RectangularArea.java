package util.geometrie;

import java.awt.Dimension;
import java.awt.Point;


/**
 * Ein Recheckiges Gebiet.
 * @author Marius
 *
 */
public class RectangularArea extends Area {
	
	/**
	 * Die Dimension dieses Gebietes.
	 */
	private Dimension size;
	
	/**
	 * Die obere Linke Ecke dieses Gebietes.
	 */
	private Point topLeft;
	
	/**
	 * Die obere Rechte Ecke dieses Gebietes.
	 */
	private Point topRight;
	
	/**
	 * Die untere Linke Ecke dieses Gebietes.
	 */
	private Point bottomLeft;
	
	/**
	 * Die untere Rechte Ecke dieses Gebietes.
	 */
	private Point bottomRight;
	
	/**
	 * Konstruktor. Fuegt dem Area Alle Koordinaten hinzum die im durch die Eckpunkte festgelegen Berech liegen.
	 * @param bL Unten Links
	 * @param s Die Dimension.
	 */
	public RectangularArea(final Point bL, final Dimension s) {
		if (bL != null && s != null) {
			size = s;
			bottomLeft = bL;
			bottomRight = new Point(bL.x + size.width, bL.y);
			topLeft = new Point(bL.x, bL.y - size.height);
			topRight = new Point(bL.x + size.width, bL.y - size.height);
			
			for (int i = bottomLeft.y; i >= bottomLeft.y - size.height; i--) {
				for (int j = bottomLeft.x; j <= bottomRight.x; j++) {
					this.add(new Point(j, i));
				}
			}
		}
	}

	/**
	 * @return the size
	 */
	public Dimension getSize() {
		return size;
	}

	/**
	 * @return the topLeft
	 */
	public Point getTopLeft() {
		return topLeft;
	}

	/**
	 * @return the topRight
	 */
	public Point getTopRight() {
		return topRight;
	}

	/**
	 * @return the bottomLeft
	 */
	public Point getBottomLeft() {
		return bottomLeft;
	}

	/**
	 * @return the bottomRight
	 */
	public Point getBottomRight() {
		return bottomRight;
	}
}
