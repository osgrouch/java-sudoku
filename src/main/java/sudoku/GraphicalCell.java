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
	 * Create a new instance to "link" the given Cell and Group.
	 */
	public GraphicalCell (Cell cell, Group group) {
		this.cell = cell;
		this.group = group;
	}

	/**
	 * Update the contents of the Group fwith this Cell's contents.
	 * This method will only be called after removing or setting this Cell's number.
	 * And so the method checks if the number is 0 and hides the label and show annotations.
	 * Else it does the opposite: shows the label with the number and erases and hides annotations.
	 */
	public void updateGroupContents () {
		GridPane gridPane = ( (GridPane) group.getChildren().get(0) );
		Label label = ( (Label) group.getChildren().get(1) );
		int number = cell.getNumber();
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
			if (cell.isGivenNumber()) {
				label.setTextFill(Color.valueOf("#C33C54"));
				label.setStyle("-fx-font-weight: bold");
			}

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
}
