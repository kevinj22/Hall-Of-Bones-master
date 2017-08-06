package BattleCommands;

import java.awt.Image;

import BattleMVC.BattleController;
import Heros.Hero;
import RPG_Exceptions.BattleModelException;

/**
 * Ability is an abstract class that extends BattleCommand. It provides very general functions for all abilities 
 * such as shared getters and setters and defines abstract abilities to be declared once more fields are known.
 * @author Kevin
 *
 */
public abstract class Ability extends BattleCommand {
	private int pointCost;
	private String name;
	private Image animationImage;
	private String description;

	public static final int ABILITY_IMAGE_WIDTH = 150;
	public static final int ABILITY_IMAGE_HEIGHT = 150;
	public static final int ABILITY_SWORD_IMAGE_HEIGHT = 250;
	public static final int DEFEND_ABILITY_IMAGE_HEIGHT = 250;
	
	/**
	 * Return the name of the class who owns this ability. Don't know the class yet thus the method is abstract.
	 * @return The name of the class who owns this ability. 
	 */
	public abstract String getClassOwner();

	/**
	 * Use the current ability. Don't know exactly what the ability does yet thus the method is abstract.
	 * @param hero current acting hero
	 * @param target target hero
	 * @throws A child of BattleModelException if the defensive ability or item used cannot increase the statistic that it affects, if you pick a 
	 * target that isn't alive, or if the character doesn't have enough ability points. 
	 */
	public abstract void useBattleCommand(Hero hero, Hero target) throws BattleModelException;
	
	/**
	 * Use the current ability with animation. Don't know exactly what the ability does yet thus the method is abstract.
	 * Throws MaximumStatException if the defensive ability or item used cannot increase the statistic that it affects.
	 * @param hero current acting hero
	 * @param controller the controller to send the signal to the view to animate the attack
	 * @throws A child of BattleModelException if the defensive ability or item used cannot increase the statistic that it affects, if you pick a 
	 * target that isn't alive, or if the character doesn't have enough ability points. 
	 */
	public abstract void useBattleCommand(Hero hero, BattleController controller) throws BattleModelException;
	
	/**
	 * Get the current abilities point cost.
	 * @return The current abilities point cost.
	 */
	public int getPointCost()
	{
		return this.pointCost;
	}
	
	/**
	 * Set the abilities point cost.
	 * @param pointCost to set abilities point cost to.
	 */
	public final void setPointCost(int pointCost) {
		this.pointCost = pointCost;
	}
	
	/**
	 * Returns the hero's ability from the hero's ability set by it's key.
	 * Why static? 
	 * Don't need to have an ability instance to get an ability from the Map.
	 * @param hero hero to get ability from
	 * @param ability name to find in hero's HashMap
	 * @return Ability 
	 */
	public static Ability getAbility(Hero hero, String ability)
	{
		return hero.getAbilities().get(ability);
	}
	
	/**
	 * Returns the hero's ability from the hero's ability set using the ability instance.
	 * @param hero hero to get ability from
	 * @return Ability 
	 */
	public Ability getAbility(Hero hero)
	{
		return hero.getAbilities().get(this.getName());
	}
	
	/**
	 * Add ability to the hero's ability set.
	 * @param hero hero to add ability to
	 */
	public void setAbility(Hero hero)
	{
		hero.getAbilities().put(this.getName(), this);
	}
	
	/**
	 * Get abilities name as a string.
	 * @return String of the abilities name.
	 */
	public String getName()
	{
		return this.name;
	}
	
	/**
	 * Set the abilities name.
	 * @param name the new abilities name. 
	 */
	public final void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * Returns the abilities name.
	 */
	@Override
	public String toString()
	{
		return this.getName();
	}
	
	/**
	 * Get this abilities animation image
	 * @return This abilities animation image. 
	 */
	public Image getAnimationImage() {
		return animationImage;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

}
