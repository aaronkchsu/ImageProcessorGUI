package imageprocessorseam;

import java.awt.image.BufferedImage;

public class EdgeFilter extends ImageRegionFilter {

	/**
	 * Produces a filter
	 */
	public BufferedImage filter(BufferedImage i) {

		BufferedImage result = new BufferedImage(i.getWidth(), i.getHeight(), BufferedImage.TYPE_INT_RGB);

		// For each pixel in the image . . . 
		for(int y = 0; y < i.getHeight(); y++)
			for(int x = 0; x < i.getWidth(); x++) {

				int pixel = i.getRGB(x, y);

				// This is to apply a filter to a selected region only
				if(y >= getMinY() && y < getMaxY() && x >= getMinX() && x < getMaxX()){

					// Decompose the pixel in the amounts of red, green, and blue.
					int redAmount = (pixel >> 16) & 0xff;
					int greenAmount = (pixel >> 8) & 0xff;
					int blueAmount = (pixel >> 0) & 0xff;
					int northAvg = 0;
					int southAvg = 0;
					int westAvg = 0;
					int eastAvg = 0; // These four primitives are used to keep track of our subtacting values

					// Only Gets pixel if it exists and performs same action above.
					// The if statements are to get the corner cases out of the way
					if(y != 0){
						int north = i.getRGB(x, y - 1);
						redAmount = (north >> 16) & 0xff;
						greenAmount = (north >> 8) & 0xff; // Separate the RGB values and then add the average to the value
						blueAmount = (north >> 0) & 0xff;
						northAvg = (redAmount + greenAmount + blueAmount) / 3;						
					}
					if(x != i.getWidth() - 1){
						int east = i.getRGB(x + 1, y);
						redAmount = (east >> 16) & 0xff;
						greenAmount = (east >> 8) & 0xff;
						blueAmount = (east >> 0) & 0xff;
						eastAvg = (redAmount + greenAmount + blueAmount) / 3;		
					}
					if(y != i.getHeight() - 1){
						int south = i.getRGB(x, y + 1);
						redAmount = (south >> 16) & 0xff;
						greenAmount = (south >> 8) & 0xff;
						blueAmount = (south >> 0) & 0xff;
						southAvg = (redAmount + greenAmount + blueAmount) / 3;		
					}
					if(x != 0){
						int west = i.getRGB(x - 1, y);
						redAmount = (west >> 16) & 0xff;
						greenAmount = (west >> 8) & 0xff;
						blueAmount = (west >> 0) & 0xff;
						westAvg = (redAmount + greenAmount + blueAmount) / 3;		
					}

					int grad = Math.abs(eastAvg-westAvg) + Math.abs(southAvg-northAvg);

					int newPixel = 0; // To keep track what we will do with the new pixel

					// Our binary decision
					if(grad > 25){
						redAmount = 255;
						greenAmount = 255;
						blueAmount = 255;
						newPixel = (greenAmount << 16 ) | (redAmount << 8) | blueAmount;;
					}
					else {
						// If the pixel is not an edge then make it black
						redAmount = 0;
						greenAmount = 0;
						blueAmount = 0;
						newPixel = (greenAmount << 16 ) | (redAmount << 8) | blueAmount;;
					}
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
