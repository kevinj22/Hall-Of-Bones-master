package RPG_Exceptions;

import BattleMVC.BattleController;

/**
 * BattleModelException is an abstract checked exception class for battle use only
 * that defines methods for it's children to implement.
 * @author Kevin
 *
 */
public abstract class BattleModelException extends Exception{
	
	/**
	 * Added so the compiler doesn't complain.
	 */
	private static final long serialVersionUID = 3567746945824316434L;
	private BattleController controller;
	
	/**
	 * Default exception constructor
	 */
	public BattleModelException()
	{
		super();
	}
	
	/**
	 * Construct this exception with a controller to send the error signal to.
	 */
	public BattleModelException(BattleController controller)
	{
		super();
		this.controller = controller;
	}
	
	/**
	 * Construct this exception with a controller to send the error signal to and a message.
	 */
	public BattleModelException(String msg, BattleController controller)
	{
		super(msg);
		this.controller = controller;
	}
	
	/**
	 * Message based exception constructor.
	 * @param msg
	 */
	public BattleModelException(String msg)
	{
		super(msg);
	}
	
	/**
	 * Abstract method as we don't know what signal is sent yet.
	 */
	public abstract void sendControllerErrorSignal();
	
	/**
	 * @return the controller
	 */
	public BattleController getController() {
		return controller;
	}

}
