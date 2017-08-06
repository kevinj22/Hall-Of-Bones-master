package MainMenu;

import java.awt.*;
import javax.swing.*;
import GridGUI.GameView;
import java.awt.event.*;
import java.net.URL;

/**
 * The main menu view. Extends JLayeredPane.
 * @author alanyork
 *
 */
public class MainMenuView extends JLayeredPane implements GameView {
	/**
	 * Because Eclipse wants me to give this a UID
	 */
	private static final long serialVersionUID = 69691337L;
	// CONSTANTS
	public static final String BACKGROUND_IMAGE_PATH = "/FFIV_PSP_Final_Dungeon_Background_2.png";
	public static final String LOGO_IMAGE_PATH = "/logo.png";
	public static final String PLAY_BUTTON_TEXT = "Play";
	public static final String INSTRUCTIONS_BUTTON_TEXT = "Instructions";
	public static final String CREDITS_BUTTON_TEXT = "Credits";
	public static final String EXIT_BUTTON_TEXT = "Exit";
	public static final String DEFAULT_MUSIC = "/FF15_Solumnus.wav";
	public static final String VICTORY_MUSIC = "/AliceInChains_ThemBones.wav";
	public static final String DEFEAT_MUSIC = "/Defeat_Deadmau5_Creep.wav";

	// FIELDS
	private static String menuMusic = DEFAULT_MUSIC;
	private BackgroundPane backgroundPane;
	private ForegroundPane foregroundPane;
	private HallOfBonesMain.HallOfBonesMain mainFrame;
	
	// CONSTRUCTORS
	private MainMenuView() {
		//Play music
		SoundEffectControl.SoundClipPlayer.playLoopingSound(menuMusic);
		// Add Background Layer
		backgroundPane = new BackgroundPane(new ImageIcon(this.getClass().getResource(BACKGROUND_IMAGE_PATH)).getImage());
		this.setSize(new Dimension(BackgroundPane.DEFAULT_WIDTH, BackgroundPane.DEFAULT_HEIGHT));
		this.setPreferredSize(new Dimension(BackgroundPane.DEFAULT_WIDTH, BackgroundPane.DEFAULT_HEIGHT));
		this.add(backgroundPane, Integer.valueOf(0));

		// Add Foreground Layer
		foregroundPane = new ForegroundPane();
		foregroundPane.setOpaque(false);
		this.add(foregroundPane, Integer.valueOf(1));

		// "Pack" the Layers
		handleResize();
	}
	
	/**
	 * Creates an instance of this class with the parameter as the ActionListener
	 * for all user-interactive components within.
	 * @param actionListener
	 * A class implementing ActionListener
	 */
	public MainMenuView(ActionListener actionListener) {
		this();
		this.setActionListener(actionListener);
	}
	
	/**
	 * Creates an instance of this class with the specified ActionListener,
	 * and frame.
	 * @param mainFrame
	 * @param actionListener
	 */
	public MainMenuView(MainMenuController controller, HallOfBonesMain.HallOfBonesMain mainFrame) {
		this((ActionListener) controller);
		controller.setView(this);
		this.mainFrame = mainFrame;
	} 

	// HELPER METHODS
	// Image Loading
	private URL parsePath(String path) {
		return this.getClass().getResource(path);
	}

	private ImageIcon createImageIcon(String path, String description) {
		URL imgURL = parsePath(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL, description);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	// VIEW EVENT HANDLERS AND CALLBACKS
	/**
	 * Handles the resize of the panel by resizing the components within.
	 */
	public void handleResize() {
		// Resize the layers
		backgroundPane.setSize(this.getWidth(), this.getHeight());
		backgroundPane.setBounds(0, 0, this.getWidth(), this.getHeight());
		foregroundPane.setSize(this.getWidth(), this.getHeight());
		foregroundPane.setBounds(0, 0, this.getWidth(), this.getHeight());
		this.validate();
	}

	/**
	 * Paints graphics on resize.
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		handleResize();
	}
	
	// PUBLIC METHODS
	/**
	 * Sets the parameter as the parameter as the ActionListener
	 * for all user-interactive components within.
	 */
	public void setActionListener(ActionListener actionListener) {
		foregroundPane.setActionListener(actionListener);
	}
	
	/**
	 * Returns the frame set by the constructor.
	 * For use with kevinj22's main frame
	 * @return
	 * The main window (frame) of the game
	 */
	public HallOfBonesMain.HallOfBonesMain getMainFrame() {
		return mainFrame;
	}
	
	/**
	 * Starts this view when called from the Main JFrame. Completes implementation
	 * of GameView interface.
	 */
	@Override
	public void startView() {
		handleResize();
		SoundEffectControl.SoundClipPlayer.shutUp();
		SoundEffectControl.SoundClipPlayer.playLoopingSound(menuMusic);
	}
	
	/**
	 * Set the path to the main menu music
	 * @param musicPath
	 * The path to the main menu music
	 */
	public static void setMenuMusic(String musicPath){
		menuMusic = musicPath;
	}
	
	// INNER CLASSES
	/**
	 * The top-most layer of the JLayeredPane, containing the UI components
	 * @author alanyork
	 *
	 */
	@SuppressWarnings("serial")
	private class ForegroundPane extends JPanel {

		// FIELDS
		private JLabel logoLabel = new JLabel(createImageIcon(MainMenuView.LOGO_IMAGE_PATH, "Kevin's RPG"));
		private JButton playButton = new JButton(PLAY_BUTTON_TEXT);
		private JButton instructionsButton = new JButton(INSTRUCTIONS_BUTTON_TEXT);
		private JButton creditsButton = new JButton(CREDITS_BUTTON_TEXT);
		private JButton exitButton = new JButton(EXIT_BUTTON_TEXT);

		// CONSTRUCTORS
		public ForegroundPane() {
			// Initialise Components
			//  Set the ActionCommand string
			playButton.setActionCommand(PLAY_BUTTON_TEXT);
			instructionsButton.setActionCommand(INSTRUCTIONS_BUTTON_TEXT);
			creditsButton.setActionCommand(CREDITS_BUTTON_TEXT);
			exitButton.setActionCommand(EXIT_BUTTON_TEXT);
			
			// Set LayoutManager
			this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

			// Add Components and Glue Spacers
			this.add(Box.createVerticalGlue());
			this.add(Box.createVerticalGlue());
			this.add(logoLabel);
			this.add(Box.createVerticalGlue());
			this.add(playButton);
			this.add(instructionsButton);
			this.add(creditsButton);
			this.add(exitButton);
			this.add(Box.createVerticalGlue());
			this.add(Box.createVerticalGlue());
			this.add(Box.createVerticalGlue());

			// Align Components
			for (Component x : this.getComponents()) {
				((JComponent) x).setAlignmentX(CENTER_ALIGNMENT);
			}
		}
		
		// PUBLIC METHODS
		/**
		 * Sets the parameter as the ActionListener
		 * for all user-interactive components within.
		 */
		public void setActionListener(ActionListener actionListener) {
			playButton.addActionListener(actionListener);
			instructionsButton.addActionListener(actionListener);
			creditsButton.addActionListener(actionListener);
			exitButton.addActionListener(actionListener);
		}
	}
	
	/**
	 * Pop up the instructions.
	 */
	public void popUpInstructions()
	{
		JOptionPane.showMessageDialog(null, "<html>" + 
				"<h2>Dungeon:</h2>" + 
	    		"<p>- Use the left and right arrow keys to move</p>" + 
	    		"<p></p>" + 
	    		"<h2>Battle:</h1>" + 
	    		"<p>- Use your mouse to select one of the moves on the bottom of the screen</p>" + 
	    		"<p>- Watch your health bar (green) and ability points bar (blue). If you run out of health, you lose!</p>" + 
	    		"<p>- Deplete the enemy party's health points to win</p>" + 
	    		"<p></p>" + 
	    		"</html>", "Instructions", JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Pop up credits.
	 */
	public void popUpCredits()
	{
		JOptionPane.showMessageDialog(null,
									 "<html>" + 
						    		 "<h2>Hall of Bones</h2>" + 
						    		 "<p>A game by Kevin Joseph, Andrew Fuoco, Anton Thomas, Alan Tang, and Yi Ngan for EECS2030</p>" + 
						    		 "</html>",
                					 "Credits", JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * A panel that contains nothing except an image.
	 * Adapted from BackGroundPane by kevinj22.
	 * @author alanyork
	 */
	@SuppressWarnings("serial")
	public static class BackgroundPane extends JPanel {
		public static final int DEFAULT_WIDTH = 1400;
	    public static final int DEFAULT_HEIGHT = 920;
		private Image background;
		
		public BackgroundPane(Image image) {
			this.background = image;
			this.setSize(new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT));
	    }
		
		@Override
	    public void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        if (background != null) {                
	        	g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), null);
	        }
	   }
		
		
	}
	
}