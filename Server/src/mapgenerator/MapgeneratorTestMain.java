package mapgenerator;

import java.awt.Point;

/**
 * Testmain Methode.
 * @author Marius
 *
 */
public final class MapgeneratorTestMain {
	
	/**
	 * 
	 */
	private MapgeneratorTestMain() {
		
	}

	/**
	 * 
	 * @param args Komandozeilenparameter.
	 */
	public static void main(final String[] args) {
		Mapgenerator.runTest(new Point(200, 200));
	}
}
