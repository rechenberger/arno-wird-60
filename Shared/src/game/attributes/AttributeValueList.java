package game.attributes;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Liste aller Attribute und ihrer Werte.
 * @author Tristan
 */
public class AttributeValueList /* extends GameObject */ implements Serializable {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 7933845255841264519L;
	/**
	 * Liste aller Attribute und ihrer Werte.
	 */
	private ConcurrentHashMap<Attribute, AttributeValue> attributeValueMap = new ConcurrentHashMap<Attribute, AttributeValue>();
	
	/**
	 * Der Wert eines Attributs.
	 * @author Tristan
	 */
	public class AttributeValue implements Serializable {
		/**
		 * serialVersionUID.
		 */
		private static final long serialVersionUID = -7580838919696201499L;
		/**
		 * Summe aller Summanden.
		 */
		int summ;
		/**
		 * Produkt aller Faktoren.
		 */
		float product;
		
		/**
		 * Konstruktor. Initialisert den Wert der Summe und setzt das Produkt auf 1.
		 * @param setSumm initialer Wert
		 * @param setProduct Produkt
		 */
		public AttributeValue(final int setSumm, final float setProduct) {
			this.summ = setSumm;
			this.product = setProduct;
		}
		
		/**
		 * Konstruktor. Initialisert den Wert der Summe und setzt das Produkt auf 1.
		 * @param setSumm Summe
		 */
		public AttributeValue(final int setSumm) {
			this(setSumm, 1.0f);
		}
		
		/**
		 * Konstruktor. Initialisert den Wert des Produkts und setzt die Summe auf 0.
		 * @param setProduct Produkt
		 */
		public AttributeValue(final float setProduct) {
			this(0, setProduct);
		}
		
		/**
		 * Gibt den finalen Wert des Attributs zurueck.
		 * @return finaler Wert (Summe * Produkt)
		 */
		public int getValue() {
			return (int) (summ * product);
		}
		
		/**
		 * Fuegt einen Summanden hinzu.
		 * @param addend Summand
		 */
		public void addAddend(final int addend) {
			this.summ += addend;
		}
		
		/**
		 * Entfernt einen Summanden.
		 * @param addend Summand
		 */
		public void removeAddend(final int addend) {
			this.addAddend(-addend);
		}

		
		/**
		 * Fuegt einen Faktor hinzu.
		 * @param factor Fakot
		 */
		public void addFactor(final float factor) {
			this.product *= factor;
		}

		/**
		 * Entfernt einen Faktor.
		 * @param factor Fakot
		 */
		public void removeFactor(final float factor) {
			if (factor == 0) {
				this.product = 1;
			} else {
				this.addFactor(1 / factor);
			}
		}
		
		/**
		 * Fuegt Summe und Produkt des anderen AttributeValues als Summand und Faktor hinzu.
		 * @param attributeValue ein anderer AttributeValue
		 */
		public void addAttributeValue(final AttributeValue attributeValue) {
			this.addAddend(attributeValue.summ);
			this.addFactor(attributeValue.product);
		}
		
		/**
		 * Entfernt Summe und Produkt des anderen AttributeValues als Summand und Faktor.
		 * @param attributeValue ein anderer AttributeValue
		 */
		public void removeAttributeValue(final AttributeValue attributeValue) {
			this.removeAddend(attributeValue.summ);
			this.removeFactor(attributeValue.product);
		}
		
		/**
		 * @return Summe.
		 */
		public int getSumm() {
			return this.summ;
		}
		
		/**
		 * @return Produkt.
		 */
		public float getProduct() {
			return this.product;
		}
	}
	
	/**
	 * Gibt den Wert eines Attributs zurueck.
	 * @param attribute Attribut
	 * @return (int) Wert des Attributs
	 */
	public int getValue(final Attribute attribute) {
		if (this.hasAttribute(attribute)) {
			return this.attributeValueMap.get(attribute).getValue();
		}
		return 0;
	}
	
	/**
	 * Gibt ein Set aller Attribute zurueck.
	 * @return Set aller Attribute
	 */
	public Set<Attribute> getAttributes() {
		return this.attributeValueMap.keySet();
	}
	
	/**
	 * Ueberprueft ob ein Attribut existiert.
	 * @param attribute zu ueberprufendes Attribut
	 * @return boolean
	 */
	public boolean hasAttribute(final Attribute attribute) {
		return this.getAttributes().contains(attribute);
	}
	
	/**
	 * Setzt den Wert eines Attributs und fuegt es (falls noetig) hinzu.
	 * @param attribute Attribut, dessen Wert eine Rolle spielt
	 * @param summ Summe
	 * @param product Produkt
	 */
	public void setAttribute(final Attribute attribute, final int summ, final float product) {
		if (this.hasAttribute(attribute)) {
			this.attributeValueMap.remove(attribute);
		}
		this.attributeValueMap.put(attribute, new AttributeValue(summ, product));
	}
	

	/**
	 * Setzt den Wert eines Attributs und fuegt es (falls noetig) hinzu.
	 * @param attribute Attribut, dessen Wert eine Rolle spielt
	 * @param value Wert dieses Attributs
	 */
	public void setAttribute(final Attribute attribute, final int value) {
		if (this.hasAttribute(attribute)) {
			this.attributeValueMap.remove(attribute);
		}
		this.attributeValueMap.put(attribute, new AttributeValue(value));
	}


	/**
	 * Setzt den Wert eines Attributs und fuegt es (falls noetig) hinzu.
	 * @param attribute Attribut, dessen Wert eine Rolle spielt
	 * @param product Produkt
	 */
	public void setAttribute(final Attribute attribute, final float product) {
		if (this.hasAttribute(attribute)) {
			this.attributeValueMap.remove(attribute);
		}
		this.attributeValueMap.put(attribute, new AttributeValue(product));
	}
	
	/**
	 * Fuegt ein Attribut mit Wert 1 hinzu.
	 * @param attribute Ein Attribut dessen Existenz und nicht dessen Wert eine Rolle spielt.
	 */
	public void addAttribute(final Attribute attribute) {
		this.setAttribute(attribute, 1);
	}
	
	/**
	 * Addiert und Multipliziert die jeweiligen Werte der Attribute.
	 * @param attributeValueList andere AttributValueList
	 */
	public void addAttributeValueList(final AttributeValueList attributeValueList) {
		if (attributeValueList == null) {
			return;
		}
		for (Attribute attribute : attributeValueList.getAttributes()) {
			if (this.hasAttribute(attribute)) {
				this.attributeValueMap.get(attribute).addAttributeValue(attributeValueList.attributeValueMap.get(attribute));
			}
		}
	}
	

	/**
	 * Subtrahiert und Dividiert die jeweiligen Werte der Attribute.
	 * @param attributeValueList andere AttributValueList
	 */
	public void removeAttributeValueList(final AttributeValueList attributeValueList) {
		if (attributeValueList == null) {
			return;
		}
		for (Attribute attribute : attributeValueList.getAttributes()) {
			if (this.hasAttribute(attribute)) {
				this.attributeValueMap.get(attribute).removeAttributeValue(attributeValueList.attributeValueMap.get(attribute));
			}
		}
	}
	
	/**
	 * @return Liste aller Attribute und ihrer Werte.
	 */
	public ConcurrentHashMap<Attribute, AttributeValue> getAttributeValueMap() {
		return this.attributeValueMap;
	}
	
}

