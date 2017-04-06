package com.messages;

import java.util.concurrent.ConcurrentHashMap;

import game.objects.GameObject;

/**
 * 
 * @author Tristan
 *
 */
public class AllGameObjectsMessage extends Message {

	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -8314864365210775996L;
	
	/**
	 * Die Liste.
	 */
	private ConcurrentHashMap<Integer, GameObject> allGameObjects = new ConcurrentHashMap<Integer, GameObject>();
	
	/**
	 * 
	 * @param setAllGameObjects Liste
	 */
	public AllGameObjectsMessage(final ConcurrentHashMap<Integer, GameObject> setAllGameObjects) {
		super(MessageType.ALLGAMEOBJECTS);
		this.allGameObjects = setAllGameObjects;
	}
	

	
	/**
	 * 
	 * @return Die Liste
	 */
	public ConcurrentHashMap<Integer, GameObject> getAllGameObjects() {
		return this.allGameObjects;
	}
}
