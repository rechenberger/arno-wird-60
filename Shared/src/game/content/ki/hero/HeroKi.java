package game.content.ki.hero;

import game.content.effects.active.Moving;
import game.content.heros.Hero;
import game.content.ki.KiGroups;
import game.content.ki.hero.behavior.PossibleBehaviors;

import game.objects.Fightable;

import java.io.Serializable;


/**
 * Gegenstueck zu der Klasse KiGroup.
 * Da Helden immer fuer sich arbeiten gehoeren diese keiner Gruppe an und fuehren auch ihre Operationen nich in einer Gruppe aus.
 * <br>
 * Da Der Graphomat aber daruaf ausgelegt ist Gruppen von Kis udn keien einzelnen Kis zu handlen, gibt es hier auch eine LinkedList mit allen Mitgliedern,
 * <br>
 * diese beinhaltet allerdings immer nur einen Turm.
 * @author Marius
 */
public class HeroKi extends KiGroups implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8358672978083981436L;
	
	/**
	 * Der Name der momentanen Verhaltensweise.
	 */
	private String currentBehaviorName = "";

	/**
	 * Konstruktor.
	 * @param h Der held der gesteuert werden soll.
	 */
	public HeroKi(final Hero h) {
		setFraction(h.getFraction());
		double rand = Math.random();
		if (rand < 0.5) {
			setStates(PossibleBehaviors.BEHAVE_LIKE_A_VASAL.getGraphomat(getFraction()));
			setCurrentBehaviorName(PossibleBehaviors.BEHAVE_LIKE_A_VASAL.name());
		} else {
			setStates(PossibleBehaviors.GO_TO_JUNGLE.getGraphomat(getFraction()));
			setCurrentBehaviorName(PossibleBehaviors.GO_TO_JUNGLE.name());
		}
		getMember().add(h);
		getAliveMember().add(h);
	}
	
	/**
	 * @return the hero
	 */
	public Hero getHero() {
		return (Hero) this.getMember().get(0);
	}
	
	/**
	 * Gibt eine Neue Instanz dieses Objektes zurueck.
	 * @param h Der Held der gesteuert werden soll.
	 * @return dieses Objekt in neuer Instanz
	 */
	public static HeroKi getNewHeroKi(final Hero h) {
		return new HeroKi(h);
	}
	
	/**
	 * Plant die nachste Aktion der gesamten Gruppe anhand vom Graphomaten.
	 */
	public void planKiGroupAction() {	
		//this.handleKiSpawns();
		if (!getStates().getCurrentState().isFinalState()) {
			getStates().doNextAction(getAliveMember());
			for (Fightable m : getAliveMember()) {
				if (!Moving.hasEffect(m) && m.getNextAction() == null && m.isAlive()) {
					getStates().doCurrentAction(getAliveMember());
				}
			}
		} else {
			this.setGroupInFinalState(true);
		}
	}
	
	/**
	 * Veraendert die aktuelle Verhaltenweise des Helden.
	 */
	public void changeBehavior() {
		if (getCurrentBehaviorName() == PossibleBehaviors.BEHAVE_LIKE_A_VASAL.name()) {
			setStates(PossibleBehaviors.GO_TO_JUNGLE.getGraphomat(getFraction()));
			setCurrentBehaviorName(PossibleBehaviors.GO_TO_JUNGLE.name());
		} else {
			setStates(PossibleBehaviors.BEHAVE_LIKE_A_VASAL.getGraphomat(getFraction()));
			setCurrentBehaviorName(PossibleBehaviors.BEHAVE_LIKE_A_VASAL.name());
		}
		
	}

	/**
	 * @return the currentBehaviorName
	 */
	public String getCurrentBehaviorName() {
		return currentBehaviorName;
	}

	/**
	 * @param currentBehaviorName the currentBehaviorName to set
	 */
	public void setCurrentBehaviorName(final String currentBehaviorName) {
		this.currentBehaviorName = currentBehaviorName;
	}

}
