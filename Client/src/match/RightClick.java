package match;

import game.actions.ActionOnNonStatic;
import game.actions.ActionOnPoint;
import game.actions.AttackAction;
import game.actions.MoveAction;
import game.content.heros.Hero;
import game.content.skills.Attack;
import game.objects.Map;
import game.objects.NonStatic;
import game.objects.WorldObject;
import game.skills.AimedOnNonStaticSkill;
import game.skills.AimedOnPointSkill;
import game.skills.Skill;

import java.awt.Point;
import java.util.LinkedList;

import module.ModuleHandler;



/**
 * Bei Rechtsklick auf die Karte wird diese Klasse aufgerufen. 
 * Sie untersucht, ob
 * das Ziel angegriffen werden kann oder
 * einen Punkt finden der begehbar ist und moeglichst nah am Zielpunkt ist.
 * @author Alex
 *
 */
public class RightClick {
	
	/**
	 * Held, der die Aktion ausfuehrt.
	 */
	private Hero executer = null;
	
	/**
	 * Punkt, wohin der Held laufen soll.
	 */
	private Point target = null;
	
	
	/**
	 * Konstruktor.
	 * Greift an wenn angreifbares NonStatic auf Punkt, ansonsten laeuft es zu Punkt.
	 * @param target Angeglickter Punkt auf der Karte
	 * @author Tristan
	 */
	public RightClick(final Point target) {
		this.executer = ModuleHandler.MATCH.getMyHero();
		this.target = target;
		
		LinkedList<WorldObject> ll = ModuleHandler.MATCH.getMatch().getMap().getList(target);
		
		if (!ll.isEmpty()) {
			WorldObject topWorldObject = ll.getLast();
//			if (!(topWorldObject instanceof Tree)) {
//				ModuleHandler.GUI.getMatchPanel().setMoveTarget(target);
//			}

			Skill nextSkillToUse = ModuleHandler.GUI.getNextSkillToUse();
			if (nextSkillToUse != null) {
				if (nextSkillToUse instanceof AimedOnNonStaticSkill) {
					if (topWorldObject instanceof NonStatic  && Attack.getInstance().willBeEffected(ModuleHandler.MATCH.getMyHero(), (NonStatic) topWorldObject)) {
						new ActionOnNonStatic(ModuleHandler.MATCH.getMyHero(), (AimedOnNonStaticSkill) nextSkillToUse, (NonStatic) topWorldObject).plan();
					}
				} else {
					new ActionOnPoint(ModuleHandler.MATCH.getMyHero(), (AimedOnPointSkill) nextSkillToUse, topWorldObject.getCoord()).plan();
				}
				ModuleHandler.GUI.setNextSkillToUse(null);
				
			} else {
				// Unteruscht, ob auf dem Punkt ein angreifbares NonStatic steht.
				if (topWorldObject instanceof NonStatic && Attack.getInstance().willBeEffected(ModuleHandler.MATCH.getMyHero(), (NonStatic) topWorldObject)) {
					//Wenn auf dem Zielfeld ein angreifbares Objekt steht, soll dieses angegriffen werden.
					new AttackAction(ModuleHandler.MATCH.getMyHero(), (NonStatic) topWorldObject).plan();		
				} else {
					//Wenn auf dem Zielfeld kein angreifbares Objekt steht. Soll sich der Charakter bewegen.
					new MoveAction(ModuleHandler.MATCH.getMyHero(), setTarget()).plan();
				}
			}
		}
	}
	
	/**
	 * Ermittelt einen begehbaren Punkt, der moeglichst nah am angeklickten Punkt liegt.
	 * @return Neuer Zielpunkt
	 */
	private Point setTarget() {
		Point newAim = null;
		LinkedList<Point> points = new LinkedList <Point>();
		boolean aimFound = false;
		int zaehler = 0;
		
		while (!aimFound) {
			//Es wir eine Liste erstellt mit allen Punkten die eine bestimmte Entfernung zum Punkt haben.		
			//Aus dieser Liste werden alle Punkte entfernt, die nicht begehbar sind.
			
			for (Point p : getPointsInCircle(target, zaehler)) {
				if (ModuleHandler.MATCH.getMatch().getMap().ifWalkable(p)) {
					points.add(p);
				}
			}
				
			if (points.size() == 1) {
				//Wenn nur ein Punkt begehbar ist, wir das neue Ziel dieser Punkte.
				newAim = points.getLast();
				aimFound = true;
			} else if (points.size() > 1) {
				//Sind mehrere Punkte begehbar, wird der Punkt mit der geringsten Entfernung 
				//zum Charakter das neue Ziel.
				for (Point p : points) {
					int best = Integer.MAX_VALUE;
					if (Map.getDistance(executer.getCoord(), p) < best) {
						best = Map.getDistance(executer.getCoord(), p);
						newAim = p;
					}
				}
				aimFound = true;
			} else {
				//Wenn kein Punkt begehbar ist, wird der Zaehler erhoeht und die do-Schleife wieder ausgefuehrt.
				zaehler++;
			}			
		}
		return newAim;
	}
	
	/**
	 * Erstellt eine Liste mit Punkten die max einen bestimmten Abstand
	 * zu einem Punkt haben. Die erstellte Liste stellt einen Kreis 
	 * um den eingegebenen Punkt dar.
	 * @param coord Punkt um den der Kreis der Punkte liegen soll
	 * @param radius max Abstand die alle Punkte in der Liste haben sollen
	 * @return Liste mit Punkten
	 */
	private LinkedList<Point> getPointsInCircle(final Point coord, final int radius) {
		LinkedList<Point> list = new LinkedList<Point>();
		for (int x = coord.x - radius; x <= coord.x + radius; x++) {
			for (int y = coord.y - radius; y <= coord.y + radius; y++) {
				if (radius == Map.getDistance(coord, new Point(x, y))) {
					list.add(new Point(x, y));
				}
			}
		}
		return list;
	}
}
