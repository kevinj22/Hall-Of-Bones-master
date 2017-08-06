package BattleCommands;

import BattleMVC.BattleController;
import Heros.Hero;
import RPG_Exceptions.BattleModelException;

/**
 * BattleCommand is an abstract class that will be used to define methods for all subclasses of battle command: Ability and Item.
 * @author Kevin
 *
 */
public abstract class BattleCommand {
	
	/**
	 * Print a string representation of this battle command.
	 * @return A string representation of this battle command.
	 */
	public abstract String toString();
	
	/**
	 * Use battle command when given a acting hero and a target.
	 * @param hero current acting hero
	 * @param target target hero
	 * @throws A child of BattleModelException if the defensive ability or item used cannot increase the statistic that it affects, if you pick a 
	 * target that isn't alive, or if the character doesn't have enough ability points. 
	 */
	public abstract void useBattleCommand(Hero hero, Hero target) throws BattleModelException;
	
	/**
	 * Use battle command in GUI, prompts a human for input as to who their target is.
	 * @param hero current acting hero
	 * @param controller controller to animate the action 
	 * @throws A child of BattleModelException if the defensive ability or item used cannot increase the statistic that it affects, if you pick a 
	 * target that isn't alive, or if the character doesn't have enough ability points. 
	 */
	public abstract void useBattleCommand(Hero hero, BattleController controller) throws BattleModelException;
	
}
