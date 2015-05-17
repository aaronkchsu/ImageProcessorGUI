package imageprocessorseam;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 * A Menu full of filter items
 * @author Aaron Kc Hsu
 *
 */
public class FilterMenu extends JMenu implements ActionListener {

	private ImageFilterTwo f;
	private ImageRegionFilter rf; // Two types of region filters

	private String text;

	// Filters.. lots of them
	private JMenuItem redGreenSwap;
	private JMenuItem redBlueSwap;
	private JMenuItem greenBlueSwap;
	private JMenuItem blackWhite;
	private JMenuItem rotateClockwise;
	private JMenuItem rotateCounter;
	private JMenuItem gain;
	private JMenuItem bias;
	private JMenuItem blur;
	private JMenuItem epic;
	private JMenuItem custom;
	private JMenuItem crop;
	private JMenuItem edgeFilter;
	private JMenuItem seamCarving;

	public FilterMenu(ActionListener listener) {

		// Names it Filters
		super("Filters");
		setToolTipText("Apply a filter to your image");

		// Filter menu items
		redGreenSwap = new JMenuItem("Red Green Swap");
		redGreenSwap.addActionListener(this);
		redGreenSwap.addActionListener(listener);
		add(redGreenSwap);
		redGreenSwap.setToolTipText("Filter swaps red and green colors of image");

		redBlueSwap = new JMenuItem("Blue Red Swap"); // Filter
		redBlueSwap.addActionListener(this);
		redGreenSwap.addActionListener(listener);
		add(redBlueSwap);
		redBlueSwap.setToolTipText("Filter swaps blue and red colors of image");

		greenBlueSwap = new JMenuItem("Green Blue Swap"); // Filter
		greenBlueSwap.addActionListener(this);
		add(greenBlueSwap);
		greenBlueSwap.setToolTipText("Filter swaps gren and blue colors in image");
		greenBlueSwap.addActionListener(listener);

		blackWhite = new JMenuItem("GrayScale Filter"); // Filter
		blackWhite.addActionListener(this);
		add(blackWhite);
		blackWhite.setToolTipText("Filter makes image black and white");
		blackWhite.setForeground(Color.BLUE);
		blackWhite.addActionListener(listener);

		rotateCounter = new JMenuItem("Rotate CounterClockwise"); // Filter
		rotateCounter.addActionListener(this);
		add(rotateCounter);
		rotateCounter.setToolTipText("Rotates image counterclockwise");
		rotateCounter.addActionListener(listener);

		rotateClockwise = new JMenuItem("Rotate Clockwise"); // Filter
		rotateClockwise.addActionListener(this);
		add(rotateClockwise);
		rotateClockwise.setToolTipText("Rotates image clockwise");
		rotateClockwise.addActionListener(listener);

		gain = new JMenuItem("Gain"); // Filter
		gain.addActionListener(this);
		add(gain);
		gain.setToolTipText("Filter changes contrast of image");
		gain.addActionListener(listener);

		bias = new JMenuItem("Bias"); // Filter
		bias.addActionListener(this);
		add(bias);
		bias.setToolTipText("Filter changes brightness of image");
		bias.addActionListener(listener);

		blur = new JMenuItem("Blur"); // Filter
		blur.addActionListener(this);
		blur.addActionListener(listener);
		add(blur);
		blur.setToolTipText("Filter blurs image");

		epic = new JMenuItem("Epic"); // Filter
		epic.addActionListener(this);
		epic.addActionListener(listener);
		add(epic);
		epic.setToolTipText("Filter makes your image into an epic fail");

		custom = new JMenuItem("Fun Fun"); // Filter
		custom.addActionListener(this);
		custom.addActionListener(listener);
		add(custom);
		custom.setToolTipText("Custom filter averages colors into something interesting");

		crop = new JMenuItem("Crop");
		crop.addActionListener(this);
		crop.addActionListener(listener);
		add(crop);
		crop.setToolTipText("Select a region to apply: Sizes image to specified area.");

		edgeFilter = new JMenuItem("Edge Filter");
		edgeFilter.addActionListener(this);
		edgeFilter.addActionListener(listener);
		add(edgeFilter);
		edgeFilter.setToolTipText("Finds the Edge of the pixels");
		edgeFilter.setForeground(Color.BLUE);
		
		seamCarving = new JMenuItem("Seam Carving Filter");
		seamCarving.addActionListener(this);
		seamCarving.addActionListener(listener);
		add(seamCarving);
		seamCarving.setToolTipText("Carves the pixel semns");
		seamCarving.setForeground(Color.BLUE);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() instanceof JButton) { // This little piece of code is to keep track of the button that filters
			JButton filterButton = (JButton) e.getSource(); // Making sure the filter button is only enabled when there
			
				filterButton.setEnabled(false);
		}
		else{
			// Takes source puts it in item and gets string for comparison
			JMenuItem item = (JMenuItem)e.getSource();
			String menuText = item.getText();

			// Changes Filter according to button
			if(menuText.equals("Red Green Swap")) 
				changeFilter(new RedGreenSwapFilter());
			else if(menuText.equals("Blue Red Swap"))
				changeFilter(new RedBlueSwapFilter());
			else if(menuText.equals("Green Blue Swap"))  					
				changeFilter(new GreenBlueSwap());
			else if(menuText.equals("GrayScale Filter"))
				changeFilter(new GrayScale());
			else if(menuText.equals("Rotate Clockwise"))
				changeFilter(new ClockwiseFilter());
			else if(menuText.equals("Rotate CounterClockwise"))
				changeFilter(new CounterClockFilter());
			else if(menuText.equals("Gain"))  	
				changeFilter(new GainFilter());
			else if(menuText.equals("Bias"))
				changeFilter(new BiasFilter());
			else if(menuText.equals("Blur")) // Blurs panel	
				changeFilter(new BlurFilterTwo());	
			else if(menuText.equals("Epic"))
				changeFilter(new EdgeFilter());
			else if(menuText.equals("Fun Fun"))
				changeFilter(new CustomFilter());
			else if(menuText.equals("Crop"))
				changeFilter(new Crop());
			else if(menuText.equals("Edge Filter"))
				changeFilter(new EdgeFilter());
			else if(menuText.equals("Seam Carving Filter"))
				changeFilter(new SeamCarvingFilter());
			text = menuText; // Changes text
		}
	}

	public void changeRegionFilter(ImageRegionFilter rf_){
		rf = rf_;
	} 

	public ImageRegionFilter getRegionFilter(){
		return rf; // Getter
	}

	public void changeFilter(ImageFilterTwo f_){
		f = f_; // A new nf
	}

	public ImageFilterTwo getFilter(){
		return f; //Getter
	}

	public String getMenuText(){
		return text;
	}

}
