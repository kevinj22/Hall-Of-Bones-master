package GridGUI;

/**
 * The GameView interface should be implemented for any GameView. It allows the MainJframe: HallOfBonesMain to start the rendering of each 
 * GameView JPanel.
 * @author Kevin
 *
 */
public interface GameView {
	/**
	 * This method should start a view, it's controller, and it's model if applicable. Otherwise it should
	 * just render the view's components. 
	 */
	public void startView();
}
