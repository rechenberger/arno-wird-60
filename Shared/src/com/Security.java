package com;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

/**
 * Utility-Klasse zim Verschluesseln.
 * 
 * @author Christian Westhoff
 * @author 
 *         http://www.java-blog-buch.de/17-03-sicherheit-bei-der-arbeit-mit-datenbanken
 *         /
 */
public class Security {

	/**
	 * Konstruktor.
	 */
	protected Security() {
	}

	/**
	 * Algorithmus, der fuer die Verschluesslung benutzt wird.
	 */
	private static final String ALGORITHM = "SHA-512";

	/**
	 * Liefert die Schiffre des eingegebenen Strings mit Hilfe des verwendeten
	 * Algorithmus.
	 * 
	 * @param clear
	 *            Klartext
	 * @return Krypttext
	 */
	public static String getSHAValue(final String clear) {
		try {
			MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
			digest.update(clear.getBytes());
			Formatter formatter = new Formatter();
			for (byte b : digest.digest()) {
				formatter.format("%02x", b);
			}
			String crypt = formatter.toString();
			formatter.close();
			return crypt;
		} catch (NoSuchAlgorithmException ex) {
			System.out
					.println("Der verwendete Algorithmus ist nicht verfuegbar: "
							+ ALGORITHM
							+ ". Achtung die Passwoerter werden im Klartext versendet!");
		}
		return clear;

	}

}
