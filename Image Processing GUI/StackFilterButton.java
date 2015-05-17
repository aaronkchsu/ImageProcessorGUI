package imageprocessorseam;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class StackFilterButton extends JButton implements ActionListener {

	// Used to see if it is on or off
	private boolean indicator;

	/**
	 * Constructs Button into Enable Filter Stacking
	 */
	public StackFilterButton() {
		super("Enable Stacking"); // Sets up text
		setVisible(true);
		setEnabled(true);
		setToolTipText("This button allows you to combine filters");
		// Start out as false
		indicator = false;
		addActionListener(this); //
	}

	/**
	 * If it pressed then return true
	 * @return the boolean indicator
	 */
	public boolean isPressed() {
		return indicator;
	}

	/**
	 * When pressed change indicator
	 */
	public void whenPressed(){
		// If manual is true send it to false else make it true
		if(indicator){
			indicator = false;
			setForeground(Color.BLACK);
		}
		else{
			indicator = true;
			// Background is yellow
			setForeground(Color.RED);
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		// Presses Button and performs actions
		whenPressed();
	}
	
	

}
