package game.actions;

import game.content.skills.Move;
import game.objects.NonStatic;
import game.skills.Skill;

import java.awt.Point;

/**
 * Bewegungsaktion.
 * @author Tristan
 *
 */
public class MoveAction extends ActionOnPoint {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 8472327923647720545L;

	/**
	 * Konstruktor.
	 * @param setExecuter Beweger
	 * @param setTarget Ziel der Bewegung
	 */
	public MoveAction(final NonStatic setExecuter, final Point setTarget) {
		super(setExecuter, getMoveSkill(setExecuter), setTarget);
	}
	
	/**
	 * @param setExecuter Ausfuehrer
	 * @return Move-Skill des Ausfuehrers.
	 */
	private static Move getMoveSkill(final NonStatic setExecuter) {
		for (Skill skill : setExecuter.getSkills()) {
			if (skill instanceof Move) {
				return (Move) skill;
			}
		}
		System.out.println(setExecuter.toString() + " kann nich laufen!");
		return null;
	}
	
	/**
	 * Fuehrt die Bewegungsaktion aus.
	 * Ueberprueft weder auf Reichweite noch auf Cooldown.
	 */
	public void execute() {
		this.executeSkill();
		this.end();
	}
}
