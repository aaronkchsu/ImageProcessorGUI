package imageprocessorseam;

import java.awt.image.BufferedImage;

public class GreenBlueSwap extends ImageRegionFilter {

	/**
	 * A piece of code used to make art
	 */
	public BufferedImage filter(BufferedImage i) {

		BufferedImage result = new BufferedImage(i.getWidth(), i.getHeight(), BufferedImage.TYPE_INT_RGB);

		// For each pixel in the image . . . 
		for(int y = 0; y < i.getHeight(); y++)
			for(int x = 0; x < i.getWidth(); x++) {

				int pixel = i.getRGB(x, y);

				if(y >= getMinY() && y < getMaxY() && x >= getMinX() && x < getMaxX()){
				// Decompose the pixel in the amounts of red, green, and blue.
				int redAmount = (pixel >> 16) & 0xff;
				int greenAmount = (pixel >> 8) & 0xff;
				int blueAmount = (pixel >> 0) & 0xff;

				// Compose the new pixel.
				int newPixel = (redAmount << 16 ) | (blueAmount << 8) | greenAmount;

				// Set the pixel of the new image.
				result.setRGB(x, y, newPixel);
				}
				else{
					result.setRGB(x, y, pixel); // Does pixel normal if not between values
				}
			}
		return result;
	}

	@Override
	public void setX(int x) {
		// TODO Auto-generated method stub
		
	}
}
