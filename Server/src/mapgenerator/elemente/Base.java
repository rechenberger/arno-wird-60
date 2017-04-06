package mapgenerator.elemente;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;

import util.geometrie.RectangularArea;

import mapgenerator.Mapgenerator;
import mapgenerator.elemente.abstractclasses.Path;
import mapgenerator.elemente.baseelemente.Inhibitor;
import mapgenerator.elemente.baseelemente.Nexus;
import mapgenerator.elemente.baseelemente.Shop;
/**
 * <b>Wenn diese Klasse instanziert wird schreibt sie die Basis des Spielfelds auf die Map des Mapgenerators</b>
 * Die Basis beschreibt das Gebiet in welcher Die Spieler starten.
 * 
 * @author Marius
 *
 */
public class Base extends RectangularArea {
	
	/**
	 * Das Verhaeltniss zwischen Map und Basis wenn Map 1000 mal 1000 gross ist, dann ist das Element 1000 / PROPOTION_MAP_BASE Gross.
	 */
	public static final int PROPOTION_MAP_BASE = 5;
	
	/**
	 * Die TileId.
	 */
	public static final int TILE_ID = 4;
	
	/**
	 * Das Objekt des Mapgenerators.
	 */
	protected Mapgenerator mg;
	
	/**
	 * Das Objekt des Nexus welches auf die Karte gesetzt wurde.
	 */
	private Nexus nexus;
	
	/**
	 * Das Objekt des Shops welches auf die Karte gesetzt wurde.
	 */
	private Shop shop;

	/**
	 * Das Objekt des Inhibitors welches auf die Karte gesetzt wurde.
	 */
	private Inhibitor inhibitor;
	
/**
 * 
 * @param bL BottomLeft der Punkt unten links vom Viereck
 * @param d Dimension Die Groesse des Vierecks
 * @param mapg das Objekt des Mapgenerators
 */
	public Base(final Point bL, final Dimension d, final Mapgenerator mapg) {
		super(bL, d);
		mg = mapg;
		this.coordsOnMap(mg.getHalbeMap(), TILE_ID);
		
		calculateNexus();
		calculateShop();
		calculateInhibitor();
	}
	
	/**
	 * Kalkuliert die Position des Nexus und setzt ihn auf die Karte.
	 */
	private void calculateNexus() {
		
		Dimension nexusSize = new Dimension(game.content.buildings.Nexus.SIZE.x, game.content.buildings.Nexus.SIZE.y);
		nexus = new Nexus(getBottomLeft(), nexusSize, mg);
	}

	/**
	 * Kalkuliert die Position des Shops und setzt ihn auf die Karte.
	 */
	private void calculateShop() {
		
		Dimension shopSize = new Dimension(game.content.buildings.Shop.SIZE.x, game.content.buildings.Shop.SIZE.y);
		int bLShopX = getBottomLeft().x + getSize().width / Shop.PROPOTION_BASE_TO_BL;
		int bLShopY = getBottomLeft().y - getSize().height / Shop.PROPOTION_BASE_TO_BL;
		Point bL = new Point(bLShopX, bLShopY);
		shop = new Shop(bL, shopSize, mg);
	}
	
	/**
	 * Kalkuliert die Position des Inhibitors und setzt ihn auf die Karte.
	 */
	private void calculateInhibitor() {
		Dimension shopSize = new Dimension(game.content.buildings.Inhibitor.SIZE.x, game.content.buildings.Inhibitor.SIZE.y);
		int bLShopX = (int) ((int) getBottomLeft().x + getSize().width / Inhibitor.PROPOTION_BASE_TO_BL);
		int bLShopY = (int) ((int) getBottomLeft().y - getSize().height / Inhibitor.PROPOTION_BASE_TO_BL);
		Point bL = new Point(bLShopX, bLShopY);
		inhibitor = new Inhibitor(bL, shopSize, mg);
	}

	/**
	 * @return the shop
	 */
	public Shop getShop() {
		return shop;
	}

	/**
	 * @return the nexus
	 */
	public Nexus getNexus() {
		return nexus;
	}
	
	/**
	 * Berechnet den Mittelpunkt des Basisareals von Team A.
	 * @return den MittelPunkt
	 */
	public ArrayList<Point> getCenterOfBaseA() {
		ArrayList<Point> centerOfBase = new ArrayList<Point>();
		
		centerOfBase.add(new Point(getTopLeft().x + (getSize().width / 2), getTopLeft().y + (getSize().height / 2))); 
 		
		return centerOfBase;
		
	}
	
	/**
	 * Berechnet ein Rechteckiges Gebiet um den MittelPunkt des BasisArealsA.
	 * Die Dimension des Rechtecks entspricht dabei der Dimension des Weges
	 * @return Das Areal
	 */
	public ArrayList<Point> getCenterAreaOfBaseA() {
		
		Dimension d = new Dimension(mg.getSize().width / Path.PROPOTION_MAP_PATH, mg.getSize().height / Path.PROPOTION_MAP_PATH);
		Point bottomLeftOfArea = new Point(getCenterOfBaseA().get(0).x - (d.width / 2), getCenterOfBaseA().get(0).y + (d.height / 2));
 		
		return new RectangularArea(bottomLeftOfArea, d).getCoords();
		
	}
	
	/**
	 * Berechnet die Positionen, an denen Vasalen von Team A,
	 * die ueber den oberen Pfad laufen, starten duerfen.
	 * @return Die Punkte
	 */
	public ArrayList<Point> getVasalTopPathSpawnTeamA() {
		
		int xStart = getSize().width / 2;
		int yStart = getTopLeft().y + getSize().height / 10;
		
		Point bottomLeftOfArea = new Point(xStart, yStart);
		
		return new RectangularArea(bottomLeftOfArea, new Dimension(4, 4)).getCoords();
		
	}
	
	/**
	 * Berechnet die Positionen, an denen Vasalen von Team A,
	 * die ueber den unteren Pfad laufen, starten duerfen.
	 * @return Die Punkte
	 */
	public ArrayList<Point> getVasalBottomPathSpawnTeamA() {
		return this.reflectCoordsOnMiddlePath(this.getVasalTopPathSpawnTeamA(), mg.getSize());
	}
	
	/**
	 * Berechnet die Positionen, an denen Vasalen von Team B,
	 * die ueber den oberen Pfad laufen, starten duerfen.
	 * @return Die Positionen
	 */
	public ArrayList<Point> getVasalTopPathSpawnTeamB() {
		return reflectCoordsOnRiver(getVasalTopPathSpawnTeamA());
	}
	
	/**
	 * Berechnet die Positionen, an denen Vasalen von Team B,
	 * die ueber den unteren Pfad laufen, starten duerfen.
	 * @return Die Punkte
	 */
	public ArrayList<Point> getVasalBottomPathSpawnTeamB() {
		return this.reflectCoordsOnMiddlePath(this.getVasalTopPathSpawnTeamB(), mg.getSize());
	}
	
	/**
	 * Berechnet den Mittelpunkt des Basisareals von Team B.
	 * @return den MittelPunkt
	 */
	public ArrayList<Point> getCenterOfBaseB() {
		return reflectCoordsOnRiver(getCenterOfBaseA());
		
	}
	
	/**
	 * Berechnet ein Rechteckiges Gebiet um den MittelPunkt des BasisArealsB.
	 * Die Dimension des Rechtecks entspricht dabei der Dimension des Weges
	 * @return Das Areal
	 */
	public ArrayList<Point> getCenterAreaOfBaseB() {
		return reflectCoordsOnRiver(getCenterAreaOfBaseA());
		
	}

	/**
	 * @return the inhibitor
	 */
	public Inhibitor getInhibitor() {
		return inhibitor;
	}

	/**
	 * @param inhibitor the inhibitor to set
	 */
	public void setInhibitor(final Inhibitor inhibitor) {
		this.inhibitor = inhibitor;
	}
}
