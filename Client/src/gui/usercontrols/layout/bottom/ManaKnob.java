package gui.usercontrols.layout.bottom;

import game.attributes.Attribute;
import gui.Colors;
import gui.listeners.KnobListener;
import gui.usercontrols.knobs.Knob;

import java.awt.Graphics;

import module.ModuleHandler;

/**
 * Zeichnet den Knopf des Manas.
 * @author Sean
 *
 */
public class ManaKnob extends Knob {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 8403917265165155494L;

	/**
	 * Setzt die Farbe und das Hintergrundbild der Manaanzeige.
	 */
	public ManaKnob() {
		super(Colors.GREEN1, ModuleHandler.GUI.getImageSet().getImage("usercontrols/knob/power"), true);
		addMouseListener(new KnobListener());
	}
	
	@Override
	public String getName() {
		return "Kraft";
	}
	
	@Override
	protected String getValueText() {
		return ModuleHandler.MATCH.getMyHero().getAttributeValue(Attribute.currentMana)  + "" /*+ "/" + ModuleHandler.MATCH.getMyHero().getAttributeValue(Attribute.maxMana) */;
	}
	
	@Override
	public void paintComponent(final Graphics g) {
		super.paintComponent(g);
		setArcAngle(ModuleHandler.MATCH.getMyHero().getAttributeValue(Attribute.currentMana)
				/ (float) (ModuleHandler.MATCH.getMyHero().getAttributeValue(Attribute.maxMana)));
	}
}
