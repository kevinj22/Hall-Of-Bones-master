package MainMenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller for the main menu. 
 * @author alanyork
 *
 */
public class MainMenuController implements ActionListener {
	private MainMenuView view;
	
	/**
	 * Assigns the view for the controller
	 * @param view
	 * The MainMenuView to be paired with the controller
	 */
	public void setView(MainMenuView view){
		this.view = view;
	}
	
	/**
	 * Event handler for actions.
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		switch (arg0.getActionCommand()) {
			// Play button pressed
			case MainMenuView.PLAY_BUTTON_TEXT:
				view.getMainFrame().switchViews(view.getMainFrame().mainMenu, "dungeon");
				System.out.print("Dungeon from MainMenu");
				break;
			// Instructions button pressed
			case MainMenuView.INSTRUCTIONS_BUTTON_TEXT:
				view.popUpInstructions();
				break;
			// Credits button pressed
			case MainMenuView.CREDITS_BUTTON_TEXT:
				view.popUpCredits();
				break;
			// Exit button pressed
			case MainMenuView.EXIT_BUTTON_TEXT:
				System.out.print("Exit from MainMenu");
				System.exit(0);
				break;
		}
	}

}
