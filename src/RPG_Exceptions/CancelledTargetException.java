package RPG_Exceptions;

import BattleMVC.BattleController;

/**
 * CancelledTargetException catches a cancelled target exception during battle.. 
 * @author Kevin
 *
 */
public class CancelledTargetException extends BattleModelException{
	/**
	 * Added so the compiler doesn't complain.
	 */
	private static final long serialVersionUID = 3567746945824316434L;

	/**
	 * Default exception constructor
	 */
	public CancelledTargetException()
	{
		super();
	}
	
	/**
	 * Message based exception constructor.
	 * @param msg
	 */
	public CancelledTargetException(String msg)
	{
		super(msg);
	}
	
	/**
	 * Construct this exception with a controller to send the error signal to.
	 */
	public CancelledTargetException(BattleController controller)
	{
		super(controller);
	}
	
	/**
	 * Construct this exception with a controller to send the error signal to and a message.
	 */
	public CancelledTargetException(String msg, BattleController controller)
	{
		super(msg,controller);
	}
	
	/**
	 * No need to send signal message to player. Just print out to console.
	 */
	@Override
	public void sendControllerErrorSignal() {
		System.out.println("Cancelled Target");
	}
}
