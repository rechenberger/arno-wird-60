package game.content.ki.tower;

import game.content.buildings.Tower;
import game.content.effects.active.Moving;
import game.content.ki.KiGroups;
import game.content.ki.tower.behavior.Attack;
import game.objects.Fightable;

import java.io.Serializable;


/**
 * Gegenstueck zu der Klasse KiGroup.
 * Da Tuerme immer fuer sich arbeiten gehoeren diese keiner Gruppe an und fuehren auch ihre Operationen nich in einer Gruppe aus.
 * <br>
 * Da Der Graphomat aber daruaf ausgelegt ist Gruppen von Kis udn keien einzelnen Kis zu handlen, gibt es hier auch eine LinkedList mit allen Mitgliedern,
 * <br>
 * diese beinhaltet allerdings immer nur einen Turm.
 * @author Marius
 *
 */
public class TowerKi extends KiGroups implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2901768210404104246L;
	
	
	/**
	 * Konstruktor.
	 * @param t der Tower
	 */
	public TowerKi(final Tower t) {
		setStates(Attack.addAttackStatesToGraph());
		getMember().add(t);
	}

	
	/**
	 * Plant die nachste Aktion des Turms anhand vom Graphomaten.
	 */
	public void planKiGroupAction() {
		
		if (!getStates().getCurrentState().isFinalState()) {
			getStates().doNextAction(getMember());
			for (Fightable m : getMember()) {
				if (!Moving.hasEffect(m) && m.getNextAction() == null) {
					getStates().doCurrentAction(getMember());
				}
			}
		} else {
			this.setGroupInFinalState(true);
		}
	}
}
