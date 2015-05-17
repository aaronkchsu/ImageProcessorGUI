package imageprocessorseam;

import java.awt.image.BufferedImage;

public class CounterClockFilter implements ImageFilterTwo {
	
	public BufferedImage filter(BufferedImage i) {

		BufferedImage result = new BufferedImage(i.getHeight(), i.getWidth(), BufferedImage.TYPE_INT_RGB);

		// For each pixel in the image . . . 
		for(int y = 0; y < i.getHeight(); y++)
			for(int x = 0; x < i.getWidth(); x++) {

				// Gets current pixel
				int pixel = i.getRGB(x, y);
				
				// Sets pixel at a new rotated spot
				result.setRGB(y, i.getWidth() - x - 1, pixel);
			}

		return result;
	}

	@Override
	public void setX(int x) {
		// TODO Auto-generated method stub
		
	}

}
