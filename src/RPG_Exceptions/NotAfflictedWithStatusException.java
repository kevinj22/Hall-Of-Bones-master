package RPG_Exceptions;

import BattleMVC.BattleController;

/**
 * NotAfflictedWithStatus should be thrown when a hero attempts to use a StatusItem that will make no difference to their statistics.
 * @author Kevin
 *
 */
public class NotAfflictedWithStatusException extends BattleModelException{

	/**
	 * Added so the compiler doesn't complain.
	 */
	private static final long serialVersionUID = 8929233347896854450L;

	/**
	 * Default exception constructor
	 */
	public NotAfflictedWithStatusException()
	{
		super();
	}
	
	/**
	 * Message based exception constructor.
	 * @param msg
	 */
	public NotAfflictedWithStatusException(String msg)
	{
		super(msg);
	}
	
	/**
	 * Construct this exception with a controller to send the error signal to.
	 */
	public NotAfflictedWithStatusException(BattleController controller)
	{
		super(controller);
	}
	
	/**
	 * Construct this exception with a controller to send the error signal to and a message.
	 */
	public NotAfflictedWithStatusException(String msg, BattleController controller)
	{
		super(msg,controller);
	}
	

	/**
	 * Send message to controller to signal view to display error popup.
	 */
	@Override
	public void sendControllerErrorSignal() {
		this.getController().sendNotAfflictedWithStatusSignal();
	}
}
