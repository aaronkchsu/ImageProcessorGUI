package imageprocessorseam;

import java.awt.image.BufferedImage;

/**
 * An interface that ensures all classes representing an image filter
 * implement the filter method as specified below.
 * 
 * @author Erin Parker
 */
public interface ImageFilterTwo  {
	
	public BufferedImage filter(BufferedImage i);
	
	public void setX(int x);

}
