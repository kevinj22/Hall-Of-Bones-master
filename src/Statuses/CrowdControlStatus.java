package Statuses;

import java.awt.Image;
import java.util.concurrent.TimeUnit;

import BattleMVC.BattleController;
import BattleMVC.BattleModel;
import GridGUI.ImagePreparation;
import Heros.Hero;

/**
 * CrowdControl status stuns a character and skips their turn for the duration of the status.
 * @author Kevin
 *
 */
public class CrowdControlStatus extends OffensiveStatusNotPerTurn{
	private static final String STAT_AFFECTED = "turn";
	private static final Image ANIMATION_IMAGE = ImagePreparation.getInstance().prepImage("/Stunned.png", STATUS_IMAGE_WIDTH, STATUS_IMAGE_HEIGHT);
	
	/**
	 * CrowdControlStatus argument constructor.
	 * @param name: status name
	 * @param statAffected stat affected
	 * @param effectStrength effect strength
	 * @param duration duration of status
	 * @param defaultDuration default duration of status
	 */
	public CrowdControlStatus(String name, int effectStrength, int duration, int defaultDuration)
	{
		super(name,STAT_AFFECTED,effectStrength,duration,defaultDuration, ANIMATION_IMAGE);
	}
	
	/**
	 * Copy constructor to clone statuses with. 
	 * @param other
	 */
	public CrowdControlStatus(Status other)
	{
		super(other);
	}
	
	/**
	 * Clone method, since it's parent Status is a abstract class we can't create instances of it. 
	 * This is used in updating a hero's statuses to create a copy of the proper run type
	 * so we can still apply polymorphism. 
	 * 
	 * Why not a static factory method? 
	 * Polymorphism doesn't apply to static methods.
	 */
	@Override
	public CrowdControlStatus clone() {
	    return new CrowdControlStatus(this);
	}
	
	/**
	 * Updates crowd control status. Returns the crowdControlled boolean, if true the remainder of their turn is skipped after all other statuses are updated.
	 * When duration ticks to 0 the status is removed but the crowdControl status is not yet set to false.
	 * It will change next turn when the enemy updates their status. 
	 * @param hero the hero you wish to target
	 */
	@Override
	public boolean updateStatus(Hero hero)
	{
		boolean crowdControlled = false;
		int duration = this.getDuration();
		
		if(duration > 0)
		{
			crowdControlled = true;
			System.out.println(this.toString() + " being updated. Stat affected: " + this.getStatAffected());
			duration--;
			this.setDuration(duration);
		}
		
		// Remove when ticked to zero 
		// crowdControlled status will update to false next turn anyways 
		if(duration == 0)
		{
			System.out.println(this.toString() + " being removed.");
			this.removeStatus(hero);
		}
		return crowdControlled; 
	}
	
	/**
	 * Updates crowd control status, sends signal to controller to tell view to animate. 
	 * Returns the crowdControlled boolean, if true the remainder of their turn is skipped after all other statuses are updated.
	 * When duration ticks to 0 the status is removed but the crowdControl status is not yet set to false.
	 * It will change next turn when the enemy updates their status. 
	 * @param hero hero whose status is updating
	 * @param controller which will send update signal to view 
	 */
	@Override
	public boolean updateStatus(Hero hero, BattleController controller)
	{
		boolean crowdControlled = false;
		int duration = this.getDuration();
		
		if(duration > 0)
		{
			crowdControlled = true;
			System.out.println(this.toString() + " being updated. Stat affected: " + this.getStatAffected());
			duration--;
			this.setDuration(duration);
			controller.animateBattleCommand(hero, this.getAnimationImage(), true);
			try {
				TimeUnit.MILLISECONDS.sleep(BattleModel.DISPLAY_SLEEP_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		// Remove when ticked to zero 
		// crowdControlled status will update to false next turn anyways 
		if(duration == 0)
		{
			System.out.println(this.toString() + " being removed.");
			this.removeStatus(hero);
		}
		return crowdControlled; 
	}
}
