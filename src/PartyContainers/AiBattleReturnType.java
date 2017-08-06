package PartyContainers;

import BattleCommands.BattleCommand;
import Heros.Hero;

/**
 * AiBattleReturnType is made so that we can return two different types at the end of the AI's turn.
 * @author Kevin
 *
 */
public class AiBattleReturnType {
	private Hero target;
	private BattleCommand cmd;
	
	/**
	 * Constructor to set this objects target and cmd fields.
	 * @param target target selected by AI
	 * @param cmd command selected by AI
	 */
	public AiBattleReturnType(Hero target, BattleCommand cmd)
	{
		this.target = target;
		this.cmd = cmd;
	}

	/**
	 * Get the selected target.
	 * @return The selected target.
	 */
	public Hero getTarget() {
		return target;
	}

	/**
	 * Get the selected BattleCommand.
	 * @return The selected BattleCommand.
	 */
	public BattleCommand getCmd() {
		return cmd;
	}
}
