package Statuses;

import java.awt.Image;

import GridGUI.ImagePreparation;

/**
 * Container class to hold all BuffStatuses.
 * @author Kevin
 *
 */
public class BuffStatuses {

	/**
	 * Defend class that ADDS to a hero's defense rating for a duration. 
	 * @author Kevin
	 *
	 */
	public static class Defend extends BuffStatus
	{
		public static final String NAME = "Defend";
		private static final String STAT_AFFECTED = "defenseRating";
		private static final Image ANIMATION_IMAGE = ImagePreparation.getInstance().prepImage("/green_arrow.png", Status.STATUS_IMAGE_WIDTH, Status.DEFEND_STATUS_HEIGHT);
		
		/**
		 * Constructor to set fields. Make sure the oneTimeEffectStrength is POSITIVE.
		 * @param oneTimeEffectStrength amount to increase the defensive rating by
		 * @param duration total duration of this status effect
		 * @param defaultDuration default duration of this status effect 
		 * @pre The oneTimeEffectStrength is POSITIVE.
		 */
		public Defend(int oneTimeEffectStrength, int duration, int defaultDuration)
		{
			super(Defend.NAME,Defend.STAT_AFFECTED,oneTimeEffectStrength,duration,defaultDuration, ANIMATION_IMAGE);
		}
	}
	
}
