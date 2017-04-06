package com.messages;

import module.ModuleType;
/**
 * Hier werden die Nachrichtentypen definiert.
 * Zu jeder Nachricht gehoert ein Modul
 * @author Christian Westhoff
 *
 */
public enum MessageType {
	/**
	 * Com: Aktivitaetsnachricht.
	 */
	ACTIVITY(ModuleType.COMMUNICATION),
	/**
	 * Chat: Benutzer.
	 */
	GLOBAL_CHAT(ModuleType.CHAT),
	/**
	 * Chat: Gruppe.
	 */
	GROUP_CHAT(ModuleType.CHAT),
	/**
	 * Chat: System.
	 */
	SYSTEM_CHAT(ModuleType.CHAT),	
	/**
	 * Spiel: Content.
	 */
	GAMECONTENT(ModuleType.MATCH),
	/**
	 * Spiel: Initialisierung.
	 */
	ALLGAMEOBJECTS(ModuleType.MATCH),
	/**
	 * Spiel: Zeit seit Match laueft.
	 */
	MATCHTIME(ModuleType.MATCH),
	/**
	 * Spiel: Zeit bis Match beginnt.
	 */
	MATCHBEGIN(ModuleType.MATCH),
	/**
	 * Spiel: Zeit bis Match beginnt.
	 */
	MATCHEND(ModuleType.MATCH),
	/**
	 * Spiel: Skillpunkt benutzen.
	 */
	SKILLPOINT(ModuleType.MATCH),
	/**
	 * Spiel: Spielerstatistik.
	 */
	PLAYERSTATISTIC(ModuleType.MATCH),
	/**
	 * Spiel: Benutzer-Bereit.
	 */
	PLAYER_READY(ModuleType.MATCH),
	
	/**
	 * Benutzer: Login.
	 */
	USER_LOGIN(ModuleType.ADMINISTRATION),
	/**
	 * Benutzer: Registrierung.
	 */
	USER_REGISTER(ModuleType.ADMINISTRATION),
	/**
	 * Benutzer: Loginantwort.
	 */
	USER_LOGIN_ANSWER(ModuleType.ADMINISTRATION),
	/**
	 * Benutzer: Wieder im Spiel.
	 */
	USER_BACK(ModuleType.ADMINISTRATION),
	/**
	 * Loggt den Benutzer aus.
	 */
	USER_LOGOFF(ModuleType.ADMINISTRATION),
	/**
	 * Benutzer: Exit.
	 */
	USER_EXIT(ModuleType.ADMINISTRATION),
	/**
	 * Benutzer: Registrierantwort.
	 */
	USER_REGISTER_ANSWER(ModuleType.ADMINISTRATION),
	/**
	 * Nachricht um neue KI anzumelden.
	 */
	ADD_NEW_KI(ModuleType.MATCH),
	/**
	 * Nachricht um den Status zu senden, dass das Spiel bereits laeuft.
	 */
	GAME_IS_RUNNING(ModuleType.ADMINISTRATION),
	/**
	 * Nachricht um den Status zu senden, dass der Benutzer eingeloggt ist.
	 */
	USER_ALREADY_IN(ModuleType.ADMINISTRATION),
	/**
	 * Nachricht, wenn der Benutzer zu wenig Geld besitzt.
	 */
	USER_LESS_MONEY(ModuleType.ADMINISTRATION),
	/**
	 * Nachricht, wenn der Held belegt ist.
	 */
	USER_HERO_TAKEN(ModuleType.ADMINISTRATION);

	/**
	 * Definiert die Modulzugehoerigkeit jeder Nachricht.
	 */	
	private final ModuleType module;
	/**
	 * Konstruktor.
	 * @param module Modultype
	 */
	private MessageType(final ModuleType module) {
		this.module = module;
	}
	/**
	 * Gibt den Modultyp zurueck.
	 * @return Modultyp
	 */
	public ModuleType getModule() {
		return module;
	}
}
