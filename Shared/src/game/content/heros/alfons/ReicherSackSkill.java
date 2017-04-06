package game.content.heros.alfons;

import game.attributes.AttributeValueList;
import game.objects.NonStatic;

/**
 * Reicher Sack, der Skill.
 * @author Tristan
 *
 */
public class ReicherSackSkill extends game.skills.PassiveSkill {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -2593697526891849152L;

	/**
	 * Konstruktor. Initialisiert die effects-boolean.
	 */
	public ReicherSackSkill() {
		super("Reicher<br>Sack");
		this.effectsSelf = true;
		this.effectsHeroOnly = true;
		this.effectsAllies = true;
		this.effectsEnemies = false;
		this.radius = 0;
		this.range = 10;
		
		this.setImageURL("usercontrols", "skill", "alfons/sack");
	}
	
	@Override
	public void giveEffect(final NonStatic executer, final NonStatic effectedNonStatic) {
		// Kreiert den entsprechenden Effekt und ruft seine Methode ready() auf.
		new ReicherSackEffect(effectedNonStatic, executer.getSkillLevel(this)).ready();
		//TODO Funktioniert noch nicht..
	}

	@Override
	public AttributeValueList getEffectsAttributeValueList(final NonStatic executer) {
		return new AttributeValueList();
	}
}
