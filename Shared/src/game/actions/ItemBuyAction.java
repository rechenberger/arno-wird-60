package game.actions;

import game.content.heros.Hero;
import game.effects.ItemEffect;
import game.objects.Item;
import game.objects.NonStatic;

import java.awt.Point;

import com.messages.Message;
import com.messages.MessageType;
/**
 * Aktion um ein Item im Shop zu kaufen.
 * @author Tristan
 *
 */
public class ItemBuyAction extends Action {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -3123882244839846104L;
	
	/**
	 * Id des zu kaufenden Items.
	 */
	private int itemId;

	/**
	 * Konstruktor.
	 * @param setExecuter Kauefer.
	 * @param setItem zu kaufendes Item.
	 */
	public ItemBuyAction(final NonStatic setExecuter, final Item setItem) {
		super(setExecuter, null);
		this.itemId = setItem.getId();
		// TODO Auto-generated constructor stub
	}

	@Override
	public Point getTargetPoint() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 
	 * @return zu kaufendes Item.
	 */
	public Item getItem() {
		Item item = Item.getById(itemId);
		return item;
	}

	@Override
	public void executeSkill() {

	}
	
	@Override
	public void execute() {
		Item item = this.getItem();
		if (item.canEffort(this.getExecuter())) {
			new ItemEffect(this.getExecuter(), item).ready();
		} else {
			matchModule.sendMessage(
					new Message(MessageType.USER_LESS_MONEY), ((Hero) this.getExecuter()).getUserId());
		}
		if (this.getExecuter() instanceof game.content.heros.Hero) {
			((Hero) this.getExecuter()).getPlayer().incStatistic("ItemsBuyed");
		}
		this.end();
	}

}
