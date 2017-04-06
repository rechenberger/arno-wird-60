package gui.image;

import java.awt.Image;

/**
 * Speichert das Ursprungsbild und das aktuelle (groessenveraenderte) Bild.
 * @author Christian
 */
public class SingleImageBuffer {

	/**
	 * Ursprungsbild.
	 */
	private Image original;
	
	/**
	 * Aktuelles Bild.
	 */
	private Image image;
	
	/**
	 * Speichert das Bild als Ursprungs- und aktuelles Bild,
	 * setzt den Faktor auf 1 und startet die Initialisierung.
	 * @param image Zu speicherndes Bild
	 */
	public SingleImageBuffer(final Image image) {
		this.original = image;
		this.image = image;
	}
	
	/**
	 * Speichert das Bild als Ursprungs- und aktuelles Bild,
	 * setzt den Faktor auf den angegeben und startet die Initialisierung.
	 * @param image Zu speichernde Bild
	 * @param factor Allgemeiner Groessenfaktor. Gibt an, ob das Bild allgemein groesser gezeichnet werden soll
	 */
	public SingleImageBuffer(final Image image, final int factor) {
		this.original = ImageProcessing.resizeImage(
				image, image.getWidth(null), image.getHeight(null), factor);
		this.image = this.original;
	}
	
	/**
	 * Vergroesser/verkleinert ein Bild um den angegebenen Zoomfaktor und speichert dies in
	 * die Variable image.
	 * @param zoom Vergroesserungsfaktor
	 */
	public void setZoom(final float zoom) {
		if (zoom != 1.0f) {
			this.image = ImageProcessing.resizeImage(
					this.original, this.original.getWidth(null), this.original.getHeight(null), zoom);
		}
	}

	/**
	 * @return Liefert das aktuelle Bild zurueck.
	 */
	public Image getImage() {
		return image;
	}	
	
}
