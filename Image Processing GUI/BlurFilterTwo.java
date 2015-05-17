package imageprocessorseam;

import java.awt.image.BufferedImage;

public class BlurFilterTwo extends ImageRegionFilter {

	private int blurSize;

	@Override
	public BufferedImage filter(BufferedImage i) {

		// Makes copy image size of original
		BufferedImage result = new BufferedImage(i.getWidth(), i.getHeight(), BufferedImage.TYPE_INT_RGB);

		// Variables reset to 0 after 
		int redAmount = 0;
		int greenAmount = 0;
		int blueAmount = 0;

		// For each pixel in the image . . . 
		for(int y = 0; y < i.getHeight(); y++)
			for(int x = 0; x < i.getWidth(); x++) {

				// Variables reset to 0 after each pixel
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

					// This loop gets the pixels row average
					for(int k = 1; k <= blurSize; k++) {

						// Get left pixel
						if(x > blurSize) {

							int left = i.getRGB(x - k, y);
							redAmount = (left >> 16) & 0xff;
							greenAmount = (left >> 8) & 0xff;
							blueAmount = (left >> 0) & 0xff;
							totalRed += redAmount;
							totalGreen += greenAmount;
							totalBlue += blueAmount;
							count++;

						}

						// Get right pixel
						if(x < i.getWidth() - blurSize) {
							int right = i.getRGB(x + k, y);
							redAmount = (right >> 16) & 0xff;
							greenAmount = (right >> 8) & 0xff;
							blueAmount = (right >> 0) & 0xff;
							totalRed += redAmount;
							totalGreen += greenAmount;
							totalBlue += blueAmount;
							count++;

						}

					}


					// This loop gets the right left pixels for each row
					for(int j = 1; j <= blurSize; j++) {

						// Get left pixel
						if(x > blurSize && y > blurSize) {

							int upperRowsLeft = i.getRGB(x - j, y - j);
							redAmount = (upperRowsLeft >> 16) & 0xff;
							greenAmount = (upperRowsLeft >> 8) & 0xff;
							blueAmount = (upperRowsLeft >> 0) & 0xff;
							totalRed += redAmount;
							totalGreen += greenAmount;
							totalBlue += blueAmount;
							count++;

						}

						// Gets left pixel of bottom row
						if(x > blurSize && y < i.getHeight() - blurSize) {

							int bottomRowsLeft = i.getRGB(x - j, y + j);
							redAmount = (bottomRowsLeft >> 16) & 0xff;
							greenAmount = (bottomRowsLeft >> 8) & 0xff;
							blueAmount = (bottomRowsLeft >> 0) & 0xff;
							totalRed += redAmount;
							totalGreen += greenAmount;
							totalBlue += blueAmount;
							count++;
						}


						// Get right pixel
						if(x < i.getWidth() - blurSize && y > blurSize) {

							int upperRowsRight = i.getRGB(x + j, y - j);
							redAmount = (upperRowsRight >> 16) & 0xff;
							greenAmount = (upperRowsRight >> 8) & 0xff;
							blueAmount = (upperRowsRight >> 0) & 0xff;
							totalRed += redAmount;
							totalGreen += greenAmount;
							totalBlue += blueAmount;
							count++;

						}


						// Get right pixel bottom row
						if(x < i.getWidth() - blurSize && y < i.getHeight() - blurSize) {

							int bottomRowsRight = i.getRGB(x + j, y + j);
							redAmount = (bottomRowsRight >> 16) & 0xff;
							greenAmount = (bottomRowsRight >> 8) & 0xff;
							blueAmount = (bottomRowsRight >> 0) & 0xff;
							totalRed += redAmount;
							totalGreen += greenAmount;
							totalBlue += blueAmount;
							count++;

						}

					}


					// Take total red green blue amount and separate them
					redAmount = totalRed / count;
					greenAmount = totalGreen / count;
					blueAmount = totalBlue / count;

					// Compose the new pixel based on swap to create the Christmas swap
					int newPixel = (redAmount << 16 ) | (greenAmount << 8) | blueAmount;

					// Set the pixel of the new image.
					result.setRGB(x, y, newPixel);

				}
				else{
					result.setRGB(x, y, pixel); // Does pixel normal if not between values
				}
			}

		// Returns final blurred image
		return result;
	}

	// Sets up blur ratio
	@Override
	public void setX(int x) {
		blurSize = x;
	}



}