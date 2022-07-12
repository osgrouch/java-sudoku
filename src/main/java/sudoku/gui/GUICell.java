package sudoku.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import sudoku.puzzle.SudokuCell;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Class to represent a SudokuCell graphically using JavaFX nodes.
 * Updates information in the SudokuCell based on user input and displays
 * that changes made to the SudokuCell to the user.
 */
public class GUICell {
	/** The GUIBoard that contains this GUICell, used to add previous states of this GUICell to the undo stack */
	private final GUIBoard guiBoard;
	/** The row number, in the 2D Array within a GUIBoard */
	private final int row;
	/** The column number, in the 2D Array within a GUIBoard */
	private final int col;

	/** Used to set numbers pressed as annotations or as the number for the SudokuCell */
	private final AtomicBoolean annotate;
	/** Used to indicate numbers pressed are to be removed from SudokuCell instead of added as annotations or its number */
	private final AtomicBoolean erase;

	/** The SudokuCell this GUICell represents graphically */
	private SudokuCell sudokuCell;
	/** The Group of nodes to visually display information on, about the SudokuCell */
	private Group group;
	/** The GridPane with buttons displaying annotations made on this SudokuCell */
	private GridPane annotationsGridPane;
	/** The Label displaying this SudokuCell's number */
	private Label cellNumberLabel;

	/**
	 * Create a new GUICell instance to display information about a SudokuCell in a Group node.
	 * This instance's SudokuCell will be set to null, and {@link GUICell#setSudokuCell(SudokuCell)} should be called
	 * afterwards to set its value.
	 *
	 * @param guiBoard the GUIBoard that contains this GUICell in a 2D Array
	 * @param row      the row this GUICell is located in
	 * @param col      the column number this GUICell is located in
	 */
	public GUICell (GUIBoard guiBoard, int row, int col) {
		this.sudokuCell = null;
		this.annotate = new AtomicBoolean(false);
		this.erase = new AtomicBoolean(false);
		this.guiBoard = guiBoard;
		this.row = row;
		this.col = col;
		initializeGUI();
	}

	/**
	 * Make a copy of the given GUICell's current state, for use in the GUIBoard's undoStack.
	 *
	 * @param other GUICell to clone
	 */
	private GUICell (GUICell other) {
		this.guiBoard = other.guiBoard;
		this.row = other.row;
		this.col = other.col;
		this.annotate = other.annotate;
		this.erase = other.erase;
		this.sudokuCell = new SudokuCell(other.sudokuCell);
		initializeGUI();
		updateDisplay();
	}

	/**
	 * Initialize the behavior each annotation button will have.
	 * Will either add the button pressed as the SudokuCell's number or as annotation (annotate flag dependent).
	 * Or will erase the annotation or number from the SudokuCell (erase flag dependent).
	 */
	public void initializeGUI () {
		try {
			// create this GUICell's Group
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(GUICell.class.getResource("sudokuCellGroup.fxml"));
			this.group = loader.load();
			this.annotationsGridPane = (GridPane) group.getChildren().get(0);
			this.cellNumberLabel = (Label) group.getChildren().get(1);
			for (Node annotationNumBtn : annotationsGridPane.getChildren()) {
				AtomicBoolean marked = new AtomicBoolean(false);    // has the button been pressed?
				// display on hover, only if erase is not on
				annotationNumBtn.setOnMouseEntered(event -> {
					if (!erase.get()) {
						annotationNumBtn.setOpacity(1.0);
					}
				});
				// disappear when not hovered, only if not marked
				annotationNumBtn.setOnMouseExited(event -> {
					if (!marked.get()) {
						annotationNumBtn.setOpacity(0.0);
					}
				});

				( (Button) annotationNumBtn ).setOnAction(event -> {
					// add current GUICell state to undo stack
					guiBoard.pushOnToUndoStack(new GUICell(this), true);
					if (erase.get()) {
						// erase annotation
						if (marked.compareAndSet(true, false)) {
							removeAnnotation(Integer.parseInt(( (Button) annotationNumBtn ).getText()));
						}
					} else if (annotate.get()) {
						// set annotation
						if (marked.compareAndSet(false, true)) {
							addAnnotation(Integer.parseInt(( (Button) annotationNumBtn ).getText()));
						}
					} else {
						// set the number selected as this SudokuCell's number
						marked.set(false);
						setSudokuCellNumber(Integer.parseInt(( (Button) annotationNumBtn ).getText()));
					}
				});
			}
			cellNumberLabel.setOnMouseClicked(event -> {
				// add current GUICell state to undo stack
				guiBoard.pushOnToUndoStack(new GUICell(this), true);
				if (erase.get()) {
					removeSudokuCellNumber();
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Update the SudokuCell this GUICell is to represent.
	 *
	 * @param sudokuCell new SudokuCell
	 */
	public void setSudokuCell (SudokuCell sudokuCell) {
		this.sudokuCell = sudokuCell;
		updateDisplay();
	}

	/**
	 * Set the given number as this instance's SudokuCell number, if the SudokuCell does not contain a given number.
	 *
	 * @param num num to set
	 */
	private void setSudokuCellNumber (int num) {
		sudokuCell.setNumber(num);
		updateDisplay();
	}

	/**
	 * Remove this instance's SudokuCell number, if the SudokuCell does not contain a given number.
	 */
	private void removeSudokuCellNumber () {
		sudokuCell.removeNumber();
		updateDisplay();
	}

	/**
	 * Add the given number to the SudokuCell's annotations Set.
	 *
	 * @param num number to add
	 */
	private void addAnnotation (int num) {
		sudokuCell.addAnnotation(num);
		updateDisplay();
	}

	/**
	 * Remove the given number from the SudokuCell's annotations Set.
	 *
	 * @param num number to remove
	 */
	private void removeAnnotation (int num) {
		sudokuCell.removeAnnotation(num);
		updateDisplay();
	}

	/**
	 * Update the contents of this instance's Group to display new information about the SudokuCell.
	 */
	private void updateDisplay () {
		// display the annotations made for this SudokuCell
		Set<Integer> annotations = sudokuCell.getAnnotations();
		for (Node annotationNumBtn : annotationsGridPane.getChildren()) {
			if (annotations.contains(Integer.parseInt(( (Button) annotationNumBtn ).getText()))) {
				annotationNumBtn.setOpacity(1.0);
			} else {
				annotationNumBtn.setOpacity(0.0);
			}
		}

		// display or hide the label depending on number of SudokuCell number
		int number = sudokuCell.getNumber();
		if (number == 0) {
			// remove the number from the label
			// setting text to "" created visual bug where the cell wasn't lined up with the rest of the row
			cellNumberLabel.setText(" ");
			cellNumberLabel.setDisable(true);
			annotationsGridPane.setDisable(false);
		} else {
			// display the number set for this SudokuCell
			cellNumberLabel.setText(String.valueOf(number));
			if (sudokuCell.isGivenNumber()) {
				cellNumberLabel.setTextFill(Color.valueOf("#C33C54"));
				cellNumberLabel.setStyle("-fx-font-weight: bold");
			}
			cellNumberLabel.setDisable(false);
			annotationsGridPane.setDisable(true);
		}
	}

	/**
	 * Set annotate boolean to indicate if numbers pressed are annotations made to the SudokuCell.
	 *
	 * @param value boolean value to set
	 */
	public void setAnnotate (boolean value) {
		annotate.set(value);
	}

	/**
	 * Set erase boolean to indicate if numbers pressed are to be erased from the SudokuCell.
	 *
	 * @param value boolean value to set
	 */
	public void setErase (boolean value) {
		erase.set(value);
	}

	/**
	 * @return the row number of this GUICell's location in the 2D Array within its GUIBoard
	 */
	public int getRow () {
		return row;
	}

	/**
	 * @return the column number of this GUICell's location in the 2D Array within its GUIBoard
	 */
	public int getCol () {
		return col;
	}

	/**
	 * @return this GUICell's Group of nodes
	 */
	public Group getGroup () {
		return group;
	}
}
