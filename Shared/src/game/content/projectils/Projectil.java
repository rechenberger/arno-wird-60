package game.content.projectils;

import game.attributes.Attribute;
import game.content.effects.active.Moving;
import game.effects.Effect;
import game.objects.Fraction;
import game.objects.NonStatic;

/**
 * Projektile.
 * @author Tristan
 *
 */
public abstract class Projectil extends NonStatic {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -700383742980542990L;
	
	/**
	 * Konstrukor.
	 */
	public Projectil() {
		this.setFraction(Fraction.Neutral);
		this.movementSpeedFactor = 1;
		this.getAttributeValueList().setAttribute(Attribute.flying, 1);
		this.getAttributeValueList().setAttribute(Attribute.movementSpeed, 100);
		this.getAttributeValueList().setAttribute(Attribute.maxHealth, 1000000);
		this.getAttributeValueList().setAttribute(Attribute.currentHealth, 1000000);
	}
	
	@Override
	public void afterRecieve() {
//		System.out.println("Neues Projektil");
		super.afterRecieve();
	}
	
	@Override
	public void die() {
		super.die();
//		for (Effect e : ((LinkedList<Effect>) this.getEffects().clone())) {
//			e.end();
//		}
//		this.unregisterGameObject();
	}
	
	@Override
	public void afterValueListUpdate() {
		int i = 0;
		for (Effect e : this.effects) {
			if (e instanceof Moving) {
				i++;
			}
		}
		if (i > 1) {
			System.err.println(this + " hat " + i + " Moving-Effekte");
		}
		
//		if (this.isAlive() && this.effects.isEmpty()) {
//			this.die();
//			this.unregisterGameObject();
//		}
	}

}
