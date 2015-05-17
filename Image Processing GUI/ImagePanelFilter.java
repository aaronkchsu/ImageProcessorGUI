package imageprocessorseam;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/** 
 * A JPanel for displaying a BufferedImage.
 *
 * @author Aaron Kc Hsu
 */
public class ImagePanelFilter extends JPanel {
	
	private BufferedImage image;
	
	public ImagePanelFilter(BufferedImage img) {
		image = img;
		setPreferredSize(new Dimension(img.getHeight(), image.getWidth()));
	}
	
	// More on this later in the week.
	public void paintComponent(Graphics g) {
		g.drawImage(image, 0, 0, this);
	}
}
