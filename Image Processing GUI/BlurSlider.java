package imageprocessorseam;

import javax.swing.JSlider;



public class BlurSlider extends JSlider {
	
	/**
	 * New Slider Based on these properties
	 */
	public BlurSlider(){
		super(JSlider.VERTICAL, 0, 15, 5);
		setMajorTickSpacing(5); // Slider adjustments settings
		setMinorTickSpacing(1);
		setPaintTicks(true);
		setPaintLabels(true);
		setEnabled(false);
		setVisible(false); // Visible and enable false until filter activated
		setName("blur"); // Will be used to differentiate action
		setSnapToTicks(true);
		setToolTipText("Use the Knob to adjust the amount of blur of the FilteredImage");
	}
	
}
