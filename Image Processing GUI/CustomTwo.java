package imageprocessorseam;

import java.awt.image.BufferedImage;

public class CustomTwo extends ImageRegionFilter {

	@Override
	public BufferedImage filter(BufferedImage i) {

		// Makes copy image size of original
		BufferedImage result = new BufferedImage(i.getWidth(), i.getHeight(), BufferedImage.TYPE_INT_RGB);

		// Variables reset to 0 after 
		int redAmount = 0;
		int greenAmount = 0;
		int blueAmount = 0;

		// For each pixel in the image . . . 
		for(int y = 0; y < i.getHeight(); y += 2)
			for(int x = 0; x < i.getWidth(); x += 2) {

				// Variables reset to 0 after
				int totalRed = 0;
				int totalGreen = 0;
				int totalBlue = 0;
				int count = 0;

				// Try to get all surrounding pixels if it exists
				int pixel = i.getRGB(x, y);
				if(y >= getMinY() && y < getMaxY() && x >= getMinX() && x < getMaxX()){
					// Performs this action of splitting the RGB for each pixel and adding to the total amount
					redAmount = (pixel >> 16) & 0xff;
					greenAmount = (pixel >> 8) & 0xff;
					blueAmount = (pixel >> 0) & 0xff;
					totalRed += redAmount;
					totalGreen += greenAmount;
					totalBlue += blueAmount;
					count++;

					// Only Gets pixel if it exists and performs same action above.
					// The if statements are to get the corner cases out of the way
					if(y != 0){
						int north = i.getRGB(x, y - 1);
						redAmount = (north >> 16) & 0xff;
						greenAmount = (north >> 8) & 0xff;
						blueAmount = (north >> 0) & 0xff;
						totalRed += redAmount;
						totalGreen += greenAmount;
						totalBlue += blueAmount;
						count++;

					}

					// Don't be afraid of the if statements they are used just so that all pixel information is read
					if(y != 0 && x != i.getWidth()- 1){
						int northEast = i.getRGB(x + 1, y - 1);
						redAmount = (northEast >> 16) & 0xff;
						greenAmount = (northEast >> 8) & 0xff;
						blueAmount = (northEast >> 0) & 0xff;
						totalRed += redAmount;
						totalGreen += greenAmount;
						totalBlue += blueAmount;
						count++;
					}
					if(x != i.getWidth() - 1){
						int east = i.getRGB(x + 1, y);
						redAmount = (east >> 16) & 0xff;
						greenAmount = (east >> 8) & 0xff;
						blueAmount = (east >> 0) & 0xff;
						totalRed += redAmount;
						totalGreen += greenAmount;
						totalBlue += blueAmount;
						count++;
					}
					if(!(x == i.getWidth() - 1 || y == i.getHeight() - 1)){	
						int southEast = i.getRGB(x + 1, y + 1);
						redAmount = (southEast >> 16) & 0xff;
						greenAmount = (southEast >> 8) & 0xff;
						blueAmount = (southEast >> 0) & 0xff;
						totalRed += redAmount;
						totalGreen += greenAmount;
						totalBlue += blueAmount;
						count++;
					}
					if(y != i.getHeight() - 1){
						int south = i.getRGB(x, y + 1);
						redAmount = (south >> 16) & 0xff;
						greenAmount = (south >> 8) & 0xff;
						blueAmount = (south >> 0) & 0xff;
						totalRed += redAmount;
						totalGreen += greenAmount;
						totalBlue += blueAmount;
						count++;
					}
					if(!(y == i.getHeight() - 1 || x == 0)){
						int southWest = i.getRGB(x - 1, y + 1);
						redAmount = (southWest >> 16) & 0xff;
						greenAmount = (southWest >> 8) & 0xff;
						blueAmount = (southWest >> 0) & 0xff;
						totalRed += redAmount;
						totalGreen += greenAmount;
						totalBlue += blueAmount;
						count++;

					}
					if(x != 0){
						int west = i.getRGB(x - 1, y);
						redAmount = (west >> 16) & 0xff;
						greenAmount = (west >> 8) & 0xff;
						blueAmount = (west >> 0) & 0xff;
						totalRed += redAmount;
						totalGreen += greenAmount;
						totalBlue += blueAmount;
						count++;
					}

					if(!(x == 0 || y == 0)){
						int northWest = i.getRGB(x - 1, y - 1);
						redAmount = (northWest >> 16) & 0xff;
						greenAmount = (northWest >> 8) & 0xff;
						blueAmount = (northWest >> 0) & 0xff;
						totalRed += redAmount;
						totalGreen += greenAmount;
						totalBlue += blueAmount;
						count++;
					}

					// Take total red green blue amount and seperate them
					redAmount = totalRed / count;
					greenAmount = totalGreen / count;
					blueAmount = totalBlue / count;


					// Compose the new pixel based on swap to create the Christmas swap
					int newPixel = (redAmount << 16 ) | (greenAmount << 8) | blueAmount;

					// Set the pixel of the new image to pixelate the image
					result.setRGB(x, y, newPixel);
					if(y != 0){
						result.setRGB(x, y - 1, newPixel);
					}
					if(y != 0 && x != i.getWidth()- 1){
						result.setRGB(x + 1, y - 1, newPixel);
					}
					if(x != i.getWidth() - 1){
						result.setRGB(x + 1, y, newPixel);
					}
					if(!(x == i.getWidth() - 1 || y == i.getHeight() - 1)){	
						result.setRGB(x + 1, y + 1, newPixel);
					}
					if(y != i.getHeight() - 1){						
						result.setRGB(x, y + 1, newPixel);
					}
					if(!(y == i.getHeight() - 1 || x == 0)){

						result.setRGB(x - 1, y + 1, newPixel);
					}
					if(x != 0){
						result.setRGB(x - 1, y, newPixel);
					}

					if(!(x == 0 || y == 0)){
						result.setRGB(x - 1, y - 1, newPixel);
					}
				}
				else{
					result.setRGB(x, y, pixel); // Does pixel normal if not between values
				}

			}

		System.out.println("complete");
		return result;
	}

	@Override
	public void setX(int x) {
		// TODO Auto-generated method stub

	}
}




