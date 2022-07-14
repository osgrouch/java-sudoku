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
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import sudoku.gui.GUIBoard;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;

/** Class that Controllers the contents of container.fxml and injects and controlls contents of the GUIBoard. */
public class ContainerController implements Initializable {
	/** Stack of GUIBoards used to restore the Board to a previous state */
	private final Stack<GUIBoard> undoStack;
	/** Stack of GUIBoards used to restore the Board to a previous state */
	private final Stack<GUIBoard> redoStack;

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

	/** A graphical representation of a SudokuBoard */
	private GUIBoard guiBoard;

	/** Default constructor. */
	public ContainerController () {
		this.guiBoard = new GUIBoard(this);
		this.undoStack = new Stack<>();
		this.redoStack = new Stack<>();
		this.annotate = false;
		this.erase = false;
	}

	/**
	 * Display the given message and color it red.
	 *
	 * @param message message to display
	 */
	public void errorMessage (String message) {
		messageLabel.setTextFill(Color.valueOf("#C33C54"));
		displayMessage(message);
	}

	/**
	 * Display the given message and color it green.
	 *
	 * @param message message to display
	 */
	public void successMessage (String message) {
		messageLabel.setTextFill(Color.valueOf("#43AA8B"));
		displayMessage(message);
	}

	/**
	 * Display the given message on the message label and make the message pane visible again.
	 *
	 * @param message message to display
	 */
	private void displayMessage (String message) {
		messageLabel.setText(message);
		messagePane.setVisible(true);
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
	 * Push this instance's GUIBoard on to the undo stack and clear the redo stack
	 * because the current board is not the first board in the redo stack.
	 */
	public void pushNewBoardToUndoStack () {
		undoStack.push(new GUIBoard(guiBoard));
		undoBtn.setDisable(false);
		clearRedoStack();
	}

	/** Push this instance's GUIBoard on to the undo stack without clearing the current redo stack. */
	private void pushBoardToUndoStack () {
		undoStack.push(new GUIBoard(guiBoard));
		undoBtn.setDisable(false);
	}

	/** Push this instance's GUIBoard on to the redo stack. */
	private void pushOnToRedoStack () {
		redoStack.push(new GUIBoard(guiBoard));
		redoBtn.setDisable(false);
	}

	/** Clear the redo stack and disable the redo button. */
	private void clearRedoStack () {
		redoStack.clear();
		redoBtn.setDisable(true);
	}

	/**
	 * Undo the last action performed by the user.
	 *
	 * @param event ActionEvent from button press
	 */
	public void undoLastAction (ActionEvent event) {
		if (!undoStack.empty()) {
			pushOnToRedoStack();
			guiBoard = undoStack.pop();
			updateBoardDisplay();
			eraseOff();
			if (undoStack.empty()) {
				undoBtn.setDisable(true);
			}
		}
	}

	/**
	 * Redo the last action performed by the user.
	 *
	 * @param event ActionEvent from button press
	 */
	public void redoLastAction (ActionEvent event) {
		if (!redoStack.empty()) {
			pushBoardToUndoStack();
			guiBoard = redoStack.pop();
			updateBoardDisplay();
			eraseOff();
			if (redoStack.empty()) {
				redoBtn.setDisable(true);
			}
		}
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

	/** Clear this instance's stacks and reset annotate and erase buttons to off. */
	private void resetButtons () {
		undoStack.clear();
		undoBtn.setDisable(true);
		redoStack.clear();
		redoBtn.setDisable(true);
		annotate = false;
		annotationBtn.setText("Annotations: OFF");
		erase = false;
		eraseBtn.setText("Erase: OFF");
		updateBoardDisplay();
	}

	/** Reset the SudokuBoard back to its original starting */
	public void resetBoard (ActionEvent event) {
		guiBoard.resetBoard();
		resetButtons();
	}

	/** Load the easy sudoku puzzle to the GUI Board and display it. */
	public void loadEasyPuzzle (ActionEvent event) {
		guiBoard.loadEasyPuzzle();
		resetButtons();
		successMessage("Successfully loaded EASY puzzle");
	}

	/** Load the medium sudoku puzzle to the GUI Board and display it. */
	public void loadMediumPuzzle (ActionEvent event) {
		guiBoard.loadMediumPuzzle();
		resetButtons();
		successMessage("Successfully loaded MEDIUM puzzle");
	}

	/** Load the hard sudoku puzzle to the GUI Board and display it. */
	public void loadHardPuzzle (ActionEvent event) {
		guiBoard.loadHardPuzzle();
		resetButtons();
		successMessage("Successfully loaded HARD puzzle");
	}

	/** Open the file explorerer to allow the user to import a CSV Sudoku file. */
	public void loadOutsidePuzzle (ActionEvent event) {
		File newFile = new FileChooser().showOpenDialog(messagePane.getScene().getWindow());
		if (newFile != null) {
			guiBoard.loadNewPuzzle(newFile.getAbsolutePath());
			resetButtons();
			successMessage("Successfully loaded EXTERNAL puzzle");
		} else {
			errorMessage("No file chosen");
		}
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
