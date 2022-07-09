package sudoku.gui;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import sudoku.puzzle.SudokuBoard;

public class GUIBoard {
	/** The number of GUICells rows in this grid */
	public static final int rows = 9;
	/** The number of GUICells columns in this grid */
	public static final int cols = 9;

	/** The SudokuBoard this GUIBoard represents graphically */
	private SudokuBoard sudokuBoard;

	/** A GridPane with Groups, displaying information about a SudokuCell */
	@FXML
	private GridPane gridPaneOfGroups;
	/** 2D Array of GUICells in the puzzle */
	private GUICell[][] boardOfGUICells;

	/**
	 * Create a new GUIBoard instance with a new GridPane to visually display the GUICells that make up the board.
	 * Create a new SudokuBoard with a sample Sudoku CSV file and link each SudokuCell in the SudokuBoard with
	 * its corresponding GUICell in this GUIBoard.
	 */
	public GUIBoard () {
		this.sudokuBoard = new SudokuBoard("input/sample_puzzle.csv");
		// create a new GridPane
		this.boardOfGUICells = new GUICell[rows][cols];
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				// link each GUICell with its corresponding SudokuCell in the SudokuBoard
				GUICell current = new GUICell();
				current.setSudokuCell(sudokuBoard.getSudokuCell(row, col));
				this.boardOfGUICells[row][col] = current;
				this.gridPaneOfGroups.getChildren().add(current.getGroup());
			}
		}
	}
}
