package JUnit_Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import BattleCommands.Ability;
import BattleCommands.DefensiveAbility;
import BattleCommands.OffensiveAbility;
import Heros.Assassin;
import Heros.Hero;
import Heros.Paladin;
import Heros.Soldier;
import PartyContainers.HumanPlayer;
import RPG_Exceptions.BattleModelException;
import RPG_Exceptions.MaximumStatException;
import RPG_Exceptions.NotEnoughAbilityPointsException;

public class JUnit_Tests_Abilities {
	
	/**
	 * BaseAttack damage application. Should damage the target.
	 * @throws MaximumStatException 
	 */
	@Test
	public void testBaseAttackDamage() throws BattleModelException
	{
		Soldier hero = new Soldier(HumanPlayer.CONTROLLED);
		Soldier target = new Soldier(HumanPlayer.CONTROLLED);
		OffensiveAbility offensiveAbility = (OffensiveAbility) Ability.getAbility(hero, "BaseAttack");
		offensiveAbility.useBattleCommand(hero, target);
		int actual = target.getHealth();
		int expected = target.getMaxHealth() - OffensiveAbility.evaluateDamage(target, offensiveAbility);
		assertEquals(expected,actual);
	}
	
	/**
	 * BaseAttack ability points subtraction application. Should remove 0 ability points from hero who uses it.
	 * @throws MaximumStatException 
	 */
	@Test
	public void testBaseAttackAbilityPointsUsage() throws BattleModelException
	{
		Soldier hero = new Soldier(HumanPlayer.CONTROLLED);
		Soldier target = new Soldier(HumanPlayer.CONTROLLED);
		OffensiveAbility offensiveAbility = (OffensiveAbility) Ability.getAbility(hero, "BaseAttack");
		offensiveAbility.useBattleCommand(hero, target);
		int actual = hero.getAbilityPoints();
		int expected = hero.getMaxAP() - offensiveAbility.getPointCost();
		assertEquals(expected,actual);
	}
	
	/**
	 * HamString damage application. Should damage the target.
	 * @throws MaximumStatException 
	 */
	@Test
	public void testHamStringDamage() throws BattleModelException
	{
		Soldier hero = new Soldier(HumanPlayer.CONTROLLED);
		Soldier target = new Soldier(HumanPlayer.CONTROLLED);
		OffensiveAbility offensiveAbility = (OffensiveAbility) Ability.getAbility(hero, "HamString");
		offensiveAbility.useBattleCommand(hero, target);
		int actual = target.getHealth();
		int expected = target.getMaxHealth() - OffensiveAbility.evaluateDamage(target, offensiveAbility);
		assertEquals(expected,actual);
	}
	
	/**
	 * HamString ability points subtraction application. Should remove ability points from hero who uses it.
	 * @throws MaximumStatException 
	 */
	@Test
	public void testHamStringAbilityPointsUsage() throws BattleModelException
	{
		Soldier hero = new Soldier(HumanPlayer.CONTROLLED);
		Soldier target = new Soldier(HumanPlayer.CONTROLLED);
		OffensiveAbility offensiveAbility = (OffensiveAbility) Ability.getAbility(hero, "HamString");
		offensiveAbility.useBattleCommand(hero, target);
		int actual = hero.getAbilityPoints();
		int expected = hero.getMaxAP() - offensiveAbility.getPointCost();
		assertEquals(expected,actual);
	}
	
	/**
	 * PoisonBlade damage subtraction on application. .
	 * @throws MaximumStatException 
	 */
	@Test
	public void testPoisonBladeDamage() throws BattleModelException
	{
		Assassin hero = new Assassin(HumanPlayer.CONTROLLED);
		Assassin target = new Assassin(HumanPlayer.CONTROLLED);
		OffensiveAbility offensiveAbility = (OffensiveAbility) Ability.getAbility(hero, "PoisonBlade");
		offensiveAbility.useBattleCommand(hero, target);
		int actual = target.getHealth();
		int expected = target.getMaxHealth() - OffensiveAbility.evaluateDamage(target, offensiveAbility);
		assertEquals(expected,actual);
	}
	
	/**
	 * PoisonBlade application of poison status. Make sure that there is a status ailment added to the target. 
	 * @throws MaximumStatException 
	 */
	@Test
	public void testPoisonBladeStatusApplication() throws BattleModelException
	{
		Assassin hero = new Assassin(HumanPlayer.CONTROLLED);
		Assassin target = new Assassin(HumanPlayer.CONTROLLED);
		OffensiveAbility offensiveAbility = (OffensiveAbility) Ability.getAbility(hero, "PoisonBlade");
		offensiveAbility.useBattleCommand(hero, target);
		int actual = target.getStatuses().size();
		int expected = 1;
		assertEquals(expected,actual);
	}
	
	/**
	 * Check for IllegalArgumentException if the acting hero does not have enough ability points. 
	 * @throws MaximumStatException
	 */
	@Test(expected = NotEnoughAbilityPointsException.class)
	public void testNotEnoughAbilityPointsException() throws BattleModelException
	{
		Soldier hero = new Soldier(HumanPlayer.CONTROLLED);
		Soldier target = new Soldier(HumanPlayer.CONTROLLED);
		OffensiveAbility offensiveAbility = (OffensiveAbility) Ability.getAbility(hero, "HamString");
		hero.setAbilityPoints(0);
		offensiveAbility.useBattleCommand(hero, target);
	}
	
	/**
	 * Check if Heal ability increases hero health. 
	 * @throws MaximumStatException
	 */
	@Test
	public void testHealAbilityHealthIncrease() throws BattleModelException
	{
		Paladin hero = new Paladin(HumanPlayer.CONTROLLED);
		DefensiveAbility ability = (DefensiveAbility) Ability.getAbility(hero, "Heal");
		hero.setHealth(hero.getMaxHealth()-ability.getEffectStrength());
		Hero target = null;
		ability.useBattleCommand(hero, target);
		int actual = hero.getHealth();
		int expected = hero.getMaxHealth();
		assertEquals(expected,actual);
	}
	
	/**
	 * Check if Heal ability throws MaxStat.
	 * @throws MaximumStatException
	 */
	@Test(expected = MaximumStatException.class)
	public void testHealAbilityThrows() throws BattleModelException
	{
		Paladin hero = new Paladin(HumanPlayer.CONTROLLED);
		DefensiveAbility ability = (DefensiveAbility) Ability.getAbility(hero, "Heal");
		Hero target = null;
		ability.useBattleCommand(hero, target);
	}
	
	/**
	 * Check if Heal ability doesn't heal above max health.
	 * @throws MaximumStatException
	 */
	@Test
	public void testHealAbilityNotAboveMax() throws BattleModelException
	{
		Paladin hero = new Paladin(HumanPlayer.CONTROLLED);
		DefensiveAbility ability = (DefensiveAbility) Ability.getAbility(hero, "Heal");
		hero.setHealth(hero.getMaxHealth()-1);
		Hero target = null;
		ability.useBattleCommand(hero, target);
		int actual = hero.getHealth();
		int expected = hero.getMaxHealth();
		assertEquals(expected,actual);
	}
}
