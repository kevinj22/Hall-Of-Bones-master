package PartyContainers;

import java.util.ArrayList;
import java.util.TreeMap;

import BattleCommands.AbilityItem;
import BattleCommands.HealthItem;
import BattleCommands.Item;
import BattleCommands.StatusItem;
import Heros.Hero;

/**
 * Player is an abstract class that will be extended by HumanPlayer and AI player.
 * It serves as a wrapped that will hold a participating entities inventory and party of heros.
 * To adhere to encapsulation the Heros composing the party and the Items composing the inventory will manage all 
 * Heros / Items / Inventory / Party actions. This class is just to store these Maps.
 * Why abstract?
 * 1) We don't know the party composition yet, the AI and the Human player have access to different Classes
 * 2) Having just one class Player for both AI and Human would not adhere to encapsulation : we need to separate the AI’s functions from a Human’s
 * during battle
 * @author Kevin
 *
 */
public abstract class Player {
	private TreeMap<String, Item> inventory = new TreeMap<String, Item>();
	private TreeMap<String , Hero> party = new TreeMap<String, Hero>();
	
	/**
	 * Empty super constructor that will just fill the inventory. As mentioned in the class description the
	 * abstract method makeDefaultParty will be defined in both AI and HumanPlayer to use classes specific to their 
	 * controller. 
	 */
	public Player()
	{
		// Empty Constructor 
		// Sub classes can fill party with their party creation method 
		this.defaultInventory();
	}
	
	/**
	 * Abstract method to be overridden once we know which classes are avialable to compose a party with.
	 */
	public abstract void makeDefaultParty();

	/**
	 * Returns the TreeMap of the Player's party. Organized in ascending order by the speed of each class of hero.
	 * @return TreeMap of Player's party
	 */
	public TreeMap<String, Hero> getParty()
	{
		return this.party;
	}
	
	/**
	 * Returns a hero from the party based on their class name.
	 * @param Name The class name of the hero you wish to get
	 * @return Hero the hero you wish to access. 
	 */
	public Hero getCharacter(String name)
	{
		return this.party.get(name);
	}
	
	/**
	 * Returns a new ArrayList with copies of each item's address of the current Player's inventory. 
	 * Used for ease of iteration in GridGUI VS going over each Map.Entry.
	 * @return A new ArrayList with shared references to this players inventory items. 
	 */
	public ArrayList<Item> inventoryItems()
	{
		ArrayList<Item> items = new ArrayList<Item>(this.inventory.values());
		return items;
	}
	

	/**
	 * A method that returns the TreeMap of the current Player's inventory.  
	 * @return A Shallow copy of this Player's Inventory TreeMap 
	 */
	public TreeMap<String,Item> getInventory()
	{
		return this.inventory;
	}
	
	/**
	 * Fills the default inventory on party creation. 
	 */
	private void defaultInventory()
	{
		// this refers to the items' player 
		HealthItem defaultHealth = new HealthItem("Health Potion",2,this);
		defaultHealth.addItem();
		AbilityItem abilityPointsPotion = new AbilityItem("AbilityPoints Potion",2,this);
		abilityPointsPotion.addItem();
		StatusItem antidote = new StatusItem("Antidote","Poison",this);
		antidote.addItem();
	}

}
