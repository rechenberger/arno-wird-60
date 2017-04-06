package gui.usercontrols.layout.bottom;

import game.attributes.Attribute;
import gui.Colors;
import gui.listeners.KnobListener;
import gui.usercontrols.knobs.Knob;

import java.awt.Graphics;

import module.ModuleHandler;

/**
 * Zeichnet den Knopf des Leben.
 * @author Sean
 *
 */
public class HealthKnob extends Knob {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -8331032436643084578L;

	/**
	 * Setzt die Farbe und das Hintergrundbild fuer die Lebensanzeige.
	 */
	public HealthKnob() {	
		super(Colors.RED1, ModuleHandler.GUI.getImageSet().getImage("usercontrols/knob/health"), true);
		addMouseListener(new KnobListener());
	}
	
	@Override
	public String getName() {
		return "Leben";
	}
	
	@Override
	protected String getValueText() {
		return ModuleHandler.MATCH.getMyHero().getAttributeValue(Attribute.currentHealth) + "" /* + "/" + ModuleHandler.MATCH.getMyHero().getAttributeValue(Attribute.maxHealth) */;
	}
	
	@Override
	public void paintComponent(final Graphics g) {
		super.paintComponent(g);
		setArcAngle(ModuleHandler.MATCH.getMyHero().getAttributeValue(Attribute.currentHealth)
				/ (float) (ModuleHandler.MATCH.getMyHero().getAttributeValue(Attribute.maxHealth)));
	}
	
}
