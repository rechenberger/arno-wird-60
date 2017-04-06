package game.content.heros.geronimo;

import java.awt.Point;
import java.util.LinkedList;

import game.attributes.Attribute;
import game.attributes.AttributeValueList;
import game.content.heros.Hero;
import game.objects.NonStatic;
import game.skills.NotAimedSkill;

/**
 * Erleuchtung, der Skill.
 * @author Alex
 *
 */
public class ErleuchtungSkill extends NotAimedSkill {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 7949994937972819584L;

	/**
	 * Konstruktor. Initialisiert die Werte.
	 */
	public ErleuchtungSkill() {
		super("Erleuchtung");
			
		this.effectsSelf = true;
		this.effectsHeroOnly = true;
		this.effectsAllies = true;
		this.effectsEnemies = false;
			
		this.radius = 500;
		this.range = 0;
		this.mana = 100;
		this.cast =  50;
		this.cooldown = 5 * 1000; 
		
		this.setImageURL("usercontrols", "skill", "geronimo/erleuchtung");
	}
	
	@Override
	public void giveEffect(final NonStatic executer, final NonStatic effectedNonStatic) {
		// Kreiert den entsprechenden Effekt und ruft seine Methode ready() auf.
		new ErleuchtungEffect(effectedNonStatic, executer.getSkillLevel(this)).ready();
	}
	
	@Override
	protected LinkedList<NonStatic> getEffected(final NonStatic executer, final Point targetPoint) {
		LinkedList<NonStatic> effected = new LinkedList<NonStatic>();
		for (Hero h : Hero.getAllHeros()) {
			if (h.getFraction().equals(executer.getFraction())) {
				effected.add(h);
			}
		}
		return effected;
	}
	
	/**
	 * Gibt die Attributevalue Liste Zurueck, die die Auswirkungen dieses Effekts wiederspiegelt.
	 * <b>Wenn die Uebergebene avListe null ist, dann wird eine neue erstellt.</b>
	 * @param skillLevel Das Level auf welchem der Skill ausgefuehrt wurde.
	 * @param avListe Die Attribute value Liste in die der Wert eingefuegt werden soll.
	 * @return Die AttributeVAlue liste.
	 */
	public static AttributeValueList getAttributeValueListWithSkillLevel(final int skillLevel, final AttributeValueList avListe)  {
		AttributeValueList av = avListe;
		if (av == null) {
			av = new AttributeValueList();
		}
		av.setAttribute(Attribute.damage, 10 * skillLevel); 
		
		av.setAttribute(Attribute.fightingSpeed, 1.1f * skillLevel); 
		return av;
	}
	
	/**
	 * Gibt die AttributeValue List des Effektes zurueck, welcher dieser Skill ausfuehrt.
	 * @param executer Der Ausfuehrer des Skills
	 * @return Die AtributeValueList
	 */
	public AttributeValueList getEffectsAttributeValueList(final NonStatic executer) {		
		return ErleuchtungEffect.getAttributeValueListWithSkillLevel(executer.getSkillLevel(this), null);
	}
}
