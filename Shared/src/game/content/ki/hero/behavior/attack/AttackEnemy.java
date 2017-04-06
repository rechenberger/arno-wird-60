package game.content.ki.hero.behavior.attack;

import game.actions.AttackAction;
import game.attributes.Attribute;
import game.attributes.AttributeValueList;
import game.content.buildings.Shop;
import game.content.effects.active.Moving;
import game.content.heros.Hero;
import game.content.skills.Spawn;
import game.objects.Character;
import game.objects.Fightable;
import game.objects.Fraction;
import game.objects.Map;
import game.objects.NonStatic;
import game.skills.AimedOnNonStaticSkill;
import game.skills.Skill;

import java.io.Serializable;
import java.util.LinkedList;

import util.Graphomat;
import util.graphomat.Conditions.Condition;

/**
 * fuegt einem Graphomaten eines Helden die Zustaende zum attackieren hinzu. 
 * @author Marius
 *
 */
public final class AttackEnemy implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3842212794372038063L;



	/**
	 * Utilityclass.
	 */
	private AttackEnemy() {
		
	}
	
	
	/**
	 * Fuegt dem Graphomaten die Action hinzu einen Character des Gegnerischen
	 * Teams in der Umgebung mit Skills anzugreifen.
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
						if (n.getFraction() != member.getFraction()
								&& n.getFraction() != Fraction.Arno
								&& (n instanceof Character || n instanceof game.content.buildings.Tower)) {
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
					Skill highestDmgSkill = null;
					if (member instanceof Hero) {
						for (Skill s : member.getSkills()) {
							int dmg = 0;
							if (s instanceof AimedOnNonStaticSkill) {
								if (!s.isCoolingDown(member)
										&& s.getMana(member) < member
												.getAttributeValue(Attribute.currentMana)) {
									AttributeValueList avl = ((AimedOnNonStaticSkill) s).getEffectsAttributeValueList(member);
									if (-1 * avl.getValue(Attribute.currentHealth) > dmg) {
										highestDmgSkill = s;
									}
								}
							}
						}
					}
					if (highestDmgSkill != null) {
						if (highestDmgSkill instanceof AimedOnNonStaticSkill) {
							((AimedOnNonStaticSkill) highestDmgSkill).execute(
									member, toAttack);
							System.out.println(highestDmgSkill.getName() + "Excecuted by Player " + ((Hero) member).getPlayer().getName());
						}
					} else {
						new AttackAction(member, toAttack).plan();
					}

					this.setEnemy(toAttack);
					this.setCoordOfAttack(kis.get(0).getCoord());
				}
			}

		}

	};
	
	
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
						if (n.getFraction() != member.getFraction() && !(n instanceof Shop)) {
							
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
		AttackEnemy self = new AttackEnemy();
		
		self.attackableCharacterInRange.setGraphomat(g);
		self.isntCharacterStillInRange.setGraphomat(g);
		g.addVertex(g.getCounterIdVertex(), self.attackACharacterInRange);
		self.attackACharacterInRange.setGraphomat(g);
		g.addArcFromAll(g.getCounterIdVertex() - 1, self.attackableCharacterInRange);
		g.addArc(g.getCounterIdVertex() - 1, g.getCounterIdVertex() - 1, self.attackableCharacterInRange);
		g.addArc(g.getCounterIdVertex() - 1, 0, self.isntCharacterStillInRange);
		
	}
}
