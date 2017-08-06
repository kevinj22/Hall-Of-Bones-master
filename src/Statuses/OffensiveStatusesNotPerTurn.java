package Statuses;

import java.awt.Image;

import GridGUI.ImagePreparation;

/**
 * Container class to hold all OffensiveStatuses that DO NOT tick per turn.
 * @author Kevin
 *
 */
public class OffensiveStatusesNotPerTurn {

	/**
	 * LowerDefense class, lowers a heros defense rating. 
	 * @author Kevin
	 *
	 */
	public static class LowerDefense extends OffensiveStatusNotPerTurn
	{
		public static final String NAME = "LowerDefense";
		private static final String STAT_AFFECTED = "defenseRating";
		private static final Image ANIMATION_IMAGE = ImagePreparation.getInstance().prepImage("/red_minus.png", Status.STATUS_IMAGE_WIDTH, Status.STATUS_IMAGE_HEIGHT);
		
		/**
		 * Constructor to set fields. Make sure the oneTimeEffectStrength is NEGATIVE.
		 * @param oneTimeEffectStrength amount to reduce the defensive rating by
		 * @param duration total duration of this status effect
		 * @param defaultDuration default duration of this status effect 
		 * @pre The oneTimeEffectStrength is NEGATIVE.
		 */
		public LowerDefense(int oneTimeEffectStrength, int duration, int defaultDuration)
		{
			super(LowerDefense.NAME,LowerDefense.STAT_AFFECTED, oneTimeEffectStrength, duration, defaultDuration, ANIMATION_IMAGE);
		}
	}
	
}
