package sudoku.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import sudoku.puzzle.SudokuBoard;

import java.net.URL;
import java.util.ResourceBundle;

public class GUIBoard implements Initializable {
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
	}

	/**
	 * Called to initialize a controller after its root element has been completely processed.
	 * Populates the 2D Array of GUICells and links up each GUICell with its corresponding SudokuCell from the
	 * SudokuBoard. Adds each GUICell Group to the GridPane to be displayed on the GUI.
	 *
	 * @param location  The location used to resolve relative paths for the root object, or
	 *                  {@code null} if the location is not known.
	 * @param resources The resources used to localize the root object, or {@code null} if
	 *                  the root object was not localized.
	 */
	@Override
	public void initialize (URL location, ResourceBundle resources) {
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
}
