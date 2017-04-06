package game.content.skills;


import settings.GlobalSettings;
import game.attributes.Attribute;
import game.attributes.AttributeValueList;
import game.content.effects.active.Following;
import game.content.effects.direct.Damage;
import game.content.effects.direct.Step;
import game.content.effects.duration.Burning;
import game.content.effects.duration.Freezing;
import game.content.effects.duration.Bleeding;
import game.content.effects.duration.Poisoned;
import game.content.ki.vasallen.Vasal;
import game.content.projectils.Arrow;
import game.content.projectils.Fish;
import game.content.projectils.Projectil;
import game.objects.NonStatic;
import game.skills.AimedOnNonStaticSkill;

/**
 * Die Standartattacke.
 * @author Tristan
 *
 */
public final class Attack extends AimedOnNonStaticSkill {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 695647284782168578L;

	/**
	 * Instanz.
	 */
	private static Attack instance = new Attack();
	
	/**
	 * @return Instanz
	 */
	public static Attack getInstance() {
		return instance;
	}
	
	/**
	 * Konstruktor.
	 */
	public Attack() {
		super("Angriff");
		this.effectsAllies = false;
		this.effectsEnemies = true;
		this.effectsHeroOnly = false;
		this.effectsSelf = false;
		this.shownInGui = false;
	}
	
//	@Override
//	public void execute(final NonStatic executer, final NonStatic target) {
//		super.execute(executer, target);
//	}
	
	@Override
	public int getRange(final NonStatic executer) {
		return executer.getAttributeValueList().getValue(Attribute.fightingRange);
	}

	
	@Override
	public int getCooldown(final NonStatic executer) {
		if (executer.getAttributeValueList().getValue(Attribute.fightingSpeed) != 0) {
			return (int) (10000 / executer.getAttributeValueList().getValue(Attribute.fightingSpeed));
		} else {
			return Integer.MAX_VALUE;
		}
	}
	@Override
	public void giveEffect(final NonStatic executer, final NonStatic effectedNonStatic) {
		

		Projectil projectil = null;
		
		if (GlobalSettings.MATCH_GENERATE_PROJECTILS && this.getRange(executer) > 2) {
			if (executer instanceof Vasal) {
				projectil = new Fish();
			} else {
				projectil = new Arrow();
			}
			projectil.send();
			new Step(projectil, executer.getCoord()).ready();
			new Following(projectil, effectedNonStatic, 0).ready();
		}
		
		
		switch (executer.getAttributeValue(Attribute.damageType)) {
		case 0:
			new Damage(effectedNonStatic, executer.getAttributeValueList().getValue(Attribute.damage)).ready();
			break;
		case 1:
			new Bleeding(effectedNonStatic, executer.getAttributeValueList().getValue(Attribute.damage)).ready();
			if (projectil != null) {
				new Bleeding(projectil, executer.getAttributeValueList().getValue(Attribute.damage)).ready();
			}
			break;
		case 2:
			new Burning(effectedNonStatic, executer.getAttributeValueList().getValue(Attribute.damage)).ready();
			if (projectil != null) {
				new Burning(projectil, executer.getAttributeValueList().getValue(Attribute.damage)).ready();
			}
			break;
		case 3:
			new Freezing(effectedNonStatic, executer.getAttributeValueList().getValue(Attribute.damage)).ready();
			if (projectil != null) {
				new Freezing(projectil, executer.getAttributeValueList().getValue(Attribute.damage)).ready();
			}
			break;
		case 4:
			new Poisoned(effectedNonStatic, executer.getAttributeValueList().getValue(Attribute.damage)).ready();
			if (projectil != null) {
				new Freezing(projectil, executer.getAttributeValueList().getValue(Attribute.damage)).ready();
			}
			break;
		
			
		default:
			break;
		
		}
		
	}

	@Override
	public AttributeValueList getEffectsAttributeValueList(final NonStatic executer) {
		AttributeValueList av = new AttributeValueList();
		av.setAttribute(Attribute.damage, executer.getAttributeValue(Attribute.damage));
		return av;
	}
	
}
