package GridGUI;

import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import BattleMVC.BattleView;
import Heros.Hero;


/**
 * Class to prep images for use by the view. Why make this class: images are made in many packages and not just the view.
 * For example Status and Hero classes have images. 
 * @author Kevin
 *
 */
public class ImagePreparation {

	private static int TOOL_TIP_Y = 70;
	private static final ImagePreparation instance = new ImagePreparation();
	
	/** 
	 * Private constructor, empty by intention as no fields necessary. 
	 */
	private ImagePreparation()
	{
		
	}
	
	/**
	 * Get the singleton instance of this class. 
	 * @return ImagePreparation instance
	 */
	public static ImagePreparation getInstance()
	{
		return ImagePreparation.instance;
	}
	
	/** 
	 * Prepare an image for use by the view.
	 * @param path path to the image
	 * @param width image width
	 * @param height image height
	 * @return the rescale Image
	 */
	public Image prepImage(String path, int width, int height)
	{
		ImageIcon currentIcon = new ImageIcon(this.getClass().getResource(path));
        Image image = currentIcon.getImage(); // transform it 
		Image scaledImage = image.getScaledInstance( width, height, java.awt.Image.SCALE_DEFAULT); // scale it the smooth way  
		return scaledImage;
	}

	/**
	 * Attach an image to a JLabel
	 * @param path path to the image
	 * @param width image width
	 * @param height image height
	 * @return JLabel with rescaled image
	 */
	public JLabel attachImageIconToJLabel(String path, int width, int height)
	{
		ImageIcon ii = new ImageIcon(this.getClass().getResource(path));
		Image image = ii.getImage(); // transform it 
		Image newimg = image.getScaledInstance(width, height, java.awt.Image.SCALE_DEFAULT); // scale it the smooth way  
		ii = new ImageIcon(newimg);  // transform it back
		JLabel imageLbl = new JLabel(ii);
		
		return imageLbl;
	}
	
	/**
	 * Helper function that returns a re scaled ImageIcon.
	 * @param path path to the image
	 * @param width image width
	 * @param height image height
	 * @return the rescaled ImageIcon
	 */
	private ImageIcon prepedIcon(String path, int width, int height)
	{
		ImageIcon ii = new ImageIcon(this.getClass().getResource(path));
		Image image = ii.getImage(); // transform it 
		Image newimg = image.getScaledInstance(width, height, java.awt.Image.SCALE_DEFAULT); // scale it the smooth way  
		ii = new ImageIcon(newimg);  // transform it back
		return ii;
	}
	
	/**
	 * Set the position of the tool tip over a JLabel to always be above the character.
	 * @param hero hero whose tool tip text to display.
	 * @param path path to image
	 * @param gridX GridBagLayout x coordinate
	 * @param width width to size image to
	 * @param height height to size image to
	 * @return JLabel which displays the tool tip above the character.
	 */
	public JLabelWithToolTip getToolTipJLabel(Hero hero, String path, int gridX, int width, int height)
	{
//		@SuppressWarnings("serial")
//		JLabel imageLbl = new JLabel() {
//		      public Point getToolTipLocation(MouseEvent event) {
//		          return new Point(gridX, TOOL_TIP_Y);
//		        }
//		      };
		JLabelWithToolTip imageLbl = new JLabelWithToolTip(hero,gridX);
		imageLbl.setIcon(ImagePreparation.getInstance().prepedIcon(hero.getImage(),BattleView.CHARACTER_WIDTH,BattleView.CHARACTER_HEIGHT));
		imageLbl.updateToolTip();
//		imageLbl.setToolTipText(hero.getToolTip());
		return imageLbl;
	}
	
	@SuppressWarnings("serial")
	public class JLabelWithToolTip extends JLabel{
		private Hero hero;
		private int gridX;
		
		public JLabelWithToolTip(Hero hero, int gridX)
		{
			super();
			this.hero = hero;
			this.gridX = gridX;
		}
		
		public Point getToolTipLocation(MouseEvent event) {
          return new Point(this.gridX, TOOL_TIP_Y);
        }
		
		public void updateToolTip()
		{
			this.setToolTipText(this.hero.getToolTip());
		}
	}
}


