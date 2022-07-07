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
	/** The GridPane object that displays the different action buttons in the top section of the window */
	@FXML
	private GridPane actionPane;

	/** The GridPane object that displays the Sudoku puzzle */
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
		// iterate through each button in the top actionPane to add background when hovered
		for (Node node : actionPane.getChildren()) {
			node.setOnMouseEntered(event -> node.setStyle("-fx-background-color: #D5DCE0;"));
			node.setOnMouseExited(event -> node.setStyle("-fx-background-color: transparent;"));
		}

		// iterate through each Sudoku annotation button to add display on hover and toggle display on press
		for (Node node : board.getChildren()) {
			for (Node child : ( (GridPane) node ).getChildren()) {
				for (Node finalChild : ( (GridPane) child ).getChildren()) {
					AtomicBoolean marked = new AtomicBoolean(false);    // has the button been pressed?
					// display on hover
					finalChild.setOnMouseEntered(event -> finalChild.setOpacity(1.0));
					// disappear when not hovered, only if not marked
					finalChild.setOnMouseExited(event -> {
						if (!marked.get()) {
							finalChild.setOpacity(0.0);
						}
					});
					// toggle button display when clicked based on its previous display state
					( (Button) finalChild ).setOnAction(event -> {
						if (marked.compareAndSet(false, true)) {
							finalChild.setOpacity(1.0);
						} else {
							marked.set(false);
							finalChild.setOpacity(0.0);
						}
					});
				}
			}
		}
	}
}
