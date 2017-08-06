package BattleCommands;

import java.awt.Image;

import BattleMVC.BattleController;
import GridGUI.ImagePreparation;
import Heros.Hero;
import RPG_Exceptions.BattleModelException;
import RPG_Exceptions.CancelledTargetException;
import Statuses.OffensiveStatusesNotPerTurn;
import Statuses.OffensiveStatusesPerTurn;
import Statuses.Status;
import Statuses.StatusEffectAbility;

/**
 * Contains many inner classes of assassin type abilities.
 * @author Kevin
 *
 */
public abstract class AssassinAbility extends Ability {

	/**
	 * Final constant string declaring which class these abilities belong to.
	 */
	private static final String BELONGS_TO_CLASS = "ASSASSIN";
	
	/**
	 * PoisonBlade class is an OffensiveAbility that implements the StatusEffectAbility interface.
	 * By implementing the StatusEffectAbility interface it must provide a method getAbilityStatus()
	 * If this ability is implemented properly in it's useBattleCommand method it should call, clone, and then 
	 * apply getAbilityStatus to it's enemy.
	 * @author Kevin
	 *
	 */
	public static class PoisonBlade extends OffensiveAbility implements StatusEffectAbility{
		
		public static final String NAME = "PoisonBlade";
		private static final int ABILITY_POINTS_COST = 4;
		private static final int BASE_DAMAGE = 2;
		private static final Image ANIMATION_IMAGE  = ImagePreparation.getInstance().prepImage("/poison_drop.png", Ability.ABILITY_IMAGE_WIDTH, Ability.ABILITY_IMAGE_HEIGHT);
		private Status poison = new OffensiveStatusesPerTurn.Poison(-2,2,2);
		private String description = "<html>The Assassin attacks it's enemy with a dagger coated in poison.<br> Does " 
		+ String.format("%d", this.getDamage()) + " base damage.<br> Costs " + this.getPointCost() + " ability points.<br> "
		+ "Afflicts the enemy with " + poison.toDescription() +"</html>";		
		
		/**
		 * Default constructor that sets the ability point cost and damage done.
		 * Also creates the poison status.
		 */
		public PoisonBlade()
		{
			super(ABILITY_POINTS_COST,BASE_DAMAGE, ANIMATION_IMAGE);
			this.setName(PoisonBlade.NAME);
			this.setDescription(description);
		}
		
		/**
		 * Calls the regular OffensiveAbility useBattleCommand to apply the base damage, 
		 * then applies the status to the target.
		 * @param hero current acting hero
		 * @param target target
		 * @throws BattleModelException's child exception NotEnoughAbilityPoints if not enough ability points 
		 */
		@Override
		public void useBattleCommand(Hero hero, Hero target) throws BattleModelException {
			super.useBattleCommand(hero, target);
			// Add status
			// Is cloned within the status
			applyAbilityStatus(target);
		}
		
		/**
		 * Does the regular base damage the same as OffensiveAbility useBattleCommand, 
		 * then applies the status to the target. Includes animation.
		 * @param hero  current acting hero
		 * @param controller the controller to display the animation 
		 * @throws BattleModelException child's exception CancelledTargetException or  BattleModelException's child exception NotEnoughAbilityPoints if not enough ability points 
		 */
		@Override
		public void useBattleCommand(Hero hero, BattleController controller) throws BattleModelException {
			hero.checkIfEnoughAP(this,controller);
			Hero target = controller.signalShowTargetOptions();
			if(target == null)
			{
				throw new CancelledTargetException(controller);
			}
			super.useBattleCommand(hero, target);
			// Add status
			// Is cloned within the status
			controller.animateBattleCommand(target, this.getAnimationImage(), true);
			applyAbilityStatus(target);
		}
		
		/**
		 * Returns the class string that owns these abilities.
		 * @return Class string that owns these abilities.
		 */
		@Override
		public String getClassOwner() {
			return AssassinAbility.BELONGS_TO_CLASS;
		}
		
		/**
		 * Applies and returns a clone of an abilities Status effect.
		 * If this interface is implemented properly in the corresponding abilities 
		 * useBattleCommand method it should applyAbilityStatus to it's enemy.
		 * @param target target to apply status effect to
		 * @return Abilities status effect
		 */
		@Override
		public Status applyAbilityStatus(Hero target)
		{
			return this.poison.addStatus(target);
		}
		
	}
	
	/**
	 * ExposeWeakness class is an OffensiveAbility that implements the StatusEffectAbility interface.
	 * By implementing the StatusEffectAbility interface it must provide a method getOffensiveStatus()
	 * If this ability is implemented properly in it's useBattleCommand method it should call, clone, and then 
	 * apply getOffensiveStatus to it's enemy.
	 * @author Kevin
	 *
	 */
	public static class ExposeWeakness extends OffensiveAbility implements StatusEffectAbility{
		
		public static final String NAME = "ExposeWeakness";
		private static final int ABILITY_POINTS_COST = 4;
		private static final int BASE_DAMAGE = 1;
		private static final Image ANIMATION_IMAGE = ImagePreparation.getInstance().prepImage("/red_minus.png", ABILITY_IMAGE_WIDTH, ABILITY_IMAGE_HEIGHT);
		private Status decreaseDefense = new OffensiveStatusesNotPerTurn.LowerDefense(-3,2,2);
		private String description = "<html>The Assassin cuts it's enemies armour straps loose forcing their armour to drop to the floor.<br> Does " 
				+ String.format("%d", this.getDamage()) + " base damage.<br> Costs " + this.getPointCost() + " ability points.<br> "
				+ "Afflicts the enemy with " + decreaseDefense.toDescription() + "</html>";
		/**
		 * Default constructor that sets the ability point cost and damage done.
		 * Also creates the decreaseDefense status.
		 */
		public ExposeWeakness()
		{
			super(ABILITY_POINTS_COST,BASE_DAMAGE, ANIMATION_IMAGE);
			this.setName(ExposeWeakness.NAME);
			this.setDescription(description);
		}
		
		/**
		 * Calls the regular OffensiveAbility useBattleCommand to apply the base damage, 
		 * then applies the status to the target.
		 * @param hero current acting hero
		 * @param target target
		 * @throws BattleModelException's child exception NotEnoughAbilityPoints if not enough ability points 
		 */
		@Override
		public void useBattleCommand(Hero hero, Hero target) throws BattleModelException {
			super.useBattleCommand(hero, target);
			// Add status
			// Is cloned within the status
			Status newDecreaseDefense = applyAbilityStatus(target);
			// Expose should tick immediately so update with new status 
			newDecreaseDefense.updateStatus(target);
		}
		
		/**
		 * Does the regular base damage the same as OffensiveAbility useBattleCommand, 
		 * then applies the status to the target. Includes animation.
		 * @param hero current acting hero
		 * @param controller the controller to display the animation 
		 * @throws BattleModelException child's exception CancelledTargetException or  BattleModelException's child exception NotEnoughAbilityPoints if not enough ability points 
		 */
		@Override
		public void useBattleCommand(Hero hero, BattleController controller) throws BattleModelException {
			hero.checkIfEnoughAP(this,controller);
			Hero target = controller.signalShowTargetOptions();
			if(target == null)
			{
				throw new CancelledTargetException(controller);
			}
			super.useBattleCommand(hero, target);
			// Add status
			// Is cloned within the status
			Status newDecreaseDefense = applyAbilityStatus(target);
			// Expose should tick immediately so update with new status 
			newDecreaseDefense.updateStatus(target);
			controller.animateBattleCommand(target, this.getAnimationImage(), true);
		}
		
		/**
		 * Returns the class string that owns these abilities.
		 * @return Class string that owns these abilities.
		 */
		@Override
		public String getClassOwner() {
			return AssassinAbility.BELONGS_TO_CLASS;
		}
		
		/**
		 * Applies and returns a clone of an abilities Status effect.
		 * If this interface is implemented properly in the corresponding abilities 
		 * useBattleCommand method it should applyAbilityStatus to it's enemy.
		 * @param target target to apply status effect to
		 * @return Abilities status effect
		 */
		@Override
		public Status applyAbilityStatus(Hero target)
		{
			return this.decreaseDefense.addStatus(target);
		}
	}
	
	/**
	 * ThrowKnives class is an OffensiveAbility. Note the absence of the useBattleCommand method.
	 * Since this is a pure OffensiveAbility there is no need to redefine this method, it will immediately call OffensiveAbiltiies
	 * useBattleCommand.
	 * @author Kevin
	 *
	 */
	public static class ThrowKnives extends OffensiveAbility {
		
		public static final String NAME = "ThrowKnives";
		private static final int ABILITY_POINTS_COST = 2;
		private static final int BASE_DAMAGE = 4;
		private static final Image ANIMATION_IMAGE = ImagePreparation.getInstance().prepImage("/dagger.png", ABILITY_IMAGE_WIDTH, ABILITY_SWORD_IMAGE_HEIGHT);
		private String description = "<html>The Assassin throws knives at it's enemy.<br> Does " 
				+ String.format("%d", this.getDamage()) + " base damage.<br> Costs " + this.getPointCost() + " ability points.</html>";
		/**
		 * Default constructor that sets the ability point cost and damage done.
		 */
		public ThrowKnives()
		{
			super(ABILITY_POINTS_COST,BASE_DAMAGE, ANIMATION_IMAGE);
			this.setName(ThrowKnives.NAME);
			this.setDescription(description);
		}
		
		/**
		 * Returns the class string that owns these abilities.
		 * @return Class string that owns these abilities.
		 */
		@Override
		public String getClassOwner() {
			return AssassinAbility.BELONGS_TO_CLASS;
		}
	}
}
