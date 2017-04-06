package game.content.ki.arno.behavior.attack;

import game.actions.AttackAction;
import game.attributes.Attribute;
import game.content.effects.active.Moving;
import game.content.skills.Spawn;
import game.objects.Character;
import game.objects.Fightable;
import game.objects.Map;
import game.objects.NonStatic;

import java.io.Serializable;
import java.util.LinkedList;

import util.Graphomat;
import util.geometrie.Points;
import util.graphomat.Conditions.Condition;


/**
 * Fuegt dem Graphomaten der Neutralen Monster die Moeglichkeit hinzu <b>gegnerische Helden anzugreifen</b>, 
 * solange diese sich nicht weiter vom Spawnpoint der Neutralen Monster entfernt haben, als <i>2 * Viewragne der Neutralen Monster</i>.
 * Hierbei ist es ausreichend, das die oben genannte Bedigung fuer mindestens ein neutrales Monster der Gruppe erfuellt ist.
 * @author Marius
 *
 */
public final class AttackCharacter implements Serializable {
	
	/**
	 * Factor der angibt wie weit sich ein Ki vom Spawnpunkt entfernen darf.
	 */
	private static final float VIEWRANGE_FACTOR = 1.5f;
	
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
						if (n.getFraction() != member.getFraction() && n instanceof Character && !(n instanceof game.content.ki.vasallen.Vasal)) {
							toAttack = n;
							break;
						}

					}
					if (toAttack != null) {
						break;
					}
				}
			}
			if (toAttack != null && isAtLeastOneMemberNotToFarAwayFromSpawnPoint(kis)) {
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
						if (n.getFraction() != member.getFraction() && n instanceof Character && !(n instanceof game.content.ki.vasallen.Vasal)) {
							toAttack = n;
							break; 
						}

					}
					if (toAttack != null && isEnemyNotToFarAwayFromSpawnPoint(kis, toAttack)) {
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
							.contains(enemy)) {
						if (isAtLeastOneMemberNotToFarAwayFromSpawnPoint(kis) && isEnemyNotToFarAwayFromSpawnPoint(kis, enemy)) {
							return false;
						}
					}
				}

			}
			for (Fightable member : kis) { 
				if (member.getNextAction() != null && !(member.getNextAction().getSkill() instanceof Spawn)) {
					member.getNextAction().end();
					Moving.stopMoving(member);
				}
			}
			return true;
		}

	};
	
	/**
	 * Ueberprueft ob zumindest ein Ki nicht weiter von seinem Spawnpunkt entfernt ist
	 *  als die doppelte viewRange.
	 * @param kis Die Gruppenmitglieder
	 * @return ob die Ki nah genug am Spawnpunkt sind.
	 */
	private boolean isAtLeastOneMemberNotToFarAwayFromSpawnPoint(final LinkedList<Fightable> kis) {
		
		for (Fightable ki : kis) {
			if (Points.distBetweenTwoPoints(ki.getCoord(), ki.getSpawnPoint()) 
					< ki.getAttributeValue(Attribute.viewRange) * VIEWRANGE_FACTOR) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Ueberpruft on ein moegliches Ziel nicht weiter
	 * als die doppelte viewrange des Ki vom dem Spawnpubkt der Ki entfernt ist.
	 * @param kis Die Gruppenmitglieder
	 * @param enemy Der Gegner
	 * @return ob der Gegner nah genung am Spawnpunkt steht
	 */
	private boolean isEnemyNotToFarAwayFromSpawnPoint(final LinkedList<Fightable> kis, final NonStatic enemy) {
		for (Fightable ki : kis) {
			if (Points.distBetweenTwoPoints(enemy.getCoord(), ki.getSpawnPoint()) 
					< ki.getAttributeValue(Attribute.viewRange) * VIEWRANGE_FACTOR) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param g Der Graphomat
	 */
	public static void addAttackStatesToGraph(final Graphomat g) {
		AttackCharacter self = new AttackCharacter();
		self.attackACharacterInRange.setName("Attackiere einen Charakter");
		
		
		self.attackableCharacterInRange.setGraphomat(g);
		self.attackableCharacterInRange.setName("Ist ein Gegner in Rechweite");
		
		self.isntCharacterStillInRange.setGraphomat(g);
		self.isntCharacterStillInRange.setName("Ist ein Gegner immernoch in Reichweite");
		g.addVertex(g.getCounterIdVertex(), self.attackACharacterInRange);
		self.attackACharacterInRange.setGraphomat(g);
		g.addArcFromAll(g.getCounterIdVertex() - 1, self.attackableCharacterInRange);
		g.addArc(g.getCounterIdVertex() - 1, g.getCounterIdVertex() - 2, self.isntCharacterStillInRange);
		
	}
}