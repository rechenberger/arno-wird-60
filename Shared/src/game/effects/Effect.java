package game.effects;

import game.attributes.Attribute;
import game.attributes.AttributeValueList;
import game.content.effects.direct.EndEffect;
import game.objects.Drawable;
import game.objects.GameObject;
import game.objects.NonStatic;

/**
 * Effekte veraendern NonStatics.
 * @author Tristan
 */
public class Effect extends Drawable {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -8240857356191991808L;

	/**
	 * Name des Effekts.
	 */
	protected String name = "";
	
	/**
	 * Id des betroffenen NonStatics.
	 * (Speichere nicht direkt um verschickbar zu sein)
	 */
	private int effectsId;

	/**
	 * Speichert die jeweiligen Auswirkung auf das jeweilige Attribut des Betroffenen NonStatics.
	 */
	protected AttributeValueList attributeValueList = new AttributeValueList();

	/**
	 * Konstruktor.
	 * Speichert auf die Id des betroffenen NonStatics.
	 * 
	 * newEffect().ready();
	 * @param effects betroffenes NonStatics.
	 */
	public Effect(final NonStatic effects) {
		super();
		this.initValues();
		this.effectsId = effects.getId();
	}
	

	/**
	 * Registriet den Effekt in der Liste aller GameObjects.
	 * Fuegt dem betroffenem NonStatic den Effekt zu.
	 */
	public void registerGameObject() {
		super.registerGameObject();
	}
	/**
	 * Fuegt dem betroffenem NonStatic den Effekt zu.
	 */
	public void addEffectToEffects() {
		this.getEffects().addEffect(this);
	}

	/**
	 * Gibt den Betroffenen zurueck.
	 * @return NonStatic Betroffener
	 */
	public NonStatic getEffects() {
		NonStatic effects = GameObject.getById(effectsId);
		return effects;
	}

	/**
	 * Beendet einen Effekt.
	 * Loescht ihn aus der Liste des betroffenen NonStatics und der Liste aller GameObjects.
	 */
	public void end() {
		if (!matchModule.isClient() && !(this instanceof DirectEffect) && !(this instanceof ActiveEffect)) {
			new EndEffect(this.getEffects(), this).ready();
		}
		this.getEffects().removeEffect(this);
		this.unregisterGameObject();
	}
	
	/**
	 * Beendet einen Effekt.
	 * Loescht ihn aus der Liste des betroffenen NonStatics und der Liste aller GameObjects.
	 */
	public void unregisterGameObject() {
		super.unregisterGameObject();
		this.attributeValueList = null;
	}

	/**
	 * Gibt die AttributeValueList zurueck.
	 * @return AttributeValueList
	 */
	public AttributeValueList getAttributeValueList() {
		return attributeValueList;
	}
	
	/**
	 * Gibt den Wert eines Attributs zurueck.
	 * @param attribute Attribut
	 * @return (int) Wert des Attributs
	 */
	public int getAttributeValue(final Attribute attribute) {
		return this.getAttributeValueList().getValue(attribute);
	}
	
	/**
	 * Initialisiert die Werte.
	 */
	public void initValues() {
		
	}

	/**
	 * Wird nach dem Konstruktor aufgerufen:
	 * newEffect().ready(); .
	 * 
	 * 
	 * Sendet das Objekt.
	 * Fuegt dem betroffenem NonStatic den Effekt zu.
	 */
	public void ready() {
		if (!matchModule.isClient() && !(this instanceof ActiveEffect)) {
			this.send();
		}
		this.addEffectToEffects();
		
		if (matchModule.isClient()) {
			matchModule.tellGuiOfNewEffect(this);
		}
	}
	
	@Override
	public void afterRecieve() {
		super.afterRecieve();
		this.ready();
	}
	
	
	/**
	 * @return Name des Skills.
	 */
	public String getName() {
		if (this.name.equals("")) {
			return this.getClass().getSimpleName();
		} else {
			return this.name;
		}
	}
}
