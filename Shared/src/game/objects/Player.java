package game.objects;

import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

import settings.DebugSettings;

import com.messages.ChatMessage;
import com.messages.PlayerStatisticMessage;

import game.content.effects.direct.ChooseHeroEffect;
import game.content.heros.Hero;

/**
 * Ein Spieler im Match.
 * 
 * @author Tristan
 * 
 */
public class Player extends GameObject {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -1330138751082542874L;

	/**
	 * Id des Helden, den der Spieler steuert.
	 */
	private int heroId;

	/**
	 * Der Name des Spielers.
	 */
	private String name;

	/**
	 * Id des Users, die er von der Kommunikation bekommt.
	 */
	private int userId;

	/**
	 * Gibt an, ob der Benutzer noch im Match ist.
	 */
	private boolean isOnline;
	/**
	 * Speichert Statistiken des Spielers des gerade laufenden Spiels.
	 */
	private ConcurrentHashMap<String, Integer> statisticCurrentGame = new ConcurrentHashMap<String, Integer>();

	/**
	 * Speichert die gesamte Statistik des Spielers.
	 */
	private ConcurrentHashMap<String, Integer> statisticAllTime = new ConcurrentHashMap<String, Integer>();

	/**
	 * Namen aller Statistiken.
	 */
	private static HashSet<String> allStatisticNames = new HashSet<String>();

	/**
	 * Speichert ob er bereit ist, das Spiel zu starten.
	 */
	private boolean isReadyToPlay;

	/**
	 * Konstruktor.
	 * 
	 * @param setUserId
	 *            Id des Users, die er von der Kommunikation bekommt.
	 * @param setName
	 *            Der Name des Spielers.
	 */
	public Player(final int setUserId, final String setName) {
		this.userId = setUserId;
		this.name = setName;
		this.incStatistic("GamesPlayed");
		isReadyToPlay = false;
	}

	/**
	 * @return Ferig fuers Spielen?
	 */
	public boolean isReadyToPlay() {
		return isReadyToPlay;
	}

	/**
	 * @param isReadyToPlay
	 *            Ferig fuers Spielen?
	 */
	public void setReadyToPlay(final boolean isReadyToPlay) {
		this.isReadyToPlay = isReadyToPlay;
	}

	/**
	 * @return Ob Alle Spieler bereit sind.
	 */
	public static boolean allPlayersReady() {
		boolean humanInGame = false;
		for (Player player : Player.getAllPlayers()) {
			if (!(player instanceof KiPlayer)) {
				if (!player.isReadyToPlay()) {
					return false;
				}
				humanInGame = true;
			}
			
		}
		return humanInGame;
	}

	/**
	 * @return Der Name des Spielers.
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @return Held, den der Spieler steuert
	 */
	public Hero getHero() {
		Hero hero = Hero.getById(heroId);
		return hero;
	}

	/**
	 * @param hero
	 *            Held, den der Spieler steuert
	 */
	public void setHero(final Hero hero) {
		this.heroId = hero.getId();
	}

	/**
	 * @return Id des Users, die er von der Kommunikation bekommt.
	 */
	public int getUserId() {
		return this.userId;
	}

	/**
	 * 
	 * @param userId
	 *            Id des Users, die er von der Kommunikation bekommt.
	 */
	public void setUserId(final int userId) {
		this.userId = userId;
	}

	/**
	 * 
	 * @return isActive Die Aktivitaet des Users.
	 */
	public boolean isOnline() {
		return this.isOnline;
	}

	/**
	 * 
	 * @param isOnline
	 *            Die Aktivitaet des Users.
	 */
	public void setOnlineState(final boolean isOnline) {
		this.isOnline = isOnline;
	}

	/**
	 * Liefert alle Spieler.
	 * 
	 * @return Alle Spieler
	 */
	public static Collection<Player> getAllPlayers() {
		Collection<Player> players = Player.getGameObjectsByClassName("Player");
		return players;
	}

	/**
	 * 
	 * @param userId
	 *            Id des Benutzers.
	 * @return zugehoeriger Spieler.
	 */
	public static Player getPlayerByUserId(final int userId) {
		for (Player player : Player.getAllPlayers()) {
			if (player.getUserId() == userId) {
				return player;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param name
	 *            Name des Benutzers.
	 * @return zugehoeriger Spieler.
	 */
	public static Player getPlayerByName(final String name) {
		for (Player player : Player.getAllPlayers()) {
			if (player.getName().equals(name)) {
				return player;
			}
		}
		return null;
	}

	/**
	 * Sendet eine SystemChatMessage an den Spieler.
	 * 
	 * @param text
	 *            Text der Nachricht.
	 */
	public void sendSystemChatMessege(final String text) {
		matchModule.sendMessage(new ChatMessage(text), this.getUserId());
	}

	/**
	 * Spieler verlaesset das Spiel.
	 */
	public void leave() {
		if (DebugSettings.EXIT_WHEN_NO_USERS
				&& (Player.getAllPlayers().size() - KiPlayer
						.getGameObjectsByClassName("KiPlayer").size()) <= 1) {
			System.exit(0);
		}
		matchModule.sendMessage(new ChatMessage("Spieler " + this.getName()
				+ " hat das Spiel verlassen."));

		if (this.getHero() != null) {
			new ChooseHeroEffect(null, this.getHero()).ready();
		}
		
		if (!matchModule.isMatchRunning()) {
			this.unregisterGameObject();
		}

		this.isOnline = false;
	}

	/**
	 * @param statName
	 *            Name der Statistik.
	 * @param value
	 *            Wert.
	 * @param alreadySent
	 *            ob bereits gesendet wurde.
	 */
	private void setStatistic(final String statName, final int value,
			final boolean alreadySent) {
		this.statisticCurrentGame.put(statName, value);
		if (!allStatisticNames.contains(statName)) {
			allStatisticNames.add(statName);
		}
		if (!alreadySent) {
			matchModule.sendMessage(new PlayerStatisticMessage(this.getId(),
					statName, value));
		}
	}

	/**
	 * @param statName
	 *            Name der Statistik.
	 * @param value
	 *            Wert.
	 */
	public void setStatistic(final String statName, final int value) {
		this.setStatistic(statName, value, false);
	}

	/**
	 * @param statName
	 *            Name der Statistik.
	 * @param value
	 *            Wert um den erhoeht wird.
	 */
	public void incStatistic(final String statName, final int value) {
		this.setStatistic(statName, this.getStatistic(statName) + value);
	}

	/**
	 * Erhoeht um 1.
	 * 
	 * @param statName
	 *            Name der Statistik.
	 */
	public void incStatistic(final String statName) {
		this.incStatistic(statName, 1);
	}

	/**
	 * @param statName
	 *            Name der Statistik.
	 * @return Wert.
	 */
	public int getStatistic(final String statName) {
		if (this.statisticCurrentGame.containsKey(statName)) {
			return this.statisticCurrentGame.get(statName);
		} else {
			this.statisticCurrentGame.put(statName, 0);
			return getStatistic(statName);
		}
	}

	/**
	 * @param statName
	 *            Name der Statistik.
	 * @return Wert.
	 */
	public int getStatisticAllTime(final String statName) {
		if (this.statisticAllTime.containsKey(statName)) {
			return this.statisticAllTime.get(statName);
		} else {
			this.statisticAllTime.put(statName, 0);
			return getStatistic(statName);
		}
	}

	/**
	 * @return StatistikCurrentGame.
	 */
	public ConcurrentHashMap<String, Integer> getStatistic() {
		return this.statisticCurrentGame;
	}

	/**
	 * @return StatistikAllTime.
	 */
	public ConcurrentHashMap<String, Integer> getStatisticAllTime() {
		return this.statisticAllTime;
	}

	/**
	 * @param statName
	 *            Name der Statistik.
	 * @param value
	 *            Wert.
	 */
	public void setStatisticByMessage(final String statName, final int value) {
		this.setStatistic(statName, value, matchModule.isClient());
	}

	/**
	 * @param statisticAllTime
	 *            All-Zeit-Statistik.
	 */
	public void setStatisticsAllTime(
			final ConcurrentHashMap<String, Integer> statisticAllTime) {
		if (statisticAllTime == null) {
			return;
		}

		for (String statName : statisticAllTime.keySet()) {
			if (!allStatisticNames.contains(statName)) {
				allStatisticNames.add(statName);
			}
		}
		this.statisticAllTime = statisticAllTime;
	}

	/**
	 * Fuegt die Statistik des momentanen Spiels zur All-Zeit-Statistik hinzu.
	 */
	public void addStatisticsCurrentGameToStatisticsAllTime() {
		for (String statName : statisticCurrentGame.keySet()) {
			this.statisticAllTime.put(
					statName,
					this.getStatisticAllTime(statName)
							+ this.getStatistic(statName));
		}
	}

	/**
	 * Fuehrt folgendes fuer Alle Spieler aus: Fuegt die Statistik des
	 * momentanen Spiels zur All-Zeit-Statistik hinzu.
	 */
	public static void addStatisticsCurrentGameToStatisticsAllTimeForAllPlayer() {
		for (Player p : Player.getAllPlayers()) {
			p.addStatisticsCurrentGameToStatisticsAllTime();
		}
	}

	/**
	 * @return Namen aller Statistiken.
	 */
	public static Collection<String> getAllStatisticNames() {
		return allStatisticNames;
	}
	
	/**
	 * Gibt die AllTimeStatistic in der Console aus.
	 */
	public void printAllTimeStat() {
		System.out.println("Held: " + this.getHero());
		for (String statName: statisticAllTime.keySet()) {
			System.out.println(statName + ": " + this.getStatisticAllTime(statName));
			if (!allStatisticNames.contains(statName)) {
				allStatisticNames.add(statName);
			}
		}
	}
}
