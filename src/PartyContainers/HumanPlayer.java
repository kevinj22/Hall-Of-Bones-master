package PartyContainers;

import java.util.ArrayList;

import Heros.Assassin;
import Heros.Hero;
import Heros.Mage;
import Heros.Paladin;
import Heros.Soldier;

/**
 * Class that extends Player that states human control. Only adds human usable Classes to the party.
 * Why not package HumanPlayer and AI player under player with one different static constant?
 * The AI player will implement many methods unique to its own class.
 * @author Kevin
 *
 */

public class HumanPlayer extends Player{
	
	// Constant used to define who has control for the battle system
	public static String CONTROLLED = "HUMAN";

	/**
	 * Call constructor to create default party
	 */
	public HumanPlayer()
	{
		super();
		makeDefaultParty();
	}
	
	/**
	 * Used in the constructor to create the default Human Player party.
	 */
	public void makeDefaultParty()
	{
		ArrayList<Hero> partyArray = new ArrayList<Hero>();
		partyArray.add(new Soldier(HumanPlayer.CONTROLLED));
		partyArray.add(new Mage(HumanPlayer.CONTROLLED));
		partyArray.add(new Assassin(HumanPlayer.CONTROLLED));
		partyArray.add(new Paladin(HumanPlayer.CONTROLLED));
		for(Hero hero : partyArray)
		{
			super.getParty().put(hero.getClass().getName(), hero);
		}
	}	
}
