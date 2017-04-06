package gui.image;

import java.awt.Image;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

/**
 * Abstrakte Klasse mit zentralen Funktionen, von der die einzelnen ImageSets abgeleitet werden.
 * @author Christian, Sean
 */
public class ImageSet {
	/**
	 * Speichert die Bilder.
	 */
	HashMap<String, SingleImageBuffer> imageSet;
	
	/**
	 * Grundsaetzliche Breite der Bodenkacheln.
	 */
	private static final int ABSOLUTE_TILE_WIDTH = 128;
	
	/**
	 * Grundsaetzliche Hoehe der Bodenkacheln.
	 */
	private static final int ABSOLUTE_TILE_HEIGHT = 96;
	
	/**
	 * Breite des Bildes.
	 */
	private int tileWidth;
	
	/**
	 * Hoehe des Bildes.
	 */
	private int tileHeight;	
	/**
	 * Konstruktor.
	 */
	public ImageSet() {
		this.imageSet = new HashMap<String, SingleImageBuffer>();
		
		LinkedList<File> files = new LinkedList<File>();
		this.getFilesInFolder(files, new File(HOMEPATH));
		for (File current : files) {
			this.imageSet.put(this.prepareString(current.getPath()),
					new SingleImageBuffer(ImageProcessing.loadImage(current)));
		}
	}
	/**
	 * Formatiert den String so, dass er verwendet werden kann. Diese Methode
	 * ersetzt den Backslash durch den Slash.
	 * @param input String
	 * @return	String
	 */
	private String prepareString(final String input) {
		return input.replace("\\", "/");
	}
	/**
	 * Methode, die saemtliche Dateien in einem Ordner rekursiv einliesst.
	 * @param folder StartOrdner
	 * @param files Liste, in der die gefundenen Dateien gespeichert werden
	 */
	private void getFilesInFolder(final LinkedList<File> files, final File folder) {
		File[] currentFiles = folder.listFiles();
		if (currentFiles != null) {
			for (File currentFile : currentFiles) {
				if (currentFile.isDirectory() && !currentFile.getName().contains(".svn")) {
					this.getFilesInFolder(files, currentFile);
				} else if (currentFile.getName().contains(FILE_TYPE)) {
					files.add(currentFile);
				}
			}
		}
	}

	/**
	 * Gibt das Hauptverzeichnis an, in dem die Bilder liegen. Achtung: Hier muss darauf geachtet werden,
	 * dass dieses auch im Hauptverzeichnis des Projektes liegt.
	 */
	static final String HOMEPATH = "images/";

	/**
	 * Gibt den Filetype an.
	 */
	static final String FILE_TYPE = ".png";
	
	/**
	 * Setzt die Groesse des Bildes neu.
	 * @param zoom Faktor um den das Bild vergroessert werden soll
	 */
	public void update(final float zoom) {
		
		this.tileWidth = (int) (ABSOLUTE_TILE_WIDTH * zoom);
		this.tileHeight = (int) (ABSOLUTE_TILE_HEIGHT * zoom);
		
		for (Entry<String, SingleImageBuffer> e : imageSet.entrySet()) {
			if (e.getKey().contains("match")) {
				e.getValue().setZoom(zoom);
			}
		}
	}
	
	/**
	 * Liefert direkt die Breite der Bodenkacheln zurueck, da diese die Grundgroesse
	 * darstellen.
	 * @return Breite einer Bodenkachel
	 */
	public int getTileWidth() {
		return this.tileWidth;
	}
	
	/**
	 * Liefert direkt die Hoehe der Bodenkacheln zurueck, da diese die Grundgroesse
	 * darstellen.
	 * @return Hoehe einer Bodenkachel
	 */
	public int getTileHeight() {
		return this.tileHeight;
	}
	
	/**
	 * Liefert das passende Image zurueck.
	 * Verkuerzt den Aufruf, der sonst getImageSet(imageSet).getImage(image) waere
	 * @param path Bild URL
	 * @return Angegebenes Bild aus dem angegeben ImageSet
	 */
	public Image getImage(final String path) {
		if (imageSet.get(HOMEPATH + path + FILE_TYPE) != null) {
			return imageSet.get(HOMEPATH + path + FILE_TYPE).getImage();
		}
		System.out.println("Bild nicht im Speicher: " + path);
		return null;
	}
}
