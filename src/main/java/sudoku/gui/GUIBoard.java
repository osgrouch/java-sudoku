package sudoku.gui;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import sudoku.puzzle.SudokuBoard;

import java.io.IOException;
import java.util.Stack;

/**
 * Class to represent a SudokuBoard graphically using GUICells.
 * Organizes and displays each GUICell for the user to interact with.
 */
public class GUIBoard {
	/** The number of GUICells rows in this grid */
	public static final int rows = 9;
	/** The number of GUICells columns in this grid */
	public static final int cols = 9;

	/** 2D Array of GUICells in the puzzle */
	private final GUICell[][] boardOfGUICells;

	/** Stack of GUICells used to undo a user's actions */
	private final Stack<GUICell> undoStack;
	/** Stack of GUICells used to redo a user's actions */
	private final Stack<GUICell> redoStack;

	/** The SudokuBoard this GUIBoard represents graphically */
	private SudokuBoard sudokuBoard;

	/** A GridPane with Groups, displaying information about a SudokuCell */
	private GridPane gridPaneOfGroups;

	/**
	 * Create a new GUIBoard instance with a new SudokuBoard from the sample Sudoku CSV file.
	 * Create a new GridPane to contain the GUICell's Group display.
	 */
	public GUIBoard () {
		this.sudokuBoard = new SudokuBoard("input/sample_puzzle.csv");
		this.boardOfGUICells = new GUICell[rows][cols];
		this.undoStack = new Stack<>();
		this.redoStack = new Stack<>();

		// create this GUIBoard's GridPane
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(GUICell.class.getResource("sudokuBoardGridPane.fxml"));
		try {
			this.gridPaneOfGroups = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		init();
	}

	/**
	 * Populates the 2D Array of GUICells and links up each GUICell with its corresponding SudokuCell from the
	 * SudokuBoard. Adds each GUICell Group to the GridPane to be displayed on the GUI.
	 */
	public void init () {
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				// link each GUICell with its corresponding SudokuCell in the SudokuBoard
				GUICell current = new GUICell(this, row, col);
				current.setSudokuCell(sudokuBoard.getSudokuCell(row, col));
				boardOfGUICells[row][col] = current;
				gridPaneOfGroups.add(current.getGroup(), col, row);

				// add slight margins to show visual distinction between sudoku regions
				if (col == 2) {
					GridPane.setMargin(current.getGroup(), new Insets(0, 4, 0, 0));
				} else if (col == 6) {
					GridPane.setMargin(current.getGroup(), new Insets(0, 0, 0, 4));
				}
				if (row == 2) {
					if (col == 2) {
						GridPane.setMargin(current.getGroup(), new Insets(0, 4, 4, 0));
					} else if (col == 6) {
						GridPane.setMargin(current.getGroup(), new Insets(0, 0, 4, 4));
					} else {
						GridPane.setMargin(current.getGroup(), new Insets(0, 0, 4, 0));
					}
				} else if (row == 6) {
					if (col == 2) {
						GridPane.setMargin(current.getGroup(), new Insets(4, 4, 0, 0));
					} else if (col == 6) {
						GridPane.setMargin(current.getGroup(), new Insets(4, 0, 0, 4));
					} else {
						GridPane.setMargin(current.getGroup(), new Insets(4, 0, 0, 0));
					}
				}
			}
		}
	}

	/**
	 * Push the given GUICell on to the undo Stack.
	 *
	 * @param cell GUICell to add
	 */
	public void pushOnToUndoStack (GUICell cell) {
		undoStack.push(cell);
	}

	/**
	 * Set each GUICell's annotate value in this GUIBoard.
	 *
	 * @param value boolean value to set
	 */
	public void setAnnotate (boolean value) {
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				boardOfGUICells[row][col].setAnnotate(value);
			}
		}
	}

	/**
	 * Set each GUICell's erase value in this GUIBoard.
	 *
	 * @param value boolean value to set
	 */
	public void setErase (boolean value) {
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				boardOfGUICells[row][col].setErase(value);
			}
		}
	}

	/**
	 * @return GridPane of Groups used to graphically represent a SudokuCell
	 */
	public GridPane getGridPaneOfGroups () {
		return gridPaneOfGroups;
	}
}
