package sudoku.gui;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import sudoku.ContainerController;
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

	/** Sample Sudoku puzzle launched on application start */
	private static final String SAMPLE_PUZZLE = "input/sample_puzzle.csv";
	/** Easy Sudoku puzzle that can be launched directly from GUI */
	private static final String EASY_PUZZLE = "input/easy_puzzle.csv";
	/** Medium Sudoku puzzle that can be launched directly from GUI */
	private static final String MEDIUM_PUZZLE = "input/medium_puzzle.csv";
	/** Hard Sudoku puzzle that can be launched directly from GUI */
	private static final String HARD_PUZZLE = "input/hard_puzzle.csv";

	/** The currently displayed Sudoku puzzle */
	private final String currentPuzzle;

	/** The top-level controller class */
	private final ContainerController controller;

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
	public GUIBoard (ContainerController controller) {
		this.controller = controller;
		this.sudokuBoard = new SudokuBoard(SAMPLE_PUZZLE);
		this.currentPuzzle = SAMPLE_PUZZLE;
		this.boardOfGUICells = new GUICell[rows][cols];
		this.undoStack = new Stack<>();
		this.redoStack = new Stack<>();
		initializeGUI();
	}

	/**
	 * Populates the 2D Array of GUICells and links up each GUICell with its corresponding SudokuCell from the
	 * SudokuBoard. Adds each GUICell Group to the GridPane to be displayed on the GUI.
	 */
	private void initializeGUI () {
		try {
			// create this GUIBoard's GridPane
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(GUICell.class.getResource("sudokuBoardGridPane.fxml"));
			this.gridPaneOfGroups = loader.load();
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Push the given GUICell on to the undo stack and enable the undo button,
	 * since the undo stack is no longer empty.
	 * ClearRedo argument shuld be true when pushing a new user action
	 * and false when cycling back through the undo stack.
	 *
	 * @param cell      GUICell to add
	 * @param clearRedo boolean value
	 */
	public void pushOnToUndoStack (GUICell cell, boolean clearRedo) {
		undoStack.push(cell);
		controller.enableUndoButton();
		if (clearRedo) {
			clearRedoStack();
		}
	}

	/**
	 * Push the given GUICell on to the redo stack and enable the redo button,
	 * since the redo stack is no longer empty.
	 *
	 * @param cell GUICell to add
	 */
	private void pushOnToRedoStack (GUICell cell) {
		redoStack.push(cell);
		controller.enableRedoButton();
	}

	/** Clear all entries from the undo stack and disable its GUI button */
	private void clearUndoStack () {
		undoStack.clear();
		controller.disableUndoButton();
	}

	/** Clear all entries from the redo stack and disable its GUI button */
	private void clearRedoStack () {
		redoStack.clear();
		controller.disableRedoButton();
	}

	/**
	 * Undo the last action performed by the user, using the undo stack, and place
	 * the overriden GUICell on to the redo stack. If the undo stack is now empty, disable its button in the GUI.
	 */
	public void undoLastAction () {
		if (!undoStack.empty()) {
			GUICell newCell = undoStack.pop();
			GUICell oldCell = boardOfGUICells[newCell.getRow()][newCell.getCol()];
			replaceCells(oldCell, newCell);
			pushOnToRedoStack(oldCell);
			if (undoStack.empty()) {
				controller.disableUndoButton();
			}
		}
	}

	/**
	 * Redo the last action performed by the user, using the redo stack, and place
	 * the overridden GUICell on to the undo stack. If the redo stack is now empty, disable its button in the GUI.
	 */
	public void redoLastAction () {
		if (!redoStack.empty()) {
			GUICell newCell = redoStack.pop();
			GUICell oldCell = boardOfGUICells[newCell.getRow()][newCell.getCol()];
			replaceCells(oldCell, newCell);
			pushOnToUndoStack(oldCell, false);
			if (redoStack.empty()) {
				controller.disableRedoButton();
			}
		}
	}

	/**
	 * Replace a GUICell with a different GUICell by replacing it from the 2D Array of GUICells
	 * and replacing it in the GridPane of GUICell Groups to update the display. Also replace the SudokuCell's
	 * of the old GUICell with the new SudokuCell in the SudokuBoard.
	 *
	 * @param remove GUICell to remove
	 * @param insert GUICell to insert
	 */
	private void replaceCells (GUICell remove, GUICell insert) {
		sudokuBoard.replaceSudokuCell(remove.getRow(), remove.getCol(), insert.getSudokuCell());

		gridPaneOfGroups.getChildren().remove(remove.getGroup());
		boardOfGUICells[remove.getRow()][remove.getCol()] = insert;
		gridPaneOfGroups.add(insert.getGroup(), insert.getCol(), insert.getRow());

		// add slight margins to show visual distinction between sudoku regions
		int row = insert.getRow();
		int col = insert.getCol();
		if (col == 2) {
			GridPane.setMargin(insert.getGroup(), new Insets(0, 4, 0, 0));
		} else if (col == 6) {
			GridPane.setMargin(insert.getGroup(), new Insets(0, 0, 0, 4));
		}
		if (row == 2) {
			if (col == 2) {
				GridPane.setMargin(insert.getGroup(), new Insets(0, 4, 4, 0));
			} else if (col == 6) {
				GridPane.setMargin(insert.getGroup(), new Insets(0, 0, 4, 4));
			} else {
				GridPane.setMargin(insert.getGroup(), new Insets(0, 0, 4, 0));
			}
		} else if (row == 6) {
			if (col == 2) {
				GridPane.setMargin(insert.getGroup(), new Insets(4, 4, 0, 0));
			} else if (col == 6) {
				GridPane.setMargin(insert.getGroup(), new Insets(4, 0, 0, 4));
			} else {
				GridPane.setMargin(insert.getGroup(), new Insets(4, 0, 0, 0));
			}
		}
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
	 * Reset the board back to its original state when initially loading up the puzzle,
	 * clearing the undo and redo stacks
	 */
	public void resetBoard () {
		sudokuBoard = new SudokuBoard(currentPuzzle);
		clearUndoStack();
		clearRedoStack();
		initializeGUI();
	}

	/** Load the easy sudoku puzzle. */
	public void loadEasyPuzzle () {
		loadNewPuzzle(EASY_PUZZLE);
	}

	/** Load the medium sudoku puzzle. */
	public void loadMediumPuzzle () {
		loadNewPuzzle(MEDIUM_PUZZLE);
	}

	/** Load the hard sudoku puzzle. */
	public void loadHardPuzzle () {
		loadNewPuzzle(HARD_PUZZLE);
	}

	/**
	 * Load a new Sudoku puzzle with the given filename.
	 *
	 * @param filename Sudoku CSV filename
	 */
	public void loadNewPuzzle (String filename) {
		sudokuBoard = new SudokuBoard(filename);
		clearUndoStack();
		clearRedoStack();
		initializeGUI();
	}

	/**
	 * @return GridPane of Groups used to graphically represent a SudokuCell
	 */
	public GridPane getGridPaneOfGroups () {
		return gridPaneOfGroups;
	}
}
