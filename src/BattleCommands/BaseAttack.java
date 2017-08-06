package BattleCommands;

import java.awt.Image;

import GridGUI.ImagePreparation;

/**
 * BaseAttack OffensiveAbility. Every Hero class has a BaseAttack.
 * Note the absence of the useBattleCommand method.
 * Since this is a pure OffensiveAbility there is no need to redefine this method, it will immediately call OffensiveAbiltiies
 * useBattleCommand.
 * @author Kevin
 *
 */
public class BaseAttack extends OffensiveAbility {
	public static final String NAME = "BaseAttack";
	private static final String BELONGS_TO_CLASS = "ALL";
	private static final int ABILITY_POINTS_COST = 0;
	private static final Image ANIMATION_IMAGE = ImagePreparation.getInstance().prepImage("/dagger.png", ABILITY_IMAGE_WIDTH,ABILITY_SWORD_IMAGE_HEIGHT);
	private String description = "<html> The Hero's BaseAttack.<br> Does " 
			+ String.format("%d", this.getDamage()) + " base damage.<br> Costs " + this.getPointCost() + " ability points. </html>";
	
	/**
	 * Make a BaseAttack ability with the given damage.
	 * @param damage: damage done by base attack
	 */
	public BaseAttack(int damage)
	{
		super(ABILITY_POINTS_COST,damage, ANIMATION_IMAGE);
		this.setName(BaseAttack.NAME);
		this.setDescription(description);
	}
	
	/**
	 * Returns the class string that owns these abilities.
	 * @return Class string that owns these abilities.
	 */
	@Override
	public String getClassOwner() {
		return BaseAttack.BELONGS_TO_CLASS;
	}
	
}
