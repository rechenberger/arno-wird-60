package module;

import java.awt.Cursor;
import java.awt.Toolkit;

import game.effects.CooldownEffect;
import game.effects.DirectEffect;
import game.effects.Effect;
import game.effects.ItemEffect;
import game.skills.Skill;
import gui.Colors;
import gui.ContentPane;
import gui.Login;
import gui.image.ImageSet;
import gui.listeners.MainWindowAdapter;
import gui.loader.Loader;
import gui.lobby.Lobby;
import gui.match.MatchPanel;
import gui.statistic.AfterGameStatistic;
import gui.statistic.InGameStatistic;
import gui.usercontrols.UserControls;
import gui.usercontrols.knobs.EffectKnob;
import gui.usercontrols.layout.left.EffectKnobPanel;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.UIManager;

import settings.GlobalSettings;

/**
 * GUI-Modul.
 * 
 * @author Sean, Tristan, Christian
 */
public class GuiModuleClient extends Module {

	/**
	 * Skill, welcher als naechstes benutzt werden soll.
	 */
	private Skill nextSkillToUse = null;

	/**
	 * Speichert das contentPane zum Zugriff von aussen darauf.
	 */
	private ContentPane contentPane;

	/**
	 * Speichert die Bilder der UserControls.
	 */
	private ImageSet imageSet;

	/**
	 * Ob Kriegsnebel gezeichnet wird.
	 */
	private boolean drawFog = true;
	/**
	 * Das Frame.
	 */
	private JFrame mainFrame;

	/**
	 * Konstruktor.
	 */
	GuiModuleClient() {
		super(ModuleType.GUI);
		mainFrame = new JFrame("Arno wird 60");
		contentPane = new ContentPane(Toolkit.getDefaultToolkit()
				.getScreenSize());
		mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		mainFrame.addWindowListener(new MainWindowAdapter());
		mainFrame.setContentPane(contentPane);

		mainFrame.pack();
		
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			UIManager.put("Button.background", Colors.ORANGE);
			UIManager.put("Button.foreground", Colors.WHITE);
			UIManager.put("Button.select", Colors.GREY2);
			UIManager.put("Button.focus", Colors.GREY2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		this.imageSet = new ImageSet();

		this.showNextView();

		mainFrame.setVisible(true);
	}

	/**
	 * @param <T>
	 *            Klasse die gesucht und in die gecastet werden soll
	 * @param type
	 *            Klassentyp (z.B. UserControls.class)
	 * @return Gibt das entsprechende Panel zurueck mit passendem TypeCast
	 */
	public <T> T getPanel(final Class<T> type) {
		for (JComponent comp : GUIManager.getCurrentComponents()) {
			if (type.isInstance(comp)) {
				return type.cast(comp);
			}
		}

		return null;
	}

	/**
	 * @return Bilder der UserControls.
	 */
	public ImageSet getImageSet() {
		return this.imageSet;
	}

	/**
	 * @return Liefert das ContentPane zurueck, um auf alle Panels zugreifen zu
	 *         koennen.
	 */
	public ContentPane getContentPane() {
		return this.contentPane;
	}

	@Override
	public void run() {
		while (this.running) {
			try {
				mainFrame.repaint();
				this.sleepForSleepTime();
			} catch (java.lang.OutOfMemoryError e) {
				GlobalSettings.GUI_SHOW_PULSING_ANIMATION = false;
				GlobalSettings.MATCH_GENERATE_PROJECTILS = false;
			}
		}
	}

	/**
	 * Diese Methode kann von anderen Modulen benutzt werden, um zur naechsten
	 * Sicht zu wechseln.
	 */
	public void showNextView() {
		this.contentPane.removeAll();
		for (JComponent c : GUIManager.getNextComponents()) {
			this.contentPane.addComponent(c);
		}
	}

	/**
	 * Diese Methode kann von anderen Modulen benutzt werden, um zu einer
	 * beliebigen Sicht zu wechseln.
	 * 
	 * @param number
	 *            Nummer der Sicht
	 */
	public void showCertainView(final int number) {
		this.contentPane.removeAll();
		for (JComponent c : GUIManager.getCertainComponent(number)) {
			this.contentPane.addComponent(c);
		}
	}

	/**
	 * @return Skill, welcher als naechstes benutzt werden soll.
	 */
	public Skill getNextSkillToUse() {
		return nextSkillToUse;
	}

	/**
	 * @param setNextSkillToUse
	 *            Skill, welcher als naechstes benutzt werden soll.
	 */
	public void setNextSkillToUse(final Skill setNextSkillToUse) {
		this.nextSkillToUse = setNextSkillToUse;
		if (this.nextSkillToUse == null) {
			contentPane.setCursor(Cursor.getDefaultCursor());
		} else if (this.nextSkillToUse instanceof game.skills.AimedOnNonStaticSkill) {
			contentPane.setCursor(Cursor
					.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		} else {
			contentPane.setCursor(Cursor
					.getPredefinedCursor(Cursor.HAND_CURSOR));
		}
	}

	/**
	 * Wird vom Match bei neuem Effekt aufgerufen.
	 * 
	 * @param effect
	 *            neuer Effekt
	 */
	public void newEffect(final Effect effect) {
		if (!(effect instanceof DirectEffect)
				&& !(effect instanceof CooldownEffect)
				&& !(effect instanceof ItemEffect)) {
			if (effect.getEffects() == ModuleHandler.MATCH.getMyHero()) {
				EffectKnobPanel.addKnob(new EffectKnob(effect));
			}
		}
	}

	/**
	 * @return Ob Kriegsnebel gezeichnet wird.
	 */
	public boolean ifDrawFog() {
		return drawFog
				&& !ModuleHandler.MATCH.getMyHero().hasEffectByClass(
						game.content.heros.geronimo.ErleuchtungEffect.class);
	}

	/**
	 * Toggelt Kriegsnebel.
	 */
	public void toggleFog() {
		this.drawFog = !drawFog;
	}

	/**
	 * Klasse zum Verwalten von JComponents. Fuegt dynamisch Komponenten hinzu.
	 * 
	 * @author Christian Westhoff
	 * 
	 */
	private static class GUIManager {
		/**
		 * Zaehler fuer die aktuell verwendete Komponente.
		 */
		private static int ccounter = 0;
		/**
		 * Speichert die aktuell verwendeten Komponenten.
		 */
		private static JComponent[] currentComponents;
		/**
		 * Speichert alle Komponenten als Klassen.
		 */
		@SuppressWarnings("rawtypes")
		private static final Class[][] COMPONENTS = {
				new Class[] {Login.class},
				new Class[] {Loader.class},
				new Class[] {Lobby.class},
				new Class[] {MatchPanel.class, UserControls.class,
						InGameStatistic.class },
				new Class[] {AfterGameStatistic.class}
		};

		/**
		 * Privater Konstruktor.
		 */
		@SuppressWarnings("unused")
		protected GUIManager() {
		}

		/**
		 * Erstellt die naechste Komponenten. Setzt diese als aktuelle
		 * Komponenten.
		 * 
		 * @return JComponents
		 */
		public static JComponent[] getNextComponents() {
			try {
				if (ccounter < COMPONENTS.length) {
					currentComponents = new JComponent[COMPONENTS[ccounter].length];
					for (int i = 0; i < COMPONENTS[ccounter].length; i++) {
						currentComponents[i] = (JComponent) (COMPONENTS[ccounter][i]
								.newInstance());
					}
					ccounter++;
					return currentComponents;
				} else {
					ccounter = 0;
					return getNextComponents();
				}
			} catch (InstantiationException e) {

			} catch (IllegalAccessException e) {

			}
			return null;
		}

		/**
		 * Erstellt eine beliebige Komponente.
		 * 
		 * @param number
		 *            Nummer der Sicht
		 * @return JComponent
		 */
		public static JComponent[] getCertainComponent(final int number) {
			ccounter = number - 1;
			return getNextComponents();
		}

		/**
		 * Gibt die aktuellen Kompomenten zurueck.
		 * 
		 * @return JComponents.
		 */
		public static JComponent[] getCurrentComponents() {
			return currentComponents;
		}

	}
}