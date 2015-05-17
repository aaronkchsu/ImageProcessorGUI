package imageprocessorseam;

import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;

public class SeamCarvingFilter extends ImageRegionFilter {

	/**
	 * Produces a Seam Fitler
	 * =======================================================================
	 * TO USE THIS.. You must first play the image processor class open and image and then choose the filter 
	 * that saids seamCarver.. then it will tell you the size of the images.. input the sizes as the window pops up
	 * be PATIENT the code is slow.... If you enter a size bigger then the picture it will not change the length at all!
	 */
	public BufferedImage filter(BufferedImage oldImage) {
		BufferedImage newImage = oldImage;
		JOptionPane.showMessageDialog(null, "Height " + newImage.getHeight() + " " + "Width " + newImage.getWidth());
		int newHeight = Integer.parseInt(JOptionPane.showInputDialog(null, "Please Enter the new Height: \n #Note that if size is bigger then image the size will not change"));
		int newWidth = Integer.parseInt(JOptionPane.showInputDialog(null, "Please Enter the new Width: \n #Note that if size is bigger then image the size will not change"));
		int totalHeight = oldImage.getHeight() - newHeight;
		int totalWidth = oldImage.getWidth() - newWidth; //Obtain new height and width values
		for(int i = 0; i < totalHeight; i++) {
			newImage = seamCarvingHorizontal(newImage);
		}
		newImage = clockwise(newImage);
		for(int j = 0; j < totalWidth; j++){
			newImage = seamCarvingHorizontal(newImage);
		}
		newImage = counterClockwise(newImage);
		return newImage;
	}

	public BufferedImage seamCarvingHorizontal(BufferedImage i){
		BufferedImage result = new BufferedImage(i.getWidth(), i.getHeight() - 1, BufferedImage.TYPE_INT_RGB);

		int seamCostMatrix[][] = new int[result.getHeight()][result.getWidth()];

		// For each pixel in the image . . . 
		for(int y = 0; y < result.getHeight(); y++)
			for(int x = 0; x < result.getWidth(); x++) {
				int pixel = i.getRGB(x, y);
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
					int north = result.getRGB(x, y - 1);
					redAmount = (north >> 16) & 0xff;
					greenAmount = (north >> 8) & 0xff; // Separate the RGB values and then add the average to the value
					blueAmount = (north >> 0) & 0xff;
					northAvg = (redAmount + greenAmount + blueAmount) / 3;						
				}
				if(x != result.getWidth() - 1){
					int east = i.getRGB(x + 1, y);
					redAmount = (east >> 16) & 0xff;
					greenAmount = (east >> 8) & 0xff;
					blueAmount = (east >> 0) & 0xff;
					eastAvg = (redAmount + greenAmount + blueAmount) / 3;		
				}
				if(y != result.getHeight() - 1){
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
				int seamCost = Math.abs(eastAvg-westAvg) + Math.abs(southAvg-northAvg);
				seamCostMatrix[y][x] = seamCost;
			}
		// An Array that keep track of total seamCosts
		int totalSeamCost[][] = new int[result.getHeight()][result.getWidth()];
		for(int j = 0 ; j < result.getHeight(); j++) // First row is just the normal seam cost
			totalSeamCost[j][0] = seamCostMatrix[j][0];
		// Total seam cost for the rest of the pixels
		for(int h = 1; h < i.getWidth(); h++) {
			for(int e = 0; e < result.getHeight(); e++) {
				if(e != 0 && e != (result.getHeight() - 1)) { // Taking care of finding total seam cost at point with special cases
					totalSeamCost[e][h] = seamCostMatrix[e][h] + Math.min(seamCostMatrix[e - 1][h - 1], 
							Math.min(seamCostMatrix[e + 1][h - 1], seamCostMatrix[e][h - 1]));
				}
				else if(e == 0){ 
					totalSeamCost[e][h] = seamCostMatrix[e][h] + Math.min(seamCostMatrix[e + 1][h - 1], 
							seamCostMatrix[e][h - 1]);
				}
				else{
					totalSeamCost[e][h] = seamCostMatrix[e][h] + Math.min(seamCostMatrix[e - 1][h - 1], 
							seamCostMatrix[e][h - 1]);
				}
			}
		}
		int path[] = new int[i.getWidth()];
		int row = 0; // Our goal is to find the path index
		int minimum = totalSeamCost[0][i.getWidth() - 1];
		int minimumIndex = 0;
		for(int j = 1; j < result.getHeight() - 3; j++){
			int current = totalSeamCost[j][i.getWidth()  - 1];
			int currentIndex = j;
			if(minimum > current) {
				minimum = current;
				minimumIndex = currentIndex;
			}
		}
		row = minimumIndex;
		for(int j = i.getWidth() - 1; j >= 0; j--){
			int center = row;
			if(row > 0 && totalSeamCost[row - 1][j] < totalSeamCost[row][j]){
				center = row - 1;
			}
			if(row < result.getHeight() - 1 && totalSeamCost[row + 1][j] < totalSeamCost[center][j]) {
				center = row + 1;
			}
			row = center; // This is required so that the next iteration we have a place to start at
			path[j] = row;
		}
		// Loop to remove row of pixels
		for (int x = 0; x < result.getWidth(); x++){
			for (int y = 0; y < path[x]; y++){
				result.setRGB(x, y, i.getRGB(x, y));
			}
			for (int y = path[x]; y < result.getHeight(); y++){
				result.setRGB(x, y, i.getRGB(x, y + 1));
			}
		}
		return result;  
	}
	public BufferedImage clockwise(BufferedImage i) {
		BufferedImage result = new BufferedImage(i.getHeight(), i.getWidth(), BufferedImage.TYPE_INT_RGB);
		// For each pixel in the image . . . 
		for(int y = 0; y < i.getHeight(); y++)
			for(int x = 0; x < i.getWidth(); x++) {
				// Takes original pixel
				int pixel = i.getRGB(x, y);
				// Sets pixel at a new rotated spot
				result.setRGB(i.getHeight() - y - 1, x, pixel);
			}
		return result;
	}

	public BufferedImage counterClockwise(BufferedImage i) {
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
