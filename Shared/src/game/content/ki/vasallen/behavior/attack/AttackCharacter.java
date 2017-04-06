package game.content.ki.vasallen.behavior.attack;

import game.actions.AttackAction;
import game.attributes.Attribute;
import game.content.effects.active.Moving;
import game.content.skills.Spawn;

import game.objects.Character;
import game.objects.Fightable;
import game.objects.Fraction;
import game.objects.Map;
import game.objects.NonStatic;

import java.io.Serializable;
import java.util.LinkedList;


import util.Graphomat;

import util.graphomat.Conditions.Condition;

/**
 * Diese Klasse fuegt dem Graphomaten der Vasalen zustaende hinzu, wodurch diese den Gegnerische Einheiten angreifen, solange diese in Reichweite sind.
 * Verlaesst eine Gegnerische Einheit die Sichtweite macht die Vasalengruppe mit dem letzten Zustand im Graphomaten weiter.
 * @author Marius
 *
 */
public final class AttackCharacter implements Serializable {
	
	/**
	 * 
	 */
	private AttackCharacter() {
		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 3989938239523675673L;

	/**
	 * Ueberpruft ob ein Gegnerischer Character in der Naehe ist, der Angegriffen werden kann.
	 */
	private Condition attackableCharacterInRange = new Condition(null) {

		/**
		 * 
		 */
		private static final long serialVersionUID = -8003847487058846625L;

		@Override
		public boolean checkConditaion(final LinkedList<Fightable> kis) {
			NonStatic toAttack = null;
			for (Fightable member : kis) {
				LinkedList<NonStatic> nonSt = Map.getMatchMap()
						.getFightablesInCircle(
								member.getCoord(),
								member.getAttributeValueList().getValue(
										Attribute.viewRange));
				if (!nonSt.isEmpty()) {
					for (NonStatic n : nonSt) {
						if (n.getFraction() != member.getFraction() && n.getFraction() != Fraction.Arno && (n instanceof Character || n instanceof game.content.buildings.Tower)) {
							
							toAttack = n;
							break;
						}

					}
					if (toAttack != null) {
						break;
					}
				}
			}
			if (toAttack != null) {
				return true;
			}
			return false;
		}
	};

	/**
	 * Fuegt dem Graphomaten die Action hinzu einen Character des Gegnerischen Teams in der Umgebung anzugreifen.
	 */
	private util.graphomat.Actions.AttackAction attackACharacterInRange = new util.graphomat.Actions.AttackAction(
			null) {

		/**
				 * 
				 */
				private static final long serialVersionUID = -6655789289280608538L;

		@Override
		public void planAction(final LinkedList<Fightable> kis) {

			NonStatic toAttack = null;
			for (Fightable member : kis) {
				LinkedList<NonStatic> nonSt = Map.getMatchMap()
						.getFightablesInCircle(
								member.getCoord(),
								member.getAttributeValueList().getValue(
										Attribute.viewRange));
				if (!nonSt.isEmpty()) {
					for (NonStatic n : nonSt) {
						if (n.getFraction() != member.getFraction() && n.getFraction() != Fraction.Arno && (n instanceof Character || n instanceof game.content.buildings.Tower)) {
							toAttack = n;
							break; 
						}

					}
					if (toAttack != null) {
						break;
					}
				}

			}
			if (toAttack != null) {
				for (Fightable member : kis) {
					new AttackAction(member, toAttack).plan();
					this.setEnemy(toAttack);
					this.setCoordOfAttack(kis.get(0).getCoord());
				}
			}

		}

	};

	/**
	 * Ueberprueft ob der Gegner immernoch in Rechweitre ist. 
	 * Wenn nicht, wird der letzte Zustand, der nicht einen Angriff auf einen Character signalisiert, wieder hergestellt.
	 */
	@SuppressWarnings("serial")
	private Condition isntCharacterStillInRange = new Condition(null) {

		@Override
		public boolean checkConditaion(final LinkedList<Fightable> kis) {
			if (this.getGraphomat() == null) {
				return false;
			}
			if (this.getGraphomat().getCurrentState().getAction() instanceof util.graphomat.Actions.AttackAction) {
				NonStatic enemy = ((util.graphomat.Actions.AttackAction) this
						.getGraphomat().getCurrentState().getAction())
						.getEnemy();

				for (Fightable member : kis) {
					if (Map.getMatchMap()
							.getFightablesInCircle(
									member.getCoord(),
									member.getAttributeValue(Attribute.viewRange))
							.contains(enemy)
							/*&& Points.distBetweenTwoPoints(member.getCoord(),
									((util.graphomat.AttackAction) this
											.getGraphomat().getCurrentState()
											.getAction()).getCoordOfAttack()) < 15*/) {
						return false;
					}
				}

			}
			for (Fightable member : kis) { 
				if (member.getNextAction() != null && !(member.getNextAction().getSkill() instanceof Spawn)) {
					member.getNextAction().end();
					Moving.stopMoving(member);
				}
			}
			this.getGraphomat().setCurrentState(
					this.getGraphomat().getLastNonAttackingState());
			return false;
		}

	};

	/**
	 * 
	 * @param g Der Graphomat
	 */
	public static void addAttackStatesToGraph(final Graphomat g) {
		AttackCharacter self = new AttackCharacter();
		
		self.attackableCharacterInRange.setGraphomat(g);
		self.isntCharacterStillInRange.setGraphomat(g);
		g.addVertex(g.getCounterIdVertex(), self.attackACharacterInRange);
		self.attackACharacterInRange.setGraphomat(g);
		g.addArcFromAll(g.getCounterIdVertex() - 1, self.attackableCharacterInRange);
		g.addArc(g.getCounterIdVertex() - 1, 0, self.isntCharacterStillInRange);
		
	}
}
