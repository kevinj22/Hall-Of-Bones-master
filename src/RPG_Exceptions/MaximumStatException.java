package RPG_Exceptions;

import BattleMVC.BattleController;

/**
 * MaximumStatException should be thrown when a hero attempts to use an ability that will make no difference to their statistics.
 * @author Kevin
 *
 */
public class MaximumStatException extends BattleModelException{
	
	/**
	 * Added so the compiler doesn't complain.
	 */
	private static final long serialVersionUID = 3567746945824316434L;

	/**
	 * Default exception constructor
	 */
	public MaximumStatException()
	{
		super();
	}
	
	/**
	 * Message based exception constructor.
	 * @param msg
	 */
	public MaximumStatException(String msg)
	{
		super(msg);
	}
	
	/**
	 * Construct this exception with a controller to send the error signal to.
	 */
	public MaximumStatException(BattleController controller)
	{
		super(controller);
	}
	
	/**
	 * Construct this exception with a controller to send the error signal to and a message.
	 */
	public MaximumStatException(String msg, BattleController controller)
	{
		super(msg,controller);
	}
	
	/**
	 * Send message to controller to signal view to display error popup.
	 */
	@Override
	public void sendControllerErrorSignal() {
		this.getController().sendMaxStatExceptionSignal();
	}
}
