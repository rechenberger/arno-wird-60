package com.messages;

import game.content.heros.Hero;
import game.skills.Skill;

/**
 * 
 * @author Tristan
 *
 */
public class SkillPointMessage extends Message {

	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -8314864365210775096L;
	
	/**
	 * Id des Helden.
	 */
	private int heroId;
	

	/**
	 * Id des Skills.
	 */
	private int skillId;
	
	/**
	 * @param hero Held
	 * @param skill Skill
	 */
	public SkillPointMessage(final Hero hero, final Skill skill) {
		super(MessageType.SKILLPOINT);
		this.heroId = hero.getId();
		this.skillId = skill.getId();
	}
	
	/**
	 * 
	 */
	public void execute() {
		Hero hero = Hero.getById(heroId);
		Skill skill = Skill.getById(skillId);
		hero.incSkillLevel(skill);
	}
}
