package sudoku.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import sudoku.puzzle.SudokuCell;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class GUICell implements Initializable {
	/** The SudokuCell this GUICell represents graphically */
	private SudokuCell sudokuCell;
	/** The Group of nodes to visually display information on, about the SudokuCell */
	@FXML
	private Group group;
	/** The GridPane with buttons displaying annotations made on this SudokuCell */
	@FXML
	private GridPane annotationsGridPane;
	/** The Label displaying this SudokuCell's number */
	@FXML
	private Label cellNumberLabel;

	/** Used to set numbers pressed as annotations or as the number for the SudokuCell */
	private AtomicBoolean annotate;
	/** Used to indicate numbers pressed are to be removed from SudokuCell instead of added as annotations or its number */
	private AtomicBoolean erase;

	/**
	 * Create a new GUICell instance to display information about a SudokuCell in a Group node.
	 * This instance's SudokuCell will be set to null, and {@link GUICell#setSudokuCell(SudokuCell)} should be called
	 * afterwards to set its value.
	 */
	public GUICell () {
		this.sudokuCell = null;
		this.annotate = new AtomicBoolean(false);
		this.erase = new AtomicBoolean(false);
	}

	/**
	 * Update the SudokuCell this GUICell is to represent.
	 *
	 * @param sudokuCell new SudokuCell
	 */
	public void setSudokuCell (SudokuCell sudokuCell) {
		this.sudokuCell = sudokuCell;
		// updateDisplay();
	}

	/**
	 * Set the given number as this instance's SudokuCell number, if the SudokuCell does not contain a given number.
	 *
	 * @param num num to set
	 */
	public void setSudokuCellNumber (int num) {
		sudokuCell.setNumber(num);
		updateDisplay();
	}

	/**
	 * Remove this instance's SudokuCell number, if the SudokuCell does not contain a given number.
	 */
	public void removeSudokuCellNumber () {
		sudokuCell.removeNumber();
		updateDisplay();
	}

	/**
	 * Add the given number to the SudokuCell's annotations Set.
	 *
	 * @param num number to add
	 */
	public void addAnnotation (int num) {
		sudokuCell.addAnnotation(num);
		updateDisplay();
	}

	/**
	 * Remove the given number from the SudokuCell's annotations Set.
	 *
	 * @param num number to remove
	 */
	public void removeAnnotation (int num) {
		sudokuCell.removeAnnotation(num);
		updateDisplay();
	}

	/**
	 * Update the contents of this instance's Group to display new information about the SudokuCell.
	 */
	public void updateDisplay () {
		// display the annotations made for this SudokuCell
		Set<Integer> annotations = sudokuCell.getAnnotations();
		for (Node annotationNumBtn : annotationsGridPane.getChildren()) {
			if (annotations.contains(Integer.parseInt(( (Button) annotationNumBtn ).getText()))) {
				annotationNumBtn.setOpacity(1.0);
			} else {
				annotationNumBtn.setOpacity(0.0);
			}
		}

		// display or hide the label depending on number of SudokuCell number
		int number = sudokuCell.getNumber();
		if (number == 0) {
			// remove the number from the label
			cellNumberLabel.setText("");
			cellNumberLabel.setDisable(true);
			annotationsGridPane.setDisable(false);
		} else {
			// display the number set for this SudokuCell
			cellNumberLabel.setText(String.valueOf(number));
			if (sudokuCell.isGivenNumber()) {
				cellNumberLabel.setTextFill(Color.valueOf("#C33C54"));
				cellNumberLabel.setStyle("-fx-font-weight: bold");
			}
			cellNumberLabel.setDisable(false);
			annotationsGridPane.setDisable(true);
		}
	}

	/**
	 * Toggle annotate boolean flag to indicate any numbers pressed are annotations made to the SudokuCell.
	 */
	public void toggleAnnotate () {
		annotate.set(!annotate.get());
	}

	/**
	 * Toggle erase boolean flag to indicate any numbers pressed are to be erased from the SudokuCell.
	 */
	public void toggleErase () {
		erase.set(!erase.get());
	}

	/**
	 * @return this GUICell's Group of nodes
	 */
	public Group getGroup () {
		return group;
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
		for (Node annotationNumBtn : annotationsGridPane.getChildren()) {
			AtomicBoolean marked = new AtomicBoolean(false);    // has the button been pressed?
			// display on hover
			annotationNumBtn.setOnMouseEntered(event -> annotationNumBtn.setOpacity(1.0));
			// disappear when not hovered, only if not marked
			annotationNumBtn.setOnMouseExited(event -> {
				if (!marked.get()) {
					annotationNumBtn.setOpacity(0.0);
				}
			});

			( (Button) annotationNumBtn ).setOnAction(event -> {
				if (erase.get()) {
					// erase annotation
					if (marked.compareAndSet(true, false)) {
						removeAnnotation(Integer.parseInt(( (Button) annotationNumBtn ).getText()));
					}
				} else if (annotate.get()) {
					// set annotation
					if (marked.compareAndSet(false, true)) {
						addAnnotation(Integer.parseInt(( (Button) annotationNumBtn ).getText()));
					}
				} else {
					// set the number selected as this SudokuCell's number
					marked.set(false);
					setSudokuCellNumber(Integer.parseInt(( (Button) annotationNumBtn ).getText()));
				}
			});
		}
		cellNumberLabel.setOnMouseClicked(event -> {
			if (erase.get()) {
				removeSudokuCellNumber();
			}
		});
	}
}