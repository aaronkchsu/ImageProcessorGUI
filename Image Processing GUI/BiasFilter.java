package imageprocessorseam;

import java.awt.image.BufferedImage;



/**
 * Increases or decreases the brightness of an image by adding X to each color component.  
 * X > 0 increases brightness.
 * X < 0 decreases brightness.
 * 
 * @author Erin Parker and CS 1410 class. and used by Aaron to make this thing
 */
public class BiasFilter extends ImageRegionFilter {

	// Number to be added
	private int X;

	/**
	 * A filter method takes in BufferedImage
	 */
	public BufferedImage filter(BufferedImage img) {

		BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

		// Two loops to make new image
		for(int y = 0; y < img.getHeight(); y++)
			for(int x = 0; x < img.getWidth(); x++) {
				int pixel = img.getRGB(x, y);
				if(y >= getMinY() && y < getMaxY() && x >= getMinX() && x < getMaxX()){
					int redAmount = (pixel >> 16) & 0xff;
					int greenAmount = (pixel >> 8) & 0xff;
					int blueAmount = (pixel >> 0) & 0xff;

					// Set up bounds for red and make use of bias 
					// the bounds are important so image does not go over
					redAmount += X;
					if(redAmount < 0)
						redAmount = 0;
					else if(redAmount > 255)
						redAmount = 255;

					greenAmount += X;
					if(greenAmount < 0)
						greenAmount = 0;
					else if(greenAmount > 255)
						greenAmount = 255;

					blueAmount += X;
					if(blueAmount < 0)
						blueAmount = 0;
					else if(blueAmount > 255)
						blueAmount = 255;

					int newPixel = (redAmount << 16 ) | (greenAmount << 8) | blueAmount;
					result.setRGB(x, y, newPixel);
				}
				else{
					result.setRGB(x, y, pixel); // Does pixel normal if not between values
				}
			}
		// Result is the new buffered mimage
		return result;
	}


	// changes our instance variable to fit with the situation
	@Override
	public void setX(int x) {
		X = x;
	}
}
