package imageprocessorseam;

import javax.swing.JSlider;

public class BiasSlider extends JSlider {

	public BiasSlider(){
		
		// Slider that is used to indicate the settings
		super(JSlider.HORIZONTAL,-100, 100, 0);

		setMajorTickSpacing(25); // Slider adjustments settings
		setMinorTickSpacing(5);
		setPaintTicks(true);
		setPaintLabels(true); // Make sure ticks and labels are visible
		setEnabled(false);
		setName("bias"); // Will be used to differentiate action
		setToolTipText("Move the knob to change the brightness of the Filtered Image");
		
	}

}
