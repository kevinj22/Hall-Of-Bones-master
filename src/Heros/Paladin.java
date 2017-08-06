package Heros;

import BattleCommands.Ability;
import BattleCommands.BaseAttack;
import BattleCommands.DefensiveAbility;
import BattleCommands.OffensiveAbility;
import BattleCommands.PaladinAbility;

/**
 * Paladin hero class. Sets base level 1 Paladin fields. 
 * Checks to insure the class only has PaladinAbilities.
 * @author Kevin
 *
 */

public class Paladin extends Hero{
	
	private static final String HERO_NAME = "Paladin";
	private static int experiencePerLevel = 100;
	private static final String IMAGE = "/paladin.gif";
	private int baseStrength;
	private int strengthItemBonus;
	private int attackPower;
	private BaseAttack baseAttack;
	private OffensiveAbility holySmite = new PaladinAbility.HolySmite();
	private DefensiveAbility defend = new PaladinAbility.Defend(4);
	private DefensiveAbility heal = new PaladinAbility.Heal();
	
	/**
	 * Default Paladin constructor
	 * @param controlledBy who will control this character, player or AI?
	 */
	public Paladin(String controlledBy)
	{
		// Set experience to 0, level 0, health 6, AB 5, defense 2, speed 2
		super(Paladin.IMAGE,0,0/Paladin.experiencePerLevel,6,5,2,2, controlledBy, HERO_NAME);
		this.baseStrength = 3;
		this.strengthItemBonus = 1;
		this.attackPower = this.baseStrength + this.strengthItemBonus;
		this.baseAttack = new BaseAttack(this.attackPower);
		this.setAbilityCheckType(baseAttack);
		this.setAbilityCheckType(holySmite);
		this.setAbilityCheckType(defend);
		this.setAbilityCheckType(heal);
		this.setAttackPower(attackPower);
	}
	
	/**
	 * Set this hero's abilities, used to insure they only receive PaladinAbilities.
	 */
	@Override
	public void setAbilityCheckType(Ability ability) {
		if(ability.getClassOwner().equals("ALL") || ability.getClassOwner().equals("PALADIN"))
		{
			ability.setAbility(this);
		}
	}
	
	/**
	 * Get the image path to be displayed by the view.
	 * @return image path
	 */
	public String getImage() {
		return Paladin.IMAGE;
	}
}
