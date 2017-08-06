package GridGUI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JPanel;

/**
 * BackGroundPane is JPanel with a background image that handles animations during battle.
 * @author Kevin
 *
 */
public class GameViewPanel extends JPanel implements Runnable{

    /**
	 * Created to stop the compiler from complaining
	 */
	private static final long serialVersionUID = -6912546907806584621L;
	
	private Image background;
    private Image actingImg;
	private Thread animator;
	private int x,y;
	private boolean down;
	private final int B_WIDTH = 1400;
    public static final int B_HEIGHT = 920;
    private final int DELAY = 1;
    private volatile boolean runThread;
    
    /**
     * Constructor to set the background image of this JPanel.
     * @param image
     */
    public GameViewPanel(Image image) {     
        this.background = image;      
    }

    /** 
     * Starts an animation thread over this JPanel.
     * The animation moves from the TOP of a character to the bottom of the screen then terminates.
     * @param x the x starting position
     * @param y the y starting position
     * @param animationImage the image to animate 
     */
    public void startDownAnimation(int x, int y, Image animationImage)
    {
    	actingImg = animationImage;
    	setStart(x,y);
    	runThread = true;
    	this.down = true;
    	// Need to make a new Thread every time
    	// Otherwise get illegal state error
    	animator = new Thread(this);
    	animator.start();
    }
    
    /** 
     * Starts an animation thread over this JPanel.
     * The animation moves from the bottom of the screen to the top of the screen then terminates.
     * @param x the x starting position
     * @param y the y starting position
     * @param animationImage the image to animate 
     */
    public void startUpAnimation(int x, int y, Image animationImage)
    {
    	actingImg = animationImage;
    	setStart(x,y);
    	runThread = true;
    	this.down = false;
    	// Need to make a new Thread every time
    	// Otherwise get illegal state error
    	animator = new Thread(this);
    	animator.start();
    }
    
    /**
     * Sets the starting position of the animation.
     * @param x the x starting position
     * @param y the y starting position
     */
    private void setStart(int x, int y)
    {
    	 this.x = x;
         this.y = y;
    }
    
    /**
     * Set window size.
     */
    @Override
    public Dimension getPreferredSize() {
//        return background == null ? new Dimension(0, 0) : new Dimension(background.getWidth(this), background.getHeight(this));
    	return background == null ? new Dimension(0, 0) : new Dimension(B_WIDTH, B_HEIGHT);   
    }

    /**
     * Override paint component to paint the background on the JPanel.
     * @param g the graphics for this JPanel.
     */
    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        if (background != null) {                
            g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), null);
        }
   
    }

    /**
     * Override the paint children method. Why? This way the animations will display on TOP of any other component in this JPanel.
     * @param g the graphics for this JPanel.
     */
    @Override
    protected void paintChildren(Graphics g) {
    	  super.paintChildren(g);
    	  if(runThread)
          {
          	drawAnimation(g);
          }
    }
    
    /**
     * Draw the animation on the JPanel.
     * @param g
     */
    private void drawAnimation(Graphics g) {

        g.drawImage(actingImg, x, y, this);
        Toolkit.getDefaultToolkit().sync();
    }

    /**
     * Cycle down method for the thread to update the images position if moving down.
     */
    private void cycleDown() {

        y += 1;

        if (y > B_HEIGHT) {
            runThread = false;
        }
    }
    
    /**
     * Cycle up method for the thread to update the images position if moving up.
     */
    private void cycleUp() {

        y -= 1;

        if (y < 0) {
            runThread = false;
        }
    }
    
    /**
     * Run portion of the animation thread. Checks to determine if thread should be running dependent on position of image.
     * Checks whether or not to animate up or down dependent on boolean set in this class. 
     */
    @Override
    public void run() {

        if(down)
        {
        	while (runThread) 
        	{
                cycleDown();
                repaint();
                paintAnimation();
            }
        }
        else
        {
        	while (runThread) 
        	{
                cycleUp();
                repaint();
                paintAnimation();
            }
        }
    }
    
    /**
     * Paints the animation.
     */
    private void paintAnimation()
    {
    	long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();
        
    	timeDiff = System.currentTimeMillis() - beforeTime;
        sleep = DELAY - timeDiff;

        if (sleep < 0) {
            sleep = 2;
        }

        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            System.out.println("Interrupted: " + e.getMessage());
        }

        beforeTime = System.currentTimeMillis();
    }
}
