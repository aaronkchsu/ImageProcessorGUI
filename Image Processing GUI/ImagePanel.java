package imageprocessorseam;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;


/** 
 * A JPanel for displaying a BufferedImage.
 * Also listens for mouse events to allow user to select a region of the image.
 *
 * @author Erin Parker
 */
public class ImagePanel extends JPanel implements MouseListener, MouseMotionListener {

	private BufferedImage image;
	private BufferedImage drawn;
	private int minX;
	private int minY;
	private int maxX;
	private int maxY;
	private int height;
	private int width;
	private boolean regionFull;

	public ImagePanel(BufferedImage img) {
		image = img;
		setPreferredSize(new Dimension(img.getHeight(), image.getWidth()));
		width = img.getWidth();
		height = img.getHeight(); // To keep track of Image size

		// Add listeners
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, this);
		g.setColor(new Color(105, 105, 105, 125));

		// Only paints rectangle if region is not full
		if(!regionFull){
			if(maxX - minX < 0 && maxY - minY < 0) {
				g.fillRect(maxX, maxY, minX - maxX, minY - maxY);
			}
			else if(maxX - minX < 0) {
				g.fillRect(maxX, minY, minX - maxX, maxY - minY);

			}
			else if(maxY - minY < 0) {
				g.fillRect(minX, maxY, maxX - minX, minY - maxY);
			}

			else {
				g.fillRect(minX, minY, maxX - minX, maxY - minY);
			}
		}
	}



	public void makeFullRegion(){
		minX = 0; // These are the default sizes
		minY = 0;
		maxX = width;
		maxY = height;
		regionFull = true;
	}

	public Region2d getSelectedRegion() {
		
		// Returns based on selection
		if(maxX - minX < 0 && maxY - minY < 0) {
			return new Region2d(maxX, minX, maxY, minY);
		}
		else if(maxX - minX < 0) {
			return new Region2d(maxX, minX, minY, maxY);

		}
		else if(maxY - minY < 0) {
			return new Region2d(minX, maxX, maxY, minY);
		}
		else {
			return new Region2d(minX, maxX, minY, maxY);
		}
	}

	public void mouseClicked(MouseEvent arg0) {
		makeFullRegion(); // Resizes region
	}

	public void mouseEntered(MouseEvent arg0) {
		// unused		
	}

	public void mouseExited(MouseEvent arg0) {
		// unused		
	}

	public void mousePressed(MouseEvent e) {
		minX = e.getX();
		minY = e.getY();
	}

	public void mouseReleased(MouseEvent arg0) {

	}

	public void mouseDragged(MouseEvent e) {
		maxX = e.getX();
		maxY = e.getY();
		// If sizes go out of boundaries
		if(maxX > image.getWidth())
			maxX = image.getWidth();
		if(maxY > image.getHeight())
			maxY = image.getHeight();
		if(maxX < 0)
			maxX = 0;
		if(maxY < 0)
			maxY = 0;
		regionFull = false;
		repaint();
	}


	public void mouseMoved(MouseEvent arg0) {
		// unused		
	}
}
