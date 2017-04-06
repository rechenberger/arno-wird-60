package gui.image;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Helferklasse um Bilder zu bearbeiten.
 * 
 * @author Christian Westhoff, Sean
 */
public class ImageProcessing {
	
	/**
	 * Leerer Konstruktor, da alle anderen Methoden statisch sind.
	 */
	protected ImageProcessing() {
	}

	/**
	 * Festlegung des Groessenfilters.
	 */
	private static final int SCALE_FILTER = Image.SCALE_FAST;

	/**
	 * Veraendert die Groesse eines Bildes.
	 * 
	 * @param input
	 *            Original Bild
	 * @param width
	 *            Breite des original Bildes
	 * @param height
	 *            Hoehe des original Bildes
	 * @param zoom
	 *            Zoomfaktor. Je kleiner desto kleiner wird das Bild
	 * @return Veraendertes Bild
	 */
	public static Image resizeImage(final Image input, final int width,
			final int height, final float zoom) {
		if (zoom != 1.0f) {
			return input.getScaledInstance((int) (width * zoom),
					(int) (height * zoom), SCALE_FILTER);
		} else {
			return input;
		}
	}

	/**
	 * Laedt ein Bild von einem Pfad und gibt dieses zurueck.
	 * 
	 * @param file File
	 * @return Geladenes Bild
	 */
	public static Image loadImage(final File file) {
		try {
			return ImageIO.read(file);
		} catch (IOException e) {
			System.out.println("Fehler beim einlesen");
		}
		return null;
	}

	/**
	 * Aendert die Farbe eines Bildes.
	 * 
	 * @param input
	 *            image
	 * @param setColor
	 *            color
	 * @return Image
	 */
	public static Image transformColors(final Image input, final Color setColor) {
		color = setColor;
		ImageProducer ip = new FilteredImageSource(input.getSource(), FILTER);
		return Toolkit.getDefaultToolkit().createImage(ip);
	}
	
	/**
	 * Die Farbe fuer den RGB filter, um die Farbe des Images zu transformieren.
	 */
	private static Color color;
	
	/**
	 * Der ImageFilter, um die Farbe des Images zu transformieren.
	 */
	private static final ImageFilter FILTER = new RGBImageFilter() {
		public int filterRGB(final int x, final int y, final int rgb) {
			if (((rgb >> 24) & 0xFF) <= 10) {
				return rgb;
			}
			return color.getRGB();
		}
		
	};

}
