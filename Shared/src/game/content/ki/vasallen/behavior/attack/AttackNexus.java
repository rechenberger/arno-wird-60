package game.content.ki.vasallen.behavior.attack;

import game.actions.AttackAction;
import game.content.buildings.Nexus;
import game.objects.Fightable;
import game.objects.GameObject;
import game.objects.NonStatic;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Diese Klasse fuegt dem Graphomaten der Vasalen zustaende hinzu, wodurch diese den Gegnerischen Nexus angreifen.
 * @author Marius
 *
 */
public final class AttackNexus implements Serializable {
	
	/**
	 * 
	 */
	private AttackNexus() {
		
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 5028853423950720405L;
	/**
	 * Befehligt die Vasallen den Nexus anzugreifen.
	 */
	private util.graphomat.Actions.AttackAction attackNexus = new util.graphomat.Actions.AttackAction(
			null) {

		/**
				 * 
				 */
				private static final long serialVersionUID = -6643799830127721500L;

		@Override
		public void planAction(final LinkedList<Fightable> kis) {

			
			for (NonStatic member : kis) {
				for (GameObject b : GameObject.getGameObjectsByClassName("Building")) {
					if (b instanceof Nexus && ((Nexus) b).getFraction() != member.getFraction()) {
						new AttackAction(member, ((Nexus) b)).plan();
					}
				}
			}
	}
	};

	/**
	 * @return the attackNexus
	 */
	public static util.graphomat.Actions.AttackAction getAttackNexus() {
		AttackNexus self = new AttackNexus();
		return self.attackNexus;
	}
}
