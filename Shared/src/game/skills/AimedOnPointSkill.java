package game.skills;

import game.objects.NonStatic;

import java.awt.Point;

/**
 * Ein Skill der auf einen Punkt zielt.
 * @author Tristan
 *
 */
public abstract class AimedOnPointSkill extends ActiveSkill {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 3421243511597143912L;

	/**
	 * Konstruktor.
	 * @param setName Name
	 */
	public AimedOnPointSkill(final String setName) {
		super(setName);
	}

	/**
	 * Fuehrt den Skill aus.
	 * @param executer Ausfuehrer
	 * @param targetPoint Ziel
	 */
	public void execute(final NonStatic executer, final Point targetPoint) {
		super.execute(executer, targetPoint);
	}
	
}
