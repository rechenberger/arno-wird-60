package game.content.ki.arno.behavior;

import game.content.ki.Ki;
import game.content.ki.arno.Default;
import game.content.ki.arno.behavior.attack.AttackCharacter;
import game.objects.Fraction;

import util.Graphomat;

/**
 * Die unterschiedlichen Graphomaten des neutralen Monster werden hier definiert.
 * Momentan gibt es nur einen Graphomaten fuer neutrale Monster.
 * 
 * @see game.content.ki.vasallen.behavior.VasalTypes
 * @author Marius
 *
 */
public enum NeutralTypes {
	
	/**
	 * Arnos Standard Ki.
	 * Default Mobs.
	 * Greifen an solange sie einen Gegner sehen und dieser, oder sie selbst nicht zuweit vom eigenen Spawnpoint entfernt ist.
	 */
	DEFAULT() {

		@Override
		public Ki getNew(final Fraction f, final NeutralGroup memberOf) {
			return new Default();
		}

		@Override
		public Graphomat getGraphomat() {
			Graphomat g = DefaultStates.getRouteAsGraphomat();
			AttackCharacter.addAttackStatesToGraph(g);
			return g;
		}
		
		
	};

	/**
	 * Gibt eine Neue Ki vom definierten Typ zuurueck.
	 * 
	 * @param f
	 *            Die Fraktion die diese Ki angehoert.
	 * @param memberOf
	 *            Die zugehoerigkeit zu einer kiGruppe
	 * @return Das neue KI objekt.
	 */
	public abstract Ki getNew(Fraction f, NeutralGroup memberOf);

	/**
	 * Hier wird der Graphomat erstellt, ueber den sich die Ki Operationen
	 * definieren fuer den unteren Pfad.
	 * 
	 * @return Das Objekt des Graphomats
	 */
	public abstract Graphomat getGraphomat();
	
}
