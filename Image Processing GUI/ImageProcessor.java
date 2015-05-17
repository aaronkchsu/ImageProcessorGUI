package imageprocessorseam;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * The full frame where it selects and uses different filters for images
 * @author Aaron KC Hsu
 * 
 */
public class ImageProcessor extends JFrame implements ActionListener, ChangeListener{

	// Menu Items for first item
	private JMenuItem open;
	private JMenuItem save;
	private JMenuItem undo;
	private ArrayList<ImagePanelFilter> undoList;

	private Region2d region; // Keeps track of region set

	// Menu bar
	private FilterMenu filterMenu;
	private JMenu fileMenu;
	private JMenuBar menuBar;

	// Set up for tabs
	private	JTabbedPane tabbys;
	private	ImagePanel 	original;
	private	ImagePanelFilter filterPane;

	// Adjustment Knobs
	private JPanel sliderPane;
	private BiasSlider filterKnob;
	private GainSlider filterKnobTwo;
	private BlurSlider blurKnob;

	private StackFilterButton stackButton;
	private JButton filterButton; // Used to call a filter

	// Image to be used for stuff
	private BufferedImage openImage;
	private BufferedImage filteredImage;
	private ImageFilterTwo newFilter;

	private JLabel displayLabel;

	// Two Types of filters
	private ImageRegionFilter rf;
	private ImageFilterTwo f;

	/**
	 * Performs the full console of filtering an image
	 * @param args
	 */
	public static void main(String[] args) {

		// Provides the menu and the options for it
		ImageProcessor imageProcessor = new ImageProcessor();
		imageProcessor.setVisible(true);
	}

	/**
	 * Constructs the panel for all the buttons
	 */
	public ImageProcessor(){

		// This stylizes the look and feel
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			// If Nimbus is not available, you can set the GUI to another look and feel.
		}

		// A menu bar for the platform
		menuBar = new JMenuBar();
		menuBar.setToolTipText("A Bar to Hold Menus");

		// A menu tab for the platform
		fileMenu = new JMenu("File");
		fileMenu.setToolTipText("Hover Over all Menu Options for Quick Tips");

		region = new Region2d(); // Enable region for editing

		// Buttons for menu
		open = new JMenuItem("Open");
		open.addActionListener(this);
		fileMenu.add(open);
		open.setToolTipText("Opens Menu to select an image you want filtered.");

		// Buttons for menu
		save = new JMenuItem("Save");
		save.addActionListener(this); // Listener
		save.setEnabled(false); // False until filter image is open
		save.setToolTipText("Once you Filter an Image this option lets you save it");

		undo = new JMenuItem("Undo");
		undo.addActionListener(this); // Listener
		undo.setEnabled(false); // False until filter image is open
		undo.setToolTipText("This option lets you go to the previous filter");
		undoList = new ArrayList<ImagePanelFilter>();

		// Add components into fileMenu
		fileMenu.add(save);
		fileMenu.add(undo);

		filterMenu = new FilterMenu(this); // New FilterMenu item
		filterMenu.setEnabled(false);

		// Stack filter button
		stackButton = new StackFilterButton();
		stackButton.addActionListener(this);
		stackButton.setVisible(true);

		filterButton = new JButton("Filter");
		filterButton.setToolTipText("Button used to filter Image");
		filterButton.addActionListener(this);
		filterButton.setVisible(true);
		filterButton.setEnabled(false); // Set all the filterButtons

		// Combine menu options
		menuBar.add(fileMenu);
		menuBar.add(filterMenu);
		menuBar.add(filterButton);
		menuBar.add(stackButton); // Button Spot

		// Make a tab holder
		tabbys = new JTabbedPane();

		// Slider that is used to indicate the settings
		filterKnob = new BiasSlider();
		filterKnob.addChangeListener(this);

		filterKnobTwo = new GainSlider(); // Second slider
		filterKnobTwo.addChangeListener(this); // Listens

		sliderPane = new JPanel(); // New Panel

		// Add all the sliders to panel
		sliderPane.setLayout(new FlowLayout());
		sliderPane.add(filterKnob);
		sliderPane.add(Box.createHorizontalStrut(50));
		sliderPane.add(filterKnobTwo);
		sliderPane.setVisible(false);

		// A separate slider for blurring
		blurKnob = new BlurSlider();
		blurKnob.addChangeListener(this); // Listens

		// Create a label for displaying messages to the user.
		displayLabel = new JLabel(">>>>!!!!== Welcome to Filter Mania ==!!!!<<<");
		displayLabel.setToolTipText("EasterEgg Text: What is a man to do when he can't find his shoe?");

		// The menuBar is set as the bar for this panel
		setJMenuBar(menuBar);

		// Add the components to the main panel
		setLayout(new BorderLayout());
		add(displayLabel, BorderLayout.NORTH);
		add(tabbys, BorderLayout.CENTER);
		add(sliderPane, BorderLayout.SOUTH);
		add(blurKnob, BorderLayout.EAST);

		// Set up this frame.
		setTitle(">>WannaBe PhotoShop<<");
		setPreferredSize(new Dimension(900, 900));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(!(e.getSource() instanceof StackFilterButton)) {

			// Default False
			sliderPane.setVisible(false);
			blurKnob.setVisible(false);

			// Default False
			blurKnob.setEnabled(false);
			filterKnob.setEnabled(false);
			filterKnobTwo.setEnabled(false);

			// Deciphers if action is a menuItem
			if(e.getSource() instanceof JMenuItem) {

				// Takes source puts it in item and gets string for comparison
				JMenuItem item = (JMenuItem)e.getSource();
				String menuText = item.getText();

				// Occurs when Open menuButton has been pressed
				if(menuText.equals("Open")) {
					// Make sure only selected extensions are available
					FileFilter filter = new FileNameExtensionFilter("JPG File", "jpg", "jpeg", "png, bmp", "gif");

					// Opens GUI for user to select file
					JFileChooser selector = new JFileChooser();
					selector.setAcceptAllFileFilterUsed(false);
					selector.addChoosableFileFilter(filter);

					// Sees if file is appropriate
					int display = selector.showOpenDialog(null);
					selector.setAcceptAllFileFilterUsed(false);
					selector.addChoosableFileFilter(filter);

					// Read the image file into a BufferedImage object.
					openImage = null;

					// If the user selected a file ...
					if(display == JFileChooser.APPROVE_OPTION) {

						// Removes tab
						tabbys.remove(original);

						// Make a scanner from the file the user chose.
						File picture = selector.getSelectedFile();

						try {
							openImage = ImageIO.read(picture); // Tries to open Image
						}
						catch(Exception e1) {
							JOptionPane.showMessageDialog(null, "Image cannot be read or opened");
							System.out.println(e1);
						}

						// If the openImage is not null set filter buttons to true if not make false
						if(openImage != null){

							// Stores the open Image in filter spot
							filteredImage = openImage; // Also prepares later filtering

							// Set fileMenu to true
							filterMenu.setEnabled(true);
							

							// Opens tab with Open Image
							original = new ImagePanel(openImage);
							tabbys.add("Original Image", original);

							original.makeFullRegion(); // Gets selected region
							region = original.getSelectedRegion();

						}
						// Make sure filters are off
						else {
							// Remove originalImage if it is not there
							tabbys.remove(original);
							filterMenu.setEnabled(false);
							filterButton.setEnabled(false);
						}
					}

					//If the file is not appropriate or it is closed
					else {
						// Information for the user.. hide filterMenu
						tabbys.remove(original);
						tabbys.remove(filterPane);
						original = null; // Set to null
						JOptionPane.showMessageDialog(null, "Image was not read or opened. Please Try Again!");
					}
					// If there is a filter tab open when original is open then delete filter tab
					if(tabbys.getTabCount() == 2){
						tabbys.remove(filterPane);
						f = null; // set no filter on
						save.setEnabled(false); // Makes sure you cant save anymore
					}
				}
				// Occurs when Save menuButton has been pressed
				else if(menuText.equals("Save")) {
					// Make sure only selected extensions are available
					FileFilter filter = new FileNameExtensionFilter("JPG File", "jpg", "jpeg", "png, bmp", "gif");

					// Opens GUI for user to select file
					JFileChooser selector = new JFileChooser();
					selector.setAcceptAllFileFilterUsed(false);
					selector.addChoosableFileFilter(filter);

					// Opens up a save dialog and if it is good then try to write an image to the location
					int saveValue = selector.showSaveDialog(null);
					if (saveValue == JFileChooser.APPROVE_OPTION) {
						// Creates a file for support
						File src = new File(selector.getSelectedFile().getAbsolutePath());
						// Takes filter so that files saved is jpg
						if(!selector.getFileFilter().accept(src)){
							// Creates a new file with .jpg appended on to it
							src = new File(src.getAbsolutePath() + ".jpg");
							// Try to write image if it doesn't work then display JOptionPane
							try {
								// Writes filtered image to selected path under jpgs only 
								ImageIO.write(filteredImage, "jpg", src);
							} catch (IOException e4) {
								JOptionPane.showMessageDialog(null, "Image cannot be saved. Exiting Program..");
								e4.printStackTrace(); 
								System.exit(0);
							}
						}
						else{
							try {
								// Writes filtered image to selected path under jpgs only 
								ImageIO.write(filteredImage, "jpg", new File(selector.getSelectedFile().getAbsolutePath()));
							} catch (IOException e3) {
								// Informs the user the mishap
								JOptionPane.showMessageDialog(null, "Image cannot be saved. Exiting Program..");
								e3.printStackTrace(); 
								System.exit(0);
							}
						}
					}
					// If not good display message and exit
					else {
						JOptionPane.showMessageDialog(null, "Exiting Program..");
						System.exit(0);
					}
				}
				// Occurs when Undo menuButton has been pressed
				else if(menuText.equals("Undo")){
					int count = undoList.size() - 1;

					filterPane = (ImagePanelFilter) undoList.get(count); // get the panel at that instance

					tabbys.removeTabAt(1); // Set up new tab
					tabbys.add(filterPane, "Filtered Image");
					tabbys.setSelectedIndex(1);

					undoList.remove(count); // Removes the recent filterpane

					if(undoList.size() == 0){
						undo.setEnabled(false);
					}
					else{
						undo.setEnabled(true);
					}
				}
				else{
					filterButton.setEnabled(true);
				}
			}
			// This occors when the filter button has been pressed
			if(e.getSource() instanceof JButton){
				f = filterMenu.getFilter();
				
				// Only occurs if a filter is selected
				if(f != null) {
					String menuText = filterMenu.getMenuText(); // MenuText
					if(menuText.equals("Gain")) { 					
						sliderPane.setVisible(true); // Sets panel to true
						filterKnobTwo.setEnabled(true);
					}
					if(menuText.equals("Bias")) {
						sliderPane.setVisible(true); // Sets panel to true so user can see
						filterKnob.setEnabled(true);
					}
					if(menuText.equals("Blur")) { // Blurs panel		
						blurKnob.setVisible(true);
						blurKnob.setEnabled(true); // So we can see the blur factors
					}
					f = filterMenu.getFilter();
					commandFilter(); // Applies or sets filter
					// Turn of save
					save.setEnabled(true);
				}
			}
		}
		// If f returns null
		if(f == null) {
			// remove tab
			tabbys.remove(filterPane);
			// Turn off saving
			save.setEnabled(false);
		}
		// so tab size does not exceed limit
		if(tabbys.getTabCount() > 2) {
			// remove tab at one
			tabbys.removeTabAt(1);
		}
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		// Object used to see if source is a object of importance
		Object source = e.getSource();
		// If its a slider... then..
		if (source instanceof JSlider) {
			// Gets source code and takes in name
			JSlider slider = (JSlider)source;
			String name = slider.getName();
			if(!slider.getValueIsAdjusting()) {
				if ("gain".equals(name)){
					f = new GainFilter();
					f.setX(slider.getValue());
					commandFilter(); // Filterrize
				}
				// if it is bias
				else if ("bias".equals(name)){
					// Make a bias filter at the x place
					f = new BiasFilter();
					//sets x value based on slider
					f.setX(slider.getValue());
					commandFilter(); // Filterrize
				}
				// if it is a blur
				else if ("blur".equals(name)){
					// Makes new blur filter
					f = new BlurFilterTwo();
					//sets x value based on slider
					f.setX(slider.getValue());
					commandFilter(); // Filterrize
				}
				// If tabbys become to great bomb one
				if(tabbys.getTabCount() > 2){
					// remove tab at one
					tabbys.removeTabAt(1);
				}
			}
		}
	}	
	
	/**
	 * Method differentiates between applying filters or setting them
	 */
	private void commandFilter() {
		// If stack button is pressed
		if(stackButton.isPressed()){
			applyFilter(); // Applies filter
		}
		else {
			setFilter(); // Replaces Filter
		}
		undoList.add(filterPane);
		undo.setEnabled(true); // enable button
	}

	/**
	 * This method will be called for when a new filter is called and a new panel is necessary
	 * Filter will not stack
	 */
	private void setFilter() {

		if(f instanceof ImageRegionFilter){

			// Make the options filter become the new filter f
			rf = (ImageRegionFilter) f;

			region = original.getSelectedRegion(); // Get region that is selected

			rf.setRegion(region); // Set filter to region

			// Apply filter to original image
			filteredImage = rf.filter(openImage);

			// Open tab with filtered Image
			filterPane = new ImagePanelFilter(filteredImage);
			tabbys.add(filterPane, "Filtered Image");

			tabbys.setSelectedIndex(1); // shows filter tab
		}
		else{

			// Make the options filter become the new filter f
			newFilter = f;

			// Apply filter to original image
			filteredImage = newFilter.filter(openImage);

			// Open tab with filtered Image
			filterPane = new ImagePanelFilter(filteredImage);
			tabbys.add(filterPane, "Filtered Image");

			tabbys.setSelectedIndex(1); // shows filter tab
		}
	}

	/**
	 * This will be called when user chooses to stack the filter
	 * Will be called when stack filter is on
	 */
	private void applyFilter() {

		if(f instanceof ImageRegionFilter){

			// Make the options filter become the new filter f
			rf = (ImageRegionFilter) f;

			region = original.getSelectedRegion(); // Get region that is selected

			rf.setRegion(region); // Set filter to region

			// Apply filter to original image
			filteredImage = rf.filter(filteredImage);

			// Open tab with filtered Image
			filterPane = new ImagePanelFilter(filteredImage);
			tabbys.add(filterPane, "Filtered Image");

			tabbys.setSelectedIndex(1); // shows filter tab

		}
		else {

			// Make the options filter become the new filter f
			newFilter = f;

			// Apply filter to original image
			filteredImage = newFilter.filter(filteredImage);

			// Open tab with filtered Image
			filterPane = new ImagePanelFilter(filteredImage);
			tabbys.add(filterPane, "Filtered Image");

			tabbys.setSelectedIndex(1); // shows filter tab

		}
	}
}


