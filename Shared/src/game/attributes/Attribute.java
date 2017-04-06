package game.attributes;
/**
 * Attribut eines NonStatic.
 * @author Tristan
 *
 */
public enum Attribute {
	
	/**
	 * am Leben sein.
	 * (Wert irrelevant)
	 */
	alive("Lebendigkeit"),
	
	/**
	 * ob Objekt fliegt.
	 * (Wert irrelevant)
	 */
	flying("Fliegend"),
	
	/**
	 * maximale Lebenspunkte.
	 */
	maxHealth("Maximale Lebenspunkte"),
	
	/**
	 * momentane Lebenspunkte.
	 */
	currentHealth("momentane Lebenspunkte"),
	
	/**
	 * Schaden einer Standartattacke.
	 */
	damage("Schaden"),
	
	/**
	 * Verteidigungspunkte, die den erlitten Schaden mindern.
	 */
	defense("Verteidigung"),
	
	/**
	 * Type des Schadens der Standartattacke.
	 * 0 = Physikalisch, Stumpf
	 * 1 = Physikalisch, Spitz
	 * 2 = Chemisch, Feuer
	 * 3 = Chemisch, Frost
	 * 4 = Chemisch, Gift
	 */
	damageType("Schadenstyp"),
	
	/**
	 * Die Reichweite der Standartattacke.
	 */
	fightingRange("Kampfreichweite"),
	
	
	/**
	 * Die Reichweite in der die Karte vom Nebel des Krieges befereit wird.
	 */
	viewRange("Sichtreichweite"),
	
	/**
	 * Angriffsgeschwindigkeit.
	 * Anzahl Standartattacken pro 10 Sekunden.
	 */
	fightingSpeed("Angriffsgeschw."),
	
	/**
	 * Bewegungsgeschwindigkeit.
	 * Anzahl Schritte pro 10 Sekunden.
	 */
	movementSpeed("Bewegungsgeschw."),
	
	////////// nur fuer Helden /////////
	
	/**
	 * Erfahrungspunkte.
	 */
	experience("Erfahrungspunkte"),
	
	/**
	 * Geld auf Tasche.
	 */
	money("Geld"),

	/**
	 * maximale Manapunkte.
	 */
	maxMana("maximale Manapunkte"),
	
	/**
	 * momentane Manapunkte.
	 */
	currentMana("momentane Manapunkte"),
	
	/**
	 * Geld, das in 1 Sekunde gutgeschrieben wird.
	 */
	moneygeneration("Geld++"),
	
	/**
	 * Lebenspunkte, die in 1 Sekunde gutgeschrieben werden.
	 */
	healthgeneration("Leben++"),
	
	/**
	 * Mana, das in 1 Sekunde gutgeschrieben wird.
	 */
	manageneration("Kraft++"),
	
	/**
	 * Gibt Winkel in Grad in den NonStatic guckt.
	 * 0 = positive y-Richtung
	 * 90 = negative x-Richtung
	 * 180 = negative y-Richtung
	 * 270 = positive x-Richtung
	 */
	headingTo("WinkelDiesDas");
	
	/**
	 * Name.
	 */
	private String name;
	
	/**
	 * Konstrukor.
	 * @param name Name des Attributs.
	 */
	private Attribute(final String name) {
		this.name = name;
	}
	/**
	 * @return Name
	 */
	public String getName() {
		return this.name;
	}
	
}
