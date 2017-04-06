package game.content.items.useable;


import game.actions.ActionNotAimed;
import game.objects.Item;
import game.skills.ItemSkill;
/**
 * Ein benutzbares Item.
 * @author Tristan
 *
 */
public abstract class UseableItem extends Item {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 5993451374796604772L;
	
	/**
	 * Id des Skills, der beim benutzen ausgefuehrt wird.
	 */
	private int itemSkillId;

	/**
	 * Konstruktor.
	 * @param setName Name des Items.
	 */
	public UseableItem(final String setName) {
		super(setName);
		this.getItemSkill().setUseableItem(this);
		this.itemSkillId = this.getItemSkill().getId();
	}
	
	/**
	 * Plant die Benutzung des Items.
	 * @param executer Benutzer
	 */
	public void planUse(final game.objects.NonStatic executer) {
		ItemSkill itemSkill = ItemSkill.getById(itemSkillId);
		new ActionNotAimed(executer, itemSkill).plan();
	}
	
	/**
	 * @return vernuepfter Skill.
	 */
	public abstract ItemSkill getItemSkill();
	

}
