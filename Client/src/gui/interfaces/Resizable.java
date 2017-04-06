package gui.interfaces;

/**
 * Componenten, die den ResizeComponentListener benutzen, muessen dieses Interace implementieren,
 * da der Listener nur die Methode resized() aufruft.
 * @author Sean
 *
 */
public interface Resizable {
	
	/**
	 * Wird vom ResizeComponentListener aufgerufen.
	 */
	void resized();
}
