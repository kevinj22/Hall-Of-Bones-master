package JUnit_Tests;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import Heros.Soldier;
import PartyContainers.HumanPlayer;
import Statuses.BuffStatuses;
import Statuses.CrowdControlStatus;
import Statuses.OffensiveStatusesNotPerTurn;
import Statuses.OffensiveStatusesPerTurn;
import Statuses.Status;

public class JUnit_Tests_Statuses {
	
	//=====================================================================================
	//=====================================================================================
	//=====================================================================================
	
	// STATUS TESTS //
	
	/**
	 * Poison tick test, make sure the hero loses health
	 */
	@Test
	public void testPoisonTick()
	{
		Soldier hero = new Soldier(HumanPlayer.CONTROLLED);
		Status poison = new OffensiveStatusesPerTurn.Poison(-2, 2, 2);
		poison.addStatus(hero);
		hero.updateStatuses();
		int actual = hero.getHealth();
		int expected = hero.getMaxHealth() + poison.getEffectStrength();
		assertEquals(expected, actual);
	}
	
	/**
	 * Poison Test 2 Ticks
	 */
	@Test
	public void testPoisonTwoTicks()
	{
		Soldier hero = new Soldier(HumanPlayer.CONTROLLED);
		Status poison = new OffensiveStatusesPerTurn.Poison(-2, 2, 2);
		poison.addStatus(hero);
		hero.updateStatuses();
		hero.updateStatuses();
		int actual = hero.getHealth();
		int expected = hero.getMaxHealth() + 2 * poison.getEffectStrength();
		assertEquals(expected, actual);
	}
	
	/**
	 * Poison Test Removal after duration
	 */
	@Test
	public void testPoisonRemoval()
	{
		Soldier hero = new Soldier(HumanPlayer.CONTROLLED);
		Status poison = new OffensiveStatusesPerTurn.Poison(-2, 2, 2);
		poison.addStatus(hero);
		hero.updateStatuses();
		hero.updateStatuses();
		int actual = hero.getStatuses().size();
		int expected = 0;
		assertEquals(expected, actual);
	}
	
	/**
	 * Poison Stacking Test
	 */
	@Test
	public void testPoisonStack()
	{
		Soldier hero = new Soldier(HumanPlayer.CONTROLLED);
		Status poison = new OffensiveStatusesPerTurn.Poison(-2, 2, 2);
		poison.addStatus(hero);
		// Sleep so time stamp is different
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		poison.addStatus(hero);
		hero.updateStatuses();
		int actual = hero.getStatuses().size();
		int expected = 2;
		assertEquals(expected, actual);
	}
	
	/**
	 * Poison Stacking Damage Test
	 */
	@Test
	public void testPoisonStackDamage()
	{
		Soldier hero = new Soldier(HumanPlayer.CONTROLLED);
		Status poison = new OffensiveStatusesPerTurn.Poison(-2, 2, 2);
		poison.addStatus(hero);
		// Sleep so time stamp is different
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		poison.addStatus(hero);
		hero.updateStatuses();
		int actual = hero.getMaxHealth() + 2 * poison.getEffectStrength();
		int expected = 2;
		assertEquals(expected, actual);
	}

	/**
	 * LowerDefense test to check if armor lowered
	 */
	@Test
	public void testLowerDefenseApplication()
	{
		Soldier hero = new Soldier(HumanPlayer.CONTROLLED);
		Status lowerDefense = new OffensiveStatusesNotPerTurn.LowerDefense(-3, 2, 2);
		lowerDefense.addStatus(hero);
		// Get defense rating before applied
		int expected = hero.getDefenseRating() + lowerDefense.getEffectStrength();
		hero.updateStatuses();
		// Get after application 
		int actual = hero.getDefenseRating();
		assertEquals(expected, actual);
	}
	
	/**
	 * LowerDefense test to check if armor ISN'T lowered a second time on tick
	 */
	@Test
	public void testLowerDefenseTick()
	{
		Soldier hero = new Soldier(HumanPlayer.CONTROLLED);
		Status lowerDefense = new OffensiveStatusesNotPerTurn.LowerDefense(-3, 2, 2);
		lowerDefense.addStatus(hero);
		// Get defense rating before applied
		int expected = hero.getDefenseRating() + lowerDefense.getEffectStrength();
		hero.updateStatuses();
		hero.updateStatuses();
		// Get after application 
		int actual = hero.getDefenseRating();
		assertEquals(expected, actual);
	}
	
	/**
	 * LowerDefense test to check if armor is returned to original value after removal
	 */
	@Test
	public void testLowerDefenseRemoval()
	{
		Soldier hero = new Soldier(HumanPlayer.CONTROLLED);
		Status lowerDefense = new OffensiveStatusesNotPerTurn.LowerDefense(-3, 2, 2);
		lowerDefense.addStatus(hero);
		// Get defense rating before applied
		int expected = hero.getDefenseRating();
		hero.updateStatuses();
		hero.updateStatuses();
		hero.updateStatuses();
		// Get after application 
		int actual = hero.getDefenseRating();
		assertEquals(expected, actual);
	}
	
	/**
	 * LowerDefense test to check if armor is returned to original value after removal
	 */
	@Test
	public void testLowerDefenseRemovalFromStatuses()
	{
		Soldier hero = new Soldier(HumanPlayer.CONTROLLED);
		Status lowerDefense = new OffensiveStatusesNotPerTurn.LowerDefense(-3, 2, 2);
		lowerDefense.addStatus(hero);
		// Get defense rating before applied
		hero.updateStatuses();
		hero.updateStatuses();
		hero.updateStatuses();
		// Get after application 
		int actual = hero.getStatuses().size();
		int expected = 0;
		assertEquals(expected, actual);
	}
	
	/**
	 * Defend add to stats test.
	 */
	@Test
	public void testDefendStatIncrease()
	{
		Soldier hero = new Soldier(HumanPlayer.CONTROLLED);
		Status defend = new BuffStatuses.Defend(3, 1, 1);
		defend.addStatus(hero);
		int defenseBeforeApplication = hero.getDefenseRating();
		hero.updateStatuses();
		// Get after application 
		int actual = hero.getDefenseRating();
		int expected = defenseBeforeApplication + defend.getEffectStrength();
		assertEquals(expected, actual);
	}
	
	/**
	 * Defend Stat bonus Removal Test on duration expired
	 */
	@Test
	public void testDefendStatRemoval()
	{
		Soldier hero = new Soldier(HumanPlayer.CONTROLLED);
		Status defend = new BuffStatuses.Defend(3, 1, 1);
		defend.addStatus(hero);
		int defenseBeforeApplication = hero.getDefenseRating();
		hero.updateStatuses();
		hero.updateStatuses();
		// Get after application 
		int actual = hero.getDefenseRating();
		int expected = defenseBeforeApplication;
		assertEquals(expected, actual);
	}
	
	/**
	 * CrowdControlStatus change crowdControl boolean test.
	 */
	@Test
	public void testCrowdControlStatusInitialApply()
	{
		Soldier hero = new Soldier(HumanPlayer.CONTROLLED);
		Status stunned = new CrowdControlStatus("CC",3, 1, 1);
		stunned.addStatus(hero);
		boolean actual = hero.updateStatuses();
		// Get after application 
		boolean expected = true;
		assertEquals(expected, actual);
	}
	
	/**
	 * CrowdControlStatus change tick test. Should still be true.
	 */
	@Test
	public void testCrowdControlStatusTick()
	{
		Soldier hero = new Soldier(HumanPlayer.CONTROLLED);
		Status stunned = new CrowdControlStatus("CC",3, 2, 2);
		stunned.addStatus(hero);
		boolean actual = hero.updateStatuses();
		actual = hero.updateStatuses();
		// Get after application 
		boolean expected = true;
		assertEquals(expected, actual);
	}
	
	/**
	 * CrowdControlStatus removal. Should be false on expiration of status.
	 */
	@Test
	public void testCrowdControlRemoval()
	{
		Soldier hero = new Soldier(HumanPlayer.CONTROLLED);
		Status stunned = new CrowdControlStatus("CC",3, 2, 2);
		stunned.addStatus(hero);
		boolean actual = hero.updateStatuses();
		actual = hero.updateStatuses();
		actual = hero.updateStatuses();
		// Get after application 
		boolean expected = false;
		assertEquals(expected, actual);
	}
}
