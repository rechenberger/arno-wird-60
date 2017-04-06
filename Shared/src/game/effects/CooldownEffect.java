package game.effects;
import game.objects.NonStatic;
import game.skills.Skill;

/**
 * Speichert wie lange ein Skill nicht benutzt werden darf.
 * @author Tristan
 *
 */
public class CooldownEffect extends DurationEffect {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 7441765499740717548L;


	/**
	 * betroffener Skill (ID).
	 */
	private final int skillId;

	/**
	 * 
	 * @param effects betroffenes NonStatic
	 * @param setSkill betroffener Skill
	 * @param millisecounds Ablinkzeit (in Millisekunden)
	 */
	public CooldownEffect(final NonStatic effects, final Skill setSkill, final int millisecounds) {
		super(effects, millisecounds);
		this.skillId = setSkill.getId();
	}
	
	/**
	 * @return betroffener Skill.
	 */
	public Skill getSkill() {
		Skill skill = Skill.getById(this.skillId);
		return skill;
	}

	
	/**
	 * @param nonStatic NonStatic.
	 * @param skill Skill.
	 * @return verbleibende Ablinkzeit (in Millisekunden)
	 */
	public static int getCooldownOfSkill(final NonStatic nonStatic, final Skill skill) {
		CooldownEffect ce = getCooldownEffectOfSkill(nonStatic, skill);
		if (ce != null) {
			return (int) ce.getTimeDurationLeft();
		} else {
			return 0;
		}
	}
	
	/**
	 * @param nonStatic NonStatic.
	 * @param skill Skill.
	 * @return Ablinkzeit (relativ)
	 */
	public static float getCooldownRatioOfSkill(final NonStatic nonStatic, final Skill skill) {
		CooldownEffect ce = getCooldownEffectOfSkill(nonStatic, skill);
		if (ce != null) {
			return ce.getTimeDurationLeftRatio();
		} else {
			return 0;
		}
	}
	
	/**
	 * @param nonStatic NonStatic.
	 * @param skill Skill.
	 * @return Ablinkzeit (in Millisekunden)
	 */
	public static CooldownEffect getCooldownEffectOfSkill(final NonStatic nonStatic, final Skill skill) {
		for (Effect e : nonStatic.getEffects()) {
			if (e instanceof CooldownEffect) {
				CooldownEffect ce = (CooldownEffect) e;
				
				Skill skillToCoolDown = Skill.getById(ce.skillId);
				if (skillToCoolDown.getClass() == skill.getClass()) {
					return ce;
				}
			}
		}
		return null;
	}
	
}
