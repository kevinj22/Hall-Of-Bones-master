package BattleMVC;

import java.awt.Color;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import Heros.Hero;
import Statuses.Status;

/**
 * StatusBar displays a hero's current statuses during battle.
 * @author Kevin
 *
 */
@SuppressWarnings("serial")
public class StatusBar extends JLabel{
	private Hero hero;
	
	/**
	 * Create a status bar for a hero that displays the hero's current status affects.
	 * @param hero hero whose statuses to display.
	 * @param text text to display.
	 */
	public StatusBar(Hero hero, String text)
	{
		super(text, SwingConstants.CENTER);
        super.setForeground(Color.WHITE);
        super.setBackground(Color.BLACK);
        super.setOpaque(true);
		this.hero = hero;
	}
	
	/**
	 * Update the status bar with each afflicted statuses' text.
	 */
	public void updateStatus()
	{
		if(hero.getHealth() <= 0)
		{
			super.setText("");
		}
		else
		{
			String updatedStatusText = "<html><center>";
			for(Map.Entry<String, Status> entry : hero.getStatuses().entrySet())
			{
				updatedStatusText += entry.getValue().toString() + "<br>";
			}
			if(updatedStatusText.equals("<html><center>"))
			{
				updatedStatusText = "None";
			}
			else
			{
				updatedStatusText += "</center></html>";
			}
			super.setText(updatedStatusText);
		}
	}
}
