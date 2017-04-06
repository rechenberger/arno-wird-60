package game.objects;
import game.actions.Action;
import game.attributes.Attribute;
import game.attributes.AttributeValueList;
import game.content.effects.active.Moving;
import game.content.items.useable.UseableItem;
import game.content.statics.WayPoints;
import game.effects.DirectEffect;
import game.effects.Effect;
import game.effects.ItemEffect;
import game.skills.Skill;

import java.awt.Point;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Jene WorldObjects, die sich im Laufe des Spiels veraendern.
 * @author Tristan
 * 
 */
public class NonStatic extends WorldObject {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 279546233400101670L;
	
	/**
	 * Speichert die naechste geplante Aktion.
	 */
	private Action nextAction;
	
	/**
	 * Fraktion.
	 * @see game.objects.Fraction
	 */
	private Fraction fraction;
	
	
	
	/**
	 * Konstruktor. 
	 * Ruft super() auf.
	 */
	public NonStatic() {
		super();
	}

	////////// Skills //////////
	
	/**
	 * Speichert einen Verweis auf Skill inkl. Level
	 * @author Tristan
	 *
	 */
	private class NonStaticSkill implements Serializable {
		/**
		 * serialVersionUID.
		 */
		private static final long serialVersionUID = 3804997289760231830L;
		/**
		 * Der Skill.
		 */
		private Skill skill;
		/**
		 * Das Level.
		 */
		private int level = 1;
		/**
		 * Konstruktor.
		 * @param setSkill Der Skill.
		 */
		public NonStaticSkill(final Skill setSkill) {
			this.skill = setSkill;
		}
		/**
		 * Skill-Getter.
		 * @return skill
		 */
		public Skill getSkill() {
			return this.skill;
		}
		/**
		 * Level-Getter.
		 * @return level
		 */
		public int getLevel() {
			return this.level;
		}
	}
	/**
	 * Liste aller beherschten Skills.
	 */
	protected LinkedList<NonStaticSkill> skills = new LinkedList<NonStaticSkill>(); 
	
	/**
	 * Fuegt Skill hinzu.
	 * @param addSkill hinzuzufuegeneder Skill
	 */
	protected void addSkill(final Skill addSkill) {
		if (this.hasSkill(addSkill)) {
			return;
		}
		NonStaticSkill tmp = new NonStaticSkill(addSkill);
		this.skills.add(tmp);
	}
	/**
	 * Fuegt Skill hinzu.
	 * @param addSkill hinzuzufuegeneder Skill
	 * @param skillLevel Skilllevel
	 */
	protected void addSkill(final Skill addSkill, final int skillLevel) {
		this.addSkill(addSkill);
		this.getNonStaticSkill(addSkill).level = skillLevel;
	}
	/**
	 * Ueberprueft ob Skill beherscht wird.
	 * @param skill zu ueberprufender Skill
	 * @return boolean
	 */
	public boolean hasSkill(final Skill skill) {
		return this.getSkillLevel(skill) >= 0;
	}
	
	/**
	 * 
	 * @return Liste aller Skills die das NonStatic beherscht.
	 */
	public LinkedList<Skill> getSkills() {
		LinkedList<Skill> list = new LinkedList<Skill>();
		for (NonStaticSkill nss : skills) {
			list.add(nss.getSkill());
		}
		return list;
	}
	
	/**
	 * 
	 * @param skill Skill
	 * @return zugehoeriger NonStaticSkill
	 */
	private NonStaticSkill getNonStaticSkill(final Skill skill) {
		for (NonStaticSkill nss : skills) {
			if (nss.getSkill().equals(skill)) {
				return nss;
			}
		}
		return null;
	}

	/**
	 * Gibt das Skilllevel eines Skills zurueck.
	 * @param skill hinzuzufuegeneder Skill 
	 * @return int Skilllevel (-1, wenn Skill nicht beherrscht)
	 */
	public int getSkillLevel(final Skill skill) {
		NonStaticSkill nss = this.getNonStaticSkill(skill);
		if (nss == null) {
			return -1;
		} else {
			return nss.getLevel();
		}
	}
	
	/**
	 * 
	 * @param addSkill Skill
	 * @param skillLevel Level
	 */
	public void setSkillLevel(final Skill addSkill, final int skillLevel) {
		this.addSkill(addSkill, skillLevel);
	}
	
//	public void executeSkill(Skill skill) {
//		skill.execute(this);
//	}

	/**
	 * Ueberprueft ob NonStatic in Reichweite fuer Skill.
	 * @param skill auszufuehrender Skill
	 * @param target Ziel des Skills
	 * @return boolean
	 */
	public boolean inRange(final Skill skill, final NonStatic target) {
		// TODO
		return true;
	}

	/**
	 * Ueberprueft ob NonStatic in Reichweite fuer Skill.
	 * @param skill auszufuehrender Skill
	 * @param target Ziel des Skills
	 * @return boolean
	 */
	public boolean inRange(final Skill skill, final Point target) {
		// TODO
		return true;
	}

	////////// Effekte //////////
	/**
	 * Liste aller auf das NonStatic wirkenden nicht direkten Effekte.
	 */
	protected LinkedList<Effect> effects = new LinkedList<Effect>(); 
	
	/**
	 * Fuege Effekt hinzu.
	 * @param effect hinzuzufuegender Effekt.
	 */
	public void addEffect(final Effect effect) {
		if (!(effect instanceof DirectEffect)) {
			this.effects.add(effect);
		}
		this.attributeValueList.addAttributeValueList(effect.getAttributeValueList());
		this.afterValueListUpdate();
	}
	
	/**
	 * Entferne einen Effekt.
	 * @param effect zu entfernender Effekt
	 */
	public void removeEffect(final Effect effect) {
		if (!(effect instanceof DirectEffect)) {
			this.effects.remove(effect);
			this.attributeValueList.removeAttributeValueList(effect.getAttributeValueList());
		}
	}
	
	/**
	 * @return Liste aller nicht direkten Effekte.
	 */
	public LinkedList<Effect> getEffects() {
		return this.effects;
	}
	
	/**
	 * 
	 * @param effect Effekt
	 * @return Ob NonStatic einen Effekt der gleichen Klasse hat.
	 */
	public boolean hasEffectByClass(final Effect effect) {
		return this.hasEffectByClass(effect.getClass());
	}
	
	/**
	 * 
	 * @param effectClass Klasse des Effekts
	 * @return Ob NonStatic einen Effekt dieser Klasse hat.
	 */
	@SuppressWarnings("rawtypes")
	public boolean hasEffectByClass(final Class effectClass) {
		if (this.effects != null) {
			for (Effect e : this.effects) {
				if (e.getClass() == effectClass) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * entfernt Effekt gleichen Klasse.
	 * @param effect Effekt
	 */
//	public void removeEffectByClass(final Effect effect) {
//		for (Effect e : this.effects) {
//			if (e.getClass() == effect.getClass()) {
//				this.removeEffect(e);
//				return;
//			}
//		}
//		return;
//	}
//	
	/**
	 * @param item Item
	 * @return Wieviele Items dieser Art das NonStatic besitzt.
	 */
	public int hasItem(final Item item) {
		int i = 0;
		for (Effect e : this.effects) {
			if (e instanceof ItemEffect && ((ItemEffect) e).getItem().getClass() == item.getClass()) {
				i++;
			}
		}
		return i;
	}
	
	/**
	 * @param item zu entfernendes Item.
	 */
	public void removeItem(final Item item) {
		for (Effect e : this.effects) {
			if (e instanceof ItemEffect && ((ItemEffect) e).getItem().getClass() == item.getClass()) {
//				this.removeEffect(e);
				e.end();
				return;
			}
		}
		return;
	}
	
	/**
	 * @return Liste mit Items und ihrer Anzahl im Inventar.
	 */
	public HashMap<Item, Integer> getInventory() {
		HashMap<Item, Integer> inventory = new HashMap<Item, Integer>();
		for (Effect e : this.effects) {
			if (e instanceof ItemEffect) {
				Item item = ((ItemEffect) e).getItem();
				int i = 0;
				if (inventory.containsKey(item)) {
					i = inventory.get(item);
				}
				inventory.put(item, ++i);
			}
		}
		
		return inventory;
		
	}
	
	/**
	 * @return Liste mit benutzbaren Items und ihrer Anzahl im Inventar.
	 */
	public HashMap<Item, Integer> getInventoryUsable() {
		HashMap<Item, Integer> inventory = getInventory();
		HashMap<Item, Integer> inventoryUsable = new HashMap<Item, Integer>();
		for (Item item : inventory.keySet()) {
			if (item instanceof UseableItem) {
				inventoryUsable.put(item, inventory.get(item));
			}
		}
		return inventoryUsable;	
	}
	
	/**
	 * @return Liste mit nicht benutzbaren Items und ihrer Anzahl im Inventar.
	 */
	public HashMap<Item, Integer> getInventoryNonUsable() {
		HashMap<Item, Integer> inventory = getInventory();
		HashMap<Item, Integer> inventoryNonUsable = getInventory();
		for (Item item : inventory.keySet()) {
			if (!(item instanceof UseableItem)) {
				inventoryNonUsable.put(item, inventory.get(item));
			}
		}
		return inventoryNonUsable;	
	}
	
	////////// Aktion //////////
	/**
	 * Aktion-Getter.
	 * @return action naechste geplante Aktion.
	 */
	public Action getNextAction() {
		return this.nextAction;
	}
	/**
	 * Aktion-Setter.
	 * @param action naechste geplante Aktion.
	 */
	public void setNextAction(final Action action) {
		Moving.stopMoving(this);
		if (this.nextAction != null) {
			this.nextAction.end();
		}
		this.nextAction = action;
	}
	
	////////// Attribute //////////
	
	/**
	 * Liste aller Attribute und ihrer Werte.
	 */
	private AttributeValueList attributeValueList = new AttributeValueList();

	/**
	 * Gibt die AttributeValueList zurueck.
	 * @return AttributeValueList
	 */
	public AttributeValueList getAttributeValueList() {
		return attributeValueList;
	}
	

	/**
	 * Gibt den Attributwert aus der AttributeValueList zurueck.
	 * @param attribute Attribut
	 * @return Wert des Attributs
	 */
	public int getAttributeValue(final Attribute attribute) {
		return this.getAttributeValueList().getValue(attribute);
	}

	/**
	 * Entfernt die naechste Aktion.
	 */
	public void removeNextAction() {
		this.nextAction = null;		
	}
	
	/**
	 * Gibt alle NonStatics zurueck.
	 * @return Liste aller NonStatics.
	 */
	public static LinkedList<NonStatic> getAllNonStatics() {
		LinkedList<NonStatic> list = GameObject.getGameObjectsByClassName("NonStatic");
		return list;
	}

	/**
	 * @return Fraktion
	 * @see game.objects.Fraction
	 */
	public Fraction getFraction() {
		return fraction;
	}

	/**
	 * @param setFraction Fraktion
	 * @see game.objects.Fraction
	 */
	public void setFraction(final Fraction setFraction) {
		this.fraction = setFraction;
	}
	
	/**
	 * Wird aufgerufen nachdem ein Effekt die ValueList veraendert hat.
	 */
	public void afterValueListUpdate() {
//		System.out.println(this.toString() + " hat jetzt neue Werte");
		if (this.getAttributeValue(Attribute.currentHealth) > this.getAttributeValue(Attribute.maxHealth)) {
			this.attributeValueList.setAttribute(Attribute.currentHealth, this.getAttributeValue(Attribute.maxHealth));
		}

		if (this.attributeValueList.hasAttribute(Attribute.currentMana) && this.getAttributeValue(Attribute.currentMana) > this.getAttributeValue(Attribute.maxMana)) {
			this.attributeValueList.setAttribute(Attribute.currentMana, this.getAttributeValue(Attribute.maxMana));
		}

		if (this.getAttributeValue(Attribute.currentHealth) <= 0) {
			if (!matchModule.isClient() && this.isAlive()) {
				this.die();
				
				if (!(this instanceof Building)) {
					new game.actions.ActionNotAimed(this, game.content.skills.Spawn.getInstance()).plan();
//					this.spawn();
				}
			}
		} 

		if (this.attributeValueList.hasAttribute(Attribute.currentMana) && this.getAttributeValue(Attribute.currentMana) < 0) {
			this.attributeValueList.setAttribute(Attribute.currentMana, 0);
		}
	}
	
	/**
	 * Das NonStatic stirbt.
	 */
	public void die() {
		game.content.skills.Die.getInstance().execute(this);
	}
	
	/**
	 * Das NonStatic wird gespawnt.
	 */
	public void spawn() {
		game.content.skills.Spawn.getInstance().execute(this);
	}
	
	/**
	 * @return ob NonStatic am Leben.
	 */
	public boolean isAlive() {
		return this.getAttributeValue(Attribute.alive) == 1;
	}
	

	/**
	 * @return Punkte die NonStatic sehen kann.
	 */
	public HashSet<Point> getPointsInViewRange() {
		HashSet<Point> pointsInViewRange = new HashSet<Point>();
		
		for (Point p : this.getCoords()) {
			pointsInViewRange.addAll(matchModule.getMap().getPointsInViewRange(p, this.getAttributeValue(Attribute.viewRange)));
		}
//		pointsInViewRange.addAll(matchModule.getMap().getPointsInViewRange(this.getCoord(), this.getAttributeValue(Attribute.viewRange)));
		return pointsInViewRange;
	}
	
	/**
	 * @return Punkt an dem gespawnt werden soll.
	 */
	public Point getSpawnPoint() {		
		if (this.getFraction().equals(Fraction.TeamA)) {
			return matchModule.getMatch().getMap().getEmptyPointInArea(matchModule.getMap().getImportantPositions().get(WayPoints.BASE_TEAM_A_CENTER_POINT_AREA));
		} else {
			return matchModule.getMatch().getMap().getEmptyPointInArea(matchModule.getMap().getImportantPositions().get(WayPoints.BASE_TEAM_B_CENTER_POINT_AREA));
		}
	}
	
	/**
	 * @return Kopie des Spielobjekts
	 */
	public GameObject clone() {
		GameObject clone = super.clone();
		((NonStatic) clone).skills = new LinkedList<NonStaticSkill>();
		((NonStatic) clone).effects = new LinkedList<Effect>();
		return clone;
	}
	
	@Override
	public void unregisterGameObject() {
		super.unregisterGameObject();
		this.effects = null;
		this.attributeValueList = null;
		this.skills = null;
	}
	
}
