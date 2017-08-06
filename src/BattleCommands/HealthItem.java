package BattleCommands;

import java.awt.Image;

import BattleMVC.BattleController;
import GridGUI.ImagePreparation;
import Heros.Hero;
import PartyContainers.Player;
import RPG_Exceptions.MaximumStatException;

/**
 * HealthItem will restore a Hero's health a certain amount based on it's effect strength.
 * @author Kevin
 *
 */
public class HealthItem extends Item{

	private static final Image ANIMATION_IMAGE = ImagePreparation.getInstance().prepImage("/green_arrow.png", Item.ITEM_WIDTH, Item.ITEM_HEIGHT);
	
	/**
	 * Constructor to set the name, effect strength, and which player inventory this item belongs to.
	 * @param name: the name of the item
	 * @param effectStrength: positive or negative effect strength of the item, the stat to which this item will affect is defined in it's appropriate subclass.
	 * @param player: the player whose inventory this item belongs to
	 */
	public HealthItem(String name, int effectStrength, Player player)
	{
		super(name,effectStrength, player, ANIMATION_IMAGE);
	}

	/**
	 * Uses the item on the given hero. Throws MaximumStatException in the event the item will make no difference to the Hero's health.
	 * This exception is handled by the controller which sends a message to the view to display the error message to the user.
	 * These items cannot increase a hero's health beyond their maximum.
	 * @param Hero hero, the hero to use this item on
	 * @param target items can only be used on self, just pass null.
	 * @throws MaximumStatException in the event the item will make no difference to the Hero's health.
	 */
	@Override
	public void useBattleCommand(Hero hero, Hero target) throws MaximumStatException
	{
		if(this.inventoryContains(this))
		{
			if(hero.getHealth() < hero.getMaxHealth())
			{
				int currentHealth = hero.getHealth();
				int healAmount = this.getEffectStrength();
				System.out.println("Character health before: " + currentHealth);
				if((healAmount + currentHealth) >= hero.getMaxHealth())
				{
					hero.setHealth(hero.getMaxHealth());
				}
				else
				{
					hero.setHealth(hero.getHealth() + this.getEffectStrength());
				}
				System.out.println("Character health after: " + hero.getHealth());
				this.updateInventory(this);
			}
			else
			{
				throw new MaximumStatException();
			}
		}
	}
	

	/**
	 * Uses the item on the given hero with animation. Throws MaximumStatException in the event the item will make no difference to the Hero's health.
	 * This exception is handled by the controller which sends a message to the view to display the error message to the user.
	 * These items cannot increase a hero's health beyond their maximum.
	 * @param Hero hero, the hero to use this item on
	 * @param controller which will send the signal to the view to animate this action.
	 * @throws MaximumStatException in the event the item will make no difference to the Hero's health.
	 */
	@Override
	public void useBattleCommand(Hero hero, BattleController controller) throws MaximumStatException
	{
		if(this.inventoryContains(this))
		{
			if(hero.getHealth() < hero.getMaxHealth())
			{
				int currentHealth = hero.getHealth();
				int healAmount = this.getEffectStrength();
				System.out.println("Character health before: " + currentHealth);
				if((healAmount + currentHealth) >= hero.getMaxHealth())
				{
					hero.setHealth(hero.getMaxHealth());
				}
				else
				{
					hero.setHealth(hero.getHealth() + this.getEffectStrength());
				}
				System.out.println("Character health after: " + hero.getHealth());
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
