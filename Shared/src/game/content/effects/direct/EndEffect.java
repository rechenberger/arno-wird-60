package game.content.effects.direct;

import game.effects.Effect;
import game.objects.NonStatic;

/**
 * Direkter Effekt um einenen anderen Effekt zu beenden.
 * @author Tristan
 *
 */
public class EndEffect extends game.effects.DirectEffect {

	/**
	 * 
	 */
	private static final long serialVersionUID = -682552279296126264L;

	/**
	 * Id des zu beendenden Effekt.
	 */
	private int effectId;

	/**
	 * Konstrukor.
	 * @param effects betroffenes Nonstatic
	 * @param effectToEnd Effekt der beendet werden soll.
	 */
	public EndEffect(final NonStatic effects, final Effect effectToEnd) {
		super(effects);
		this.effectId = effectToEnd.getId();
	}
	
	/**
	 * Beendet den zu beendenden Effekt.
	 */
	public void execute() {
		Effect effectToEnd = this.getEffect();
		if (matchModule.isClient() && effectToEnd != null) {
			effectToEnd.end();
		}
	}
	
	/**
	 * @return zu beendender Effekt
	 */
	public Effect getEffect() {
		Effect effect = Effect.getById(this.effectId);
		return effect;
	}
}
