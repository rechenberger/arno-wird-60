package game.content.ki;

import java.util.LinkedList;

import util.Graphomat;

import game.objects.Fightable;
import game.objects.Fraction;
import game.objects.GameObject;

/**
 * Eine Kigroup ist eine Ansammlung von Kis.
 * <br>
 * Dies ist die Oberklasse, jede art von Ki ausser der Tower leitet diese Klasse mit einer eigenen Gruppenklasse ab.
 * <br>
 * Diese Klasse ist dazu da das verhalten einer Gruppe von Kis zu steuern.
 * <br>
 * Hier wird auch der <b>Graphomat</b> der Kigruppe gespeichert. Hieraus resultiert das Kis, die einer Gruppe angehoeren, immer die selbe Aktion ausfuehren.
 * @author Marius
 *
 */
public class KiGroups extends GameObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 38134450569356448L;
	
	/**
	 * Alle Gruppenmitglieder die noch leben.
	 */
	private LinkedList<Fightable> aliveMember = new LinkedList<Fightable>();
	
	/**
	 * Alle Gruppenmitglieder.
	 */
	private LinkedList<Fightable> member = new LinkedList<Fightable>();

	
	
	/**
	 * Ob Gruppe im Endzustand ist und geloescht werden soll.
	 */
	private boolean isGroupInFinalState = false;
	
	/**
	 * Die Fraktion dieser Gruppe.
	 */
	private Fraction fraction;
	
	/**
	 * Der Graphomat dieser Gruppe ueber den sich die Moeglichen Operationen definieren.
	 */
	private Graphomat states;

	
	/**
	 * Ueberpruft ob ein Ki Tod ist und falls ja, wird dieser aus der aliveMemberliste geloescht.
	 */
	private void checkIfAnyKiIsDead() {
		for (Fightable m: getMember()) {
			if (!m.isAlive()) {
				this.getAliveMember().remove(m);
			}
		}
	}
	
	/**
	 * belebt alle Kis wieder und resetet den Graphen.
	 */
	private void respawnKi() {
		this.getStates().setCurrentState(this.getStates().getVertex(0));
		this.getAliveMember().clear();
		this.getAliveMember().addAll(getMember());
		for (Fightable m : getAliveMember()) {
			m.spawn();
		}
	}
	
	/**
	 * Fuehrt operationen aus, die die Ki respawnen laesst wenn die gesammte gruppe tod ist, oder 
	 * einen Member aus der Alivelist rausnimmt, wenn dieser Tod ist.
	 */
	protected void handleKiSpawns() {
		this.checkIfAnyKiIsDead();
		if (this.getAliveMember().isEmpty()) {
			respawnKi();
		}
	}

	
	/**
	 * @return the isGroupInFinalState
	 */
	public boolean isGroupInFinalState() {
		return isGroupInFinalState;
	}

	/**
	 * @param isGroupInFinalState the isGroupInFinalState to set
	 */
	public void setGroupInFinalState(final boolean isGroupInFinalState) {
		this.isGroupInFinalState = isGroupInFinalState;
	}

	/**
	 * @return the aliveMember
	 */
	public LinkedList<Fightable> getAliveMember() {
		return aliveMember;
	}

	/**
	 * @param aliveMember the aliveMember to set
	 */
	public void setAliveMember(final LinkedList<Fightable> aliveMember) {
		this.aliveMember = aliveMember;
	}

	/**
	 * @return the member
	 */
	public LinkedList<Fightable> getMember() {
		return member;
	}

	/**
	 * @param member the member to set
	 */
	public void setMember(final LinkedList<Fightable> member) {
		this.member = member;
	}

	/**
	 * @return the fraction
	 */
	public Fraction getFraction() {
		return fraction;
	}

	/**
	 * @param fraction the fraction to set
	 */
	public void setFraction(final Fraction fraction) {
		this.fraction = fraction;
	}

	/**
	 * @return the states
	 */
	public Graphomat getStates() {
		return states;
	}

	/**
	 * @param states the states to set
	 */
	public void setStates(final Graphomat states) {
		this.states = states;
	}


}
