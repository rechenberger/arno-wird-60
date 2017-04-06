package game.content.ki.vasallen.behavior;




import game.content.effects.active.Moving;
import game.content.ki.Ki;
import game.content.ki.KiGroups;
import game.content.ki.vasallen.Vasal;
import game.content.ki.vasallen.VasalKinds;
import game.objects.Fightable;
import game.objects.Fraction;
import game.objects.Map;

/**
 * Verwaltet das Verhalten der Vasallen.
 * @author Marius
 *
 */
public class VasalGroup extends KiGroups {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 710125987094668977L;

	/**
	 * Hier wird eine neue KiGruppe erstellt.
	 * @param typ Welchen Typ soll die gruppe haben
	 * @param size Gruppengroesse
	 * @param f welcher Fraktion soll diese Gruppe angehoeren.
	 * @param map Das Objekt der Map.
	 */
	public VasalGroup(final VasalTypes typ, final int size, final Fraction f, final Map map) {
		
		setFraction(f);
		
		for (int i = 0; i < size - 1; i++) {
			Vasal tmp = (Vasal) typ.getNew(f, this, VasalKinds.MELEE);
			getAliveMember().add(tmp);
			getMember().add(tmp);
			
			tmp.setSpawnPoints(typ.getSpawn(f, map));
			tmp.placeOnMap(map, tmp.getSpawnPoint());
		}
		
		Vasal tmp = (Vasal) typ.getNew(f, this, VasalKinds.RANGED);
		getAliveMember().add(tmp);
		getMember().add(tmp);
		
		tmp.setSpawnPoints(typ.getSpawn(f, map));
		tmp.placeOnMap(map, tmp.getSpawnPoint());
			
		setStates(typ.getGraphomat(f));
	}
	
	
	/**
	 * Plant die nachste Aktion der gesamten Gruppe anhand vom Graphomaten.
	 */
	public void planKiGroupAction() {
		this.handleKiSpawns();
		
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
	
	/**
	 * Entfertn einen Ki aus der Liste der noch lebenden Kis.
	 * @param member Der zu entfernende Member.
	 */
	public void removeMemberFromAliveList(final Ki member) {
		this.getAliveMember().remove(member);
	}
}
