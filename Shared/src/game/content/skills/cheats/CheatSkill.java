package game.content.skills.cheats;

import game.attributes.AttributeValueList;
import game.content.heros.Hero;
import game.objects.NonStatic;
import game.skills.NotAimedSkill;

/**
 * Skill zum cheaten.
 * @author Tristan
 */
public class CheatSkill extends NotAimedSkill {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -3495852702546157198L;

	/**
	 * Konstrukor. 
	 * @param setName Name des Cheats.
	 */
	public CheatSkill(final String setName) {
		super(setName);
		this.effectsAllies = true;
		this.effectsEnemies = false;
		this.effectsHeroOnly = true;
		this.effectsSelf = true;
		this.radius = 0;
		this.shownInGui = false;
	}
	
	@Override
	public void execute(final NonStatic executer) {
		super.execute(executer);
		if (executer instanceof Hero) {
			((Hero) executer).getPlayer().incStatistic("CheatsUsed");
		}
	}
	
	@Override
	public AttributeValueList getEffectsAttributeValueList(final NonStatic executer) {
		return new AttributeValueList();
	}
	
}
