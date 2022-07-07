package sudoku;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

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
		// iterate through each button to add display on hover and toggle display on press
		for (Node node : board.getChildren()) {
			for (Node child : ( (GridPane) node ).getChildren()) {
				for (Node finalChild : ( (GridPane) child ).getChildren()) {
					AtomicBoolean marked = new AtomicBoolean(false);
					finalChild.setOnMouseEntered(event -> finalChild.setOpacity(1.0));
					finalChild.setOnMouseExited(event -> {
						if (!marked.get()) {
							finalChild.setOpacity(0.0);
						}
					});
					( (Button) finalChild ).setOnAction(event -> {
						if (finalChild.getOpacity() == 0.0) {
							finalChild.setOpacity(1.0);
							marked.set(true);
						} else {
							finalChild.setOpacity(0.0);
							marked.set(false);
						}
					});
				}
			}
		}
	}
}
