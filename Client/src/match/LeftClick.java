package match;

import game.attributes.Attribute;
import game.content.buildings.Shop;
import game.objects.Map;
import game.objects.NonStatic;
import game.objects.WorldObject;
import gui.shop.ShopPanel;
import gui.usercontrols.UserControls;
import gui.usercontrols.modal.ComponentModal;
import gui.usercontrols.modal.Modal;

import java.awt.Point;
import java.util.LinkedList;

import module.ModuleHandler;

/**
 * //Bei Linksklick auf die Karte wird diese Klasse aufgerufen. 
 * Sie soll untersuchen, ob
 * ob auf der angeklickten Koordinate ein NonStatic steht, 
 * ueber das Informationen angezeigt werden koennen.
 * Wenn sich dort eins befindet wird das NonStatic in der GUI gesetzt.
 * @author Alex
 *
 */
public class LeftClick {	
	/**
	 * Konstruktor.
	 * @param point Angeklickter Punkt
	 */
	public LeftClick(final Point point) {
		LinkedList<WorldObject> ll = ModuleHandler.MATCH.getMatch().getMap().getList(point);
		if (ll.size() > 0) {
			if (ll.getLast() instanceof Shop) {
				Shop shop = (Shop) ll.getLast();
				if (shop.getFraction() == ModuleHandler.MATCH.getMyHero().getFraction()) {
					int dist = Map.getDistance(shop.getCoord(), ModuleHandler.MATCH.getMyHero().getCoord());
					if (dist <= ModuleHandler.MATCH.getMyHero().getAttributeValue(Attribute.viewRange)) {
						ShopPanel shopPanel = new ShopPanel(shop);
						ComponentModal shopModal = new ComponentModal("Shop", shopPanel);
						shopModal.renameCancelButton("Schlie\u00dfen");
						shopModal.show();
					} else {
						new Modal("Einkauf unm\u00f6glich!", "Sie sind leider zu weit weg und k\u00f6nnen nicht einkaufen.").show();
					}
				} else {
					new Modal("Arrrrr. Der Feind in meinem Shop!", "Im Shop des Feindes einzukaufen ist wohl keine gute Idee."
							+ "<br>Sie werden rausgeworfen.").show();
				}
				
			} else if (ll.getLast() instanceof NonStatic) {
				ModuleHandler.GUI.getPanel(UserControls.class).setNonStaticInfoPanel((NonStatic) ll.getLast());
			} else {
				ModuleHandler.GUI.getPanel(UserControls.class).setNonStaticInfoPanel(null);
			}
		}
		
	}
}
