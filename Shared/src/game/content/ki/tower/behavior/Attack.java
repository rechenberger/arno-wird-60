package game.content.ki.tower.behavior;

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
import util.graphomat.Actions.Action;
import util.graphomat.Conditions.Condition;

/**
 * Fuegt dem Graphomaten der Tuerme die Mogelichkeit hinzu Gegnerische Einheiten anzugreifen.
 * @author Marius
 *
 */
public final class Attack implements Serializable {
	
	/**
	 * 
	 */
	private Attack() {
		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 7714024540075243742L;
	
	/**
	 * Ist ein Gegnerischer Character in Reichweite.
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
						if (n.getFraction() != member.getFraction() && n instanceof Character && n.getFraction() != Fraction.Arno) {
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
	 * Attackiere einen gegnerischen Character.
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
						if (n.getFraction() != member.getFraction() && n instanceof Character) {
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
							.contains(enemy)) {
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
			return true;
		}
	};
	
	/**
	 * 
	 * @return g Der Graphomat
	 */
	public static Graphomat addAttackStatesToGraph() {
		
		Attack self = new Attack();
		
		Graphomat g = new Graphomat();
		self.attackACharacterInRange.setName("Attackiere einen Charakter");
		self.attackableCharacterInRange.setName("Ist ein Gegner in Rechweite");
		self.isntCharacterStillInRange.setName("Ist ein Gegner immernoch in Reichweite");
		
		g.addVertex(0, new Action(g) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void planAction(final LinkedList<Fightable> kis) {
			}

		});
		g.addVertex(1, self.attackACharacterInRange);
		
		g.addArc(1, 0, self.isntCharacterStillInRange);
		g.addArc(0, 1, self.attackableCharacterInRange);
		g.setCurrentState(g.getVertex(0));
		
		return g;
		
	}

}
