package gui.usercontrols.knobs;

import game.effects.Effect;
import game.effects.PermanentEffect;
import gui.Colors;
import gui.usercontrols.layout.left.EffectKnobPanel;

import java.awt.Graphics;
import module.ModuleHandler;

/**
 * Zeichnet den Knopf der Effekte.
 * @author Tristan
 *
 */
public class EffectKnob extends Knob {

	/**
	 * SerialUID.
	 */
	private static final long serialVersionUID = -6935733560058608905L;
	
	/**
	 * Effekt, der durch den Knob angezeigt wird.
	 */
	private Effect effect;
	
	/**
	 * Setzt die Farbe und das Bild des Skills, sowie die maximale Groesse.
	 * @param effect Effekt, der durch den Knob angezeigt wird.
	 */
	public EffectKnob(final Effect effect) {
		super(Colors.ORANGE, ModuleHandler.GUI.getImageSet().getImage(effect.getImageURL()), false);
		this.setShowAnchor(false);
		this.effect = effect;
		this.arcInverse = true;
		this.setName(this.effect.getName());
		this.small = true;
		this.circleTranslation = this.minSize;
	}
	
	@Override
	public void paintComponent(final Graphics g) {
		super.paintComponent(g);
		if (this.effect instanceof PermanentEffect && ((PermanentEffect) this.effect).isAutoEnding()) {
			if (((PermanentEffect) this.effect).getTimeDurationLeft() <= 0) {
				EffectKnobPanel.removeKnob(this);
			}
			this.setArcAngle(1.0f - ((PermanentEffect) this.effect).getTimeDurationLeftRatio());
		} else {
			this.setArcAngle(1);
		}
	}
	
	@Override
	protected String getValueText() {
		return "";
	}
	
	/**
	 * Liefert den Effect zurueck.
	 * @return Effect
	 */
	public Effect getEffect() {
		return this.effect;
	}
}
