package imageprocessorseam;

import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.JSlider;



public class GainSlider extends JSlider {
	
	public GainSlider(){
		
		super(JSlider.HORIZONTAL, 0, 20, 10); // Take previous properties
		setPaintTicks(true);

		// A group of labels for the gain slider
		Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
		labelTable.put(0, new JLabel("0.1"));
		labelTable.put(5, new JLabel("0.5"));
		labelTable.put(10, new JLabel("1.0"));
		labelTable.put(15, new JLabel("1.5"));
		labelTable.put(20, new JLabel("2.0"));
		setLabelTable(labelTable);
	    setPaintLabels(true);
		setPaintTicks(true);
		setEnabled(false);
		setName("gain"); // Will be used to differentiate action
		setSnapToTicks(true);
		setToolTipText("Move the knob to change the contrast of the Filtered Image");
		
	}

}
