package game.skills;

import game.attributes.AttributeValueList;
import game.content.heros.Hero;
import game.content.items.useable.UseableItem;
import game.objects.NonStatic;

/**
 * Skill der ausfefuehrt wird, wenn Item benutzt wird.
 * @author Tristan
 *
 */
public class ItemSkill extends NotAimedSkill {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 8398856595946934707L;
	
	/**
	 * Das zu benutzende Item.
	 */
	protected UseableItem item;

	/**
	 * Konstruktor.
	 */
	public ItemSkill() {
		super("Item benutzen");
	}
	
	/**
	 * @param useableItem Das zu benutzende Item.
	 */
	public void setUseableItem(final UseableItem useableItem) {
		this.item = useableItem;
	}
	
	/**
	 * Nimmt dem Verwender den ItemEffekt ab und fuehrt dann den Skill wie immer aus.
	 * @param executer Itemverwender.
	 */
	public void execute(final NonStatic executer) {
		if (executer.hasItem(item) > 0) {
			executer.removeItem(item);
			super.execute(executer);
			return;
		} else {
			if (executer instanceof Hero) {
				((Hero) executer).getPlayer().sendSystemChatMessege("Held kann Item " + item.getName() + " nicht einsetzten, da er es nicht besitzt.");
			}
			return;
		}
	}

	@Override
	public AttributeValueList getEffectsAttributeValueList(final NonStatic executer) {
		return new AttributeValueList();
	}

}
