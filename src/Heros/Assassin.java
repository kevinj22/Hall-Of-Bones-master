package Heros;

import BattleCommands.Ability;
import BattleCommands.AssassinAbility;
import BattleCommands.BaseAttack;
import BattleCommands.OffensiveAbility;

/**
 * Assassin hero class. Sets base level 1 Assassin fields. 
 * Checks to insure the class only has AssassinAbilities.
 * @author Kevin
 *
 */

public class Assassin extends Hero{
	private static final String HERO_NAME = "Assassin";
	private static int experiencePerLevel = 100;
	private static final String IMAGE = "/assassin.gif";
	private int baseStrength;
	private int strengthItemBonus;
	private int attackPower;
	private BaseAttack baseAttack;
	private OffensiveAbility poisonBlade = new AssassinAbility.PoisonBlade();
	private OffensiveAbility exposeWeakness = new AssassinAbility.ExposeWeakness();
	private OffensiveAbility throwKnives = new AssassinAbility.ThrowKnives();
	
	/**
	 * Default Assassin constructor
	 * @param controlledBy who will control this character, player or AI?
	 */
	public Assassin(String controlledBy)
	{
		super(Assassin.IMAGE,0,0/Assassin.experiencePerLevel,6,10,2,4, controlledBy, HERO_NAME);
		this.baseStrength = 2;
		this.strengthItemBonus = 1;
		this.attackPower = this.baseStrength + this.strengthItemBonus;
		this.baseAttack = new BaseAttack(this.attackPower);
		this.setAbilityCheckType(baseAttack);
		this.setAbilityCheckType(poisonBlade);
		this.setAbilityCheckType(exposeWeakness);
		this.setAbilityCheckType(throwKnives);
		this.setAttackPower(attackPower);
	}
	
	/**
	 * Set this hero's abilities, used to insure they only receive AssassinAbilities.
	 */
	@Override
	public void setAbilityCheckType(Ability ability) {
		if(ability.getClassOwner().equals("ALL") || ability.getClassOwner().equals("ASSASSIN"))
		{
			ability.setAbility(this);
		}
	}
	
	/**
	 * Get the image path to be displayed by the view.
	 * @return image path
	 */
	public String getImage() {
		return Assassin.IMAGE;
	}
	
}
