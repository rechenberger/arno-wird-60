package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Stellt eine Verbindung zur Datenbank her und kann nach Benutzern suchen.
 * 
 * @author Melanie, Christian Westhoff
 * 
 */
public class Database {
	/**
	 * Der Pfad zur DB.
	 */
	static final String DB_URL = "jdbc:mysql://localhost/gruppe06";

	/**
	 * Benutzername fuer die Datenbank.
	 */
	static final String DB_USER = "gruppe06";

	/**
	 * Passwort fuer die Datenbank.
	 */
	static final String DB_PASS = "YLhFF3HF";

	/**
	 * Verbindung zur Datenbank.
	 */
	Connection con = null;

	/**
	 * Konstruktor.
	 */
	public Database() {
	}

	/**
	 * Herstellen der Verbindung zur Datenbank.
	 * 
	 * @throws SQLException
	 *             Ausgeloest durch DriveManger.getConnection()
	 */
	public void openConnection() throws SQLException {
		// DriverManager.getConnection() stellt Verbindung her
		this.con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
	}

	/**
	 * Schliesst die Verbindung.
	 * 
	 * @throws SQLException
	 *             Ausgeloest durch Connection.clos()
	 */
	public void closeConnection() throws SQLException {
		// Schliessen der Verbindung
		this.con.close();
		this.con = null;
	}

	/**
	 * Sucht in der DB nach dem Benutzernamen und Passwort.
	 * 
	 * @param user
	 *            Username, der eingegeben wurde
	 * @param password
	 *            Passwort, welches eingegeben wurde
	 * @return return Erfolg bzw. Fehlschlag
	 * @throws SQLException
	 *             Ausgeloest durch openConnection(), prepareStatement(),
	 *             executeQuery()
	 */
	public boolean queryLookup(final String user, final String password)
			throws SQLException {

		if (this.con == null) {
			this.openConnection();
		}

		PreparedStatement ps = con
				.prepareStatement("SELECT * FROM User WHERE name=? AND password=?");

		ps.setString(1, user);
		ps.setString(2, password);

		ResultSet result = ps.executeQuery();

		return result.next();
	}

	/**
	 * Sucht in der DB nach dem Benutzernamen.
	 * 
	 * @param user
	 *            Username, der eingegeben wurde
	 * @return return Erfolg bzw. Fehlschlag
	 * @throws SQLException
	 *             Ausgeloest durch openConnection(), prepareStatement(),
	 *             executeQuery()
	 */
	public boolean queryLookup(final String user) throws SQLException {

		if (this.con == null) {
			this.openConnection();
		}

		PreparedStatement ps = con
				.prepareStatement("SELECT * FROM User WHERE name=?");

		ps.setString(1, user);

		ResultSet result = ps.executeQuery();

		return result.next();
	}

	/**
	 * Schreibt neuen Nutzer in die DB und gibt aus, ob Benutzername schon
	 * existiert.
	 * 
	 * @param user
	 *            Username, der eingegeben wurde
	 * @param pw
	 *            Passwort, welches eingegeben wurde
	 * @throws SQLException
	 *             Ausgeloest durch queryLookup(String), prepareStatement(),
	 *             executeUpdate(), closeConnection()
	 * @return Erfolg bzw. Fehlschlag
	 */
	public boolean queryRegister(final String user, final String pw)
			throws SQLException {

		if (this.queryLookup(user)) {
			return false;

		} else {
			PreparedStatement ps = con
					.prepareStatement("INSERT INTO User (name, password) VALUES (?,?)");

			ps.setString(1, user);
			ps.setString(2, pw);

			ps.executeUpdate();
		}

		this.closeConnection();

		return true;
	}

	/**
	 * Laedt die Statistik aus der Datenbank.
	 * 
	 * @param user
	 *            Von welchem Spieler soll die Statistik zurueckgegeben werden?
	 * @return Statistik des Spielers.
	 * @throws SQLException
	 *             Ausgeloest durch openConnection(), prepareStatement(),
	 *             executeQuery()
	 */
	public ConcurrentHashMap<String, Integer> queryLoadStatistics(
			final String user) throws SQLException {

		if (this.con == null) {
			this.openConnection();
		}
		ConcurrentHashMap<String, Integer> statistic = new ConcurrentHashMap<String, Integer>();

		String query = "SELECT statname, value FROM User_Statistic WHERE username=?";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, user);

		ResultSet result = ps.executeQuery();

		while (result.next()) {
			statistic.put(result.getString(1), result.getInt(2));
		}
		return statistic;
	}

	/**
	 * Speichert die Statistik in die Datenbank.
	 * 
	 * @param user
	 *            Die Statistik welches Users soll geupdatet werden?
	 * @param statistic
	 *            Neue Statistik.
	 * @throws SQLException
	 *             Ausgeloest durch queryLookup(String), prepareStatement(),
	 *             executeUpdate(), closeConnection()
	 */
	public void querySaveStatistics(final String user,
			final ConcurrentHashMap<String, Integer> statistic)
			throws SQLException {

		if (this.con == null) {
			this.openConnection();
		}

		for (Entry<String, Integer> e : statistic.entrySet()) {
			if (e.getKey() != null && e.getValue() != null) {
				String name = e.getKey();
				int newValue = e.getValue();

				String query = "INSERT INTO User_Statistic (username, statname, value) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE value = ?";
				PreparedStatement ps = con.prepareStatement(query);
				ps.setString(1, user);
				ps.setString(2, name);
				ps.setInt(3, newValue);
				ps.setInt(4, newValue);

				System.out.println(ps.toString());

				ps.executeUpdate();
			}
		}

		this.closeConnection();
	}
}
