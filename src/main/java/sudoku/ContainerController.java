package sudoku;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import sudoku.gui.GUIBoard;

import java.net.URL;
import java.util.ResourceBundle;

/** Class that Controllers the contents of container.fxml and injects and controlls contents of the GUIBoard. */
public class ContainerController implements Initializable {
	/** StackPane where messages are printed out to the user */
	@FXML
	private StackPane messagePane;
	/** Label used to display messages to the user, such as a win prompt */
	@FXML
	private Label messageLabel;

	/** The AnchorPane which wraps GUIBoard's GridPane */
	@FXML
	private AnchorPane boardAnchor;

	/** Button that undoes the user's last input */
	@FXML
	private Button undoBtn;
	/** Button that redoes the user's next input */
	@FXML
	private Button redoBtn;

	/** Button toggles annotation mode on or off */
	@FXML
	private Button annotationBtn;
	/** Has the annotation button been toggled? */
	private boolean annotate;

	/** Button that toggles erasing mode on or off */
	@FXML
	private Button eraseBtn;
	/** Has the erase button been toggled? */
	private boolean erase;

	/** Button that resets the entire grid back to the starting grid */
	@FXML
	private Button resetBtn;

	/** A graphical representation of a SudokuBoard */
	private GUIBoard guiBoard;

	/** Default constructor. */
	public ContainerController () {
		this.guiBoard = new GUIBoard(this);
		this.annotate = false;
		this.erase = false;
	}

	/**
	 * "Close" the message box by making it invisible.
	 *
	 * @param event ActionEvent from button press
	 */
	public void hideMessagePane (ActionEvent event) {
		messagePane.setVisible(false);
	}

	/**
	 * Undo the last action performed by the user.
	 *
	 * @param event ActionEvent from button press
	 */
	public void undoLastAction (ActionEvent event) {
		guiBoard.undoLastAction();
		updateBoardDisplay();
		eraseOff();
	}

	/**
	 * Redo the last action performed by the user.
	 *
	 * @param event ActionEvent from button press
	 */
	public void redoLastAction (ActionEvent event) {
		guiBoard.redoLastAction();
		updateBoardDisplay();
		eraseOff();
	}

	/** Disable the undo button in the GUI */
	public void disableUndoButton () {
		undoBtn.setDisable(true);
	}

	/** Enabel the undo button in the GUI */
	public void enableUndoButton () {
		undoBtn.setDisable(false);
	}

	/** Disable the redo button in the GUI */
	public void disableRedoButton () {
		redoBtn.setDisable(true);
	}

	/** Enable the redo button in the GUI */
	public void enableRedoButton () {
		redoBtn.setDisable(false);
	}

	/**
	 * Toggle annotations on all GUICell's, so that any number pressed is marked as an
	 * annotation on the SudokuCell instead of the SudokuCell's number.
	 *
	 * @param event ActionEvent from button press
	 */
	public void toggleAnnotate (ActionEvent event) {
		this.annotate = !annotate;
		guiBoard.setAnnotate(annotate);
		if (annotate) {
			annotationBtn.setText("Annotations: ON");
		} else {
			annotationBtn.setText("Annotations: OFF");
		}
		eraseOff();
	}

	/**
	 * Toggle erase on all GUICell's, so that any number pressed is removed from the SudokuCell
	 * instead of being added to the SudokuCell as a number or annotation.
	 *
	 * @param event ActionEvent from button press
	 */
	public void toggleErase (ActionEvent event) {
		this.erase = !erase;
		guiBoard.setErase(erase);
		if (erase) {
			eraseBtn.setText("Erase: ON");
		} else {
			eraseBtn.setText("Erase: OFF");
		}
	}

	/**
	 * Turn erase boolean false by having the button fire if erase is currently true.
	 * Used to clear erase when user presses a different button with erase on.
	 */
	private void eraseOff () {
		if (erase) {
			eraseBtn.fire();
		}
	}

	/** Reset the SudokuBoard back to its original starting */
	public void resetBoard (ActionEvent event) {
		guiBoard.resetBoard();
		// turn on annotate then toggle it off, which will in turn, turn off erase button, if on
		annotate = true;
		toggleAnnotate(new ActionEvent());

		updateBoardDisplay();
	}

	/** Load the easy sudoku puzzle to the GUI Board and display it. */
	public void loadEasyPuzzle (ActionEvent event) {
		guiBoard.loadEasyPuzzle();
		// turn on annotate then toggle it off, which will in turn, turn off erase button, if on
		annotate = true;
		toggleAnnotate(new ActionEvent());

		updateBoardDisplay();
	}

	/** Load the medium sudoku puzzle to the GUI Board and display it. */
	public void loadMediumPuzzle (ActionEvent event) {
		guiBoard.loadMediumPuzzle();
		// turn on annotate then toggle it off, which will in turn, turn off erase button, if on
		annotate = true;
		toggleAnnotate(new ActionEvent());

		updateBoardDisplay();
	}

	/** Load the hard sudoku puzzle to the GUI Board and display it. */
	public void loadHardPuzzle (ActionEvent event) {
		guiBoard.loadHardPuzzle();
		// turn on annotate then toggle it off, which will in turn, turn off erase button, if on
		annotate = true;
		toggleAnnotate(new ActionEvent());

		updateBoardDisplay();
	}

	/** Update the GUI to display the changes made to GUI Board. */
	private void updateBoardDisplay () {
		boardAnchor.getChildren().clear();
		boardAnchor.getChildren().add(guiBoard.getGridPaneOfGroups());
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
		// display intitial sudoku board
		boardAnchor.getChildren().add(guiBoard.getGridPaneOfGroups());

		// set undo and redo images
		ImageView undoImg = new ImageView(new Image(ContainerController.class.getResourceAsStream("undo-arrow.png")));
		undoImg.setFitHeight(18);
		undoImg.setPreserveRatio(true);
		undoBtn.setGraphic(undoImg);
		ImageView redoImg = new ImageView(new Image(ContainerController.class.getResourceAsStream("redo-arrow.png")));
		redoImg.setFitHeight(18);
		redoImg.setPreserveRatio(true);
		redoBtn.setGraphic(redoImg);
	}
}
