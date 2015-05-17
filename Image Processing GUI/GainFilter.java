package imageprocessorseam;

import java.awt.image.BufferedImage;



/**
 * Changes the contrast among pixels in an image by scaling all color components by X.
 * X must be > 0.  
 * X < 1 decreases contrast and makes the image darker.
 * X > 1 increases contrast and makes the image lighter.
 * 
 * @author Erin Parker and CS 1410 class and Aaron
 */
public class GainFilter extends ImageRegionFilter {

	private double X = 1;

	/**
	 * Takes in a bufferedimage and makes art
	 */
	public BufferedImage filter(BufferedImage img) {

		BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

		// Two loops are used to build the image by height and width
		for(int y = 0; y < img.getHeight(); y++)
			for(int x = 0; x < img.getWidth(); x++) {

				int pixel = img.getRGB(x, y);
				
				if(y >= getMinY() && y < getMaxY() && x >= getMinX() && x < getMaxX()){
					// Gets each rbg amount of each pixel
					int redAmount = (pixel >> 16) & 0xff;
					int greenAmount = (pixel >> 8) & 0xff;
					int blueAmount = (pixel >> 0) & 0xff;

					// scale each component by X, clamp to 255 as needed
					redAmount *= X;
					if(redAmount > 255)
						redAmount = 255;

					greenAmount *= X;
					if(greenAmount > 255)
						greenAmount = 255;

					blueAmount *= X;
					if(blueAmount > 255)
						blueAmount = 255;

					int newPixel = (redAmount << 16 ) | (greenAmount << 8) | blueAmount;


					result.setRGB(x, y, newPixel);
				}
				else{
					result.setRGB(x, y, pixel); // Does pixel normal if not between values
				}
			}

		return result;
	}

	/**
	 * Sets the value for x
	 * @param x
	 */
	public void setX(int x){

		X = x * .1;

		if(x == 0){
			X = .10;
		}
	}
}
