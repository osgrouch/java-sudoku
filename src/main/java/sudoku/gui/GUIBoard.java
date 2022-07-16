package sudoku.gui;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import sudoku.ContainerController;
import sudoku.puzzle.SudokuBoard;

import java.io.IOException;
import java.util.ArrayList;

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

	/** The total number of GUICells in the 9x9 Board */
	private final int totalNumOfCells;
	/** The number of GUICells that have a number set */
	private int numOfGuessedCells;

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
		this.totalNumOfCells = 81;
		this.numOfGuessedCells = 0;
		initializeGUI();
	}

	/**
	 * Create a new GUIBoard instance cloning the fields of the given GUIBoard.
	 *
	 * @param other GUIBoard to clone
	 */
	public GUIBoard (GUIBoard other) {
		this.controller = other.controller;
		this.sudokuBoard = new SudokuBoard(other.sudokuBoard);
		this.currentPuzzle = other.currentPuzzle;
		this.boardOfGUICells = new GUICell[rows][cols];
		this.totalNumOfCells = other.totalNumOfCells;
		this.numOfGuessedCells = other.numOfGuessedCells;
		initializeGUI(other);
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
					GUICell current = new GUICell(this);
					current.setSudokuCell(sudokuBoard.getSudokuCell(row, col));
					if (current.getSudokuCell().isGivenNumber()) {
						++numOfGuessedCells;
					}
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
			controller.errorMessage("Failed to create SudokuBoard GUI");
			e.printStackTrace();
		}
	}

	/**
	 * Populates the 2D Array of GUICells with clones of the given GUIBoard and links up each GUICell
	 * with its corresponding SudokuCell from this instance's SudokuBoard.
	 * Adds each GUICell Group to the GridPane to be displayed on the GUI.
	 *
	 * @param guiBoard GUIBoard to clone GUICells from
	 */
	private void initializeGUI (GUIBoard guiBoard) {
		try {
			// create this GUIBoard's GridPane
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(GUICell.class.getResource("sudokuBoardGridPane.fxml"));
			this.gridPaneOfGroups = loader.load();
			for (int row = 0; row < rows; row++) {
				for (int col = 0; col < cols; col++) {
					// link each GUICell with its corresponding SudokuCell in the SudokuBoard
					GUICell current = new GUICell(this, guiBoard.boardOfGUICells[row][col]);
					current.setSudokuCell(sudokuBoard.getSudokuCell(row, col));
					if (current.getSudokuCell().isGivenNumber()) {
						++numOfGuessedCells;
					}
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
			controller.errorMessage("Failed to create SudokuBoard GUI");
			e.printStackTrace();
		}
	}

	/** Push the current GUIBoard state to the undo stack. */
	public void pushNewBoardToUndoStack () {
		controller.pushNewBoardToUndoStack();
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
		numOfGuessedCells = 0;
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
		numOfGuessedCells = 0;
		initializeGUI();
	}

	/**
	 * Increment the count of cells with a guess. If the number equals the total number of cells, check if the current
	 * board is the sudoku solution.
	 */
	public void incrementGuessedCellsCount () {
		++numOfGuessedCells;
		if (numOfGuessedCells == totalNumOfCells) {
			if (sudokuBoard.isSolution()) {
				controller.successMessage("Congratulations,\nyou've solved the puzzle!");
			} else {
				controller.errorMessage("There are repeating numbers\nin the puzzle.");
			}
		}
	}

	/** Decrement the number of cells with a guess by one. */
	public void decrementGuessedCellsCount () {
		--numOfGuessedCells;
	}

	/**
	 * Find all GUICells with their number set to the given number guessed on the given GUICell, in the region,
	 * row and column of the given GUICell. Highlight these numbers red to indicate there is a conflict.
	 *
	 * @param guiCell a GUICell
	 * @param num     the number guessed on the GUICell
	 */
	public void highlightConflictingSetNumbers (GUICell guiCell, int num) {
		int region = guiCell.getSudokuCell().getRegion();
		for (GUICell current : getRegion(region)) {
			if (current.equals(guiCell)) {
				continue;
			}
			if (current.getSudokuCell().getNumber() == num) {
				current.setConflicting(true);
				guiCell.setConflicting(true);
			}
		}
		int row = guiCell.getSudokuCell().getRow();
		for (GUICell current : getRow(row)) {
			if (current.equals(guiCell)) {
				continue;
			}
			if (current.getSudokuCell().getNumber() == num) {
				current.setConflicting(true);
				guiCell.setConflicting(true);
			}
		}
		int col = guiCell.getSudokuCell().getCol();
		for (GUICell current : getCol(col)) {
			if (current.equals(guiCell)) {
				continue;
			}
			if (current.getSudokuCell().getNumber() == num) {
				current.setConflicting(true);
				guiCell.setConflicting(true);
			}
		}
	}

	/**
	 * Find all GUICells that were in conflict with the given GUICell, due to the given number,
	 * and remove the color highlighting previously set on those GUICells because the GUICell causing conflict
	 * no longer has that number set.
	 *
	 * @param guiCell a GUICell
	 * @param num     the number removed from the GUICell
	 */
	public void removeHighlightFromPreviouslyConflictingSetNumbers (GUICell guiCell, int num) {
		int region = guiCell.getSudokuCell().getRegion();
		for (GUICell current : getRegion(region)) {
			if (current.equals(guiCell)) {
				continue;
			}
			if (current.getSudokuCell().getNumber() == num) {
				current.setConflicting(false);
				guiCell.setConflicting(false);
			}
		}
		int row = guiCell.getSudokuCell().getRow();
		for (GUICell current : getRow(row)) {
			if (current.equals(guiCell)) {
				continue;
			}
			if (current.getSudokuCell().getNumber() == num) {
				current.setConflicting(false);
				guiCell.setConflicting(false);
			}
		}
		int col = guiCell.getSudokuCell().getCol();
		for (GUICell current : getCol(col)) {
			if (current.equals(guiCell)) {
				continue;
			}
			if (current.getSudokuCell().getNumber() == num) {
				current.setConflicting(false);
				guiCell.setConflicting(false);
			}
		}
	}

	/**
	 * Find all annotations of the given number in the region, row and column of the given GUICell and remove them.
	 * These annotations are in conflict with the number just guessed on the GUICell and can be removed.
	 *
	 * @param guiCell a GUICell
	 * @param num     the number guessed on the GUICell
	 */
	public void removeConflictingAnnotations (GUICell guiCell, int num) {
		int region = guiCell.getSudokuCell().getRegion();
		for (GUICell current : getRegion(region)) {
			current.removeAnnotation(num);
		}
		int row = guiCell.getSudokuCell().getRow();
		for (GUICell current : getRow(row)) {
			current.removeAnnotation(num);
		}
		int col = guiCell.getSudokuCell().getCol();
		for (GUICell current : getCol(col)) {
			current.removeAnnotation(num);
		}
	}

	/**
	 * Get all the GUICell's that contain a SudokuCell in the given region.
	 *
	 * @param region region to look for
	 * @return ArrayList of GUICells
	 */
	private ArrayList<GUICell> getRegion (int region) {
		ArrayList<GUICell> guiCells = new ArrayList<>(9); // there will only ever be 9 GUICells in a region
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				GUICell current = boardOfGUICells[row][col];
				if (current.getSudokuCell().getRegion() == region) {
					guiCells.add(current);
				}
			}
		}
		return guiCells;
	}

	/**
	 * Get all the GUICells in the given row.
	 *
	 * @param row row to look for
	 * @return Array of GUICells
	 */
	private GUICell[] getRow (int row) {
		return boardOfGUICells[row];
	}

	/**
	 * Get all the GUICells in the given col.
	 *
	 * @param col column to look for
	 * @return Array of GUICells
	 */
	private GUICell[] getCol (int col) {
		GUICell[] column = new GUICell[rows];
		for (int row = 0; row < rows; row++) {
			column[row] = boardOfGUICells[row][col];
		}
		return column;
	}

	/**
	 * @return GridPane of Groups used to graphically represent a SudokuCell
	 */
	public GridPane getGridPaneOfGroups () {
		return gridPaneOfGroups;
	}
}
