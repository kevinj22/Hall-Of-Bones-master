package BattleMVC;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.TreeMap;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import BattleCommands.Ability;
import BattleCommands.BattleCommand;
import BattleCommands.Item;
import Heros.Hero;
import PartyContainers.AI;
import PartyContainers.AiBattleReturnType;
import PartyContainers.Player;
import RPG_Exceptions.BattleModelException;

/**
 * The BattleModel handles all data computation during battle. It also sends signals to the controller to update the view.
 * Contains the BattleState which is a thread that runs during the battle.
 * @author Kevin
 *
 */
public class BattleModel {
	private BattleController controller; 
	private Player human;
	private AI AI;
	private Queue<Hero> gameQueue = new LinkedList<Hero>();
	private final BattleState gameState = new BattleState(false);
	private Hero currentHero; 
	private Hero targetedChar;
	private boolean gameOver = false;
	private boolean firstGo = true;
	public static int DISPLAY_SLEEP_TIME = 750;

	/*********************************************************************************************************/
	/*********************************************************************************************************/
	/*********************************************************************************************************/
	// ======================================= Constructor ====================================================
	
	/**
	 * Construct this battle model. Sends signal to constructor to tell the view to display everything.
	 * @param controller the controller
	 * @param human human party
	 * @param AI AI party
	 */
	public BattleModel(BattleController controller, Player human, AI AI){
        this.controller = controller;
        this.human = human;
        this.AI = AI;
        AI.setController(controller);
        // Fill the initial battle queue
        this.fillInitialQueue();
    }
	
	/*********************************************************************************************************/
    
	/**
	 * Starts the game state thread. 
	 */
    public void battle()
    {
    	gameState.start();
    }
    
    /*********************************************************************************************************/
    
    /**
     * Fills the initial battle queue when the gameState starts. 
     */
    private void fillInitialQueue()
    {
    	// Add human party to array list to be sorted
    	TreeMap<String, Hero> party = human.getParty();
		Collection<Hero> p = party.values();
		ArrayList<Hero> sortMe = new ArrayList<Hero>(p);

		// Add AI party to array list to be sorted
		TreeMap<String, Hero> aiParty = AI.getParty();
		Collection<Hero> aiP = aiParty.values();
		sortMe.addAll(aiP);
		
		// Sort the complete list
		Collections.sort(sortMe);
		// Since it sorts in ascending order 
		// i.e lower speed first 
		// Need to reverse the order 
		Collections.reverse(sortMe);
		
		// Add to queue in sorted order 
		for(Hero hero : sortMe)
		{
			gameQueue.add(hero);
		}
    }
	
	/*********************************************************************************************************/
	//====================================== Battle Status Checks =============================================
    
    /**
     * Checks the battle status to determine if the battle is over or not. If all characters in either party are dead the battle is over. 
     */
	public void checkBattleStatus()
	{
		// Will remove characters from party when they die
		// So if either party is empty, end battle 
		int deadCountHuman = partyDead(human.getParty());
		if(deadCountHuman == 4)
		{
			gameOver = true;
			try {
				controller.Defeat();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		int deadCountAI = partyDead(AI.getParty());
		if(deadCountAI == 4)
		{
			gameOver = true;
			controller.Victory();
		}
	}
	
	/**
	 * Computes the dead count in a party. If the dead count is 4 then the battle is over.
	 * @param party the party whose dead you wish to count
	 * @return deadCount an integer of how many characters have 0 or less health 
	 */
	private int partyDead(TreeMap<String,Hero> party)
	{
		Collection<Hero> p = party.values();
		int deadCount = 0;
		for(Hero currentClass : p)
		{
			if(currentClass.getHealth() <= 0)
			{
				deadCount ++;
			}
		}
		return deadCount;
	}
	
	/**
	 * Updates statuses, and all display bars on the view on successful turn completion. Also readds the character whose turn has 
	 * just finished to the queue. 
	 * @param actingHero the hero whose bars and statuses must be updated. 
	 */
    private void updateOnSuccessfulEvent(Hero actingHero)
    {
    	controller.sendUpdateEndOfTurnSignal(actingHero);
    	controller.addActionListenersAI_Turn();
		gameQueue.add(actingHero);
		checkBattleStatus(); 	
    }

    /*********************************************************************************************************/
    //=========================================== GETTERS AND SETTERS =========================================
    
	/**
	 * Gets the human player from the model
	 * @return the human player
	 */
	public Player getHuman() {
		return human;
	}

	/**
	 * Gets the AI player from the model
	 * @return the AI player 
	 */
	public AI getAI() {
		return AI;
	}

	/**
	 * Gets the current acting hero from the model
	 * @return the current acting hero 
	 */
	public Hero getCurrentHero() {
		return currentHero;
	}

	/**
	 * Get the targeted hero from the model
	 * @return the targetedChar
	 */
	public Hero getTargetedChar() {
		return targetedChar;
	}
	
	/**
	 * Get the game state.
	 * @return the gameState
	 */
	public BattleState getGameState() {
		return gameState;
	}

	/**
	 * Is it the first go?
	 * @return the whether or no it's the first turn 
	 */
	public boolean isFirstGo() {
		return firstGo;
	}
	
	/**
	 * Set the targeted character in the model
	 * @param targetedChar the targetedChar to set
	 */
	public void setTargetedChar(Hero targetedChar) {
		this.targetedChar = targetedChar;
	}
	
	/**
	 * Returns the game over status.
	 * @return game over boolean.
	 */
	public boolean battleCheck() {
		return this.gameOver;
	}
	
	/*********************************************************************************************************/
	/*********************************************************************************************************/
	/*********************************************************************************************************/
	//========================================== Battle Thread ================================================
	
	/**
	 * BattleState is the BattleModel's thread which actually runs the battle.
	 * It pauses on player's turn until a successful event is triggered.
	 * Sends all update signals and waits until animations are complete before continuing to next turn.
	 * @author Kevin
	 *
	 */
    public class BattleState extends Thread{
    	
   	  private final Object GUI_INITIALIZATION_MONITOR = new Object();
      private boolean pauseThreadFlag = false;
    
      /**
       * Create the BattleState thread.
       * @param pauseThreadFlag wheter to start paused or not.
       */
   	  public BattleState(boolean pauseThreadFlag) { 
   		  super("BattleState");
   		  this.pauseThreadFlag = pauseThreadFlag; 
   	  }
   	  
   	  /**
   	   * Get the running state of this thread.
   	   * @return The running state of this thread.
   	   */
   	  public boolean getpauseThreadFlag() { 
   		  return pauseThreadFlag; 
   	  }
   	  
   	  /**
   	   * Set the running state of this thread.
   	   * @param pauseThreadFlag whether to pause or run.
   	   */
   	  public void setPauseThreadFlag(boolean pauseThreadFlag) { 
   		  this.pauseThreadFlag = pauseThreadFlag; 
   	  }
   	  
   	  /**
   	   * Run method of this thread. Handles:
   	   * 1) Determining whose turn it is, then passing control to them
   	   * 2) Updating all hero statistics on / before / during actions.
   	   * 3) Triggering animations and pausing the state until they are finished.
   	   * 4) Sending many signals to the controller to update the view when actions are taken. 
   	   * Ends when one party is completely composed of dead characters.
   	   */
      @Override
      public void run() {
    	  while(!gameOver)
    	  {
    		  if(!firstGo)
    		  {
    			  try {
  					BattleState.sleep(DISPLAY_SLEEP_TIME);
  				} catch (InterruptedException e) {
  					e.printStackTrace();
  				}
    			  controller.signalRemoveAbilityUsed(currentHero);
    		  }
    		  
    		  currentHero = gameQueue.poll();
    		  if(currentHero.getHealth() > 0)
    		  {
    			  // Display indicator arrow
        		  controller.signalDisplayIndicator(currentHero);
	        	  if(currentHero.getControlledBy().equals("AI"))
	        	  {
	        		try {
						BattleState.sleep(DISPLAY_SLEEP_TIME);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
	        		  
	        		  System.out.println("****************************************************");
	        		  System.out.println("Current AI health: " + currentHero.getHealth());
	        		  
	        		  AiBattleReturnType AI_Move = null; 
					
					  try {
						AI_Move = AI.aiTurn(currentHero, human);
					} catch (BattleModelException e1) {
						e1.printStackTrace();
					}
				
	        		  // Extract target and ability from AiBattleReturnType
	        		  Hero AI_target = AI_Move.getTarget();
	        		  BattleCommand AI_ability = AI_Move.getCmd();
	        		  
	        		  if(AI_target != null)
	        		  {
	        			  controller.signalDisplayAbilityUsed(currentHero, AI_ability);
	        		  }
	        		  else
	        		  {
	        			  if(AI_ability != null)
	        			  {
	        				  controller.signalDisplayAbilityUsed(currentHero, AI_ability);
	        			  }
	        			  controller.signalRemoveIndicator(currentHero);
	        		  }
	        		  try {
							BattleState.sleep(DISPLAY_SLEEP_TIME);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
	        		  controller.sendUpdateEndOfTurnSignal(currentHero);
	        		  if(AI_target != null)
	        		  {
	        			  controller.signalRemoveAbilityUsed(currentHero);
	        		  }
	        		  checkBattleStatus();
	        		  gameQueue.add(currentHero);
	        	  }
	        	  else
	        	  {
	        		  // Only add action listeners ONCE
	        		  // If add more than once get many repeated actions 
        			  controller.addActionListeners(currentHero);
        			  controller.changeButtonNamesAndCmds(currentHero);
        			  firstGo = false;
        			  boolean crowdControlled = currentHero.updateStatuses(controller);
                      if(currentHero.getHealth() < 1)
                       {
                    	  System.out.println("Knocked Out!!!");
                          updateOnSuccessfulEvent(currentHero);
                      }
                      else if (crowdControlled) {
                    	  
                          System.out.println("Stunned!!!");
                          updateOnSuccessfulEvent(currentHero);
                      }
                      else 
                      {
                    	  System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                          System.out.println("Current hero health: " + currentHero.getHealth());
                          try {
                            pauseThread();
                          } catch (InterruptedException e) {
                            e.printStackTrace();
                          }
                          System.out.println("Please input a command");
                          checkForPause();
                      }
                  }
    		 }
    	  }
       	}

        private void checkForPause() {
            synchronized (GUI_INITIALIZATION_MONITOR) {
                while (pauseThreadFlag) {
                    try {
                        GUI_INITIALIZATION_MONITOR.wait();
                    } catch (Exception e) {}
                }
            }
        }

        public void pauseThread() throws InterruptedException {
            pauseThreadFlag = true;
        }

        public void resumeThread() {
            synchronized(GUI_INITIALIZATION_MONITOR) {
                pauseThreadFlag = false;
                GUI_INITIALIZATION_MONITOR.notify();
            }
        }

   }
    
    /*********************************************************************************************************/
    /*********************************************************************************************************/
    /*********************************************************************************************************/
    //=========================================== Listeners ===================================================
    
    /**
     * AbilityListener executes a human hero's chosen action. If the chosen action executes successfully the BattleState thread 
     * is resumed.
     * @author Kevin
     *
     */
    public class AbilityListener implements ActionListener {
    
    	private Hero watchingHero;
    	
    	/**
    	 * AbilityListener constructor. Sets which hero this ability listener adheres to.
    	 * @param hero which hero this ability listener adheres to.
    	 */
    	public AbilityListener(Hero hero)
    	{
    		this.watchingHero = hero;
    	}
    	
    	/**
    	 * Execute the ability performed based on the action event from the button pressed.
    	 * @param e the action event triggered
    	 */
        public void actionPerformed(ActionEvent e) {
        	// Retrieve Command
    		String cmd = e.getActionCommand();
    		
    		// If not enough AP can't go, display error message and thread will loop around again 
    		// Until a successful action is made
    		Ability abilityToUse = Ability.getAbility(watchingHero, cmd);

    		try{
    			abilityToUse.useBattleCommand(watchingHero, controller);
				// Update statuses
				gameState.resumeThread();
    			controller.signalDisplayAbilityUsed(watchingHero, abilityToUse);
    			controller.signalUpdateStatuses();
				// Animate Attack
				updateOnSuccessfulEvent(watchingHero);
    		}
    		catch(BattleModelException battleException)
    		{
    			battleException.sendControllerErrorSignal();
    		}
    	} 
    }

    //========================================== AI Turn Listener =============================================
    
    /**
     * AI_Turn_Listener tells the controller to signal the view to display an error if the player is trying to act when it's not their turn.
     * @author Kevin
     *
     */
    public class AI_Turn_Listener implements ActionListener {
        
    	/**
    	 * Send an error on action event if not the player's turn.
    	 * @param e any action event when not the player's turn.
    	 */
        public void actionPerformed(ActionEvent e) {
        	controller.AI_Turn_Error();
    	} 
    }
    
    //========================================== Item Listener ================================================
    
    /**
     * ItemListener adds the pop up menu when the Item button is clicked
     * @author Kevin
     *
     */
    public class ItemListener implements ActionListener{
    	/**
    	 * Adds the pop up menu when the item button is clicked.
    	 * @param e the action event linked to the item button.
    	 */
    	public void actionPerformed(ActionEvent e) 
    	{
    		battleTriggerAddPopUpMenu();
    	}
    }
    
    /**
     * addPopUpMenu creates and adds the pop up menu to the item button when it is clicked.
     * It iterates through the player's inventory and adds each item to the pop up list. 
     * As the items are added to the pop up list and action listener is created for each item that calls
     * that item's useBattleCommand method on click.
     */
	private void battleTriggerAddPopUpMenu()
	{
		 //Create the popup menu.
	    JPopupMenu itemPopUp = new JPopupMenu();
	    ArrayList<JMenuItem> menuItems = new ArrayList<JMenuItem>();
	    int itemCount = 0;
	    ArrayList<Item> items = human.inventoryItems();
	    for(Item currentItem : items)
	    {
	        menuItems.add(new JMenuItem(currentItem.toString()));
	        itemPopUp.add(menuItems.get(itemCount));
	        itemCount ++;
	    }
	    
	    for(int i = 0; i<menuItems.size(); i++)
	    {
	    	JMenuItem currentItem= menuItems.get(i);
	    	Item item = items.get(i);
	    	currentItem.addActionListener((ActionEvent e) -> {
	            try {
	            	// Try and use the item
	            	// It useItem throws MaximumStatException if your character is at the maximum of said stat 
					item.useBattleCommand(currentHero,controller);
		    		itemPopUp.setVisible(false);
		    		controller.signalDisplayAbilityUsed(currentHero, item);
		    		updateOnSuccessfulEvent(currentHero);
		    		gameState.resumeThread();
				} catch (BattleModelException itemException) {
					itemException.sendControllerErrorSignal();				
				} 
	        });
	    }
	    controller.signalItemPopUp(itemPopUp);
	}

}
