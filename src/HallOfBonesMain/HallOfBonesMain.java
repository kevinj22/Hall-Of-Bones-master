package HallOfBonesMain;

import java.awt.CardLayout;
import java.awt.Component;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import BattleMVC.BattleView;
import DungeonMVC.DungeonView;
import GridGUI.GameView;
import PartyContainers.AI;
import PartyContainers.HumanPlayer;

/**
 * The HallOfBonesMain class is a JFrame that contains a CardLayout JPanel that switches between the various model/view/controllers 
 * of the Hall Of Bones game.
 * @author Kevin
 *
 */
@SuppressWarnings("serial")
public class HallOfBonesMain extends JFrame {

    private JFrame frame;
    public MainMenu.MainMenuView mainMenu;
    private JPanel mainContentPane;
    private CardLayout mainCard;
    private BattleView battleView;
    public DungeonView dungeonView;
    private HashMap<String, GameView> gameViews = new HashMap<String, GameView>();
    
    /**
     * Construct the HallOfBonesMain JFrame.
     * @throws IOException
     */
    public HallOfBonesMain() throws IOException
    {
    	this.frame = new JFrame("Hall of Bones");
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	createThisCardLayout();
    	createAllGamePanels();
    	frame.add(mainContentPane);
    	frame.pack();
        frame.setVisible(true);
    }
    
    /**
     * Creates all of the panels involved in this game. Does not start any threads in each model until
     * the GameView startView() function is called in switch views.
     * @throws IOException
     */
    private void createAllGamePanels() throws IOException {
    	mainMenu = new MainMenu.MainMenuView(new MainMenu.MainMenuController(),this);
        String filename2 = "/FFIV_PSP_Final_Dungeon_Background_2.png";
		battleView = new BattleView(ImageIO.read(this.getClass().getResource(filename2)), new HumanPlayer(),new AI(),this);
		String filename3 = "/Dungeon_Background_49.png";
		dungeonView = new DungeonView(ImageIO.read(this.getClass().getResource(filename3)), this);
        gameViews.put("mainMenu", mainMenu);
        gameViews.put("battle", battleView);
        gameViews.put("dungeon", dungeonView);
        mainContentPane.add(mainMenu, "mainMenu");
        mainContentPane.add(battleView, "battle");
        mainContentPane.add(dungeonView, "dungeon");
        
    }

    /**
     * Create the CardLayout JPanel used in this JFrame.
     */
    private void createThisCardLayout()
    {
    	mainContentPane = new JPanel();
    	mainContentPane.setLayout(new CardLayout());
    	mainCard = (CardLayout) (mainContentPane.getLayout());
    }
    
    /**
     * Reset the entire game on loss. 
     * @throws IOException
     */
    public void resetOnLoss() throws IOException
    {
    	for(Component component : mainContentPane.getComponents())
    	{
    		mainContentPane.remove(component);
    	}
    	createAllGamePanels();
    }
    
    /**
     * Function to switch between views in this game. Makes the current working view invisible, then 
     * calls the GameView startView function on the new view to add all necessary images / start necessary threads in the new view.
     * @param activePanel
     * @param changeToView
     */
    public void switchViews(Component activePanel, String changeToView)
    {
    	activePanel.setVisible(false);
    	GameView currentView = gameViews.get(changeToView);
    	currentView.startView();
    	mainCard.show(mainContentPane, changeToView);
    }

    /**
     * Starts the game
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
    	@SuppressWarnings("unused")
    	HallOfBonesMain runGame = new HallOfBonesMain();
    }
}
