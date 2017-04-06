package util.graphomat.Actions;

import game.actions.MoveAction;
import game.objects.Fightable;

import java.util.LinkedList;
import util.Graphomat;

/**
 * Die Klasse spiegelt einen Befehl an die Ki wieder, die dieser Singnalisiert sich zu seinem Spawnpunkt bewegen.
 * @author Marius
 *
 */
public class MovingToSpawnPointAction extends Action {


	
	/**
	 * Konstruktor.
	 * @param gr der Graphomat
	 */
	public MovingToSpawnPointAction(final Graphomat gr) {
		super(gr);

	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void planAction(final LinkedList<Fightable> kis) {
		for (Fightable member : kis) {
			new MoveAction(member, member.getSpawnPoint()).plan();
		}
	}
}
