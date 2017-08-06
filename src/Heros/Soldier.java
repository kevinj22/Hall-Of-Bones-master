package Heros;

import BattleCommands.Ability;
import BattleCommands.BaseAttack;
import BattleCommands.OffensiveAbility;
import BattleCommands.SoldierAbility;
import BattleCommands.SoldierAbility.Defend;

/**
 * Solder hero class. Sets base level 1 Soldier fields. 
 * Checks to insure the class only has SoldierAbilities.
 * @author Kevin
 *
 */
public class Soldier extends Hero{
	
	private static final String HERO_NAME = "Solider";
	private static int experiencePerLevel = 100;
	private static final String IMAGE = "/human_warrior.gif";
	private int baseStrength;
	private int strengthItemBonus;
	private int attackPower;
	private BaseAttack baseAttack;
	private OffensiveAbility hamString = new SoldierAbility.HamString();
	private OffensiveAbility shieldBash = new SoldierAbility.ShieldBash();
	
	/**
	 * Default Soldier constructor
	 * @param controlledBy who will control this character, player or AI?
	 */
	public Soldier(String controlledBy)
	{
		// Set experience to 0, level 0, health 6, AB 5, defense 2, speed 2
		super(Soldier.IMAGE,0,0/Soldier.experiencePerLevel,6,5,2,2,controlledBy, HERO_NAME);
		this.baseStrength = 3;
		this.strengthItemBonus = 1;
		this.attackPower = this.baseStrength + this.strengthItemBonus;
		this.baseAttack = new BaseAttack(this.attackPower);
		Defend soldierDefend = new SoldierAbility.Defend(3);
		this.setAbilityCheckType(baseAttack);
		this.setAbilityCheckType(hamString);
		this.setAbilityCheckType(soldierDefend);
		this.setAbilityCheckType(shieldBash);
		this.setAttackPower(attackPower);
	}
	
	/**
	 * Set this hero's abilities, used to insure they only receive SoldierAbilities.
	 */
	@Override
	public void setAbilityCheckType(Ability ability) {
		if(ability.getClassOwner().equals("ALL") || ability.getClassOwner().equals("SOLDIER"))
		{
			ability.setAbility(this);
		}
	}
	
	/**
	 * Get the image path to be displayed by the view.
	 * @return image path
	 */
	public String getImage() {
		return Soldier.IMAGE;
	}
	
}
