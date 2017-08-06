package BattleMVC;

import java.awt.Color;
import javax.swing.JProgressBar;
import Heros.Hero;

/**
 * Displays a Hero's remaining health. 
 * @author Kevin
 *
 */
public class HealthBar extends JProgressBar{
	/**
	 * Added so compiler doesn't complain
	 */
	private static final long serialVersionUID = 1L;
	private Hero hero;
	
	/**
	 * Constructor for a hero's health bar.
	 * @param hero whose health you will be displaying.
	 */
	public HealthBar(Hero hero)
	{
		super();
		super.setForeground(Color.GREEN);
        super.setMaximum(hero.getHealth());
        super.setMinimum(0);
        this.hero = hero;
        updateHealthBar();
	}
	
	/**
	 * Update the health bar's fill amount.
	 */
	public void updateHealthBar()
	{
		super.setValue(hero.getHealth());
		super.setString(String.format("%d",hero.getHealth()));
      	super.setStringPainted(true);
	}
}
