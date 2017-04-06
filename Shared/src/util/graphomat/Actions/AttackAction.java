package util.graphomat.Actions;

import game.objects.Fightable;
import game.objects.NonStatic;

import java.awt.Point;
import java.io.Serializable;
import java.util.LinkedList;

import util.Graphomat;

/**
 * 
 * @author Marius
 *
 */
public class AttackAction extends Action implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1276215415543387815L;

	/**
	 * Der Gegner der Angegriffen werden soll.
	 */
	private NonStatic enemy;
	
	/**
	 * Von wo aus angegriffen wurde.
	 */
	private Point coordOfAttack;
	
	/**
	 * Konstruktor.
	 * @param gr Der Graphomat
	 */
	public AttackAction(final Graphomat gr) {
		super(gr);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void planAction(final LinkedList<Fightable> kis) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @return the enemy
	 */
	public NonStatic getEnemy() {
		return enemy;
	}

	/**
	 * @param e the enemy to set
	 */
	public void setEnemy(final NonStatic e) {
		enemy = e;
	}

	/**
	 * @return the coordOfAttack
	 */
	public Point getCoordOfAttack() {
		return coordOfAttack;
	}

	/**
	 * @param coordOfAttack the coordOfAttack to set
	 */
	public void setCoordOfAttack(final Point coordOfAttack) {
		this.coordOfAttack = coordOfAttack;
	}

}
