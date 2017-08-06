package BattleMVC;

import java.awt.Color;
import javax.swing.JProgressBar;
import Heros.Hero;

/**
 * Displays a Hero's remaining ability points. 
 * @author Kevin
 *
 */
public class AbilityPointsBar extends JProgressBar{
	
	/**
	 * Added so compiler doesn't complain
	 */
	private static final long serialVersionUID = 1L;
	private Hero hero;
	
	/**
	 * Constructor for a hero's ability bar.
	 * @param hero whose ability points you will be displaying.
	 */
	public AbilityPointsBar(Hero hero)
	{
		super();
		super.setForeground(Color.BLUE);
        super.setMaximum(hero.getAbilityPoints());
        super.setMinimum(0);
        this.hero = hero;
        updateAPBar();
	}
	
	/**
	 * Update the ability bar's fill amount.
	 */
	public void updateAPBar()
	{
		super.setValue(hero.getAbilityPoints());
		super.setString(String.format("%d",hero.getAbilityPoints()));
      	super.setStringPainted(true);
	}
}
