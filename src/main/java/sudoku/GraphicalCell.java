package sudoku;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import sudoku.puzzle.Cell;

public class GraphicalCell {
	/** The Cell this instance represents */
	private Cell cell;
	/** The Group this instance is responsible for updating */
	private Group group;
	/**
	 * The Label originally within the Group, used to remove and reinsert to the Group to prevent resizing bug
	 * that occurs when setting label text to "" when removing number from Cell
	 */
	private Label label;

	/**
	 * Create a new instance to "link" the given Cell and Group.
	 */
	public GraphicalCell (Cell cell, Group group) {
		this.cell = cell;
		this.group = group;
		this.label = (Label) group.getChildren().remove(1);
	}

	/**
	 * Update the contents of the Group fwith this Cell's contents.
	 * This method will only be called after removing or setting this Cell's number.
	 * And so the method checks if the number is 0 and hides the label and show annotations.
	 * Else it does the opposite: shows the label with the number and erases and hides annotations.
	 */
	public void updateGroupContents () {
		GridPane gridPane = ( (GridPane) group.getChildren().get(0) );
		int number = cell.getNumber();
		if (number == 0) {
			try {
				// remove the number from this Cell's Label
				this.label = (Label) group.getChildren().remove(1);
			} catch (Exception e) {
				// catch cases when first updating group contents and label was removed in constructor,
				// and so it would throw an exception when trying to remove it again in the try block
			}
			label.setDisable(true);
			gridPane.setDisable(false);
		} else {
			// erase annotations previously made on this Cell
			for (Node annotationBtn : gridPane.getChildren()) {
				annotationBtn.setOpacity(0.0);
			}
			// display the number set for this Cell
			this.label.setText(String.valueOf(number));
			if (cell.isGivenNumber()) {
				this.label.setTextFill(Color.valueOf("#C33C54"));
				this.label.setStyle("-fx-font-weight: bold");
			}

			group.getChildren().add(label);
			label.setDisable(false);
			gridPane.setDisable(true);
		}
	}

	/**
	 * Remove the number within the Cell and update the contents of the Group display,
	 * only if the cell is not a given number.
	 */
	public void removeNumber () {
		if (!cell.isGivenNumber()) {
			cell.removeNumber();
			updateGroupContents();
		}
	}

	/**
	 * Set the number within the Cell and update the contents of the Group display,
	 * only if the cell is not a given number.
	 *
	 * @param number new Cell number
	 */
	public void setNumber (int number) {
		if (!cell.isGivenNumber()) {
			cell.setNumber(number);
			updateGroupContents();
		}
	}

	/**
	 * @return this Cell's Label
	 */
	public Label getLabel () {
		return label;
	}
}
