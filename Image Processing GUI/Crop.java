package imageprocessorseam;

import java.awt.image.BufferedImage;

public class Crop extends ImageRegionFilter {

	@Override
	public BufferedImage filter(BufferedImage i) {
		BufferedImage result = new BufferedImage(getMaxX() - getMinX() + 1, getMaxY() - getMinY() + 1, BufferedImage.TYPE_INT_RGB);

		int countY = 0;
		// For each pixel in the image . . . 
		for(int y = getMinY(); y < getMaxY(); y++){
			int countX = 0;
			for(int x = getMinX(); x < getMaxX(); x++) {

				// Set the pixel of the new image.
				result.setRGB(countX, countY, i.getRGB(x, y));
				countX++;
			}
			countY++;
		}
		return result;
	}

	@Override
	public void setX(int x) {
		// TODO Auto-generated method stub

	}

}


