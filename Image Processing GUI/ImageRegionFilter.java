package imageprocessorseam;

/**
 * An abstract class that ensures all classes representing an image filter
 * implement the filter method as specified in ImageFilter.
 * Also provides data and methods for a region selected from an image to 
 * be filtered.
 * 
 * @author Erin Parker
 */
public abstract class ImageRegionFilter implements ImageFilterTwo {

	private Region2d r;
	
	public ImageRegionFilter() {
		r = new Region2d();
	}
	
	public void setRegion(Region2d _r) {
		r = _r;
	}
			
	public void setRegion(int minX, int maxX, int minY, int maxY) {
		r.setPoints(minX, maxX, minY, maxY);
	}
	
	public int getMinX() {
		return r.getMinX();
	}
	
	public int getMaxX() {
		return r.getMaxX();
	}
	
	public int getMinY() {
		return r.getMinY();
	}
	
	public int getMaxY() {
		return r.getMaxY();
	}
}
