package BattleCommands;

import java.awt.Image;

import BattleMVC.BattleController;
import GridGUI.ImagePreparation;
import Heros.Hero;
import PartyContainers.Player;
import RPG_Exceptions.MaximumStatException;

/**
 * AbilityItem increases a heros available AbilityPoints.
 * @author Kevin
 *
 */
public class AbilityItem extends Item{
	
	private static final Image ANIMATION_IMAGE = ImagePreparation.getInstance().prepImage("/green_arrow.png", Item.ITEM_WIDTH, Item.ITEM_HEIGHT);
	
	/**
	 * Constructor for an AbilityItem.
	 * @param name name of item
	 * @param effectStrength effectStrength
	 * @param player player whose inventory this item belongs to
	 */
	public AbilityItem(String name, int effectStrength, Player player)
	{
		super(name,effectStrength, player, ANIMATION_IMAGE);
	}
	
	/**
	 * Use the AbilityItem, if the hero's ability points are at their maximum this method will throw
	 * a MaximumStatException. These items cannot increase a hero's ability points beyond their maximum.
	 * @param hero current acting hero
	 * @param target targeted hero, should be null as targeting self
	 * @throws MaximumStatException if the hero's ability points are at their maximum 
	 */
	@Override
	public void useBattleCommand(Hero hero, Hero target) throws MaximumStatException
	{
		if(this.inventoryContains(this))
		{
			if(hero.getAbilityPoints() < hero.getMaxAP())
			{
				int currentHeroAbilityPoints = hero.getAbilityPoints();
				System.out.println("Character AP before: " + currentHeroAbilityPoints);
				int abilityPointsAdded = this.getEffectStrength();
				if((abilityPointsAdded + hero.getAbilityPoints()) >= hero.getMaxAP())
				{
					hero.setAbilityPoints(hero.getMaxAP());
				}
				else
				{
					hero.setAbilityPoints(currentHeroAbilityPoints + this.getEffectStrength());
				}
				System.out.println("Character AP after: " + hero.getAbilityPoints());
				this.updateInventory(this);
			}
			else
			{
				throw new MaximumStatException();
			}
		}
	}

	/**
	 * Use the AbilityItem with animation, if the hero's ability points are at their maximum this method will throw
	 * a MaximumStatException. These items cannot increase a hero's ability points beyond their maximum.
	 * @param hero current acting hero
	 * @param controller which will send the signal to the view to animate this activity
	 * @throws MaximumStatException if the hero's ability points are at their maximum 
	 */
	@Override
	public void useBattleCommand(Hero hero, BattleController controller) throws MaximumStatException
	{
		if(this.inventoryContains(this))
		{
			if(hero.getAbilityPoints() < hero.getMaxAP())
			{
				int currentHeroAbilityPoints = hero.getAbilityPoints();
				System.out.println("Character AP before: " + currentHeroAbilityPoints);
				int abilityPointsAdded = this.getEffectStrength();
				if((abilityPointsAdded + hero.getAbilityPoints()) >= hero.getMaxAP())
				{
					hero.setAbilityPoints(hero.getMaxAP());
				}
				else
				{
					hero.setAbilityPoints(currentHeroAbilityPoints + this.getEffectStrength());
				}
				System.out.println("Character AP after: " + hero.getAbilityPoints());
				this.updateInventory(this);
				controller.animateBattleCommand(hero, this.getAnimationImage(), false);
			}
			else
			{
				throw new MaximumStatException(controller);
			}
		}
	}
}
