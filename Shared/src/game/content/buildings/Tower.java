package game.content.buildings;

import game.attributes.Attribute;
import game.content.effects.active.Moving;
import game.content.ki.tower.TowerKi;
import game.content.skills.Spawn;

import java.awt.Point;

/**
 * Der Turm der die Spieler der Gegnerischen Partei angreift.
 * @author Marius
 *
 */
public class Tower extends game.objects.Building {
	
	/**
	 * Die Ki de Turms.
	 */
	private TowerKi towerKi;

	/**
	 * 
	 */
	private static final long serialVersionUID = 3187983477709024364L;
	
	/**
	 * Groesse des Gebaeudes.
	 */
	public static final Point SIZE = new Point(2, 2);
	
	/**
	 * Konstruktor.
	 */
	public Tower() {
		this.size = SIZE;
		this.getAttributeValueList().setAttribute(Attribute.fightingRange, 10);
		this.getAttributeValueList().setAttribute(Attribute.viewRange, 10);
		this.getAttributeValueList().setAttribute(Attribute.damage, 25);
		this.getAttributeValueList().setAttribute(Attribute.fightingSpeed, 15);
		this.getAttributeValueList().setAttribute(Attribute.maxHealth, 50);
		this.getAttributeValueList().setAttribute(Attribute.currentHealth, 50);
		this.setImageURL("match", "tower", "tower");
	}

	/**
	 * @return the towerKi
	 */
	public TowerKi getTowerKi() {
		return towerKi;
	}
	
	/**
	 * Setzt den Turm in den Final state und laesst diesen Sterben.
	 */
	public void die() {
		super.die();
		
		this.towerKi.setGroupInFinalState(true);
		if (this.getNextAction() != null && !(this.getNextAction().getSkill() instanceof Spawn)) {
			this.getNextAction().end();
		}
		Moving.stopMoving(this);
	}

	/**
	 * @param towerKi the towerKi to set
	 */
	public void setTowerKi(final TowerKi towerKi) {
		this.towerKi = towerKi;
	}
}
