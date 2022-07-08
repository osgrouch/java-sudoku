package sudoku.puzzle;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 * Class representing a cell in a Sudoku puzzle.
 * Keeps track of the number this Cell has been marked as.
 * Also tracks what region this Cell belongs to within the puzzle.
 */
public class Cell {
	/** The region within the Sudoku puzzle this Cell belongs to */
	private final int region;

	/** The number of this Cell in the Sudoku puzzle */
	private int number;

	/**
	 * The Group of JavaFX nodes that are used to display information about this Cell.
	 * The Group will always contain two children, a Label and a GridPane of buttons, representing annotations.
	 * When this Cell's number is updated, the Label displays the updated number.
	 */
	private Group group;

	/**
	 * Create a new Cell instance and initialize its private fields to a blank state.
	 */
	public Cell (int region, int number) {
		this.region = region;
		this.number = number;
	}

	/**
	 * Update the contents of this Cell's Group to be displayed on the GUI.
	 * This method will only be called after removing or setting this Cell's number.
	 * And so the method checks if the number is 0 and hides the label and show annotations.
	 * Else it does the opposite: shows the label with the number and erases and hides annotations.
	 */
	public void updateGroupContents () {
		GridPane gridPane = ( (GridPane) group.getChildren().get(0) );
		Label label = ( (Label) group.getChildren().get(1) );
		if (number == 0) {
			// remove the number from this Cell's Label
			label.setText("");

			label.setDisable(true);
			gridPane.setDisable(false);
		} else {
			// erase annotations previously made on this Cell
			for (Node annotationBtn : gridPane.getChildren()) {
				annotationBtn.setOpacity(0.0);
			}
			// display the number set for this Cell
			label.setText(String.valueOf(number));

			label.setDisable(false);
			gridPane.setDisable(true);
		}
	}

	/**
	 * Reset this Cell's number back to 0, indicating the number is "erased."
	 * Then call method to update this Cell's Group for graphical display.
	 */
	public void removeNumber () {
		this.number = 0;
		updateGroupContents();
	}

	/**
	 * Set this Cell's number, then call method to update this Cell's Group for graphical display.
	 *
	 * @param number number to set in this Cell
	 */
	public void setNumber (int number) {
		this.number = number;
		updateGroupContents();
	}

	/**
	 * Set this Cell's Group of JavaFX nodes. Used to update the number of this Cell.
	 *
	 * @param group JavaFX Group node
	 */
	public void setGroup (Group group) {
		this.group = group;
	}

	/**
	 * @return the region this Cell belongs to
	 */
	public int getRegion () {
		return region;
	}

	/**
	 * @return the number within this Cell
	 */
	public int getNumber () {
		return number;
	}

	/**
	 * Create a String representation of this Cell using its number.
	 *
	 * @return String with this Cell's number
	 */
	@Override
	public String toString () {
		return "#" + number + ", Region: " + region;
	}
}
