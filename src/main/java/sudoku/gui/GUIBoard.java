package sudoku.gui;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import sudoku.puzzle.SudokuBoard;

import java.io.IOException;

/**
 * Class to represent a SudokuBoard graphically using GUICells.
 * Organizes and displays each GUICell for the user to interact with.
 */
public class GUIBoard {
	/** The number of GUICells rows in this grid */
	public static final int rows = 9;
	/** The number of GUICells columns in this grid */
	public static final int cols = 9;

	/** The SudokuBoard this GUIBoard represents graphically */
	private SudokuBoard sudokuBoard;

	/** A GridPane with Groups, displaying information about a SudokuCell */
	private GridPane gridPaneOfGroups;
	/** 2D Array of GUICells in the puzzle */
	private GUICell[][] boardOfGUICells;

	/**
	 * Create a new GUIBoard instance with a new SudokuBoard from the sample Sudoku CSV file.
	 * Create a new GridPane to contain the GUICell's Group display.
	 */
	public GUIBoard () {
		this.sudokuBoard = new SudokuBoard("input/sample_puzzle.csv");
		this.boardOfGUICells = new GUICell[rows][cols];

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
				GUICell current = new GUICell();
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
	 * @return GridPane of Groups used to graphically represent a SudokuCell
	 */
	public GridPane getGridPaneOfGroups () {
		return gridPaneOfGroups;
	}
}
