package JUnit_Tests;

import org.junit.Test;

import Heros.SkeletonBoss;
import PartyContainers.AI;
import PartyContainers.HumanPlayer;
import RPG_Exceptions.BattleModelException;

public class JUnit_Tests_AI {

	/**
	 * Get AI to scan the player
	 * @throws BattleModelException 
	 */
	@Test
	public void testAI_Turn() throws BattleModelException {
		HumanPlayer human = new HumanPlayer();
		AI ai = new AI();
		SkeletonBoss hero = new SkeletonBoss(AI.CONTROLLER);
		hero = (SkeletonBoss) ai.getCharacter(hero.getClass().getName());
		ai.aiTurn(hero, human);
		// Whatever the AI does in this situation test it
	}
	
	/**
	 * Test AI heal
	 * @throws BattleModelException 
	 */
	@Test
	public void testAI_Heal() throws BattleModelException {
		HumanPlayer human = new HumanPlayer();
		AI ai = new AI();
		SkeletonBoss hero = new SkeletonBoss(AI.CONTROLLER);
		hero = (SkeletonBoss) ai.getCharacter(hero.getClass().getName());
		hero.setHealth(2);
		ai.aiTurn(hero, human);
		// The AI should heal
	}
	
}
