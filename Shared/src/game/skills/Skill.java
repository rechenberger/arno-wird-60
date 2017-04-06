package game.skills;

import java.awt.Point;
import java.util.LinkedList;

import com.messages.ChatMessage;

import game.attributes.Attribute;
import game.attributes.AttributeValueList;
import game.content.heros.Hero;
import game.effects.CooldownEffect;
import game.effects.Effect;
import game.objects.Drawable;
import game.objects.NonStatic;

/**
 * Ein Skill ist im allgemeinen eine Faehigkeit eines NonStatic, die Effekte  mit sich bringt.
 * @author Tristan
 *
 */
public abstract class Skill extends Drawable {
	
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -8709757668734782969L;
	
	/**
	 * Name des Skills.
	 */
	protected String name;
	
	/**
	 * Beschreibung.
	 */
	protected String description = "";

	/**
	 * Reichweite.
	 */
	protected int range = 0;
	
	/**
	 * Radius auf den der Effekt wirkt.
	 */
	protected int radius = 0;
	
	/**
	 * Zeiteinheiten zwischen zwei Nutzungen.
	 * Der Skill ist waehrenddessen nicht nutzbar. 
	 */
	protected int cooldown = 0;
	
	/**
	 * Zeiteinheiten in der der Skill vorbereitet werden muss.
	 */
	protected int cast = 0;
	
	/**
	 * Mana welches verbraucht wird, wenn man den Skill ausfuehrt.
	 */
	protected int mana = 0;
	
	/**
	 * Gibt an, ob der Skill den Ausfuehrenden betrifft.
	 */
	protected boolean effectsSelf = false;
	
	/**
	 * Gibt an, ob der Skill nur Helden betrifft.
	 */
	protected boolean effectsHeroOnly = false;
	
	/**
	 * Gibt an, ob der Skill Verbuendete betrifft.
	 */
	protected boolean effectsAllies = false;
	
	/**
	 * Gibt an, ob der Skill Feinde betrifft.
	 */
	protected boolean effectsEnemies = false;
	
	
	
	/**
	 * Ob Skill in Gui dargestellt wird.
	 */
	protected boolean shownInGui = true;
	
	/**
	 * Konstruktor der den Namen definiert.
	 * @param setName Name des Skills
	 */
	public Skill(final String setName) {
		super();
		this.name = setName;
		this.initValues();
		this.setImageURL("usercontrols", "knob", "skill");
	}
	
	/**
	 * Hier werden Werte initialisiert.
	 */
	public void initValues() { }
	

	/**
	 * Der mit dem Skill verbundene Effekt.
	 * Zum ueberschreiben gedacht.
	 * @param executer Ausfuehrer
	 * @return null
	 */
	protected Effect getEffect(final NonStatic executer) {
		return null;
	}

//	public static LinkedList<Skill> getAll() {
//		LinkedList<Skill> ll = new LinkedList<Skill>();
//		for(GameObject go : GameObject.allGameObjects.values()) {
//			if(go instanceof Skill)
//				ll.add( (Skill) go);
//		}
//		return ll;
//	}
	
	/**
	 * Macht den Skill zu einem Angriffsskill.
	 */
	protected void makeTypeAttack() {
		this.effectsEnemies = true;
	}

	/**
	 * Macht den Skill zu einem Verstaerkungsskill.
	 */
	protected void makeTypeBuff() {
		this.effectsSelf = true;
	}

	/**
	 * Macht den Skill zu einem Schwaechungsskill.
	 */
	protected void makeTypeDebuff() {
		this.effectsEnemies = true;
	}
	
	/**
	 * 
	 * @param executer Ausfuehrer
	 * @return Reichweite
	 */
	public int getRange(final NonStatic executer) {
		return this.range;
	}
	
	/**
	 * @param executer Ausfuehrer
	 * @return Cooldown
	 */
	public int getCooldown(final NonStatic executer) {
		return this.cooldown;
	}
	

	
	/**
	 * @param executer Ausfuehrer
	 * @return Cooldown
	 */
	public int getCast(final NonStatic executer) {
		return this.cast;
	}
	
	
	/**
	 * @param executer Ausfuehrer
	 * @return Radius
	 */
	public int getRadius(final NonStatic executer) {
		return this.radius;
	}
	

	/**
	 * @param executer Ausfuehrer
	 * @return Mana, welches verbaucht wird.
	 */
	public int getMana(final NonStatic executer) {
		return this.mana;
	}
	
	/**
	 * Initialisiert den entsprechenden Cooldowneffekt.
	 * @param executer Ausfuehrer.
	 */
	public void cooldown(final NonStatic executer) {
		new CooldownEffect(executer, this, this.getCooldown(executer)).ready();
	}
	
	/**
	 * 
	 * @param executer Ausfuehrer.
	 * @return ob sich Skill momentan abkuehlt.
	 */
	public boolean isCoolingDown(final NonStatic executer) {
		return CooldownEffect.getCooldownOfSkill(executer, this) > 0;
	}
	
	/**
	 * @param executer Ausfuehrer
	 * @return Cooldown Verhaeltnis
	 */
	public float getCooldownRatio(final NonStatic executer) {
		return CooldownEffect.getCooldownRatioOfSkill(executer, this);
	}

	
	/**
	 * Fuehrt den Skill aus. Die Standardausfuehrung.
	 * Schaut auf die booleans effectsSelf, effectsHeroOnly, effectsAllies und effectsEnemies
	 * Gibt den betroffenen NonStatics im getRadius(executer) den Effekt giveEffect(executer, effectedNonStatic);
	 * @param executer Ausfuehrender
	 * @param targetPoint Zielpunkt
	 */
	public void execute(final NonStatic executer, final Point targetPoint) {
		if (executer instanceof Hero) {
			if (executer.getAttributeValue(Attribute.currentMana) < this.getMana(executer)) {
				matchModule.sendMessage(new ChatMessage("Nicht genug Mana!"), ((Hero) executer).getUserId());			
				return;
			} else {
				new game.content.effects.direct.Mana(executer, -this.getMana(executer)).ready();
			}
		}
		
		LinkedList<NonStatic> effectedNonStatics = this.getEffected(executer, targetPoint);
		if (effectedNonStatics.isEmpty()) {
			return;
		}
		for (NonStatic effectedNonStatic : effectedNonStatics) {
			this.giveEffect(executer, effectedNonStatic);
		}
	}

	/**
	 * Gibt dem betroffenem NonStatic den mit dem Skill verbunden Effekt.
	 * @param executer Ausfuehrer
	 * @param effectedNonStatic betroffenes NonSatic
	 * 
	 * Methode muss ueberschrieben werden, wenn es einen mit dem Skill verbundenen Effekt gibt.
	 */
	protected void giveEffect(final NonStatic executer, final NonStatic effectedNonStatic) {
		// Solange die Methode nicht ueberschrieben wird, passiert hier nichts.
	}

	/**
	 * 
	 * @param executer Ausfuehrender
	 * @param targetPoint Zielpunkt
	 * @return Liste der betroffenen NonStatics
	 */
	protected LinkedList<NonStatic> getEffected(final NonStatic executer, final Point targetPoint) {

		LinkedList<NonStatic> standingBys = matchModule.getMatch().getMap().getFightablesInCircle(targetPoint, this.getRadius(executer));
		LinkedList<NonStatic> effectedNonStatics = new LinkedList<NonStatic>();
		if (standingBys.isEmpty()) {
			return effectedNonStatics;
		}
		for (NonStatic standingBy : standingBys) {
			if (this.willBeEffected(executer, standingBy)) {
				effectedNonStatics.add(standingBy);
			}
		}
		return effectedNonStatics;
	}
	
	/**
	 * 
	 * @param executer Ausfuehrer
	 * @param effectedNonStatic betroffenes NonStatic
	 * @return ob NonStatic vom Skill betroffen ist.
	 */
	public boolean willBeEffected(final NonStatic executer, final NonStatic effectedNonStatic) {
		boolean willBeEffected = true;
		if (!this.effectsSelf && effectedNonStatic.equals(executer)) {
			willBeEffected = false;
		}

		if (this.effectsHeroOnly && !(effectedNonStatic instanceof Hero)) {
			willBeEffected = false;
		}
		
		if (!this.effectsAllies && effectedNonStatic.getFraction().equals(executer.getFraction())) {
			willBeEffected = false;
		}

		if (!this.effectsEnemies && !(effectedNonStatic.getFraction().equals(executer.getFraction()))) {
			willBeEffected = false;
		}
		return willBeEffected;
	}
	
	
	/**
	 * @param executer Ausfuehrer
	 * @return Level des Skills
	 */
	protected int getLevel(final NonStatic executer) {
		return executer.getSkillLevel(this);
	}
	
	/**
	 * @return Name des Skills.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * @return Ob Skill in Gui dargestellt wird.
	 */
	public boolean isShownInGui() {
		return this.shownInGui;
	}
	
	/**
	 * Gibt die AttribtueValue Liste des zu diesem Skill korespondierenden Effekts zurueck.
	 * @param executer Der Ausfuehrer, wichtig um an das Skilllevel zu kommen.
	 * @return Die AttributeValue Liste
	 */
	public abstract AttributeValueList getEffectsAttributeValueList(final NonStatic executer);

	/**
	 * 
	 * @return Gibt an, ob der Skill den Ausfuehrenden betrifft.
	 */
	public boolean getEffectsSelf() {
		return effectsSelf;
	}

	/**
	 * 
	 * @return Gibt an, ob der Skill nur Helden betrifft.
	 */
	public boolean getEffectsHeroOnly() {
		return effectsHeroOnly;
	}

	/**
	 * 
	 * @return Gibt an, ob der Skill Verbuendete betrifft.
	 */
	public boolean getEffectsAllies() {
		return effectsAllies;
	}

	/**
	 * 
	 * @return Gibt an, ob der Skill Feinde betrifft.
	 */
	public boolean getEffectsEnemies() {
		return effectsEnemies;
	}
	
	/**
	 * 
	 * @return Beschreibung
	 */
	public String getDescription() {
		return description;
	}

	
}
