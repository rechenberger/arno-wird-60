package game.actions;

import java.awt.Point;
import java.util.LinkedList;

import game.attributes.Attribute;
import game.content.effects.active.Moving;
import game.content.effects.active.MovingToPoint;
import game.content.skills.Spawn;
import game.effects.CooldownEffect;
import game.objects.GameObject;
import game.objects.Map;
import game.objects.NonStatic;
import game.skills.Skill;

/**
 * eine auszufuehrende Aktion.
 * @author Tristan
 */
public abstract class Action extends GameObject {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -2115630623223631649L;
	
	/**
	 * Ausfuehrer. (ID)
	 */
	protected final int executerId;
	
	/**
	 * Auszufuehrender Skill. (ID)
	 */
	protected int skillId;
	
	/**
	 * Gibt an ob eine Aktion nach seiner Ausfuehrung endet.
	 */
	protected boolean endAfterExecution = true;
	
	/**
	 * @return Ausfuehrer
	 */
	public NonStatic getExecuter() {
		NonStatic executer = NonStatic.getById(executerId);
		return executer;
	}
	
	
	/** 
	 *  Konstrukteur.
	 *  @param setExecuter Ausfuehrer
	 *  @param setSkill Auszufuehrender Skill
	 */
	public Action(final NonStatic setExecuter, final Skill setSkill) {
		this.executerId = setExecuter.getId();

		if (setSkill == null) {
			this.skillId = 0;
			return;
		} else if (setExecuter.getSkills().contains(setSkill) || setSkill instanceof game.skills.ItemSkill) {
			this.skillId = setSkill.getId();
			return;
		} else {
		
			for (Skill heroSkill : setExecuter.getSkills()) {
				if (heroSkill.getClass() == setSkill.getClass()) {
					this.skillId = heroSkill.getId();
					return;
				}
			}
			System.err.println("Ausfuehrer " + setExecuter + " hat Skill " + setSkill + " nicht!");
		}
	}
	
	/** 
	 *  Plant die Aktion indem er sie dem Ausfuehrer als naechste geplante Aktion markiert.
	 *  Falls diese Methode vom Clienten aufgerufen wird. Wird die Aktion an den Server gesendet.
	 */
	public void plan() {
		if (!this.getExecuter().isAlive() && !(this.getSkill() instanceof Spawn)) {
			this.end();
			return;
		}
		if (matchModule.isClient()) {
			this.send();
		} 
		this.getExecuter().setNextAction(this);
	}

	/** 
	 *  Gibt zurueck ob Aktion ausfuehrbar ist.
	 *  @return boolean
	 */
	public final boolean isPossible() {
		// TODO
		return true;
	}
	
	/** 
	 *  Fuehrt die Aktion aus.
	 *  Testet ob in Reichweite.
	 *  Testet ob Cooldown aktiv.
	 */
	public void execute() {
		if (!this.getExecuter().isAlive() && !(this.getSkill() instanceof Spawn)) {
			this.end();
			return;
		}
		
		if (Map.getDistance(this.getExecuter().getCoord(), this.getTargetPoint()) <= this.getSkill().getRange(this.getExecuter())) {
			// In Reichweite
			
			if (CooldownEffect.getCooldownOfSkill(this.getExecuter(), this.getSkill()) <= 0) {

				this.executeSkill();
				this.getSkill().cooldown(this.getExecuter());
				if (this.endAfterExecution) {
					this.end();
				}
				
			}
		} else {
			// Nicht in Reichweite
			if (this.getExecuter().getAttributeValue(Attribute.movementSpeed) > 0) {
				this.getInRange();
			} else {
				this.end();
			}
		}
	}
	
	/**
	 * Beendet die Aktion.
	 */
	public void end() {
		if (this.getExecuter().getNextAction() == this) {
			this.getExecuter().removeNextAction();
		}
		this.unregisterGameObject();
	}


	/**
	 * Bringt den Ausfuehrer in Reichweite.
	 */
	protected void getInRange() {
		if (!MovingToPoint.hasEffect(getExecuter(), this.getTargetPoint())) {
			Moving.stopMoving(this.getExecuter());
			new MovingToPoint(this.getExecuter(), this.getTargetPoint(), this.getRange()).ready();
		}
	}


	/**
	 * 
	 * @return Reichweite
	 */
	protected final int getRange() {
		return this.getSkill().getRange(this.getExecuter());
	}


	/**
	 * @return Betroffene NonStatics
	 */
	public LinkedList<NonStatic> getEffects() {
		return matchModule.getMatch().getMap().getFightablesInCircle(this.getTargetPoint(), this.getSkill().getRange(getExecuter()));
	}
	
	/**
	 * @return Koordinaten des Ziels.
	 */
	public abstract Point getTargetPoint();
	
	/**
	 * @return Skill
	 */
	public Skill getSkill() {
		Skill skill = Skill.getById(skillId);
		return skill;
	}

	/**
	 * Fuehrt den Skill aus.
	 */
	public abstract void executeSkill();
	
	@Override
	public void afterRecieve() {
		super.afterRecieve();
		if (!matchModule.isClient()) {
			this.plan();
		}
	}
	
}
