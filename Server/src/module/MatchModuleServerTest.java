package module;
import com.messages.ChatMessage;
import game.objects.GameObject;
//import java.awt.Point;
//import java.util.Arrays;
//
//import com.messages.GameContentMessage;
//import com.messages.MessageType;
//
//import match.KiHandler;
//import module.MatchModuleServer;
//import game.actions.AttackAction;
//import game.actions.MoveAction;
//import game.attributes.Attribute;
//import game.content.effects.active.Following;
//import game.content.effects.direct.Damage;
//import game.content.heros.Hero;
//import game.content.heros.Sprintomanius;
//import game.content.heros.geronimo.GeronimoVonNazareth;
//import game.content.ki.Default;
//import game.content.ki.behavior.KiGroup;
//import game.content.ki.behavior.KiTypes;
//import game.content.statics.Gras;
//import game.content.statics.Path;
//import game.content.statics.Tree;
//import game.effects.ActiveEffect;
//import game.effects.DirectEffect;
//import game.effects.Effect;
//import game.effects.PermanentEffect;
//import game.objects.Fraction;
//import game.objects.Map;
//import game.objects.Match;
//import game.objects.NonStatic;
//import game.objects.WorldObject;

/**
 * TestMatchModul.
 * @author Tristan
 * 
 * CHECKSTYLE:OFF
 *
 */
public class MatchModuleServerTest extends MatchModuleServer {
	
	public  void run() {
		
//		this.initializeTestWorld();
//		this.kampf();
//		this.geroParty();
//		this.wettrennen();
//		this.schoeneNeueWelt();
//		this.konfrontation();
//		this.verfolgungsjagt();
//		this.lonleyWulf();
//		this.zweiJungsInnerMitte();
		//new KiGroup(KiTypes.DEFAULT, 6, Fraction.TeamA, match.getMap());

		super.run();
		
	}
	

	public void turn() {

//		System.out.println("Runde " + (this.turn+1));
		
//		System.out.println("Neue Runde");
		super.turn();
//		System.out.println(this.turn);
		
		
//		if(turn == 20) {
//			System.out.println(GameObject.getAllGameObjectsSize());
//			GameObject.sendAllGameObjects();
//		}
		
		if (turn%100 == 0) {

//			int[][] mapArray1 = this.getMatch().getMap().toArray();
//			GameObject.testSaveNLoad();
//			this.match = Match.getMatch();
//			int[][] mapArray2 = this.getMatch().getMap().toArray();
//			
//			if(Arrays.equals(mapArray1, mapArray2)) {
//				System.out.println("JAAAAAAA");
//			} else {
//				
//				for (int x = 0; x < mapArray2.length; x++) {
//					for (int y = 0; y < mapArray2.length; y++) {
//						if (mapArray1[x][y] != mapArray2[x][y]) {
//							System.out.println("mapArray1: " + mapArray1[x][y]);
//							System.out.println("mapArray2: " + mapArray2[x][y]);
//						}
//					}
//				}
//				
//				for (int x = mapArray2.length/4; x < mapArray2.length/2; x++) {
//					for (int y = mapArray2.length/4; y < mapArray2.length/2; y++) {
//						System.out.print(mapArray2[x][y]);
//					}
//					System.out.println();
//				}
//
//				System.out.println();
//				System.out.println("================================================================================================================================================================================================================================================");
//				System.out.println();
//				
//				for (int x = mapArray2.length/4; x < mapArray2.length/2; x++) {
//					for (int y = mapArray2.length/4; y < mapArray2.length/2; y++) {
//						System.out.print(mapArray2[x][y]);
//					}
//					System.out.println();
//				}
//				
//				System.exit(0);
//			}

			System.out.print("Runde " + this.turn + ", ");
			System.out.println(GameObject.getAllGameObjectsSize() + " GameObjects, ");
			ModuleHandler.COM.pushMessage((new ChatMessage("Runde " + this.turn)));
//			System.out.print( ((double) GameObject.getNextId())*100 / ((double) Integer.MAX_VALUE) );
//			System.out.println("%  ausgelastet." );
//			aufZuNeuenUfern();
		}
//		Hero gero = GameObject.getById(4);
//		System.out.print("Geros Effekte: "); gero.printEffects();
//		System.out.println("Geros Position: " + gero.getCoord().toString());
	}
	
//	public void geroParty() {
//		for(int i=0; i<150; i++) {
//
//			Hero gero = new Sprintomanius();
//			if (i % 2 == 0) {
//				gero.setFraction(Fraction.TeamA);
//			} else {
//				gero.setFraction(Fraction.TeamB);
//			}
//			
//			gero.placeOnMap(match.getMap(), findEmptyJunglePoint() );
//
//			new MoveAction(gero, findEmptyJunglePoint()).plan();
//		}
//	}
//	
//	public Point findEmptyJunglePoint() {
//		return findEmptyPoint("static/floor/gras");
//	}
//	
//	private Point findEmptyPoint(final String imageURL) {
//		Point coord;
//		do {
//			coord = new Point((int) (Math.random() * Map.DIMENSION.x), (int) (Math.random() * Map.DIMENSION.y));
//		} while(this.getMatch().getMap().ifWalkable(coord) || !this.getMatch().getMap().getList(coord).getFirst().getImageURL().equals(imageURL) );
//		return coord;
//	}
//	
//	public void schoeneNeueWelt() {
//		Hero gero1 = new Sprintomanius();
//		gero1.placeOnMap(match.getMap(), new Point(0, 199) );
//		new MoveAction(gero1, new Point(162, 42) ).plan();
//	}
//	
//
//	private void konfrontation() {
//		for(int x=0; x<20; x=x+2) {
//			for(int y=0; y<20; y=y+2) {
//
//				Point coord1 = new Point(5+x, 195-y );
//				Point coord3 = new Point(25+x, 175-y );
//				
//				Point coord2 = new Point(195-x, 5+y);
//				Point coord4 = new Point(175-x, 25+y);
//
//				Hero gero1 = new Sprintomanius();
//				Hero gero2 = new Sprintomanius();
//				
//				gero1.setFraction(Fraction.TeamA);
//				gero2.setFraction(Fraction.TeamB);
//
//				gero1.placeOnMap(match.getMap(), coord1 );
//				gero2.placeOnMap(match.getMap(), coord2 );
//
//				new MoveAction(gero1, coord4).plan();
//				new MoveAction(gero2, coord3).plan();
//			}
//		}
//	}
//	
//	private void verfolgungsjagt() {
//
//		Hero gero1 = new Sprintomanius();
//		Hero gero2 = new Sprintomanius();
//		
//
//		gero1.placeOnMap(match.getMap(), new Point(90, 110) );
//		gero2.placeOnMap(match.getMap(), new Point(110, 90) );
//		
//
//		new MoveAction(gero1, new Point(199, 0) ).plan();
//		new Following(gero2, gero1, 0).ready();
////		new ActionOnNonStatic(gero2, Follow.getInstance(), gero1).plan();
//		
//
////		System.out.println("Held 1 : "+gero1.getId());
////		System.out.println("Held 2 : "+gero2.getId());
//	}
//
//
//	private void kampf() {
//
//		Hero gero1 = new GeronimoVonNazareth();
//		Hero gero2 = new GeronimoVonNazareth();
//		
//
//		gero1.placeOnMap(match.getMap(), new Point(90, 110));
//		gero2.placeOnMap(match.getMap(), new Point(110, 90));
//		
//		new AttackAction(gero1, gero2).plan();
//		
//	}
//	
//	private void aufZuNeuenUfern() {
//		for (NonStatic ns : NonStatic.getAllNonStatics()) {
//			new MoveAction(ns, findEmptyJunglePoint()).plan();
//		}
//	}
//	
//	private void lonleyWulf() {
//		Hero gero1 = new GeronimoVonNazareth();
//
//		gero1.placeOnMap(match.getMap(), this.findEmptyPoint() );
//	}
//	
//	private void zweiJungsInnerMitte() {
//
//		Hero gero1 = new GeronimoVonNazareth();
//		Hero gero2 = new game.content.heros.brocky.BrockyAloa();
//		
//
//		Hero gero3 = new GeronimoVonNazareth();
//		Hero gero4 = new game.content.heros.brocky.BrockyAloa();
//		
//		gero1.setFraction(Fraction.TeamA);
//		gero2.setFraction(Fraction.TeamB);
//		gero4.setFraction(Fraction.TeamA);
//		gero3.setFraction(Fraction.TeamB);
//		
//		
//		gero1.placeOnMap(match.getMap(), new Point(this.getMatch().getMap().DIMENSION.x / 2 - 1, this.getMatch().getMap().DIMENSION.x / 2 + 1));
//		gero2.placeOnMap(match.getMap(), new Point(this.getMatch().getMap().DIMENSION.y / 2 + 1, this.getMatch().getMap().DIMENSION.y / 2 - 1));
//		gero3.placeOnMap(match.getMap(), new Point(this.getMatch().getMap().DIMENSION.x / 2 + 1, this.getMatch().getMap().DIMENSION.x / 2 + 1));
//		gero4.placeOnMap(match.getMap(), new Point(this.getMatch().getMap().DIMENSION.x / 2 - 1, this.getMatch().getMap().DIMENSION.x / 2 - 1));
//		
//	}
}
