package game.content.ki.arno.behavior;

import java.awt.Point;
import java.util.ArrayList;

import game.content.effects.active.Moving;
import game.content.ki.Ki;
import game.content.ki.KiGroups;
import game.content.ki.arno.Neutral;

import game.objects.Fightable;
import game.objects.Fraction;
import game.objects.Map;

/**
 * Verwaltet das Verhalten der neutralen Monster.
 * @author Marius
 *
 */
public class NeutralGroup extends KiGroups {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1158675690325979623L;
	
	/**
	 * 
	 * @param size Die groesse der Gruppe
	 * @param spawnArea Das Gebiet in dem diese Gruppe erscheinen darf
	 * @param map Die Karte 
	 */
	public NeutralGroup(final int size, final ArrayList<Point> spawnArea, final Map map) {
		
		setFraction(Fraction.Arno);
		int tmp = spawnArea.size() / size;
		for (int i = 0; i < size; i++) {
			
			Point p = spawnArea.get((int) ((Math.random()) * tmp) + tmp * i);
			Ki newMember = NeutralTypes.DEFAULT.getNew(Fraction.Arno, this);
			
			if (newMember instanceof Neutral) {
				((Neutral) newMember).setSpawnPoint(p);
			}
			
			getAliveMember().add(newMember);
			getMember().add(newMember);
			newMember.placeOnMap(map, p);
		}
		
		setStates(NeutralTypes.DEFAULT.getGraphomat());
		
	}
	
	/**
	 * Plant die nachste Aktion der gesamten Gruppe anhand vom Graphomaten.
	 */
	public void planKiGroupAction() {	
		if (!getStates().getCurrentState().isFinalState()) {
			getStates().doNextAction(getAliveMember());
			for (Fightable m : getAliveMember()) {
				if (!Moving.hasEffect(m) && m.getNextAction() == null) {
					getStates().doCurrentAction(getAliveMember());
				}
			}
		} else {
			this.setGroupInFinalState(true);
		}
	}

}
