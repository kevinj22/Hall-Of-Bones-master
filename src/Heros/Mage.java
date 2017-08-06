package Heros;

import BattleCommands.Ability;
import BattleCommands.BaseAttack;
import BattleCommands.MageAbility;
import BattleCommands.OffensiveAbility;

/**
 * Mage hero class. Sets base level 1 Mage fields. 
 * Checks to insure the class only has MageAbilities.
 * @author Kevin
 *
 */

public class Mage extends Hero{
	private static final String HERO_NAME = "Mage";
	private static int experiencePerLevel = 100;
	private static final String IMAGE = "/Dark-priest-idle.gif";
	private int baseStrength;
	private int strengthItemBonus;
	private int attackPower;
	private BaseAttack baseAttack;
	private OffensiveAbility fireBall = new MageAbility.FireBall();
	private OffensiveAbility lightning = new MageAbility.Lightning();
	private OffensiveAbility blizzard = new MageAbility.Blizzard();
	
	/**
	 * Default Mage constructor
	 * @param controlledBy who will control this character, player or AI?
	 */
	public Mage(String controlledBy)
	{
		// Set experience to 0, level 0, health 6, AB 5, defense 1, speed 3
		super(Mage.IMAGE,0,0/Mage.experiencePerLevel,3,20,1,3, controlledBy, HERO_NAME);
		this.baseStrength = 1;
		this.strengthItemBonus = 0;
		this.attackPower = this.baseStrength + this.strengthItemBonus;
		this.baseAttack = new BaseAttack(this.attackPower);
		this.setAbilityCheckType(baseAttack);
		this.setAbilityCheckType(fireBall);
		this.setAbilityCheckType(lightning);
		this.setAbilityCheckType(blizzard);
		this.setAttackPower(attackPower);
	}
	
	/**
	 * Set this hero's abilities, used to insure they only receive MageAbilities.
	 */
	@Override
	public void setAbilityCheckType(Ability ability) {
		if(ability.getClassOwner().equals("ALL") || ability.getClassOwner().equals("MAGE"))
		{
			ability.setAbility(this);
		}
	}

	/**
	 * Get the image path to be displayed by the view.
	 * @return image path
	 */
	public String getImage() {
		return Mage.IMAGE;
	}
}
