
package game.content.ki.hero.behavior.attack;

import game.actions.AttackAction;
import game.attributes.Attribute;
import game.content.heros.Hero;
import game.objects.Character;
import game.objects.Fightable;
import game.objects.Fraction;
import game.objects.Map;
import game.objects.NonStatic;
import game.skills.AimedOnNonStaticSkill;
import game.skills.Skill;

import java.util.LinkedList;

/**
 * Angriff in Kampf.
 * @author Marius
 *
 */
public class AttackInBatttle {
	/**
	 * Fuegt dem Graphomaten die Action hinzu einen Character des Gegnerischen Teams in der Umgebung anzugreifen.
	 */
	@SuppressWarnings("unused")
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
					if (member instanceof Hero) {
						for (Skill s : member.getSkills()) {
							if (s instanceof AimedOnNonStaticSkill) {
								((AimedOnNonStaticSkill) s).getEffectsAttributeValueList(member);
							}
						}
					}
					new AttackAction(member, toAttack).plan();
					this.setEnemy(toAttack);
					this.setCoordOfAttack(kis.get(0).getCoord());
				}
			}

		}

	};
}
