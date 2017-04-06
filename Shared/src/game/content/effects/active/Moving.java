package game.content.effects.active;

import game.attributes.Attribute;
import game.content.effects.direct.HeadTo;
import game.content.effects.direct.Step;
import game.content.projectils.Projectil;
import game.content.skills.Move;
import game.effects.ActiveEffect;
import game.effects.CooldownEffect;
import game.effects.Effect;
import game.objects.Map;
import game.objects.NonStatic;
import game.objects.Route;
import game.objects.RouteFlying;
import game.objects.RouteInterface;

import java.awt.Point;
import java.util.LinkedList;

/**
 * Aktiver Effekt.
 * Das betroffene Nonstatic bewegt sich momentan auf einen Punkt oder ein anderes NonStatic zu.
 * @author Tristan
 *
 */
public abstract class Moving extends ActiveEffect {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -4245400945761605079L;
	
	/**
	 * Route.
	 * TODO initialisieren.
	 */
	protected RouteInterface route;
	
	/**
	 * Ob NonStatic fliegt.
	 */
	protected boolean flying = false;
	
	/**
	 * Die Entfernung die am Ende des Bewegungsablaufs zum Ziel unterschritten werden soll.
	 */
	private int range = 0;
	
	/**
	 * Privater Konstruktor.
	 * Seichert die Parameter in den Attributen des Objekts.
	 * @param effects NonStatic deren Bewegung beschrieben wird.
	 * @param setRange Die Entfernung die am Ende des Bewegungsablaufs zum Ziel unterschritten werden soll.
	 */
	protected Moving(final NonStatic effects, final int setRange) {
		super(effects);
		this.flying = this.getEffects().getAttributeValue(Attribute.flying) == 1;
		this.range = setRange;
		if (this.flying) {
			route = new RouteFlying();
		} else {
			route = new Route();
		}
	}
	
	/**
	 * Synchronisiert Aufenthalsort und Ziel mit der Route.
	 */
	protected void updateRoute() {
		
		// Setzt die aktuellen Koordinaten, falls nicht synchron
		if (this.route.getCurrentCoord() != this.getEffects().getCoord()) {
			this.route.setCurrentCoord(this.getEffects().getCoord());
		}

		// Setzt das aktuelle Ziel, falls nicht synchron
		if (this.route.getDestination() != this.getDestination()) {
			this.route.setDestination(this.getDestination());
		}
		
	}
	
	/**
	 * 
	 * @return Ziel
	 */
	public abstract Point getDestination();
	
	/**
	 * Erschafft den direkten Effekt Step, indem er die Route nach naechste Zwischenzielkoordinate fragt.
	 * @see game.content.effects.direct.Step
	 */
	public void doStep() {
		
		new Step(this.getEffects(), this.route.doNextStep()).ready();
	}
	
	@Override
	public void execute() {
		if (CooldownEffect.getCooldownOfSkill(this.getEffects(), Move.getInstance()) > 0) {
			return;
		}
		
		int test = this.route.getDistanceToGo();
		if (test <= range) {
			this.end();
			return;
		} else {
			Point nextStep = this.route.getNextStep();
			if (nextStep == null) {
//				System.out.println("Stehe hier ohne Route auf: " + matchModule.getMatch().getMap().getList(this.getEffects().getCoord()).getFirst().toString() + this.getEffects().getCoord().toString());
				this.end();
				return;
			}
			
			int movementSpeed = this.getEffects().getAttributeValueList().getValue(Attribute.movementSpeed);
			if (!this.flying) {
				movementSpeed = (int) (movementSpeed * matchModule.getMatch().getMap().getMovementSpeedFactor(nextStep));
			}
				
			if (movementSpeed == 0) {
				this.route.setupNewRoute(this.getEffects().getCoord(), this.getDestination());
				return;
			} else {
				this.doStep();
				// 10 Sekunden durch Geschwindigkeit
				int sleepTimer = (int) (10000 / movementSpeed);
				new CooldownEffect(this.getEffects(), Move.getInstance(), sleepTimer).ready();
//				this.setSleepTimer(sleepTimer);
//				Move.getInstance().cooldown(this.getEffects());
				return;
			}
		}
	}
	
	/**
	 * @param nonStatic ein Nonstatic
	 * @return ob das NonStatic diesen Effekt hat.
	 */
	public static boolean hasEffect(final NonStatic nonStatic) {
		for (Effect effect : nonStatic.getEffects()) {
			if (effect instanceof Moving) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Entfernt alle Bewegungseffekte.
	 * @param nonStatic Nonstatic
	 */
	public static void stopMoving(final NonStatic nonStatic) {
		LinkedList<Effect> toEnd = new LinkedList<Effect>();
		for (Effect effect : nonStatic.getEffects()) {
			if (effect instanceof Moving) {
				toEnd.add(effect);
			}
		}
		for (Effect effect : toEnd) {
			effect.end();
		}
	}
	
	@Override
	// TODO testen
	public void ready() {
		Moving.stopMoving(this.getEffects());
		super.ready();
		if (!matchModule.isClient() && this.flying) {
			new HeadTo(this.getEffects(), Map.directionToAngle(this.getEffects().getCoord(), this.getDestination())).ready();
		}
			
	}

	@Override
	public void end() {
		super.end();
		if (this.getEffects() instanceof Projectil) {
			this.getEffects().die();
		}
	}
	
	@Override
	public void unregisterGameObject() {
		super.unregisterGameObject();
		if (this.route != null) {
			this.route.unregisterGameObject();
//			this.route = null;
		}
	}
}
