package BattleCommands;

import java.awt.Image;

import BattleMVC.BattleController;
import GridGUI.ImagePreparation;
import Heros.Hero;
import RPG_Exceptions.BattleModelException;
import RPG_Exceptions.MaximumStatException;
import Statuses.BuffStatuses;
import Statuses.Status;
import Statuses.StatusEffectAbility;

/**
 * Contains many inner classes of Paladin type abilities.
 * @author Kevin
 *
 */
public abstract class PaladinAbility{

	private static final String BELONGS_TO_CLASS = "PALADIN";
	
	/**
	 * HolySmite class is an OffensiveAbility. Note the absence of the useBattleCommand method.
	 * Since this is a pure OffensiveAbility there is no need to redefine this method, it will immediately call OffensiveAbiltiies
	 * useBattleCommand.
	 * @author Kevin
	 *
	 */
	public static class HolySmite extends OffensiveAbility {
		public static final String NAME = "HolySmite";
		private static final int ABILITY_POINTS_COST = 4;
		private static final int BASE_DAMAGE = 8;
		private static final Image ANIMATION_IMAGE = ImagePreparation.getInstance().prepImage("/dagger.png", ABILITY_IMAGE_WIDTH, ABILITY_SWORD_IMAGE_HEIGHT);
		private String description = "<html>The Paladin summons his divine power to burn the wicked.<br> Does " 
				+ String.format("%d", this.getDamage()) + " base damage.<br> Costs " + this.getPointCost() + " ability points.</html>";
		
		/**
		 * Default constructor that sets the ability point cost and damage done.
		 */
		public HolySmite()
		{
			super(ABILITY_POINTS_COST,BASE_DAMAGE, ANIMATION_IMAGE);
			this.setName(HolySmite.NAME);
			this.setDescription(description);
		}
		
		/**
		 * Returns the class string that owns these abilities.
		 * @return Class string that owns these abilities.
		 */
		@Override
		public String getClassOwner() {
			return PaladinAbility.BELONGS_TO_CLASS;
		}
		
	}

	/**
	 * Defend class is an DefensiveAbility that implements the StatusEffectAbility interface.
	 * By implementing the StatusEffectAbility interface it must provide a method getAbilityStatus()
	 * If this ability is implemented properly in it's useBattleCommand method it should call, clone, and then 
	 * apply getAbilityStatus to the current acting hero.
	 * @author Kevin
	 *
	 */
	public static class Defend extends DefensiveAbility implements StatusEffectAbility{
		public static final String NAME = "Defend";
		private static final String STAT_AFFECTED = "defenseRating";
		private static final int ABILITY_POINTS_COST = 0;
		private static final Image ANIMATION_IMAGE = ImagePreparation.getInstance().prepImage("/green_arrow.png", ABILITY_IMAGE_WIDTH, DEFEND_ABILITY_IMAGE_HEIGHT);
		private Status defend = new BuffStatuses.Defend(3,1,1);
		private String description = "<html>The Paladin raises his shield in defense.<br> Defends himself " 
				+ defend.toDescription() + "</html>";
		
		/**
		 * Default constructor that sets the ability point cost, stat affected, effect strength, 
		 * and creates the defend status.
		 */
		public Defend(int effectStrength)
		{
			super(ABILITY_POINTS_COST,Defend.STAT_AFFECTED, effectStrength, ANIMATION_IMAGE);
			this.setName(Defend.NAME);
			this.setDescription(description);
		}
		
		/**
		 * Applies the defensive status to the current acting hero.
		 * @param hero: current acting hero
		 * @param target: target, should always be null as can only use on self
		 */
		@Override
		public void useBattleCommand(Hero hero, Hero target)
		{
			// Apply to self 
			Status newDefend = applyAbilityStatus(hero);
			// Only want to update the defensive status, all other statuses are updated at the beginning of the turn 
			newDefend.updateStatus(hero);
		}
		
		/**
		 * Applies the defensive status to the current acting hero with animation.
		 * @param hero current acting hero
		 * @param controller controller to animate action 
		 */
		@Override
		public void useBattleCommand(Hero hero, BattleController controller)
		{
			// Apply to self 
			Status newDefend = applyAbilityStatus(hero);
			// Only want to update the defensive status, all other statuses are updated at the beginning of the turn 
			newDefend.updateStatus(hero,controller);
		}
		
		/**
		 * Returns the class string that owns these abilities.
		 * @return Class string that owns these abilities.
		 */
		@Override
		public String getClassOwner() {
			return PaladinAbility.BELONGS_TO_CLASS;
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
			return this.defend.addStatus(target);
		}
	}
	
	/**
	 * Heal class is a DefensiveAbility that heals the current acting hero.
	 * @author Kevin
	 *
	 */
	public static class Heal extends DefensiveAbility {
		public static final String NAME = "Heal";
		private static final String STAT_AFFECTED = "health";
		private static final int ABILITY_POINTS_COST = 4;
		private static final int EFFECT_STRENGTH = 2;
		private static final Image ANIMATION_IMAGE = ImagePreparation.getInstance().prepImage("/green_arrow.png", ABILITY_IMAGE_WIDTH, ABILITY_IMAGE_HEIGHT);
		private String description = "<html> The Paladin heals himself.<br> Heal amount " 
				+ this.getEffectStrength() + ".<br> Ability point cost " + this.getPointCost() + " ability points.</html>";
		
		/**
		 * Default constructor that sets the ability point cost, stat affected, and effect strength of this ability. 
		 */
		public Heal()
		{
			// Point Cost, Stat effected, Effect Strength
			super(ABILITY_POINTS_COST,Heal.STAT_AFFECTED,EFFECT_STRENGTH, ANIMATION_IMAGE);
			this.setName(Heal.NAME);
			this.setDescription(description);
		}
		
		/**
		 * Returns the class string that owns these abilities.
		 * @return Class string that owns these abilities.
		 */
		@Override
		public String getClassOwner() {
			return PaladinAbility.BELONGS_TO_CLASS;
		}
		
		/**
		 * Applies the defensive status to the current acting hero. If the hero is at max health will throw 
		 * a MaximumStatException
		 * @param hero current acting hero
		 * @param target target, should always be null as can only use on self
		 * @throws A child of BattleModelException if the defensive ability or item used cannot increase the statistic that it affects, 
		 * or if the character doesn't have enough ability points. 
		 */
		@Override
		public void useBattleCommand(Hero hero, Hero other) throws BattleModelException
		{
			hero.checkIfEnoughAP(this);
			if(hero.getHealth() == hero.getMaxHealth())
			{
				throw new MaximumStatException();
			}
			else if(hero.getMaxHealth() < (hero.getHealth()) + this.getEffectStrength())
			{
				hero.setHealth(hero.getMaxHealth());
				hero.setAbilityPoints(hero.getAbilityPoints() - this.getPointCost());
			}
			else
			{
				hero.setHealth(hero.getHealth() + this.getEffectStrength());
				hero.setAbilityPoints(hero.getAbilityPoints() - this.getPointCost());
			}
		}
		
		/**
		 * Applies the defensive status to the current acting hero. If the hero is at max health will throw 
		 * a MaximumStatException
		 * @param hero current acting hero
		 * @param target target, should always be null as can only use on self
		 * @throws A child of BattleModelException if the defensive ability or item used cannot increase the statistic that it affects, if you pick a 
		 * target that isn't alive, or if the character doesn't have enough ability points. 
		 */
		@Override
		public void useBattleCommand(Hero hero, BattleController controller) throws BattleModelException
		{
			hero.checkIfEnoughAP(this,controller);
			if(hero.getHealth() == hero.getMaxHealth())
			{
				throw new MaximumStatException(controller);
			}
			else if(hero.getMaxHealth() < (hero.getHealth()) + this.getEffectStrength())
			{
				hero.setHealth(hero.getMaxHealth());
				hero.setAbilityPoints(hero.getAbilityPoints() - this.getPointCost());
				controller.animateBattleCommand(hero, this.getAnimationImage(),false);
			}
			else
			{
				hero.setHealth(hero.getHealth() + this.getEffectStrength());
				hero.setAbilityPoints(hero.getAbilityPoints() - this.getPointCost());
				controller.animateBattleCommand(hero, this.getAnimationImage(),false);
			}
		}
	}
}
