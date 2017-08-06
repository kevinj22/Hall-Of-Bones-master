package RPG_Exceptions;

import BattleMVC.BattleController;

/**
 * NotEnoughAbilityPoints should be thrown when a hero attempts to use a Ability they don't have enough AbilityPoints to use.
 * @author Kevin
 *
 */
public class NotEnoughAbilityPointsException extends BattleModelException{
	
	/**
	 * Added so the compiler doens't complain
	 */
	private static final long serialVersionUID = -5709828237365122833L;

	/**
	 * Default exception constructor
	 */
	public NotEnoughAbilityPointsException()
	{
		super();
	}
	
	/**
	 * Message based exception constructor.
	 * @param msg
	 */
	public NotEnoughAbilityPointsException(String msg)
	{
		super(msg);
	}
	
	/**
	 * Construct this exception with a controller to send the error signal to.
	 */
	public NotEnoughAbilityPointsException(BattleController controller)
	{
		super(controller);
	}
	
	/**
	 * Construct this exception with a controller to send the error signal to and a message.
	 */
	public NotEnoughAbilityPointsException(String msg, BattleController controller)
	{
		super(msg,controller);
	}
	

	/**
	 * Send message to controller to signal view to display error popup.
	 */
	@Override
	public void sendControllerErrorSignal() {
		this.getController().sendNotEnoughAbilityPointsSignal();
	}
}
