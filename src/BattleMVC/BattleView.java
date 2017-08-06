package BattleMVC;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.TreeMap;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.ToolTipManager;

import BattleCommands.BattleCommand;
import GridGUI.GameView;
import GridGUI.GameViewPanel;
import GridGUI.ImagePreparation;
import GridGUI.ImagePreparation.JLabelWithToolTip;
import HallOfBonesMain.HallOfBonesMain;
import Heros.Hero;
import PartyContainers.AI;
import PartyContainers.HumanPlayer;
import PartyContainers.Player;
import SoundEffectControl.SoundClipPlayer;

public class BattleView extends GameViewPanel implements Runnable, GameView {

    /**
	 * Created to stop the compiler from complaining
	 */
	private static final long serialVersionUID = -6912546907806584621L;
	
	public static String ACTIVITY_MUSIC = "/FF15_StandYourGround.wav";
	
    public static final int B_HEIGHT = 920;
    private Player human;
	private AI AI;
	private BattleController controller;
	private BattleModel model;
	
	
	// Declare constants for positioning
	// Y for row positioning
	public static final int STATUS_Y = 0;
	public static final int HEALTH_BAR_Y = 1;
	public static final int AP_BAR_Y = 2;
	public static final int MOVE_TEXT_Y = 3;
	public static final int INDICATOR_Y = 4;
	public static final int CHARACTER_Y = 5;
	public static final int ABILITY_Y = 6;
	
	// Position to indicate grid height and grid width in makeGbc function 
	public static final int BARS_POS = 1;
	public static final int INDICATOR_POS = 0;
	public static final int STATUS_POS = 0;
	public static final int CHAR_POS = 2;
	public static final int MOVE_TEXT_POS = 0;
	public static final int ABILITY_POS = 4;
	
	// Constant for images
	private static final int INDICATOR_WIDTH = 50;
	private static final int INDICATOR_HEIGHT = 50;
	private static final String INDICATOR_PATH = "/indicator.gif";
	private static final int HEALTH_PLACEHOLDER_WIDTH = 35;
	private static final int HEALTH_PLACEHOLDER_HEIGHT = 35;
	private static final String HEALTH_PLACEHOLDER_PATH = "/AnimationHeart.gif";
	private static final int MANA_PLACEHOLDER_WIDTH = 35;
	private static final int MANA_PLACEHOLDER_HEIGHT = 35;
	private static final String MANA_PLACEHOLDER_PATH = "/Mana.png";
	public static final int CHARACTER_WIDTH = (int) (1024 / 9.0);
	public static final int CHARACTER_HEIGHT = 250;
	private static final int SWORDS_WIDTH = 50;
	private static final int SWORDS_HEIGHT = 50;
	private static final String SWORDS_PATH = "/swords.png";
	private static final String ABILITY_USED_TEXT = "Ability Used";
	private static final String STATUSES_PLACEHOLDER_TEXT = "Statuses";
	
	// Declare buttons to be made
	ArrayList<JButton> buttons = new ArrayList<JButton>();
	
	// Declare health bars / AP bars
	public ArrayList<HealthBar> healthBars = new ArrayList<HealthBar>();
	public ArrayList<AbilityPointsBar> apBars = new ArrayList<AbilityPointsBar>();
	
	// Keep track of character position for targeting
	public HashMap<Integer, Hero> charPos = new HashMap<Integer, Hero>();
	
	// Keep track of current acting character for indicator arrow
	public HashMap<Integer, JLabel> indicatorPos = new HashMap<Integer, JLabel>();

	// HashMap for targeted character position
	public ArrayList<JLabelWithToolTip> toolTipLabels = new ArrayList<JLabelWithToolTip>();
		
	// HashMap for notifying which Ability was used
	public HashMap<Integer, AbilityNotifier> abilityNotifiers = new HashMap<Integer, AbilityNotifier>();
	
	// ArrayList for status effect positions 
	public ArrayList<StatusBar> statusBars = new ArrayList<StatusBar>();
	
	// Declare targetOptionPanel
	public JPanel targetOptionPanel;
	
	// Declare action command names
    static final private String ABILITY1 = "ability1";
    static final private String ABILITY2 = "ability2";
    static final private String ABILITY3 = "ability3";
    static final private String ABILITY4 = "ability4";
    static final private String ITEM = "Item";
    static final private ArrayList<String> actionCommandNames = new ArrayList<String>();
    static
    {
    	actionCommandNames.add(ABILITY1);
    	actionCommandNames.add(ABILITY2);
    	actionCommandNames.add(ABILITY3);
    	actionCommandNames.add(ABILITY4);
    	actionCommandNames.add(ITEM);
    }
    
    public HallOfBonesMain mainJFrame;
    
    /**
     * Construct the BattleView. Since this is being called by the main JFrame this requires multiple fields to it can construct
     * the controller and model. 
     * @param image background image
     * @param human the human party
     * @param AI the AI party
     * @param mainJFrame the main JFrame to switch views with 
     */
    public BattleView(Image image, HumanPlayer human,AI AI, HallOfBonesMain mainJFrame) {     
    	super(image);
    	super.setLayout(new GridBagLayout());
    	// Set tool tip values, make sure they last until you move away and appear immediately 
        ToolTipManager.sharedInstance().setDismissDelay(Integer.MAX_VALUE);
        ToolTipManager.sharedInstance().setInitialDelay(3);
        this.human = human;
        this.AI = AI;
        this.mainJFrame = mainJFrame;
        initializeInitialDisplay(human, AI);
    }

    /**
     * Creates the controller and model then starts the battle thread.
     */
    public void startBattle()
    {
    	System.out.println("here");
		controller = new BattleController(this);
		model = new BattleModel(controller, human,AI);
		controller.setModel(model);
		model.battle();
		repaint();
		revalidate();
    }
    
	/**
	 * Add all battle display elements to this view's BackGroundPane JPanel. Including the ability buttons
	 * characters, health bars, and ability point bars.
	 * @param human the human player
	 * @param ai the AI player
	 */
	public void initializeInitialDisplay(Player human, AI ai)
	{
		this.addAbilityButtons();
		this.addPlaceHolders();
        this.addCharacters(human, ai);
        this.addHealthBars(human, ai);
        this.addAbilityPointBars(human, ai);
	}
    

	/**
	 * Add the ability buttons to this view's BackGroundPane JPanel. 
	 */
	private void addAbilityButtons()
	{
	    // Create buttons
		for(int i = 0; i < 4; i++)
		{
			String currentButtonText = "Ability" + String.format("%d", i);
			String currentButtonToolTip = "Ability" + String.format("%d", i);
			this.buttons.add(makeButton(currentButtonText,actionCommandNames.get(i),currentButtonToolTip));
		}
		
		this.buttons.add(makeButton("Item", ITEM, ""));
		
		// Add buttons to layout
		int gridX = 0;
		for(JButton currentButton : this.buttons)
		{
			GridBagConstraints c = makeGbc(gridX,ABILITY_Y,ABILITY_POS);
			this.add(currentButton, c);
			gridX = gridX + 2;
		}
	
		// Add this for target option this
		targetOptionPanel = new JPanel();
		targetOptionPanel.setVisible(false);
		GridBagConstraints c = makeGbc(2,0,0);
		this.add(targetOptionPanel,c);
		
		repaint();
		revalidate();
	}
	
	/**********************************************************************/
	
	/**
	 * Add health bars to this view's BackGroundPane JPanel.
	 * @param player the human player
	 * @param ai the AI player 
	 */
	private void addHealthBars(Player player, AI ai)
	{
		// Add beating heart place holder
		// Place holder for the Status Effect bar
		// So the Components don't constantly resize 
		GridBagConstraints placeHolder = makeGbc(4,HEALTH_BAR_Y,BARS_POS);
		JLabel beatingHeart = ImagePreparation.getInstance().attachImageIconToJLabel(HEALTH_PLACEHOLDER_PATH,HEALTH_PLACEHOLDER_WIDTH,HEALTH_PLACEHOLDER_HEIGHT);
		this.add(beatingHeart,placeHolder);
		
		TreeMap<String, Hero> party = player.getParty();
		Collection<Hero> p = party.values();
		int gridCount = 0;
		for(Hero hero : p)
		{
			GridBagConstraints c = makeGbc(gridCount,HEALTH_BAR_Y,BARS_POS);
			HealthBar currentHealthBar = new HealthBar(hero);
			this.healthBars.add(currentHealthBar);
			this.add(currentHealthBar, c);
			gridCount ++;
		}
		
		TreeMap<String, Hero> aiParty = ai.getParty();
		Collection<Hero> aiP = aiParty.values();
		gridCount = 5;
		for(Hero hero : aiP)
		{
			GridBagConstraints c = makeGbc(gridCount,HEALTH_BAR_Y,BARS_POS);
			HealthBar currentHealthBar = new HealthBar(hero);
			this.healthBars.add(currentHealthBar);
			this.add(currentHealthBar, c);
			gridCount ++;
		}
		
		repaint();
		revalidate();
	}
	
	/**********************************************************************/
	
	/**
	 * Add Ability Point bars to this view's BackGroundPane JPanel.
	 * @param player the human player
	 * @param ai the AI player 
	 */
	private void addAbilityPointBars(Player player, AI ai)
	{
		// Add beating heart place holder
		// Place holder for the Status Effect bar
		// So the Components don't constantly resize 
		GridBagConstraints placeHolder = makeGbc(4,AP_BAR_Y,BARS_POS);
		JLabel mana = ImagePreparation.getInstance().attachImageIconToJLabel(MANA_PLACEHOLDER_PATH,MANA_PLACEHOLDER_WIDTH,MANA_PLACEHOLDER_HEIGHT);
		this.add(mana,placeHolder);
		
		TreeMap<String, Hero> party = player.getParty();
		Collection<Hero> p = party.values();
		int gridCount = 0;
		for(Hero hero : p)
		{
			GridBagConstraints c = makeGbc(gridCount,AP_BAR_Y,BARS_POS);
			AbilityPointsBar currentAP_Bar = new AbilityPointsBar(hero);
			this.apBars.add(currentAP_Bar);
			this.add(currentAP_Bar, c);
			gridCount ++;
		}
		
		TreeMap<String, Hero> aiParty = ai.getParty();
		Collection<Hero> aiP = aiParty.values();
		gridCount = 5;
		for(Hero hero : aiP)
		{
			GridBagConstraints c = makeGbc(gridCount,AP_BAR_Y,BARS_POS);
			AbilityPointsBar currentAP_Bar = new AbilityPointsBar(hero);
			this.apBars.add(currentAP_Bar);
			this.add(currentAP_Bar, c);
			gridCount ++;
		}
		
		revalidate();
		repaint();
	}
	
	/**********************************************************************/
	//=================== Health / Ability Points / Status Updates =========
	
	/**
	 * Update this views HealthBars.
	 */
	public void updateHealthBars()
	{
		for(HealthBar hb : this.healthBars)
		{
			hb.updateHealthBar();
		}
		
		revalidate();
		repaint();
	}
	
	/**
	 * Update this views AbilityPoint Bars.
	 */
	public void updateAbilityPointBars()
	{
		for(AbilityPointsBar apb : this.apBars)
		{
			apb.updateAPBar();
		}
		
		revalidate();
		repaint();
	}
	
	/**
	 * Update this views Statuses text bars.
	 */
	public void updateStatuses()
	{
		for(StatusBar statBar : this.statusBars)
		{
			statBar.updateStatus();
		}
		
		revalidate();
		repaint();
	}
	
	/**
	 * Update all hero tool tips to change the values if their statuses change 
	 */
	public void updateToolTips()
	{
		for(JLabelWithToolTip currentLabel : toolTipLabels)
		{
			currentLabel.updateToolTip();
		}
		
		revalidate();
		repaint();
	}
	/**********************************************************************/
	
	/**
	 * Make the GridBagConstraints for the various components of the view. 
	 * @param x the column the view component will be in
	 * @param y the row the view component will be in, generally a static constant
	 * @param typeNum sets other variables like the grid bag fill, height, width, and ipadding dependent on the type num. 
	 * Each type of component has a static constant that dictates their type.
	 * @return
	 */
	public GridBagConstraints makeGbc(int x, int y, int typeNum) {
	    GridBagConstraints gbc = new GridBagConstraints();
	
	    gbc.gridx = x;
	    gbc.gridy = y;
	    gbc.weightx = 0.5;
	    gbc.weighty = 0.5;
	    
	    if(typeNum == 0)
	    {
	    	gbc.gridwidth=1;
	    	gbc.gridheight=1;
	    	gbc.fill = GridBagConstraints.BOTH;
	    }
	    if(typeNum == 1)
	    {
	    	gbc.gridwidth = 1;
	   	    gbc.gridheight = 1;
	   	    gbc.fill = GridBagConstraints.BOTH;
	    }
	    if(typeNum == 2)
	    {
	    	gbc.gridwidth = 1;
	   	    gbc.gridheight = 1;
	   	    gbc.ipady = 250;  
	   	    gbc.fill = GridBagConstraints.BOTH;
	    }
	    if(typeNum == 4)
	    {
    	    gbc.gridwidth = 2;
	   	    gbc.gridheight = 1;
	   	    gbc.ipadx = 80;
	   	    gbc.fill = GridBagConstraints.BOTH;
	    	gbc.anchor = GridBagConstraints.PAGE_END; //bottom of space
	    }

	    return gbc;
	}
	
	/**********************************************************************/
	
	/**
	 * Add the place holders for the indicators, status's text bars, and ability used text bars.
	 * These indicators prevent the screen from resizing every time the view updates. 
	 */
	private void addPlaceHolders()
	{
		// Place holder for the Indicators so
		// The components don't constantly resize
		GridBagConstraints placeHolder = makeGbc(4,INDICATOR_Y,INDICATOR_POS);
		JLabel swords = ImagePreparation.getInstance().attachImageIconToJLabel(SWORDS_PATH,SWORDS_WIDTH,SWORDS_HEIGHT);
		this.add(swords,placeHolder);
		
		// Place holder for the Status Effect bar
		// So the Components don't constantly resize 
		placeHolder = makeGbc(4,STATUS_Y,STATUS_POS);
		JLabel statusEffectText = new JLabel(STATUSES_PLACEHOLDER_TEXT);
		statusEffectText.setHorizontalTextPosition(JLabel.CENTER);
        statusEffectText.setVerticalTextPosition(JLabel.CENTER);
        statusEffectText.setForeground(Color.WHITE);
        statusEffectText.setBackground(Color.BLACK);
        statusEffectText.setOpaque(true);
		this.add(statusEffectText,placeHolder);
		
		// Place holder for ability used
		placeHolder = makeGbc(4,MOVE_TEXT_Y,MOVE_TEXT_POS);
		JLabel moveText = new JLabel(ABILITY_USED_TEXT);
		moveText.setHorizontalTextPosition(JLabel.CENTER);
        moveText.setVerticalTextPosition(JLabel.CENTER);
        moveText.setForeground(Color.WHITE);
        moveText.setBackground(Color.BLACK);
        moveText.setOpaque(true);
		this.add(moveText,placeHolder);
		
		revalidate();
		repaint();
	}
	
	/**
	 * Add all characters and their associated displays i.e their ability used, indicators, and status used bars.
	 * @param player player whose characters you wish to display
	 * @param ai AI whose characters you wish to display 
	 */
	private void addCharacters(Player player, AI ai)
	{
		TreeMap<String, Hero> party = player.getParty();
		Collection<Hero> p = party.values();
		int gridX = 0;
		
		for(Hero hero : p)
		{
			addAllDisplayBarsPerCharacter(hero, gridX);
			gridX ++;
		}

		TreeMap<String, Hero> aiParty = ai.getParty();
		Collection<Hero> aiP = aiParty.values();
		gridX = 5;
		for(Hero hero : aiP)
		{
			addAllDisplayBarsPerCharacter(hero, gridX);
			gridX ++;
		}
		
		revalidate();
		repaint();
	}
	
	/**
	 * Adds the character and their associated displays i.e their ability used, indicators, and status used bars.
	 * @param hero the hero you wish to add
	 * @param gridX which column you wish to display the character  
	 */
	private void addAllDisplayBarsPerCharacter(Hero hero, int gridX)
	{
		// Create hero images
		GridBagConstraints c = makeGbc(gridX,CHARACTER_Y,CHAR_POS);
		JLabelWithToolTip imageLbl = ImagePreparation.getInstance().getToolTipJLabel(hero, hero.getImage(),gridX,CHARACTER_WIDTH,CHARACTER_HEIGHT);
		this.toolTipLabels.add(imageLbl);
		this.add(imageLbl, c);
		this.charPos.put(gridX, hero);
		hero.setPosition(gridX);
		
		// Create the turn indicators 
		// NOTE: they go above the characters
		c = makeGbc(gridX,INDICATOR_Y,INDICATOR_POS);
		JLabel indicator = ImagePreparation.getInstance().attachImageIconToJLabel(INDICATOR_PATH,INDICATOR_WIDTH,INDICATOR_HEIGHT);
		indicator.setVisible(false);
		this.add(indicator,c);
		indicatorPos.put(gridX, indicator);	
	
		// Create JLabels for status effect text
		c = makeGbc(gridX,STATUS_Y,STATUS_POS);
		StatusBar currentStatusBar = new StatusBar(hero,"None");
		this.statusBars.add(currentStatusBar);
		this.add(currentStatusBar, c);
	
		// Create JLabels for the ability text
		c = makeGbc(gridX,MOVE_TEXT_Y,MOVE_TEXT_POS);
		AbilityNotifier currentNotifier = new AbilityNotifier(hero);
		this.abilityNotifiers.put(gridX, currentNotifier);
		this.add(currentNotifier, c);
		
		revalidate();
		repaint();
	}
	/**********************************************************************/
	
	/**
	 * Display the current acting character's indicator.
	 * @param actingChar the acting character to display the indicator above.
	 */
	public void displayIndicator(Hero actingChar)
	{
		int position = actingChar.getPosition();
		JLabel imageLbl = indicatorPos.get(position);
		imageLbl.setVisible(true);
		
		revalidate();
		repaint();
	}
	
	/**
	 * Remove the indication from the current acting character at the END of their turn
	 * @param actingChar the character whose turn just finished. 
	 */
	public void removeIndicator(Hero actingChar)
	{
		int position = actingChar.getPosition();
		JLabel imageLbl = indicatorPos.get(position);
		imageLbl.setVisible(false);
		
		revalidate();
		repaint();
	}
	
	/**
	 * Display the text of the ability used above the acting character.
	 * @param actingChar the character to display text above.
	 * @param ability the BattleCommand that the character used. 
	 */
	public void displayAbilityUsed(Hero actingChar, BattleCommand ability)
	{
		int position = actingChar.getPosition();
		AbilityNotifier abilityLbl = abilityNotifiers.get(position);
		abilityLbl.updateAbilityNotifier(ability);
		
		revalidate();
		repaint();
	}
	
	/**
	 * Remove the text of the ability used above the character whose turn just finished.
	 * @param actingChar character whose turn just finished
	 */
	public void removeAbilityUsed(Hero actingChar)
	{
		int position = actingChar.getPosition();
		AbilityNotifier abilityLbl = abilityNotifiers.get(position);
		abilityLbl.clearAbilityNotifier();
		
		revalidate();
		repaint();
	}
	
	/**********************************************************************/
	//========================= Button Maker ===============================
	
	/**
	 * Make a JButton for use by the view.
	 * @param buttonText the text to display
	 * @param actionCommand actionCommand invoked when clicked
	 * @param toolTipText hover over tool tip text
	 * @return 
	 */
	private JButton makeButton(String buttonText, String actionCommand, String toolTipText) 
	{

		//Create and initialize the button.
		// Set tool tips to start from the bottom center of the button 
		@SuppressWarnings("serial")
		JButton button = new JButton(buttonText) {
			public Point getToolTipLocation(MouseEvent event) {
		        return new Point(getWidth() / 2, getHeight());
		      }
		    };
		button.setActionCommand(actionCommand);
		button.setToolTipText(toolTipText);
		
		return button;
	}
	
	/**********************************************************************/
	
	/**
	 * Gets the array of buttons.
	 * @return ArrayList<JButtons> of this views buttons.
	 */
	public ArrayList<JButton> getButtons() {
		return buttons;
	}

	/**
	 * Adds an ability listener to a button. 
	 * @param buttonDex the index of the button in this view's ArrayList
	 * @param action the action to be added to the button
	 */
	public void addAbilityListener(int buttonDex, ActionListener action)
	{
		this.buttons.get(buttonDex).addActionListener(action);
	}
	
	/**
	 * Sets the ability listener of the button. Removes all previous action listeners on the button before adding the new one.
	 * Other many action listeners are invoked at once.
	 * @param buttonDex the index of the button in this view's ArrayList
	 * @param action the action to be set on the button
	 */
	public void setAbilityListener(int buttonDex, ActionListener action)
	{
		JButton currentButton = this.buttons.get(buttonDex);
		for(ActionListener act : currentButton.getActionListeners())
		{
			currentButton.removeActionListener(act);
		}
		currentButton.addActionListener(action);
	}

    /*********************************************************************/
	//===================== Display Messages / Menus ======================
	
	/**
	 * Show Not Enough Ability Points Error Message
	 */
	public void showNotEnoughAbilityPointsMessage()
    {
    	JOptionPane.showMessageDialog(this,"Not Enough AP.", "Not Enough AP.", JOptionPane.ERROR_MESSAGE);
    }
    
	/**
	 * Show Max Stat Error Message
	 */
    public void showMaxStatExceptionMessage()
    {
    	JOptionPane.showMessageDialog(this,"Stat at Maximum Value.", "Stat at Maximum Value.", JOptionPane.ERROR_MESSAGE);
    }

    /**
	 * Show Not Afflicted With Status Error Message
	 */
    public void showNotAfflictedWithStatusMessage()
    {
    	JOptionPane.showMessageDialog(this,"Not Afflicted with Status.", "Not Afflicted with Status.", JOptionPane.ERROR_MESSAGE);
    }
    
    /**
   	 * Show Pick an Alive Target Error Message
   	 */
    public void showPickAliveTarget()
    {
		JOptionPane.showMessageDialog(this,"Please select a target that isn't dead.", "Invalid Target Error", JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Show wait your turn error message.
     */
    public void displayAI_TurnError()
    {
    	JOptionPane.showMessageDialog(this,"Wait your turn!", "Not Your Turn", JOptionPane.ERROR_MESSAGE);
    }
	
    /**
	 * Show item pop up menu
	 */
    public void showItemPopUp(JPopupMenu itemMenu)
    {
    	JButton itemButton = this.getButtons().get(4); 
	    itemButton.setComponentPopupMenu(itemMenu);
	    itemMenu.show(itemButton, itemButton.getWidth()/2, itemButton.getHeight()/2);
    }
    
    /**
	 * Start battle down animation.
	 * @param startX x starting position
	 * @param attackAnimationStartY y starting position
	 * @param animationImage image to animate
	 */
    public void startBattleDownAnimation(int startX, int attackAnimationStartY, Image animationImage)
    {
    	SoundEffectControl.SoundClipPlayer.playSound("/ElsaBlast.wav");
    	this.startDownAnimation(startX, attackAnimationStartY,animationImage);
    }
    
    /**
	 * Start battle up animation.
	 * @param startX x starting position
	 * @param attackAnimationStartY y starting position
	 * @param animationImage image to animate
	 */
    public void startBattleUpAnimation(int startX, int attackAnimationStartY, Image animationImage)
    {
    	SoundEffectControl.SoundClipPlayer.playSound("/ElsaMagic.wav");
    	this.startUpAnimation(startX, GameViewPanel.B_HEIGHT,animationImage);
    }
    
    /**
     * Display target selection
     * @param options the options to display in the pop up window
     * @return An integer representing the option / target selected 
     */
    public int pickATarget(Object[] options)
    {
		return JOptionPane.showOptionDialog(this,"Pick a Target", "Target Selection", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
    }

    /**
     * Start the battle. Creates the model and controller, sets all necessary fields, and starts the BattleState thread.
     */
    public void startView()
    {
    	SoundClipPlayer.shutUp();
    	SoundClipPlayer.playLoopingSound(ACTIVITY_MUSIC);
    	this.startBattle();
    }
    
    /**
     * Switch views to the DungeonView when the battle is over.
     */
    public void switchViews(String nameOfView)
    {
    	mainJFrame.switchViews(this, nameOfView);
    }
    
}
