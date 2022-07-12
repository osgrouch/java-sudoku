package sudoku.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Class that Controllers the contents of container.fxml and injects
 * and controlls contents of the GUIBoard.
 */
public class ContainerController {
	/** Button toggles annotation mode on or off */
	@FXML
	public Button annotationBtn;
	/** Button that undoes the user's last input */
	@FXML
	public Button undoBtn;
	/** Button that redoes the user's next input */
	@FXML
	public Button redoBtn;
	/** Button that toggles erasing mode on or off */
	@FXML
	public Button eraseBtn;
	/** Button that resets the entire grid back to the starting grid */
	@FXML
	public Button resetBtn;
}
