package BattleCommands;

import java.awt.Image;

import GridGUI.ImagePreparation;

/**
 * Contains many inner classes of Mage type abilities.
 * @author Kevin
 *
 */
public abstract class MageAbility {
	
	private static final String BELONGS_TO_CLASS = "MAGE";
	
	/**
	 * FireBall class is an OffensiveAbility. Note the absence of the useBattleCommand method.
	 * Since this is a pure OffensiveAbility there is no need to redefine this method, it will immediately call OffensiveAbiltiies
	 * useBattleCommand.
	 * @author Kevin
	 *
	 */
	public static class FireBall extends OffensiveAbility {
		public static final String NAME = "FireBall";
		private static final int ABILITY_POINTS_COST = 6;
		private static final int BASE_DAMAGE = 8;
		private static final Image ANIMATION_IMAGE = ImagePreparation.getInstance().prepImage("/fireball.png", ABILITY_IMAGE_WIDTH, ABILITY_IMAGE_HEIGHT);
		private String description = "<html>The Mage conjures and hurls a Fireball at it's enemy.<br> Does " 
				+ String.format("%d", this.getDamage()) + " base damage.<br> Costs " + this.getPointCost() + " ability points.</html>";
		
		/**
		 * Default constructor that sets the ability point cost and damage done.
		 */
		public FireBall()
		{
			// Point Cost, damage
			super(ABILITY_POINTS_COST,BASE_DAMAGE, ANIMATION_IMAGE);
			this.setName(FireBall.NAME);
			this.setDescription(description);
		}
		
		/**
		 * Returns the class string that owns these abilities.
		 * @return Class string that owns these abilities.
		 */
		@Override
		public String getClassOwner() {
			return MageAbility.BELONGS_TO_CLASS;
		}
	}
	
	/**
	 * Lightning class is an OffensiveAbility. Note the absence of the useBattleCommand method.
	 * Since this is a pure OffensiveAbility there is no need to redefine this method, it will immediately call OffensiveAbiltiies
	 * useBattleCommand.
	 * @author Kevin
	 *
	 */
	public static class Lightning extends OffensiveAbility {
		public static final String NAME = "Lightning";
		private static final int ABILITY_POINTS_COST = 2;
		private static final int BASE_DAMAGE = 6;
		private static final Image ANIMATION_IMAGE = ImagePreparation.getInstance().prepImage("/lightning.png", ABILITY_IMAGE_WIDTH, ABILITY_IMAGE_HEIGHT);
		private String description = "<html> The Mage conjures a bolt of Lightning and rains it on it's enemy.<br> Does " 
				+ String.format("%d", this.getDamage()) + " base damage.<br> Costs " + this.getPointCost() + "  ability points.</html>";
		
		/**
		 * Default constructor that sets the ability point cost and damage done.
		 */
		public Lightning()
		{
			// Point Cost, damage
			super(ABILITY_POINTS_COST,BASE_DAMAGE, ANIMATION_IMAGE);
			this.setName(Lightning.NAME);
			this.setDescription(description);
		}
		
		/**
		 * Returns the class string that owns these abilities.
		 * @return Class string that owns these abilities.
		 */
		@Override
		public String getClassOwner() {
			return MageAbility.BELONGS_TO_CLASS;
		}
	}
	
	/**
	 * Blizzard class is an OffensiveAbility. Note the absence of the useBattleCommand method.
	 * Since this is a pure OffensiveAbility there is no need to redefine this method, it will immediately call OffensiveAbiltiies
	 * useBattleCommand.
	 * @author Kevin
	 *
	 */
	public static class Blizzard extends OffensiveAbility {
		public static final String NAME = "Blizzard";
		private static final int ABILITY_POINTS_COST = 4;
		private static final int BASE_DAMAGE = 4;
		private static final Image ANIMATION_IMAGE = ImagePreparation.getInstance().prepImage("/dagger.png", ABILITY_IMAGE_WIDTH, ABILITY_IMAGE_HEIGHT);
		private String description = "<html> The Mage conjures and hurls a Blizzard at it's enemy.<br> Does " 
				+ String.format("%d", this.getDamage()) + " base damage.<br> Costs " + this.getPointCost() + " ability points.</html>";
		
		/**
		 * Default constructor that sets the ability point cost and damage done.
		 */
		public Blizzard()
		{
			// Point Cost, damage
			super(ABILITY_POINTS_COST,BASE_DAMAGE, ANIMATION_IMAGE);
			this.setName(Blizzard.NAME);
			this.setDescription(description);
		}
		
		/**
		 * Returns the class string that owns these abilities.
		 * @return Class string that owns these abilities.
		 */
		@Override
		public String getClassOwner() {
			return MageAbility.BELONGS_TO_CLASS;
		}
	}
}
