package JUnit_Tests;

import static org.junit.Assert.*;

import java.util.TreeSet;

import org.junit.Test;

import BattleCommands.Item;
import Heros.Assassin;
import Heros.Hero;
import Heros.Soldier;
import PartyContainers.AI;
import PartyContainers.HumanPlayer;
import RPG_Exceptions.BattleModelException;
import RPG_Exceptions.MaximumStatException;
import RPG_Exceptions.NotAfflictedWithStatusException;
import Statuses.OffensiveStatusesPerTurn;
import Statuses.Status;

public class JUnit_Tests_Party_And_Items {

	//=====================================================================================
	//=====================================================================================
	//=====================================================================================
	
	// PARTY CREATION TESTS //
	
	/**
	 * Should create a party of 4
	 */
	@Test
	public void testPartyCreation() {
		HumanPlayer player = new HumanPlayer();
		int actual = player.getParty().size();
		int expected = 4;
		assertEquals(expected, actual);
	}

	/**
	 * Should create a party of 4
	 */
	@Test
	public void testAIPartyCreation() {
		AI ai = new AI();
		int actual = ai.getParty().size();
		int expected = 4;
		assertEquals(expected, actual);
	}
	
	/**
	 * Test hero compare to by speed only. Ascending sort test. 
	 */
	@Test
	public void testHeroCompareTo()
	{
		Soldier slowHero = new Soldier(HumanPlayer.CONTROLLED);
		Assassin fastHero = new Assassin(HumanPlayer.CONTROLLED);
		TreeSet<Hero> sortMe = new TreeSet<Hero>();
		sortMe.add(slowHero);
		sortMe.add(fastHero);
		// Sorts in ascending order
		Hero actual = sortMe.pollFirst();
		Hero expected = slowHero;
		assertEquals(expected,actual);
	}
	
	//=====================================================================================
	//=====================================================================================
	//=====================================================================================
	
	// ITEM TESTS //
	
	/**
	 * Should create an inventory of 3 separate items.
	 */
	@Test
	public void testInventoryCreation()
	{
		HumanPlayer player = new HumanPlayer();
		int actual = player.getInventory().size();
		int expected = 3;
		assertEquals(expected, actual);
	}
	
	/**
	 * Check for maximum stat exception on health potion.
	 * @throws MaximumStatException
	 */
	@Test(expected = MaximumStatException.class)
	public void testMaxStatExceptionHealthPotion() throws BattleModelException
	{
		HumanPlayer player = new HumanPlayer();
		Soldier hero = new Soldier(HumanPlayer.CONTROLLED);
		hero = (Soldier) player.getCharacter(hero.getClass().getName());
		Item healthPotion = Item.getItem(player, "Health Potion");
		Hero target = null;
		healthPotion.useBattleCommand(hero, target);
	}
	
	/**
	 * Check for maximum stat exception on ability points potion.
	 * @throws MaximumStatException
	 */
	@Test(expected = MaximumStatException.class)
	public void testMaxStatExceptionAbilityPotion() throws BattleModelException
	{
		HumanPlayer player = new HumanPlayer();
		Soldier hero = new Soldier(HumanPlayer.CONTROLLED);
		hero = (Soldier) player.getCharacter(hero.getClass().getName());
		Item abilityPointsPotion = Item.getItem(player, "AbilityPoints Potion");
		Hero target = null;
		abilityPointsPotion.useBattleCommand(hero, target);
	}
	
	/**
	 * Check if hero ability points is increased by the effect strength of the item.
	 * @throws MaximumStatException
	 */
	@Test
	public void testIfHealed() throws BattleModelException
	{
		HumanPlayer player = new HumanPlayer();
		Soldier hero = new Soldier(HumanPlayer.CONTROLLED);
		hero = (Soldier) player.getCharacter(hero.getClass().getName());
		Item abilityPointsPotion = Item.getItem(player, "AbilityPoints Potion");
		hero.setAbilityPoints(0);
		Hero target = null;
		abilityPointsPotion.useBattleCommand(hero, target);
		int actual = hero.getAbilityPoints();
		int expected = abilityPointsPotion.getEffectStrength();
		assertEquals(expected, actual);
	}
	
	/**
	 * Check that hero is not healed passed maximum value ability points.
	 * @throws MaximumStatException
	 */
	@Test
	public void testIfNotHealedPastMaxAmountAbilityPoints() throws BattleModelException
	{
		HumanPlayer player = new HumanPlayer();
		Soldier hero = new Soldier(HumanPlayer.CONTROLLED);
		hero = (Soldier) player.getCharacter(hero.getClass().getName());
		Item abilityPointsPotion = Item.getItem(player, "AbilityPoints Potion");
		hero.setAbilityPoints(hero.getMaxAP()-1);
		Hero target = null;
		abilityPointsPotion.useBattleCommand(hero, target);
		int actual = hero.getAbilityPoints();
		int expected = hero.getMaxAP();
		assertEquals(expected, actual);
	}
	
	/**
	 * Check that hero is not healed passed maximum value health points.
	 * @throws MaximumStatException
	 */
	@Test
	public void testIfNotHealedPastMaxAmountHealth() throws BattleModelException
	{
		HumanPlayer player = new HumanPlayer();
		Soldier hero = new Soldier(HumanPlayer.CONTROLLED);
		hero = (Soldier) player.getCharacter(hero.getClass().getName());
		Item healthPotion = Item.getItem(player, "Health Potion");
		hero.setHealth(hero.getMaxHealth()-1);
		Hero target = null;
		healthPotion.useBattleCommand(hero, target);
		int actual = hero.getHealth();
		int expected = hero.getMaxHealth();
		assertEquals(expected, actual);
	}

	/**
	 * Test status item use, should remove poison
	 */
	@Test
	public void testStatusItemUse() throws BattleModelException
	{
		HumanPlayer player = new HumanPlayer();
		Soldier hero = new Soldier(HumanPlayer.CONTROLLED);
		hero = (Soldier) player.getCharacter(hero.getClass().getName());
		Item antidote = Item.getItem(player, "Antidote");
		Status poison = new OffensiveStatusesPerTurn.Poison(-2,2,2);
		poison.addStatus(hero);
		Hero target = null;
		antidote.useBattleCommand(hero, target);
		int actual = hero.getStatuses().size();
		int expected = 0;
		assertEquals(expected, actual);
	}
	
	/**
	 * Test status item exception. Should throw NotAfflicted exception
	 */
	@Test(expected = NotAfflictedWithStatusException.class)
	public void testStatusException() throws BattleModelException
	{
		HumanPlayer player = new HumanPlayer();
		Soldier hero = new Soldier(HumanPlayer.CONTROLLED);
		hero = (Soldier) player.getCharacter(hero.getClass().getName());
		Item antidote = Item.getItem(player, "Antidote");
		Hero target = null;
		antidote.useBattleCommand(hero, target);
	}
	
}
