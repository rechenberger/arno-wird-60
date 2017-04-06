package mapgenerator;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import util.Field;
import util.geometrie.RectangularArea;
import game.content.statics.WayPoints;
import game.objects.Fraction;
import game.objects.Map;

import mapgenerator.elemente.Base;
import mapgenerator.elemente.BottomPath;
import mapgenerator.elemente.Jungle;
import mapgenerator.elemente.MiddlePath;
import mapgenerator.elemente.River;
import mapgenerator.elemente.TopPath;
import mapgenerator.elemente.abstractclasses.Path;
import mapgenerator.elemente.baseelemente.Inhibitor;
import mapgenerator.elemente.baseelemente.Nexus;
import mapgenerator.elemente.baseelemente.Shop;
import mapgenerator.elemente.jungleelemente.JungleSpawnArea;
import mapgenerator.elemente.pathelemente.Tower;
import match.KiHandler;
import module.ModuleHandler;


/**
 * Fuer mehr informationen, bitte in den Entsprechenden Unterklassen nachschauen.
 * Alle Informationen bezueglich eines Elementes im Mapgenerator befinden sich in den entsprechenden Klasse.
 * In der Klasse Jungle befindet sich eine ausfuehrliche Dokumentation darueber, wie der Jungle generiert wird.
 * <br>
 * <b>Grundsaetlich gilt:</b>
 * <br>
 * Wenn ein Element sich auf einem anderen Element des Jungles befindet(z.B. die Tuerme befinden sich auf einem der 3 Wege),
 * Dann wird die Berechnung der Position des Elements in der Klasse des Elementes die das zu berechnende Element beinhaltet durchgefuehrt.
 * Wenn es dich bei dem unteren Element um eine Kindklasse des RactangularArea handelt, dann wird nur der untere linke Punkt berechnet und dieser zusaetzlich zu der Groesse 
 * dem Konstruktor uebergeben. Das eigentliche Element setzt sich selbst dann auf die halbe Map des Mapgenerators.
 * @author Marius
 *
 */
public class Mapgenerator extends RectangularArea {

	
	/**
	 * Die Distanz zwischen Ende der Map und einem Weg.
	 */
	public static final int DIST_BETWEEN_END_OF_MAP_AND_PATH = 25;

	
	/**
	 * Die Distanz zwischen dem Jungle und Dem Weg.
	 */
	public static final int DIST_BETWEEN_JUNGLE_AND_PATH = 2;
	
	
	/**
	 * 
	 */
	private static int[][] map;
	
	/**
	 * Eine haelfte der Map.
	 */
	private int[][] halbeMap;
	
	/**
	 * Der Weg nach oben hin.
	 */
	private TopPath topPath;
	/**
	 * Der Weg nach rechts hin.
	 */
	private BottomPath bottomPath;
	/**
	 * Der mittlere Weg.
	 */
	private MiddlePath middlePath;
	
	/**
	 * Die Basis.
	 */
	private Base base;
	
	/**
	 * Der Jungle zwischen mittlerem Weg und oberen Weg.
	 */
	private Jungle juTop;
	
	/**
	 * Der Jungle zwischen mittlerem Weg und unterem Weg.
	 */
	private Jungle juBottom;
	
	/**
	 * Der Fluss.
	 */
	private River river;
	
	
	/**
	 * Konstruktor.
	 * @param bL linker unterer Punkt
	 * @param d Die Groesse der Karte
	 */
	public Mapgenerator(final Point bL, final Dimension d) {
		super(bL, d);
		map = new int[getSize().width][getSize().height];
		setHalbeMap(new int[getSize().width][getSize().height]);
		halbiereMap();
		
	}
	
 

	
	/**
	 * Halbiert die Map sodass auf der Haelfte die Wegfaellt -1 steht.
	 */
	private void halbiereMap() {
		for (int i = 0; i < getSize().height; i++) {
			for (int j = 0; j < getSize().width; j++) {
				if (j > i) {
					getHalbeMap()[j][i] = -1;
				} else {
					continue;
				}
			}
		}
	}
	
	/**
	 * Hier wird die Eine haelfte der Map in die eigentliche Uebertragen.
	 * Dabei wird die andere Haelfte welche in der halben Map nicht vorhanden
	 * ist durch spiegelung der der anderen haelfte erstellt
	 */
	private void mergeMap() {
		for (int i = 0; i < getSize().height; i++) {
			for (int j = 0; j < getSize().width; j++) {
				if (j > i) {
					if (getHalbeMap()[i][j] == Nexus.FRACTION_A_TILE_ID) {
						map[j][i] = Nexus.FRACTION_B_TILE_ID;
					} else if (getHalbeMap()[i][j] == Shop.FRACTION_A_TILE_ID) {
						map[j][i] = Shop.FRACTION_B_TILE_ID;
					} else if (getHalbeMap()[i][j] == Tower.FRACTION_A_TILE_ID) {
						map[j][i] = Tower.FRACTION_B_TILE_ID;
					} else if (getHalbeMap()[i][j] == Inhibitor.FRACTION_A_TILE_ID) {
						map[j][i] = Inhibitor.FRACTION_B_TILE_ID;
					} else {
						map[j][i] = getHalbeMap()[i][j];
					}  
				} else {
					map[j][i] = getHalbeMap()[j][i];
				}
			}
		}
	}
	
	/**
	 * Speichert die SpawnPoints im KiHandler.
	 */
	private void saveSpawnPointsInKiHandler() {
		this.juTop.getSpawnAreas();
		
		for (JungleSpawnArea js : this.juTop.getSpawnAreas()) {
			KiHandler.getSpawnPointsNeutral().add(js.reflectCoordsOnRiver(js.getCoords()));
			KiHandler.getSpawnPointsNeutral().add(js.getCoords());
		}
		
		for (JungleSpawnArea js : this.juBottom.getSpawnAreas()) {
			KiHandler.getSpawnPointsNeutral().add(js.reflectCoordsOnRiver(js.reflectCoordsOnMiddlePath(js.getCoords(), getSize())));
			KiHandler.getSpawnPointsNeutral().add(js.reflectCoordsOnMiddlePath(js.getCoords(), getSize()));
			
		}
	}
	
	/**
	 * Hier wird die Basis berechnet und auf die Karte gesetzt.
	 */
	private void calculateBase() {
		Dimension baseSize = new Dimension(getSize().width / Base.PROPOTION_MAP_BASE, getSize().width / Base.PROPOTION_MAP_BASE);
		base = new Base(getBottomLeft(), baseSize, this);
	}
	
	/**
	 * Diese Methode erstellt die eigentliche Map.
	 */
	public static void run() {
		final Point mapSize = Map.DIMENSION;
		
		Mapgenerator m = new Mapgenerator(new Point(0, mapSize.y - 1), new Dimension(mapSize.x, mapSize.y));

		
		m.calculateBase();
		m.topPath = new TopPath(m);
		m.bottomPath = new BottomPath(m);
		m.middlePath = new MiddlePath(m);
		
		m.river = new River(m);
		
		

		m.juTop = new Jungle(m);
		m.juTop.coordsOnMap();
		m.juBottom = new Jungle(m);
		m.juBottom.coordsOnMapReflectedByMiddlePath();

		m.mergeMap();
		m.saveSpawnPointsInKiHandler();
		
		m.placeMgMapOnMap();
		
	}
	
	/**
	 * Methode zum Testen. Gibt Testframe aus.
	 * @param p groesse der Map
	 */
	public static final void runTest(final Point p) {
		Mapgenerator m = new Mapgenerator(new Point(0, p.y - 1), new Dimension(p.x, p.y));

		
		m.calculateBase();
		m.topPath = new TopPath(m);
		m.bottomPath = new BottomPath(m);
		m.middlePath = new MiddlePath(m);
		
		m.river = new River(m);
		

		m.juTop = new Jungle(m);
		m.juTop.coordsOnMap();
		m.juBottom = new Jungle(m);
		m.juBottom.coordsOnMapReflectedByMiddlePath();

		m.mergeMap();
		print();
		//m.printHalbe();
	}
	
	/**
	 * Schreibt die Wichtigen Punkte(WayPoints) in die dafuer vorgesehene HashMap in dem MapObject.
	 * @param m Das Mapobjekt ohne WegPunkte
	 * @return Das MapObjekt mit Wegpukten
	 */
	private Map placeWayPoints(final Map m) {
		
		m.getImportantPositions().put(WayPoints.TOPPATH_ENTREE_TEAM_A, topPath.getEntreeTeamA());
		m.getImportantPositions().put(WayPoints.TOPPATH_ENTREE_TEAM_B, topPath.getEntreeTeamB());
		m.getImportantPositions().put(WayPoints.TOPPATH_CENTER_POINT, topPath.getCenterPoint());
		
		m.getImportantPositions().put(WayPoints.TOPPATH_ENTREE_TEAM_A_AREA, topPath.getEntreeTeamAArea());
		m.getImportantPositions().put(WayPoints.TOPPATH_ENTREE_TEAM_B_AREA, topPath.getEntreeTeamBArea());
		m.getImportantPositions().put(WayPoints.TOPPATH_CENTER_POINT_AREA, topPath.getCenterPointArea());
		
		m.getImportantPositions().put(WayPoints.BOTTOMPATH_ENTREE_TEAM_A, bottomPath.getEntreeTeamA());
		m.getImportantPositions().put(WayPoints.BOTTOMPATH_ENTREE_TEAM_B, bottomPath.getEntreeTeamB());
		m.getImportantPositions().put(WayPoints.BOTTOMPATH_CENTER_POINT, bottomPath.getCenterPoint());
		
		m.getImportantPositions().put(WayPoints.BOTTOMPATH_ENTREE_TEAM_A_AREA, bottomPath.getEntreeTeamAArea());
		m.getImportantPositions().put(WayPoints.BOTTOMPATH_ENTREE_TEAM_B_AREA, bottomPath.getEntreeTeamBArea());
		m.getImportantPositions().put(WayPoints.BOTTOMPATH_CENTER_POINT_AREA, bottomPath.getCenterPointArea());
		
		m.getImportantPositions().put(WayPoints.MIDDLEPATH_ENTREE_TEAM_A, middlePath.getEntreeTeamA());
		m.getImportantPositions().put(WayPoints.MIDDLEPATH_ENTREE_TEAM_B, middlePath.getEntreeTeamB());
		m.getImportantPositions().put(WayPoints.MIDDLEPATH_CENTER_POINT, middlePath.getCenterPoint());
		
		m.getImportantPositions().put(WayPoints.MIDDLEPATH_ENTREE_TEAM_A_AREA, middlePath.getEntreeTeamAArea());
		m.getImportantPositions().put(WayPoints.MIDDLEPATH_ENTREE_TEAM_B_AREA, middlePath.getEntreeTeamBArea());
		m.getImportantPositions().put(WayPoints.MIDDLEPATH_CENTER_POINT_AREA, middlePath.getCenterPointArea());
		
		m.getImportantPositions().put(WayPoints.BASE_TEAM_A_CENTER_POINT, base.getCenterOfBaseA());
		m.getImportantPositions().put(WayPoints.BASE_TEAM_B_CENTER_POINT, base.getCenterOfBaseB());
		
		m.getImportantPositions().put(WayPoints.BASE_TEAM_A_CENTER_POINT_AREA, base.getCenterAreaOfBaseA());
		m.getImportantPositions().put(WayPoints.BASE_TEAM_B_CENTER_POINT_AREA, base.getCenterAreaOfBaseB());
		
		
		m.getImportantPositions().put(WayPoints.TOPPATH_SPAWNPOINT_VASAL_TEAM_A_AREA, base.getVasalTopPathSpawnTeamA());
		m.getImportantPositions().put(WayPoints.TOPPATH_SPAWNPOINT_VASAL_TEAM_B_AREA, base.getVasalTopPathSpawnTeamB());
	
		m.getImportantPositions().put(WayPoints.BOTTOMPATH_SPAWNPOINT_VASAL_TEAM_A_AREA, base.getVasalBottomPathSpawnTeamA());
		m.getImportantPositions().put(WayPoints.BOTTOMPATH_SPAWNPOINT_VASAL_TEAM_B_AREA, base.getVasalBottomPathSpawnTeamB());

		
		
		return m;
	}
	
	/**
	 * Erstellt die Eigentliche Map mit ihren WorldObjects aus dem TileId Array. 
	 */
	public void placeMgMapOnMap() { 
		
		Map m = ModuleHandler.MATCH.getMap();
		
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				
				if (map[j][i] == Path.TILE_ID) {
					
					new game.content.statics.Path().placeOnMap(m, new Point(j, i));
						
				} else if (map[j][i] == River.RIVER_TILE_ID) {
					
					new game.content.statics.Water().placeOnMap(m, new Point(j, i));
					
				} else if (map[j][i] == River.BRIGDE_TILE_ID) {
					
					new game.content.statics.Bridge().placeOnMap(m, new Point(j, i));
					
				} else if (map[j][i] == JungleSpawnArea.TILE_ID) {
					
					new game.content.statics.Gras().placeOnMap(m, new Point(j, i));
					if (Math.random() < 0.0f) {
						new game.content.statics.Tree().placeOnMap(m, new Point(j, i));
					}
					
				} else if (map[j][i] == 0) {
					
					new game.content.statics.Gras().placeOnMap(m, new Point(j, i));
					
					if (Math.random() < 0.75f) {
						new game.content.statics.Tree().placeOnMap(m, new Point(j, i));
					}
					
				} else if (map[j][i] == Base.TILE_ID) {
				
					new game.content.statics.BaseGround().placeOnMap(m, new Point(j, i));
				
				} else if (map[j][i] == Nexus.FRACTION_A_TILE_ID) {
				
					new game.content.statics.BaseGround().placeOnMap(m, new Point(j, i));
					game.content.buildings.Nexus teamA = new game.content.buildings.Nexus();
					teamA.setFraction(Fraction.TeamA);
					teamA.placeOnMap(m, new Point(j, i));
				
				} else if (map[j][i] == Nexus.FRACTION_B_TILE_ID) {
				
					new game.content.statics.BaseGround().placeOnMap(m, new Point(j, i));
					game.content.buildings.Nexus teamB = new game.content.buildings.Nexus();
					teamB.setFraction(Fraction.TeamB);
					teamB.placeOnMap(m, new Point(j, i));
			
				} else if (map[j][i] == Shop.FRACTION_A_TILE_ID) {
				
					new game.content.statics.BaseGround().placeOnMap(m, new Point(j, i));
					game.content.buildings.Shop teamA = new game.content.buildings.Shop();
					teamA.setFraction(Fraction.TeamA);
					teamA.placeOnMap(m, new Point(j, i));					
				
				} else if (map[j][i] == Shop.FRACTION_B_TILE_ID) {
				
					new game.content.statics.BaseGround().placeOnMap(m, new Point(j, i));
					game.content.buildings.Shop teamA = new game.content.buildings.Shop();
					teamA.setFraction(Fraction.TeamB);
					teamA.placeOnMap(m, new Point(j, i));					
				
				} else if (map[j][i] == Tower.FRACTION_B_TILE_ID) {
					
					new game.content.statics.Path().placeOnMap(m, new Point(j, i));
					game.content.buildings.Tower teamB = new game.content.buildings.Tower();
					teamB.setFraction(Fraction.TeamB);
					teamB.placeOnMap(m, new Point(j, i));
					KiHandler.getTowers().add(teamB);

				} else if (map[j][i] == Tower.FRACTION_A_TILE_ID) {
					
					new game.content.statics.Path().placeOnMap(m, new Point(j, i));
					game.content.buildings.Tower teamA = new game.content.buildings.Tower();
					teamA.setFraction(Fraction.TeamA);
					teamA.placeOnMap(m, new Point(j, i));	
					KiHandler.getTowers().add(teamA);
					
				} else if (map[j][i] == Inhibitor.FRACTION_A_TILE_ID) {
					
					new game.content.statics.BaseGround().placeOnMap(m, new Point(j, i));
					game.content.buildings.Inhibitor teamA = new game.content.buildings.Inhibitor();
					teamA.setFraction(Fraction.TeamA);
					teamA.placeOnMap(m, new Point(j, i));
					
				} else if (map[j][i] == Inhibitor.FRACTION_B_TILE_ID) {
					
					new game.content.statics.BaseGround().placeOnMap(m, new Point(j, i));
					game.content.buildings.Inhibitor teamB = new game.content.buildings.Inhibitor();
					teamB.setFraction(Fraction.TeamB);
					teamB.placeOnMap(m, new Point(j, i));
					
				}
			}
		}
		
		placeWayPoints(m);
	}
	
	/**
	 * test.
	 */
	public static void print() {
		
		@SuppressWarnings("unused")
		TestGuiMapgenerator t = new TestGuiMapgenerator("test", map);
		
		
	}

	/**
	 * test.
	 */
	public void printHalbe() {
		
		@SuppressWarnings("unused")
		TestGuiMapgenerator t = new TestGuiMapgenerator("test", this.getHalbeMap());
		
		
	}

	/**
	 * Gibt der Mittleren Pfad zurueck.
	 * @return den Mittlere Pfad.
	 */
	public Path getMiddlePath() {
		return middlePath;
	}

	/**
	 * Gibt den Pfad zurueck, der von der Basis in de unteren linken Ecke nach rechts hin fuehrt.
	 * (Der unten lang laeuft)
	 * @return Der Pfad
	 */
	public Path getBottomPath() {
		return bottomPath;
	}

	/**
	 * Gibt den Pfad zurueck, der von der Basis in der unteren linken Ecke noch oben hin fuehrt.
	 * @return Der Pfad
	 */
	public Path getTopPath() {
		return topPath;
	}


	/**
	 * Gibt die Halbe Map als ArrayList zurueck.
	 * @return die halbe Map.
	 */
	public ArrayList<Field<Integer>> getHalbeMapAsArrayList() {
		
		ArrayList<Field<Integer>> points = new ArrayList<Field<Integer>>();
		for (int i = 0; i < getHalbeMap()[0].length; i++) {
			for (int j = 0; j < getHalbeMap().length; j++) {
				points.add(new Field<Integer>(new Point(j, i), getHalbeMap()[j][i]));
			}
		}
		
		return points;
		
	}
	
	/**
	 * Gibt die Basis Zurueck. 
	 * @return DIe Basis
	 */
	public Base getBase() {
		return base;
	}




	/**
	 * @return the river
	 */
	public River getRiver() {
		return river;
	}




	/**
	 * @param river the river to set
	 */
	public void setRiver(final River river) {
		this.river = river;
	}




	/**
	 * @return the halbeMap
	 */
	public int[][] getHalbeMap() {
		return halbeMap;
	}




	/**
	 * @param halbeMap the halbeMap to set
	 */
	public void setHalbeMap(final int[][] halbeMap) {
		this.halbeMap = halbeMap;
	}
}


