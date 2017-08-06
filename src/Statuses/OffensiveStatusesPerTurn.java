package Statuses;

import java.awt.Image;
import GridGUI.ImagePreparation;


/**
 * Container class to hold all OffensiveStatuses that tick per turn.
 * @author Kevin
 *
 */
public class OffensiveStatusesPerTurn {
	
	/**
	 * Poison class, damages health.
	 * @author Kevin
	 *
	 */
	public static class Poison extends OffensiveStatusPerTurn
	{
		public static final String NAME = "Poison";
		private static final String STAT_AFFECTED = "health";
		private static final Image ANIMATION_IMAGE = ImagePreparation.getInstance().prepImage("/poison_drop.png", Status.STATUS_IMAGE_WIDTH, Status.STATUS_IMAGE_HEIGHT);
		
		/**
		 * Constructor to set fields. Make sure the per turn damage is NEGATIVE.
		 * @param perTurnDamage: per turn damage of status, must be NEGATIVE.
		 * @param duration: how many turns this status will tick.
		 * @param defaultDuration: default duration. 
		 * @pre perTurnDamage should be negative
		 */
		public Poison(int perTurnDamage, int duration, int defaultDuration)
		{
			super(Poison.NAME,Poison.STAT_AFFECTED,perTurnDamage,duration,defaultDuration, ANIMATION_IMAGE);
		}
		
		public Image getAnimationImage()
		{
			return Poison.ANIMATION_IMAGE;
		}
		
	}
	
	
}
