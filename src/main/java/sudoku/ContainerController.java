package sudoku;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import sudoku.gui.GUIBoard;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class that Controllers the contents of container.fxml and injects
 * and controlls contents of the GUIBoard.
 */
public class ContainerController implements Initializable {
	/** The AnchorPane which wraps GUIBoard's GridPane */
	@FXML
	private AnchorPane boardAnchor;

	/** Button toggles annotation mode on or off */
	@FXML
	private Button annotationBtn;
	/** Button that undoes the user's last input */
	@FXML
	private Button undoBtn;
	/** Button that redoes the user's next input */
	@FXML
	private Button redoBtn;
	/** Button that toggles erasing mode on or off */
	@FXML
	private Button eraseBtn;
	/** Button that resets the entire grid back to the starting grid */
	@FXML
	private Button resetBtn;

	/** A graphical representation of a SudokuBoard */
	private GUIBoard guiBoard;

	/** Default constructor. */
	public ContainerController () {
		this.guiBoard = new GUIBoard();
	}

	/**
	 * Called to initialize a controller after its root element has been
	 * completely processed.
	 *
	 * @param location  The location used to resolve relative paths for the root object, or
	 *                  {@code null} if the location is not known.
	 * @param resources The resources used to localize the root object, or {@code null} if
	 *                  the root object was not localized.
	 */
	@Override
	public void initialize (URL location, ResourceBundle resources) {
		// TODO: update docstring
		boardAnchor.getChildren().add(guiBoard.getGridPaneOfGroups());
	}
}
