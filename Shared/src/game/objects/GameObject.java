package game.objects;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import module.MatchModuleShared;

import com.messages.AllGameObjectsMessage;
import com.messages.GameContentMessage;

/**
 * Spielelement. (Jedes Spielelement erbt von dieser Klasse)
 * @author Tristan
 * TODO Senden testen, Id-Vertielung testen
 */

public class GameObject implements Serializable, Cloneable {
	
	/**
	 * Array aller Klassennamen zu denen Listen gespeichert werden sollen.
	 */
	private static final String[] GAMEOBJECTSLISTS = {"Player", "KiPlayer", "NonStatic", "WorldObject", "Hero", "Vasal", "Effect",  "DircetEffect", "Match", "Action", "ActiveEffect" , "PermanentEffect", "Building", "PassiveSkill" };

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 8618367714804654918L;

	/**
	 * Zaehler fuer geloeschte GameObjects.
	 */
	private static int finalized = 0;
	
	/**
	 * Zaehler fuer entregistrierte GameObjects.
	 */
	private static int unregistered = 0;

	/**
	 * Matchmodul.
	 */
	protected static MatchModuleShared matchModule;
	
	/**
	 * 
	 * @param setMatchModule Matchmodul.
	 */
	public static void setMatchModule(final MatchModuleShared setMatchModule) {
		matchModule = setMatchModule;
	}
	
	/**
	 * speichert die naechste zu vergebende id.
	 */
	private static int nextId = 0;
	
	/**
	 * Liste aller Spielobjekt mit id als Key.
	 */
	protected static ConcurrentHashMap<Integer, GameObject> allGameObjects = new ConcurrentHashMap<Integer, GameObject>();
	
	/**
	 * Liste von Listen von GameObjects. Sortiert nach Klassenname.
	 */
	protected static ConcurrentHashMap<String, CopyOnWriteArrayList<Integer>> gameObjectLists = newGameObjectsList();
	
	/**
	 * @return Liste aller Spielobjekt mit id als Key.
	 */
	public static Collection<GameObject> getAllGameObjects() {
		return allGameObjects.values();
	}

	/**
	 * speichert die id dieses Objektes.
	 */
	private int id;
	/**
	 * Konstruktor.
	 * Gibt dem Objekt eine noch nicht vergebene Id und registriert es.
	 */
	public GameObject() {
		this.id = getNextId();
		this.registerGameObject();
	}
	
	/**
	 * @return naechste Id.
	 */
	private static int getNextId() {
		do {
			if (matchModule.isClient()) {
				nextId--;
			} else {
				nextId++;
			}
		}
		while (allGameObjects.keySet().contains(nextId));
		
		return nextId;
	}
	
	/**
	 * Gibt die Id des Objektes zurueck.
	 * @return id id
	 */
	public final int getId() {
		return this.id;
	}

	/**
	 * Gibt das Objekt gecastet in <T> mit der id zurueck.
	 * @param <T> GameObjectTyp der von GameObject erbt
	 * @param id id
	 * @return (T) zurueckzugeben gekastes GameObject
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getById(final int id) {
		return (T) allGameObjects.get(id);
	}
	/**
	 * Registriet das Objekt in der Liste aller GameObjects.
	 */
	public void registerGameObject() {
		if (allGameObjects.keySet().contains(this.getId())) {
			if (this.getClass() != getById(this.getId()).getClass()) {
				System.out.println("Error: Id schon vergeben! (Darf nicht vorkommen) ");
			} else {
				// Objekt wahrscheinlich schon vorhanden
				System.out.println("Objekt " + this + " wahrscheinlich schon vorhanden");
			}
		} else {
			allGameObjects.put(this.getId(), this);
			this.putIntoGameObjectsLists();
		}

	}
	
	/**
	 * Loescht das Objekt aus der Liste aller GameObjects.
	 */
	public void unregisterGameObject() {
		allGameObjects.remove(this.getId());
		this.removeOfGameObjectsLists();
		unregistered++;
	}
	
	/**
	 * Sendet das Objekt ueber die Kommunikation.
	 */
	public void send() {
		GameContentMessage msg;
		msg = new GameContentMessage(this.clone());
		matchModule.sendMessage(msg);
	}
	
	/**
	 * Wird ausgefuehrt nachdem ein Spielobjekt empfangen wurde.
	 */
	public void afterRecieve() {
		this.registerGameObject();
	}
	
	/**
	 * 
	 * @return Anzahl aller Spielobjekte in der Liste.
	 */
	public static int getAllGameObjectsSize() {
		return getAllGameObjects().size();
	}

	/**
	 * @param setAllGameObjects HashMap aller Spielobjekte.
	 */
	public static void setAllGameObjects(final ConcurrentHashMap<Integer, GameObject> setAllGameObjects) {
		allGameObjects = setAllGameObjects;
		for (GameObject go : getAllGameObjects()) {
			go.putIntoGameObjectsLists();
		}
	}
	
	/**
	 * @return HashMap aller Spielobjekte
	 */
	public static ConcurrentHashMap<Integer, GameObject> getAllGameObjectsMap() {
		return allGameObjects;
	}
	
	/**
	 * @return new AllGameObjectsMessage(allGameObjects)
	 */
	public static AllGameObjectsMessage getAllGameObjectsMessage() {
		return new AllGameObjectsMessage(allGameObjects);
	}
	
	/**
	 * Sendet allGameObjects.
	 */
	public static void sendAllGameObjects() {
		matchModule.sendMessage(getAllGameObjectsMessage());
	}
	
	/**
	 * @return Kopie des Spielobjekts
	 */
	public GameObject clone() {
		try {
			return (GameObject) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    throw new InternalError(); 
	}
	
//	/**
//	 * 
//	 * @return Liste aller GameObjects dieser Klasse.
//	 */
//	public LinkedList<GameObject> getAllGameObjectsOfThisClass() {
//		String className = getClass().getName();
////		String className = Thread.currentThread().getStackTrace()[1].getClassName();
//		System.out.println(className);
//		return getGameObjectsByClassName(className);
//	}
	
	/**
	 * @param className Klassenname.
	 * @param <T> GameObjectUnterKlasse
	 * @return Alle GameObjects dieser Klasse.
	 */
	public static <T extends GameObject> LinkedList<T> getGameObjectsByClassName(final String className) {
		LinkedList<T> list = new LinkedList<T>();
		for (int id : gameObjectLists.get(className)) {
			T t = GameObject.getById(id);
			list.add(t);
		}
		return list;
	}
	
	/**
	 * Fuegt das GameObject in die entsprechende Liste ein.
	 * @param gameObject GameObject
	 * @param className Klassenname
	 */
	public static void putIntoGameObjectsLists(final GameObject gameObject, final String className) {
		if (!gameObjectLists.containsKey(className)) {
//			System.out.println("Erstelle Liste fuer: " + className);
			gameObjectLists.put(className, new CopyOnWriteArrayList<Integer>());
		}
		gameObjectLists.get(className).add(gameObject.getId());
	}
	
	/**
	 * Fuegt das GameObject in die entsprechende Liste ein.
	 * @param gameObject GameObject
	 * @param className Klassenname
	 */
	public static void removeOfGameObjectsLists(final GameObject gameObject, final String className) {
		try {
			gameObjectLists.get(className).remove(gameObjectLists.get(className).indexOf(gameObject.getId()));
		} catch (java.lang.ArrayIndexOutOfBoundsException e) {
			
		}
	}
	
	/**
	 * Fuegt GameObject in Liste aller seiner VaterKlassen ein.
	 */
	public void putIntoGameObjectsLists() {
		Class<?> clas = this.getClass();
		while (!clas.getSimpleName().equals("GameObject")) {
			if (Arrays.asList(GAMEOBJECTSLISTS).contains(clas.getSimpleName())) {
				putIntoGameObjectsLists(this, clas.getSimpleName());
			}
			clas = clas.getSuperclass();
		}
	}
	
	/**
	 * Entfernt GameObject aus Liste aller seiner VaterKlassen.
	 */
	public void removeOfGameObjectsLists() {
		Class<?> clas = this.getClass();
		while (!clas.getSimpleName().equals("GameObject")) {
			if (Arrays.asList(GAMEOBJECTSLISTS).contains(clas.getSimpleName())) {
				removeOfGameObjectsLists(this, clas.getSimpleName());
			}
			clas = clas.getSuperclass();
		}
	}
	
	/**
	 * @return newGameObjectsList.
	 */
	private static ConcurrentHashMap<String, CopyOnWriteArrayList<Integer>> newGameObjectsList() {
		ConcurrentHashMap<String, CopyOnWriteArrayList<Integer>> newGameObjectsList = new ConcurrentHashMap<String, CopyOnWriteArrayList<Integer>>();
		for (int i = 0; i < GAMEOBJECTSLISTS.length; i++) {
			newGameObjectsList.put(GAMEOBJECTSLISTS[i], new CopyOnWriteArrayList<Integer>());
		}
		return newGameObjectsList;
	}
	
	@Override
	public void finalize() {
		finalized++;
	}
	
	/**
	 * Gibt Informationen ueber Speicherstand aus.
	 */
	public static void printGameObjectSize() {
		System.out.println("Aktiv: " + getAllGameObjectsSize() + ", Unregistered: " + unregistered + ", Geloescht: " + finalized + "");
	}

}
