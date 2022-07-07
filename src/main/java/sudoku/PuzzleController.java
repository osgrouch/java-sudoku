package sudoku;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for user interactions with Puzzle view in puzzle.fxml.
 */
public class PuzzleController implements Initializable {
	@FXML
	private GridPane board;

	/** Default constructor. */
	public PuzzleController () {}

	/**
	 * Called to initialize a controller after its root element has been completely processed.
	 * Used to add display on hover property and toggle display on button press for each button
	 * representing a pencil mark in each cell of the board.
	 *
	 * @param location  The location used to resolve relative paths for the root object, or
	 *                  {@code null} if the location is not known.
	 * @param resources The resources used to localize the root object, or {@code null} if
	 *                  the root object was not localized.
	 */
	@Override
	public void initialize (URL location, ResourceBundle resources) {
	}
}
