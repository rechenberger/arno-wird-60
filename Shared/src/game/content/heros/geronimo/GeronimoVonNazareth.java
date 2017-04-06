package game.content.heros.geronimo;

import java.awt.Point;

import game.attributes.Attribute;
import game.content.heros.Hero;

/**
 * Geronimo Von Nazareth.
 * @author Tristan
 *
 */
public class GeronimoVonNazareth extends Hero {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 8951174708970493123L;


	/**
	 * Konstruktor. Initialisert den Helden mit Namen, Attributen und Faehigkeiten.
	 */
	public GeronimoVonNazareth() {
		this.name = "Geronimo von Nazareth";
		this.getAttributeValueList().setAttribute(Attribute.fightingRange, 1);
		this.getAttributeValueList().setAttribute(Attribute.movementSpeed, 40);
		this.getAttributeValueList().setAttribute(Attribute.money, 10);
		this.getAttributeValueList().setAttribute(Attribute.damageType, 3);
		this.getAttributeValueList().setAttribute(Attribute.flying, 0);
		this.addSkill(new AbendmahlSkill(), 1);
		this.addSkill(new ErleuchtungSkill(), 1);
		this.addSkill(new OpfergabeSkill(), 1);
		this.addSkill(new GesalbteFuesseSkill(), 1);
		this.setImageURL("match", "hero", "geronimo");
	}
	
	@Override
	public Point getSpawnPoint() {
		if (this.getPlayer() != null && this.getPlayer().getStatistic("Deaths") >= 0) {
			return this.getCoord();
		} else {
			return super.getSpawnPoint();
		}
	}
}
