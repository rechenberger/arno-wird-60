package gui.match;

import java.awt.Color;
import java.awt.Image;

import module.ModuleHandler;

import game.content.effects.duration.PulsingEffect;
import game.effects.Effect;
import game.objects.NonStatic;
import gui.image.ImageProcessing;

/**
 * Utility Klasse fuer Pulsierende Animation der Effekte.
 * @author Tristan, Christian.
 *
 */
public final class PulsingEffectAnimation {
	
	/**
	 * Transparenzverlauf der Farbe.
	 */
	private static final int[] OPACITY_PROGRESSION = {0, 25, 50, 75, 100, 125, 150, 175, 200, 175, 150, 125, 100, 75, 50, 25};
	
	/**
	 * 
	 * @param image Bild.
	 * @param color Farbe.
	 * @return eingefaerbtes teil-transparentes Bild.
	 */
	public static Image animate(final Image image, final Color color) {
		//CHECKSTYLE:OFF
		Color current = new Color(color.getRed(), color.getGreen(), color.getBlue(), OPACITY_PROGRESSION[ModuleHandler.GUI.getSleepCounter() % OPACITY_PROGRESSION.length]);
		return ImageProcessing.transformColors(image, current);
	}
	
	/**
	 * Faerbt das Bild abhaengig von PulsingEffect eines NonStatic.
	 * @param nonStatic NonStatic
	 * @param image Bild des NonStatics.
	 * @return eingefarbetes teil-transparentes Bild.
	 */
	public static Image animate(final NonStatic nonStatic, final Image image) {
		for (Effect effect : nonStatic.getEffects()) {
			if (effect instanceof PulsingEffect) {
				return animate(image, ((PulsingEffect) effect).getPulsingColor());
			}
		}
		return null;
			
	}
	
	/**
	 * Utility-Klasse.
	 */
	private PulsingEffectAnimation() {
		
	}
}
