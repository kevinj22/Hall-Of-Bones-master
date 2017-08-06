package BattleMVC;

import java.awt.GridBagLayout;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JPopupMenu;
import BattleCommands.Ability;
import BattleCommands.BattleCommand;
import DungeonMVC.DungeonModel;
import GridGUI.GameViewPanel;
import Heros.Hero;
import PartyContainers.*;

/**
 * The BattleController sends messages between the view and model. Also implements action listeners on view's buttons based on input 
 * from the BattleModel.
 * @author Kevin
 *
 */
public class BattleController{
	private BattleView view;
	private BattleModel model;
	private int[][] layoutDim;
	private int attackAnimationStartY;
	private int characterColumnWidth;
	
	/**********************************************************************/
	
	/**
	 * Constructor for the controller.
	 * @param view the view related to this controller.
	 */
    public BattleController(BattleView view){
    	this.view = view;
    }
    
    /**********************************************************************/
	
    /**
     * Set the BattleModel for this controller.
     * @param model the BattleModel for this controller.
     */
    public void setModel(BattleModel model)
    {
    	this.model = model;
    	getAnimationConstants();
    }
    
    /**********************************************************************/
    
    /**
     * Get the animation constants dependent on the size of the screen and how many rows / columns there are in the 
     * GridBagLayout. 
     */
    private void getAnimationConstants()
    {
    	// Get the layout
    	// Done in battle as the layout is now made
        GridBagLayout gridBagLayout = (GridBagLayout) view.getLayout();
        
        // Get the layout dimensions
    	// Returns an array of arrays. The first array is the COLUMN widths. The second is the row WIDTHS.
    	layoutDim = gridBagLayout.getLayoutDimensions();
     		
   		// Need to add up to the row -1 distance from the top of the view
    	attackAnimationStartY = 0;
    	
    	for(int rowCount = 0; rowCount <= BattleView.CHARACTER_Y-1; rowCount++)
    	{
    		attackAnimationStartY += layoutDim[1][rowCount];
    	}
    	
    	// Get the character column width, it's a constant so only want to get it once
    	characterColumnWidth = layoutDim[0][0];
    }
	
    /**********************************************************************/
    //========================= SIGNALS ====================================
    
    /**
     * Send signal to view to display the heros, buttons, and status bars.
     * @param human human party
     * @param ai AI party
     */
    public void sendInitializeSignal(Player human, AI ai)
    {
    	// Send the signal to the view to add everything 
    	view.initializeInitialDisplay(human, ai);
    }
    
    /**
     * Send signal to view to update on the end of a turn.
     * @param actingHero hero who just finished their turn.
     */
    public void sendUpdateEndOfTurnSignal(Hero actingHero)
    {
    	view.updateHealthBars();
		view.updateAbilityPointBars();
		view.updateStatuses();
		view.updateToolTips();
		view.removeIndicator(actingHero);
		model.checkBattleStatus();
    }
    
    /**
     * Send the signal to the view to update health bars and statuses after a status tick.
     */
    public void sendPostStatusUpdateSignal()
    {
    	view.updateHealthBars();
		view.updateStatuses();
		model.checkBattleStatus();
    }
    
    /**
     * Send the signal to the view to remove the ability used above the hero who just finished their turn.
     * @param actingHero the hero who just finished their turn.
     */
    public void signalRemoveAbilityUsed(Hero actingHero)
    {
    	view.removeAbilityUsed(actingHero);
    }
    
    /**
     * Send the signal to the view to display the ability used by the current hero.
     * @param actingHero hero who just used an ability.
     * @param ability the ability the hero just used.
     */
    public void signalDisplayAbilityUsed(Hero actingHero, BattleCommand ability)
    {
    	view.displayAbilityUsed(actingHero, ability);
    }
    
    /**
     * Send the signal to the view to display the indicator over the current acting hero.
     * @param actingHero the current acting hero.
     */
    public void signalDisplayIndicator(Hero actingHero)
    {
    	view.displayIndicator(actingHero);
    }
    
    /**
     * Send the signal to the view to remove the indicator over the hero whose turn just finished.
     * @param actingHero the hero whose turn just finished.
     */
    public void signalRemoveIndicator(Hero actingHero)
    {
    	view.removeIndicator(actingHero);
    }
    
    /**
     * Send the signal to the view to update statuses text after a status tick.
     */
    public void signalUpdateStatuses()
    {
    	view.updateStatuses();
    }
    
    /**
     * Send the signal to the display not enough ability points error message.
     */
    public void sendNotEnoughAbilityPointsSignal()
    {
    	view.showNotEnoughAbilityPointsMessage();
    }
    
    /**
     * Send the signal to the view to display max stat error message.
     */
    public void sendMaxStatExceptionSignal()
    {
    	view.showMaxStatExceptionMessage();
    }
    
    /**
     * Send the signal to the view to display not afflicted with status error message on StatusItem use.
     */
    public void sendNotAfflictedWithStatusSignal()
    {
    	view.showNotAfflictedWithStatusMessage();
    }
    
    /**
     * Send the signal to the view to pop up the item menu.
     */
    public void signalItemPopUp(JPopupMenu itemMenu)
    {
    	view.showItemPopUp(itemMenu);
    }
    
    
    /**
     * Send the message to the view to display an error if you try to take your turn, out of turn.
     */
    public void AI_Turn_Error()
    {
    	view.displayAI_TurnError();
    }
    
    /**********************************************************************/
    //============================ BattleCommand Animation =================
    
    /**
     * Send the signal to the view to animate the appropriate battle command.
     * @param targetedChar target to animate over
     * @param animationImage image to animate
     * @param down whether to animate up or down
     */
    public void animateBattleCommand(Hero targetedChar, Image animationImage, boolean down)
    {
    	int startX = getAnimationStartingX(targetedChar);
    	if(down)
    	{
    		view.startBattleDownAnimation(startX, attackAnimationStartY, animationImage);

    	}
    	else
    	{
    		view.startBattleUpAnimation(startX, GameViewPanel.B_HEIGHT,animationImage);
    	}
    }
    
    /**
     * Find the X coordinate in the grid system of the targeted character.
     * @param targetedChar target to animate over
     * @return X coordinate in the grid system of the targeted character.
     */
    private int getAnimationStartingX(Hero targetedChar)
    {
    	// Determine where to start the animation
    	int targetPosition = targetedChar.getPosition();
    	int startX;
    
    	// If targeting an AI must include the offset of the middle column
    	// Since the first 4 columns are of equal width can just multiply the 
    	// Column width by 4 then add the offset
    	if(targetPosition > 4)
    	{
    		startX = 4 * characterColumnWidth + layoutDim[0][4] +  characterColumnWidth*(targetPosition - 5);
    	}
    	else
    	{
    		startX = targetPosition * characterColumnWidth;
    	}
    	return startX;
    }
    
    /**********************************************************************/
    //========================== Action Listeners ==========================
    
    /**
     * Add the action listeners to the ability buttons of the current acting hero
     * @param addHero the hero whose ability buttons to customize 
     */
    public void addActionListeners(Hero addHero)
    {
    	if(model.isFirstGo())
    	{
    		for(int i = 0; i < 4; i ++)
    		{
    			view.addAbilityListener(i, model.new AbilityListener(addHero));
    		}
            view.addAbilityListener(4, model.new ItemListener());
    	}
    	else
    	{
    		for(int i = 0; i < 4; i ++)
    		{
    			view.setAbilityListener(i, model.new AbilityListener(addHero));
    		}
    	}
    }

    /**
     * Add action listeners on AI turn. Prevents you from going when it is not your turn.
     */
    public void addActionListenersAI_Turn()
    {
    	for(int i = 0; i < 4; i ++)
		{
    		view.setAbilityListener(i, model.new AI_Turn_Listener());
		}
    }
    
    /**
     * Change the text and action event text of the current acting hero's ability buttons.
     * @param addHero the hero whose ability buttons to customize 
     */
    public void changeButtonNamesAndCmds(Hero hero)
    {
    	HashMap<String, Ability> abilities = hero.getAbilities();
    	ArrayList<JButton> buttons = view.getButtons();
    	
    	int buttonCount = 0;
    	for(Ability currentAbility : abilities.values())
    	{
    		buttons.get(buttonCount).setText(currentAbility.getName());
			buttons.get(buttonCount).setActionCommand(currentAbility.getName());
    		buttons.get(buttonCount).setToolTipText(currentAbility.getDescription());
    		buttonCount ++;
    	}
    }
    
    /**********************************************************************/
    //======================== Targeting ===================================
    
    /**
     * Send a signal to the view to create the targeting pop up menu.
     * @return the target selected
     */
    public Hero signalShowTargetOptions()
    {
    	Object[] options = {"1","2","3","4","Cancel"};   
    	int target = view.pickATarget(options);
    	if(target == 4)
    	{
    		return null;
    	}
    	target = target + 5;
    	System.out.println(target);
    	Hero charTarget = view.charPos.get(target);
    	// If target is dead, send error message 
    	if(charTarget.getHealth() <= 0)
    	{
    		view.showPickAliveTarget();
    		return null;
    	}
    	return charTarget;
    }
    
    /**********************************************************************/
    //=========================== Battle Complete ==========================
    
    /**
     * Set this view to be invisible, let the DungeonModel know the battle is over, and change the view.
     */
    public void Victory ()
    {
        DungeonModel.setBattleOver(true);
    	view.setVisible(false);
    	MainMenu.MainMenuView.setMenuMusic(MainMenu.MainMenuView.VICTORY_MUSIC);
        view.switchViews("dungeon");
    }
    
    /**
     * Set this view to be invisible, let the DungeonModel know the battle is over, and change the view to the main menu.
     * @throws IOException 
     */
    public void Defeat () throws IOException
    {
    	view.setVisible(false);
    	view.mainJFrame.resetOnLoss();
    	MainMenu.MainMenuView.setMenuMusic(MainMenu.MainMenuView.DEFEAT_MUSIC);
        view.switchViews("mainMenu");
    }
    /**********************************************************************/
}
