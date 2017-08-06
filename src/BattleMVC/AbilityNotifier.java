package BattleMVC;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import BattleCommands.BattleCommand;
import Heros.Hero;

/**
 * Ability Notifier writes the text of whichever ability a hero just used above them. 
 * @author Kevin
 *
 */
public class AbilityNotifier extends JLabel{
	
	/**
	 * Added so compiler doesn't complain
	 */
	private static final long serialVersionUID = 467004103231290586L;
	private Hero hero;
	
	/**
	 * Ability Notifier constructor. Requires the hero it will update whichever ability they just used.
	 * Sets the default ability text to "".
	 * @param hero
	 */
	public AbilityNotifier(Hero hero)
	{
		super("", SwingConstants.CENTER);
        super.setForeground(Color.WHITE);
        super.setBackground(Color.BLACK);
        super.setOpaque(true);
		this.setHero(hero);
	}
	
	/**
	 * Update this ability notifiers text with the abilities text.
	 * @param ability whose text you want to display
	 */
	public void updateAbilityNotifier(BattleCommand ability)
	{
		super.setText(ability.toString());
	}
	
	/**
	 * Clears this ability notifier to display "".
	 * Used after a short period of time to clear the ability used.
	 */
	public void clearAbilityNotifier()
	{
		super.setText("");
	}

	/**
	 * Return the hero of this ability notifier.
	 * @return The hero of this ability notifier.
	 */
	public Hero getHero() {
		return hero;
	}

	/**
	 * Set the hero of this ability notifier.
	 * @param hero 
	 */
	public void setHero(Hero hero) {
		this.hero = hero;
	}
}
