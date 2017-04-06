package gui.usercontrols.layout.left;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;

import game.attributes.Attribute;
import game.attributes.AttributeValueList;
import game.content.heros.Hero;
import game.effects.Effect;
import game.objects.GameObject;
import game.objects.Item;
import game.objects.NonStatic;
import game.skills.Skill;
import gui.Colors;
import gui.StringHelper;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import module.ModuleHandler;

import settings.GlobalSettings;

/**
 * Zeigt Informationen ueber ein angeklickes NonStatic an.
 * 
 * @author Alex, Marius, Tristan
 * 
 */
public class GameObjectInfoPanel extends JPanel {

	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = -5161498229716507477L;

	/**
	 * GameObject ueber den Informationen angezeigt werden.
	 */
	private GameObject gameObject;

	/**
	 * Konstruktor.
	 */
	public GameObjectInfoPanel() {
		this.setBackground(Colors.BLACK_04);
		Dimension size;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		if (GlobalSettings.GUI_SHOW_POS_IN_NONSTATIC_PANEL) {
			size = new Dimension(300, 165);
		} else {
			size = new Dimension(300, 150);
		}

		this.setPreferredSize(size);
		this.setMinimumSize(size);
		this.setMaximumSize(size);
		this.setOpaque(true);
	}
	
	/**
	 * schreibt alle Zeilen in die GUI.
	 * @param g2 Das Graphics Object
	 * @param lines Die Zeilen.
	 */
	private void printLines(final Graphics2D g2, final LinkedList<String> lines) {
		int currentLineY = 5;
		int startLineX = 10;
		int ySize = (int) (g2.getFontMetrics().getAscent() * 1.3);
		for (String l : lines) {
			ySize += StringHelper.stringSize(g2, l).getHeight() * 1.4;
		}
		
		if (ySize !=  this.getSize().height) {
			this.setSize(this.getSize().width, ySize);
			this.setPreferredSize(new Dimension(300, ySize));
			this.setMinimumSize(new Dimension(300, ySize));
			this.setMaximumSize(new Dimension(300, ySize));
			this.revalidate();
		}
		
		for (String line : lines) {
			if (line == " <br> ") { // Eine komplett neue Linie ist einfach zuviel Abstand, deswegen heir der quickfix
				currentLineY += 15;
			} else {
				StringHelper.drawString(g2, line, startLineX, currentLineY);
				Rectangle2D rect = StringHelper.stringSize(g2, line);
				currentLineY += (int) rect.getHeight() + 5;
			}
		}
	}

	/**
	 * Zeichnet das GameObjectInfoPanel fuer einen NonStatic.
	 * 
	 * @param g2 Das Graphicsobjekt
	 * @param ns Das Nonstatic
	 */
	private void paintNonStaticInfoPanel(final Graphics2D g2, final NonStatic ns) {

		LinkedList<String> lines = new LinkedList<String>();
		
		if (ns instanceof Hero) {
			Hero hero = (Hero) ns;
			if (hero.getPlayer() != null) {
				String player = " Spieler: " + hero.getPlayer().getName();
				lines.add(player);
			}
			String hName = " Held: " + hero.getName();
			lines.add(hName);
			String lvl = " Level: " + hero.getLevel();
			lines.add(lvl);
			if (GlobalSettings.GUI_SHOW_POS_IN_NONSTATIC_PANEL) {
				String pos = " Position: " + hero.getCoord();
				lines.add(pos);
			}
			

		}
		String dmg = " Schaden: " + ns.getAttributeValue(Attribute.damage);
		String fSpeed = " Angriffsgeschwindigkeit: " + ns.getAttributeValue(Attribute.fightingSpeed);
		String fRange = " Reichweite: " + ns.getAttributeValue(Attribute.fightingRange);
		String mSpeed = " Bewegungsgeschwindigkeit: " + ns.getAttributeValue(Attribute.movementSpeed);
		String vRange = " Sichtweite: " + ns.getAttributeValue(Attribute.viewRange);
		
		lines.add(dmg);
		lines.add(fSpeed);
		lines.add(fRange);
		lines.add(mSpeed);
		lines.add(vRange);

		if (ns instanceof Hero) {
			
			String inventory = " Inventar";
			lines.add(inventory);
			
			Hero h = (Hero) ns;
			for (Item i :  h.getInventoryNonUsable().keySet()) {
				String iInfo = " " + i.getName() + "(" + h.getInventoryNonUsable().get(i) + ")";
				lines.add(iInfo);
				
			}
			
		}
		
		this.printLines(g2, lines);
	}
	
	/**
	 * Fuegt die Zeilen der Attribute Value List zu der zu Zeichnenden LinkedList hinzu.
	 * 
	 * @param g2 Das Graphicsobjekt
	 * @param avl Die AttributeValueList
	 * @param lines Die Bisherigen auszugebenden Linien, zu welchen nun welche hinzugefuegt werden.
	 */
	private void getLinesToPrintOfAttributeValueList(final Graphics2D g2, final AttributeValueList avl, final LinkedList<String> lines) {
		if (avl.getAttributeValueMap() == null) {
			return;
		}
		
		
		for (Attribute attribute : avl
				.getAttributeValueMap().keySet()) {
			
			String string = attribute.getName();
			string += ":";
			
			if (avl.getAttributeValueMap()
					.get(attribute).getSumm() != 0) {
				string += " ";
				if (avl.getAttributeValueMap()
						.get(attribute).getSumm() > 0) {
					string += "+";
				}
				string += avl.getAttributeValueMap()
						.get(attribute).getSumm();
			}
			if (avl.getAttributeValueMap()
					.get(attribute).getProduct() != 1.0f) {
				
				string += " ";
				
				int prozent = (int) (avl
						.getAttributeValueMap().get(attribute)
						.getProduct() * 100) - 100;
				if (prozent > 0) {
					string += "+ ";
				}
				string += prozent + "%";
			}

			lines.add(string);
		}
	}
	
	/**
	 * Zeichnet das GameObjectInfoPanel fuer einen NonStatic.
	 * 
	 * @param g2 Das Graphicsobjekt
	 * @param s Der Skill
	 */
	private void paintSkillInfoPanel(final Graphics2D g2, final Skill s) {
		LinkedList<String> lines = new LinkedList<String>();
		String sName = "Name: " + s.getName().replaceAll("<br>", " ");
		String sLevel = "Level: " + Integer.toString(ModuleHandler.MATCH.getMyHero().getSkillLevel(s));
		lines.add(sName);
		lines.add(sLevel);
		if (s.getDescription().length() > 0) {
			lines.add(s.getDescription());
		}
		
		lines.add("Typ: " + s.getClass().getSuperclass().getSimpleName());
		
		lines.add("Cooldown: " + s.getCooldown(ModuleHandler.MATCH.getMyHero()) / 1000 + "s" + ", Mana: " + s.getMana(ModuleHandler.MATCH.getMyHero()));
		lines.add("Reichweite: " + s.getRange(ModuleHandler.MATCH.getMyHero()) + ", Radius: " + s.getRadius(ModuleHandler.MATCH.getMyHero()));
		String sEffects = "Betrifft: ";
		if (s.getEffectsSelf()) {
			sEffects += "Mich, ";
		}
		if (s.getEffectsAllies()) {
			sEffects += "Verbuendete, ";
		}
		if (s.getEffectsEnemies()) {
			sEffects += "Feinde, ";
		}
		if (s.getEffectsHeroOnly()) {
			sEffects += "nur Helden";
		}
		lines.add(sEffects);
		
		lines.add(" <br> ");
		
		this.getLinesToPrintOfAttributeValueList(g2, s.getEffectsAttributeValueList(ModuleHandler.MATCH.getMyHero()), lines);
		
		this.printLines(g2, lines);
	}

	@Override
	public void paintComponent(final Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		super.paintComponent(g);

		g2.setColor(Colors.WHITE);
		this.setOpaque(gameObject != null);

		if (gameObject == null) {
			return;
		} else if (gameObject instanceof NonStatic) {
			NonStatic ns = (NonStatic) gameObject;
			if (ns != null) {
				this.setOpaque(true);
				this.paintNonStaticInfoPanel(g2, ns);
			}
		} else if (gameObject instanceof Skill) {
				this.paintSkillInfoPanel(g2, (Skill) gameObject);
		} else if (gameObject instanceof Effect) {
			try {
				LinkedList<String> lines = new LinkedList<String>();
				Effect e = (Effect) gameObject;
				lines.add(e.getName());
				lines.add("Typ " + e.getClass().getSuperclass().getSimpleName());
				this.getLinesToPrintOfAttributeValueList(g2, e.getAttributeValueList(), lines);
				this.printLines(g2, lines);
			} catch (java.lang.NullPointerException e) {
				this.setGameObject(null);
			}
		} else if (gameObject instanceof Item) {
			try {
				LinkedList<String> lines = new LinkedList<String>();
				Item item = (Item) gameObject;
				lines.add(item.getName());
				this.getLinesToPrintOfAttributeValueList(g2, item.getAttributeValueList(), lines);
				this.printLines(g2, lines);
			} catch (java.lang.NullPointerException e) {
				this.setGameObject(null);
			}
		}

	}

	/**
	 * SetterMethode fuer den NonStatic.
	 * 
	 * @param gameObject
	 *            Anzuzeigendes GameObject
	 */
	public void setGameObject(final GameObject gameObject) {
		this.gameObject = gameObject;
	}

}
