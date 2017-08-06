package BattleCommands;

import java.awt.Image;

import BattleMVC.BattleController;
import Heros.Hero;
import PartyContainers.Player;
import RPG_Exceptions.BattleModelException;

/**
 * The item abstract class defines the abstract method useItem which is overridden by 
 * each specific subclass of Item. Beyond this to implement Encapsulation is manages all Item related inventory control.
 * It is also abstract so the class cannot be used to create a stand alone object.
 * Why? The Classes field that the item affects has yet to be defined.  
 * @author Kevin
 *
 */

public abstract class Item extends BattleCommand implements Comparable<Item>{
	private String name;
	private int count;
	private int effectStrength;
	private Player player;
	private Image animationImage;
	public static final int ITEM_WIDTH = 125;
	public static final int ITEM_HEIGHT = 250;
	
	/**
	 * Constructor to set the name, effect strength, and which player inventory this item belongs to.
	 * @param animationImage image to animate on ability use
	 * @param name: the name of the item
	 * @param effectStrength: positive or negative effect strength of the item, the stat to which this item will affect is defined in it's appropriate subclass.
	 * @param player: the player whose inventory this item belongs to
	 */
	public Item(String name, int effectStrength, Player player, Image animationImage)
	{
		this.name = name;
		this.effectStrength = effectStrength;
		this.count = 1;
		this.player = player;
		this.animationImage = animationImage;
	}
	
	/**
	 * Abstract method which will be defined in the subclasses dependent on their Heros field affected.
	 * The item will either increase a Heros field, or remove a Status effect from the Classes character.
	 * @param target If offensive item will require a target, other wise will be given a null field 
	 * @param hero hero whose fields will be adjusted
	 * @throws BattleModelException will throw MaxStat is stat at maximum, or NotAfflicted with if hero doesn't have status
	 */
	public abstract void useBattleCommand(Hero hero, Hero target) throws BattleModelException;
	
	/**
	 * Abstract method which will be defined in the subclasses dependent on their Heros field affected.
	 * The item will either increase a Heros field, or remove a Status effect from the Classes character.
	 * This method will animate the action. 
	 * @param hero here whose fields will be adjusted
	 * @param controller the controller which will animate this item
	 * @throws A child of BattleModelException if the defensive ability or item used cannot increase the statistic that it affects, if you pick a 
	 * target that isn't alive, or if the character doesn't have enough ability points. 
	 */
	public abstract void useBattleCommand(Hero hero, BattleController controller) throws BattleModelException;
	
	/**
	 * Check if the inventory contains the item you are looking for 
	 * @param item
	 * @return if the inventory contains the item
	 */
	public boolean inventoryContains(Item item)
	{
		return player.getInventory().containsKey(item.toString());
	}
	
	/**
	 * Update the inventory count of an item. If the count is 0 the item is removed from the inventory.
	 * @param item: item whose count to update
	 */
	public void updateInventory(Item item)
	{
		int currentCount = item.getCount();
		currentCount--;
		item.setCount(currentCount);
		if(currentCount <= 0)
		{
			player.getInventory().remove(item.toString());
		}
		else
		{
			player.getInventory().put(item.toString(), item);
		}
	}
	
	/**
	 * Adds the current item to the inventory of the given player.
	 */
	public void addItem()
	{
		String k = this.toString();
		Item val = player.getInventory().put(k,this);
		if(val == null)
		{
			player.getInventory().put(k, this);
		}
		else
		{
			val.setCount(val.getCount() + 1);
			player.getInventory().put(k, val);
		}
	}
	
	/**
	 * Gets an item from the inventory by the item's name.
	 * @param player the player whose inventory you are getting an item from
	 * @param itemName the name of the item you wish to retrieve
	 * @return Item: the item you wish to retrieve 
	 */
	public static Item getItem(Player player, String name)
	{
		return player.getInventory().get(name);
	}
	
	/**
	 * Gets an item from the inventory by the item.
	 * @return Item: the item you wish to retrieve 
	 */
	public Item getItem()
	{
		return player.getInventory().get(this.toString());
	}
	
	/**
	 * Returns the name of this item
	 */
	@Override
	public String toString()
	{
		return this.name;
	}

	/**
	 * Sets the name of the item
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the effect strength of the item
	 * @return effect strength of the item
	 */
	public int getEffectStrength() {
		return effectStrength;
	}

	/**
	 * Set the effect strength of the item
	 * @param effectStrength
	 */
	public void setEffectStrength(int effectStrength) {
		this.effectStrength = effectStrength;
	}

	/**
	 * Get the remaining count of the item
	 * @return Remaining count of item
	 */
	public int getCount() {
		return count;
	}

	/**
	 * Set the count of an item
	 * @param count
	 */
	public void setCount(int count) {
		this.count = count;
	}


	/**
	 * Get this image's animation image.
	 * @return This image's animation image.
	 */
	public Image getAnimationImage() {
		return animationImage;
	}
	
	/**
	 * Compare to for use in TreeMap, sorted alphabetically based on the item name.
	 */
	@Override
	public int compareTo(Item other){
		return this.name.compareTo(other.name);
	}
	
}
