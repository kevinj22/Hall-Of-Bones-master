package BattleCommands;

import java.awt.Image;

import BattleMVC.BattleController;
import GridGUI.ImagePreparation;
import Heros.Hero;
import PartyContainers.Player;
import RPG_Exceptions.BattleModelException;
import RPG_Exceptions.NotAfflictedWithStatusException;
import Statuses.Status;

/**
 * StatusItem removes a status from the acting hero.
 * @author Kevin
 *
 */
public class StatusItem extends Item{

	private String statusAffected;
	private static int EFFECT_STRENGTH = 0;
	private static final Image ANIMATION_IMAGE = ImagePreparation.getInstance().prepImage("/green_arrow.png", Item.ITEM_WIDTH, Item.ITEM_HEIGHT);
	
	/**
	 * StatusItem constructor to set it's various fields. 
	 * @param name name of item
	 * @param statusAffected which status ailment will be affected
	 * @param player player whose inventory this item belongs to
	 */
	public StatusItem(String name, String statusAffected, Player player)
	{
		super(name, EFFECT_STRENGTH, player, ANIMATION_IMAGE);
		this.statusAffected = statusAffected;
	}
	
	/**
	 * Check if the status that this item affects is actually applied on the acting hero.
	 * @param hero hero to check if this status is currently applied to them
	 * @return true status is currently applied, false: not applied
	 */
	public boolean statusExist(Hero hero)
	{
		boolean exists = false;
		if(Status.getStatus(hero,this.statusAffected) != null)
		{
			exists = true;
		}
		return exists;
	}

	/**
	 * Get status affected.
	 * @return A string the name of that status that this item will affect 
	 */
	public String getStatusAffected() {
		return statusAffected;
	}
	
	/**
	 * Use this status item. First checks if the status is afflicted on the hero, if so removes the status.
	 * If not prints out an error message.
	 * @param hero current acting hero
	 * @param target target hero will usually be null as acting on self
	 */
	@Override
	public void useBattleCommand(Hero hero, Hero target) throws BattleModelException
	{
		if(this.inventoryContains(this))
		{
			boolean exists = this.statusExist(hero);
			if(exists)
			{
				Status.removeStatus(hero, this.getStatusAffected());
				this.updateInventory(this);
			}
			else
			{
				System.out.println("Not afflicted with that status effect");
				throw new NotAfflictedWithStatusException();
			}
		}
	}
	
	/**
	 * Use this status item. First checks if the status is afflicted on the hero, if so removes the status.
	 * If not prints out an error message.
	 * @param hero current acting hero
	 * @param target target hero will usually be null as acting on self
	 * @throws A child of BattleModelException if the item cannot remove that status affect it effects.
	 */
	@Override
	public void useBattleCommand(Hero hero, BattleController controller) throws BattleModelException
	{
		if(this.inventoryContains(this))
		{
			boolean exists = this.statusExist(hero);
			if(exists)
			{
				Status.removeStatus(hero, this.getStatusAffected());
				this.updateInventory(this);
				controller.animateBattleCommand(hero, this.getAnimationImage(), false);
			}
			else
			{
				System.out.println("Not afflicted with that status effect");
				throw new NotAfflictedWithStatusException(controller);
			}
		}
	}

}
