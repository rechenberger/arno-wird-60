package game.content.heros.bruce;

import game.attributes.AttributeValueList;
import game.objects.NonStatic;
import game.skills.NotAimedSkill;


/**
 * Schleichen, der Skill.
 * @author Alex
 *
 */
public class SchleichenSkill extends NotAimedSkill {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor. Initialisiert die effects-boolean.
	 */
	public SchleichenSkill() {
		// Der Skill heisst Aufpumpen.
			super("Schleichen");
		// Nur er selber wird betroffen.
			this.effectsSelf = true;
			this.effectsHeroOnly = true;
			this.effectsAllies = true;
			this.effectsEnemies = false;
		// 0 Felder Radius
			this.radius = 0;
		// 0 Felder Reichweite.
			this.range = 0;
		// 0 Sekunden Castzeit
			this.cast = 0 * 1000;
		// 10 Sekunden Cooldown
			this.cooldown = 10 * 1000; 
	}
	
	@Override
	public void giveEffect(final NonStatic executer, final NonStatic effectedNonStatic) {
		// Kreiert den entsprechenden Effekt und ruft seine Methode ready() auf.
		new SchleichenEffect(effectedNonStatic, executer.getSkillLevel(this)).ready();
	}
	
	@Override
	public AttributeValueList getEffectsAttributeValueList(final NonStatic executer) {
		return new AttributeValueList();
	}

}

