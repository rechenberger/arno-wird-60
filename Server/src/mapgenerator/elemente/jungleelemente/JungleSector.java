package mapgenerator.elemente.jungleelemente;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Random;

import util.geometrie.RectangularArea;

import mapgenerator.Mapgenerator;

/**
 * Diese Klasse ist dazu da das <b>SpawnArea(falls vorhanden),
 * die ConnectionLines, und die Wege</b> zu instanzieren.
 * <br>
 * Neben den Oben genannten Klassen speichert diese Klasse auch wichtige Informationen
 * wie zum Beispiel ob eine <b>kalkulierte ConnectionLine</b> auch wirklich auf der <b>Karte erscheinen</b> soll.
 * Dies ist naemlich nicht der Fall, wenn diese ConnectionLine nicht im MST zu finden ist.
 * <br>
 * Des Weiteren werden hier die <b>Masze des SpawnAreas</b> berechnet.
 * <br>
 * Die Masze fuer die ConnectionLine und die Wege werden in den dafuer vorgesehen Klassen berechnet,
 * der Grund hierfuer ist das ConnectionLine und Spawnarea nicht von der Klasse Rectangular Area abgeleitet werden.
 * 
 * @author Marius
 *
 */
public class JungleSector extends RectangularArea {
	
	/**
	 * Verhaeltniss zwischen Kartengroesse und groesse eines JungleSectors.
	 */
	protected static final int PROPOTION_MAP_JUNGLESECTOR = 15;
	
	/**
	 * Verhaeltniss zwischen JungleSector groesse und minimale Entfernung vom spawnArea zum Kartenende.
	 */
	protected static final int PROPOTION_DIST_SPAWNAREA_JUNGLESECTOR = 3;
	
	/**
	 * Verhaeltniss zwischen JungleSector groesse und minimale Entfernung von einer ConnectionLine zum Eckpunkt.
	 */
	protected static final int PROPOTION_DIST_ADJACENTCONNECTION_JUNGLESECTOR_POINT = 4;
		
	/**
	 * Das Object des Mapgenerator, in welchem der JungleSector plaziert werden soll.
	 */
	protected Mapgenerator mg;
	
	
	/**
	 * Die Koordinaten des Weges des JungleSectors.
	 */
	private ArrayList<JungleSectorPath> paths;
	
	
	////////////////////////////////////////////////////////////////////////////////
	//////Hier Werden Die Attribute welche das SpawnArea betreffen definiert.
	////////////////////////////////////////////////////////////////////////////////
	/**
	 * die Mindestdistanz zwischen dem Rand des JungleSectors und dem spawnArea.
	 * <br>
	 * berechnet sich im Konstruktor durch die Proportion <b>PROPOTION_DIST_SPAWNAREA_JUNGLESECTOR</b> und der JungleSectorgroesse.
	 */
	private int distBetweenEndOfSectorAndSpawn;
	
	/**
	 * Die Punkte welche innerhalb der Mindestdistanz zwischen Ende des Sektors und dem SpawnArea liegen.
	 */
	private ArrayList<Point> distBetweenEndOfSectorAndSpawnAreaCoords;
	
	/**
	 * Das Objekt des SpawnAreas.
	 */
	private JungleSpawnArea spawnArea;

	
	////////////////////////////////////////////////////////////////////////////////
	//////Hier Werden Die Attribute welche Die ConnectionLines betreffen definiert.
	////////////////////////////////////////////////////////////////////////////////
	/**
	 * Das Objekt der Topconnection.
	 */
	private JungleConnectionLine topConnection;
	
	/**
	 * Ob TopConnection auf Karte gesetzt werden soll.
	 */
	private boolean printTopConnection = true;
	
	/**
	 * Das Objekt der Bottomconnection.
	 */
	private JungleConnectionLine bottomConnection;
	
	/**
	 * Ob BottomConnection auf Karte gesetzt werden soll.
	 */
	private boolean printBottomConnection = true;
	
	/**
	 * Das Objekt der Leftconnection.
	 */
	private JungleConnectionLine leftConnection;
	
	/**
	 * Ob LeftConnection auf Karte gesetzt werden soll.
	 */
	private boolean printLeftConnection = true;
	
	/**
	 * Das Objekt der Rightconnection.
	 */
	private JungleConnectionLine rightConnection;
	
	/**
	 * Ob RightConnection auf Karte gesetzt werden soll.
	 */
	private boolean printRightConnection = true;

	
	
	
	/////////////////////////////////////////////////////////////////////////	
	////////////////////////////Hier hoert die Definition der Attribute auf!
	/////////////////////////////////////////////////////////////////////////
	
	/**
	 * JungleSector wird initialisiert.
	 * <br>
	 * Die Eckpunkte werden berechnet und die Kordinaten werden gespeichert.
	 * <br>
	 * Alle weiteren oben definierten Bedingungen werden aus ihren Proportionen berechnet.
	 * <br>
	 * Der spawnArea wird berechnet.
	 * <br>
	 * <b> ConnectionLines werden hier nicht berechent </b>
	 * @param mapg das Mapgenerator Object
	 * @param bL der Startpunkt des JungleSectors(die untere linke Ecke)
	 */
	public JungleSector(final Mapgenerator mapg, final Point bL) {
		super(bL, getSize(mapg.getSize()));
		mg = mapg;

		topConnection = new JungleConnectionLine(this);
		bottomConnection = new JungleConnectionLine(this);
		leftConnection = new JungleConnectionLine(this);
		rightConnection = new JungleConnectionLine(this);

		distBetweenEndOfSectorAndSpawn = getSize().height / PROPOTION_DIST_SPAWNAREA_JUNGLESECTOR;
		

		paths = new ArrayList<JungleSectorPath>();
		distBetweenEndOfSectorAndSpawnAreaCoords = new ArrayList<Point>();
		
		this.calculateDistBetweenEndOfSectorAndSpawnAreaCoords();
		
		if (JungleSpawnArea.CHANGE_OF_HAVING_SPAWNAREA > Math.random()) {
		 this.calculateRamdomSpawnArea(); 
		} else {
			spawnArea = new JungleSpawnArea(null, null, this);
		}
	}
	/**
	 * Zufaelliger aber die Bedingungen erfuellender spawnArea wird berechnet.
	 * <br>
	 * Bedingungen siehe Konstanten und Attribute dieser Klasse.
	 * <br>
	 * Unter anderem: <b>groesse, abstand zum ende des Sectors</b>
	 */
	private void calculateRamdomSpawnArea() {
		
		Dimension spawnAreaSize = new Dimension(getSize().width 
				/ JungleSpawnArea.PROPOTION_JUNGLESECTOR_SPAWNAREA, 
			getSize().height 
				/ JungleSpawnArea.PROPOTION_JUNGLESECTOR_SPAWNAREA);
		
		int minY = this.getTopLeft().y + distBetweenEndOfSectorAndSpawn + spawnAreaSize.height;
		int maxY = this.getBottomLeft().y - distBetweenEndOfSectorAndSpawn;
		int minX = this.getTopLeft().x + distBetweenEndOfSectorAndSpawn;
		int maxX = this.getTopRight().x - distBetweenEndOfSectorAndSpawn - spawnAreaSize.width;
		
		Random ran = new Random();     
		int randomY = 0;
		while (randomY < minY) {
		  randomY = ran.nextInt(maxY);
		}
		
		int randomX = 0;
		while (randomX < minX) {
			  randomX = ran.nextInt(maxX);
		}

		Point randomP = new Point(randomX, randomY);
		spawnArea = new JungleSpawnArea(randomP, spawnAreaSize, this);	
	}
	
	/**
	 * Ruft die Methode CoordsOnMap fuer alle Mapelemente auf die sich in dem aktuellen Element befinden.
	 * Wenn das aktuelle Element auf die Karte geschrieben wird, wird das auch hier gemacht.
	 */
	public void coordsOnMap() {
		spawnArea.coordsOnMap();
		if (printTopConnection) {
			topConnection.coordsOnMap();
		}
		if (printBottomConnection) {
			bottomConnection.coordsOnMap();
		}
		if (printLeftConnection) {
			leftConnection.coordsOnMap();
		}
		if (printRightConnection) {
			rightConnection.coordsOnMap();
		}
		for (JungleSectorPath jungleP : paths) {
			jungleP.coordsOnMap();
		}
	}
	
	/**
	 * Ruft die Methode coordsOnMapReflectedByMiddlePath fuer alle Mapelemente auf die sich in dem aktuellen Element befinden.
	 * Wenn das aktuelle Element auf die Karte geschrieben wird, wird das auch hier gemacht.
	 * <br>
	 * Hier werden allerdings die Elemente gespiegelt auf die Karte gesetzt.
	 */
	public void coordsOnMapReflectedByMiddlePath() {
		spawnArea.coordsOnMapReflectedByMiddlePath();
		if (printTopConnection) {
			topConnection.coordsOnMapReflectedByMiddlePath();
		}
		if (printBottomConnection) {
			bottomConnection.coordsOnMapReflectedByMiddlePath();
		}
		if (printLeftConnection) {
			leftConnection.coordsOnMapReflectedByMiddlePath();
		}
		if (printRightConnection) {
			rightConnection.coordsOnMapReflectedByMiddlePath();
		}
		for (JungleSectorPath jungleP : paths) {
			jungleP.coordsOnMapReflectedByMiddlePath();
		}
	}
	
	/**
	 * Berechnet zufaellig die TopConnection des JungleSectors, ohne dabei die dafuer definierten Bedingungen zu verletzten.
	 * <br>
	 * unter anderem:
	 * <br>
	 * <b> entfernung der Linie zu Eckpunkten, laenge</b>
	 * <br>
	 * setzt diese Pubkte auch auf die Karte, falls printTopConnection == true
	 */
	public void calculateTopConnection() {
		topConnection = new JungleConnectionLine(this);
		topConnection.calculateConnectionLine(this.getTopLeft(), this.getTopRight());
	}
	
	/**
	 * Berechnet zufaellig die BottomConnection des JungleSectors, ohne dabei die dafuer definierten Bedingungen zu verletzten.
	 * <br>
	 * unter anderem:
	 * <br>
	 * <b> entfernung der Linie zu Eckpunkten, laenge</b>
	 * <br>
	 * setzt diese Pubkte auch auf die Karte, falls printBottomConnection == true
	 */
	public void calculateBottomConnection() {
		bottomConnection = new JungleConnectionLine(this);
		bottomConnection.calculateConnectionLine(this.getBottomLeft(), this.getBottomRight());
		
	}
	
	/**
	 * Berechnet zufaellig die LeftConnection des JungleSectors, ohne dabei die dafuer definierten Bedingungen zu verletzten.
	 * <br>
	 * unter anderem:
	 * <br>
	 * <b> entfernung der Linie zu Eckpunkten, laenge</b>
	 * <br>
	 * setzt diese Punkte auch auf die Karte, falls printLeftConnection == true
	 */
	public void calculateLeftConnection() {
		leftConnection = new JungleConnectionLine(this);
		leftConnection.calculateConnectionLine(this.getTopLeft(), this.getBottomLeft());
	}
	
	/**
	 * Berechnet zufaellig die RightConnection des JungleSectors, ohne dabei die dafuer definierten Bedingungen zu verletzten.
	 * <br>
	 * unter anderem:
	 * <br>
	 * <b> entfernung der Linie zu Eckpunkten, laenge</b>
	 * <br>
	 * setzt diese Pubkte auch auf die Karte, falls printRightConnection == true
	 */
	public void calculateRightConnection() {
		rightConnection = new JungleConnectionLine(this);
		rightConnection.calculateConnectionLine(this.getTopRight(), this.getBottomRight());
	}
	
	/**
	 * Berechnet den Bereich der zwischen dem Ende des Sektors und dem gueltigen bereich fuer SpawnAreas liegt.
	 */
	private void calculateDistBetweenEndOfSectorAndSpawnAreaCoords() {
		
		Line2D.Double topLine = new Line2D.Double(this.getTopLeft(), this.getTopRight());
		Line2D.Double leftLine = new Line2D.Double(this.getTopLeft(), this.getBottomLeft());
		Line2D.Double bottomLine = new Line2D.Double(this.getBottomLeft(), this.getBottomRight());
		Line2D.Double rightLine = new Line2D.Double(this.getBottomRight(), this.getTopRight());
		
		for (Point p : this.getCoords()) {
			
			if ((int) topLine.ptLineDist(p) <= distBetweenEndOfSectorAndSpawn) {
				distBetweenEndOfSectorAndSpawnAreaCoords.add(p);
			} else if ((int) leftLine.ptLineDist(p) <= distBetweenEndOfSectorAndSpawn) {
				distBetweenEndOfSectorAndSpawnAreaCoords.add(p);
			} else if ((int) bottomLine.ptLineDist(p) <= distBetweenEndOfSectorAndSpawn) {
				distBetweenEndOfSectorAndSpawnAreaCoords.add(p);
			} else if ((int) rightLine.ptLineDist(p) <= distBetweenEndOfSectorAndSpawn) {
				distBetweenEndOfSectorAndSpawnAreaCoords.add(p);
			}
		}
		
	}
	
	/**
	 * Berechnet den JunglePath in diesem JungleSector zwischen allen ConnectionLines und dem Mittelpunkt des SpawnAreas
	 * oder dem Mittelpunkt des JungleSectors.
	 * <br>
	 * Wenn dieser JungleSector benachbart zu Hauptweg ist, wird hier auch eine neue ConnectionLine 
	 * zum Hauptweg hinzugefuegt und der Weg zum Hauptweg hin berechnet.
	 */
	public void calculateJunglePath() {
		
		//Wenn ich eine Verbindung in die gegebene Richtung habe udn diese auf die Karte soll
		// dann berechne den Weg zwischen dieser Verbindung und dem SpawnArea oder der Mitte der Karte.
		if (printTopConnection && !topConnection.isEmpty()) {
			JungleSectorPath topPath = new JungleSectorPath(topConnection, this);
			topPath.calculatePath();
			paths.add(topPath);
		}
		
		//Wenn ich eine Verbindung in die gegebene Richtung habe udn diese auf die Karte soll
		// dann berechne den Weg zwischen dieser Verbindung und dem SpawnArea oder der Mitte der Karte.
		if (printBottomConnection && !bottomConnection.isEmpty()) {
			JungleSectorPath bottomPath = new JungleSectorPath(bottomConnection, this);
			bottomPath.calculatePath();
			paths.add(bottomPath);
		}
		
		//Wenn ich eine Verbindung in die gegebene Richtung habe udn diese auf die Karte soll
		// dann berechne den Weg zwischen dieser Verbindung und dem SpawnArea oder der Mitte der Karte.
		if (printLeftConnection && !leftConnection.isEmpty()) {
			JungleSectorPath leftPath = new JungleSectorPath(leftConnection, this);
			leftPath.calculatePath();
			paths.add(leftPath);
			
			//Wenn ich keine Verbindung nach links hin habe, dann muss dort der Hauptweg sein
			//also berechne ich auch dorthin einen neuen Weg
		} else if (leftConnection.isEmpty()) { 
			//erstmal muss eine ConnectionLine auf dieser seite berechnet werden.
			calculateLeftConnection();
			printLeftConnection = true;
			//dann der Weg von der ConnectionLine zum Mittelpunkt
			JungleSectorPath leftPath = new JungleSectorPath(leftConnection, this);
			leftPath.calculatePath();
			paths.add(leftPath);
			
			//Und jetzt noch der Weg von der Connectionline zum Hauptweg
			ConnectionJungleSectorMainPath pathToMainPath = new ConnectionJungleSectorMainPath(leftConnection, this, new Dimension(-1, 0));
			pathToMainPath.calculatePath();
			paths.add(pathToMainPath);
		}
		
		//Wenn ich eine Verbindung in die gegebene Richtung habe udn diese auf die Karte soll
		// dann berechne den Weg zwischen dieser Verbindung und dem SpawnArea oder der Mitte der Karte.
		if (printRightConnection && !rightConnection.isEmpty()) {
			JungleSectorPath rightPath = new JungleSectorPath(rightConnection, this);
			rightPath.calculatePath();
			paths.add(rightPath);
		
			//Wenn ich keine Verbindung nach rechts hin habe, dann muss dort der Hauptweg sein
			//also berechne ich auch dorthin einen neuen Weg
		} else if (rightConnection.isEmpty() && !topConnection.isEmpty()) {
			//erstmal muss eine ConnectionLine auf dieser seite berechnet werden.
			calculateRightConnection();
			printRightConnection = true;
			//dann der Weg von der ConnectionLine zum Mittelpunkt
			JungleSectorPath rightPath = new JungleSectorPath(rightConnection, this);
			rightPath.calculatePath();
			paths.add(rightPath);
			
			//Und jetzt noch der Weg von der Connectionline zum Hauptweg
			ConnectionJungleSectorMainPath pathToMainPath = new ConnectionJungleSectorMainPath(rightConnection, this, new Dimension(+1, +1));
			pathToMainPath.calculatePath();
			paths.add(pathToMainPath);
			
		}
	}

	
	/////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////GETTER UND SETTER
	//////////////////////////////////////////////////////////////////////////////////

	
	/**
	 * gibt die Groesse des Vierecks zurueck.
	 * Kann benutzt werden wenn noch kein Sector instanziert wurde.
	 * @param mgSize die groesse des zugrundeliegenden Mapgenerators
	 * @return die groesse
	 */
	public static Dimension getSize(final Dimension mgSize) {
		return new Dimension(mgSize.width / PROPOTION_MAP_JUNGLESECTOR, mgSize.height / PROPOTION_MAP_JUNGLESECTOR);
	}
	
	/**
	 * @return the paths
	 */
	public ArrayList<JungleSectorPath> getPaths() {
		return paths;
	}
	/**
	 * @param allpaths the paths to set
	 */
	public void setPaths(final ArrayList<JungleSectorPath> allpaths) {
		this.paths = allpaths;
	}
	
	/**
	 * Gibt Die Koordinaten in dem sich das SpawnArea nicht befinden soll zurueck.
	 * <br>
	 * Der Grund warum hier kein SpawnArea hin soll ist, das es sich zu nah am Sector Rand befindet.
	 * @return Die Koordinaten in dem sich das SpawnArea nicht befinden soll
	 */
	public ArrayList<Point> getDistBetweenEndOfSectorAndSpawnAreaCoords() {
		return distBetweenEndOfSectorAndSpawnAreaCoords;
	}
	
	/**
	 * @return Das Objekt des SpawnAreas
	 */
	public JungleSpawnArea getSpawnArea() {
		return spawnArea;
	}
	
	/////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////GETTER UND SETTER FUER DIE CONNECTIONLINES
	//////////////////////////////////////////////////////////////////////////////////
	/**
	 * @return the printTopConnection
	 */
	public boolean isPrintTopConnection() {
		return printTopConnection;
	}

	/**
	 * Bestimmt ob die Verbindung auf der Karte erscheinen soll oder nicht.
	 * @param printTopCon the printTopConnection to set
	 */
	public void setPrintTopConnection(final boolean printTopCon) {
		this.printTopConnection = printTopCon;
	}

	/**
	 * @return the printBottomConnection
	 */
	public boolean isPrintBottomConnection() {
		return printBottomConnection;
	}

	/**
	 * Bestimmt ob die Verbindung auf der Karte erscheinen soll oder nicht.
	 * @param printBottomCon the printBottomConnection to set
	 */
	public void setPrintBottomConnection(final boolean printBottomCon) {
		this.printBottomConnection = printBottomCon;
	}

	/**
	 * @return the printLeftConnection
	 */
	public boolean isPrintLeftConnection() {
		return printLeftConnection;
	}

	/**
	 * Bestimmt ob die Verbindung auf der Karte erscheinen soll oder nicht.
	 * @param printLeftCon the printLeftConnection to set
	 */
	public void setPrintLeftConnection(final boolean printLeftCon) {
		this.printLeftConnection = printLeftCon;
	}

	/**
	 * @return the printRightConnection
	 */
	public boolean isPrintRightConnection() {
		return printRightConnection;
	}

	/**
	 * Bestimmt ob die Verbindung auf der Karte erscheinen soll oder nicht.
	 * @param printRightCon the printRightConnection to set
	 */
	public void setPrintRightConnection(final boolean printRightCon) {
		this.printRightConnection = printRightCon;
	}
	
	/**
	 * Setzt die Koordinaten der TopConnection.
	 * @param topCon die Koordinaten
	 */
	public void setTopConnection(final JungleConnectionLine topCon) {
		this.topConnection = topCon;
	}
	
	/**
	 * Liefert die Koordinaten der TopConnection zurueck.
	 * @return die Koordinaten der TopConnection
	 */
	public final JungleConnectionLine getTopConnection() {
		return topConnection;
	}
	
	/**
	 * gibt die Koordinaten der BottomConnection zurueck.
	 * @return the bottomConnection
	 */
	public JungleConnectionLine getBottomConnection() {
		return bottomConnection;
	}

	/**
	 * Setzt die Koordinaten der BottomConnection.
	 * @param bottomCon die Koordinaten
	 */
	public void setBottomConnection(final JungleConnectionLine bottomCon) {
		this.bottomConnection = bottomCon;
	}

	/**
	 * gibt die Koordinaten der LeftConnection zurueck.
	 * @return the leftConnection
	 */
	public JungleConnectionLine getLeftConnection() {
		return leftConnection;
	}

	/**
	 * Setzt die Koordinaten der LeftConnection.
	 * @param leftCon die Koordinaten
	 */
	public void setLeftConnection(final JungleConnectionLine leftCon) {
		this.leftConnection = leftCon;
	}

	
	/**
	 * Setzt die Koordinaten der RightConnection.
	 * @param rightCon die Koordinaten
	 */
	public void setRightConnection(final JungleConnectionLine rightCon) {
		this.rightConnection = rightCon;
	}
	
	/**
	 * gibt die Koordinaten der RightConnection zurueck.
	 * @return the rightConnection
	 */
	public JungleConnectionLine getRightConnection() {
		return rightConnection;
	}

}
