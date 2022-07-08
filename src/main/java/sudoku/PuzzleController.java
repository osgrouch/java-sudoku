package sudoku;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import sudoku.puzzle.Cell;
import sudoku.puzzle.Grid;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Controller for user interactions with Puzzle view in puzzle.fxml.
 */
public class PuzzleController implements Initializable {
	/** The GridPane object that displays the different action buttons in the top section of the window */
	@FXML
	private GridPane actionBar;
	/** Undo button in the actionBar */
	@FXML
	private Button undoBtn;
	/** Redo button in the actionBar */
	@FXML
	private Button redoBtn;
	/** Reset button in the actionBar */
	@FXML
	private Button resetBtn;

	/**
	 * Annotations button in the actionBar, controls whether to set the numbers pressed as annotations
	 * or as the number value of the Cell
	 */
	@FXML
	private Button annotationBtn;
	/** Has the annotate button been pressed? */
	private boolean annotate;

	/**
	 * Erase button in the actionBar, controls whether to set the number pressed or "erase" the number pressed,
	 * on both annotations and number value of Cells
	 */
	@FXML
	private Button eraseBtn;
	/** Hass the erase button been pressed? */
	private boolean erase;

	/** The GridPane object that displays the Sudoku puzzle */
	@FXML
	private GridPane board;
	/** The Sudoku Grid class with the different Cells */
	private Grid grid;

	/** Default constructor. */
	public PuzzleController () {
		this.grid = new Grid("input/sample_puzzle.csv");
	}

	/**
	 * Called to initialize a controller after its root element has been completely processed.
	 *
	 * @param location  The location used to resolve relative paths for the root object, or
	 *                  {@code null} if the location is not known.
	 * @param resources The resources used to localize the root object, or {@code null} if
	 *                  the root object was not localized.
	 */
	@Override
	public void initialize (URL location, ResourceBundle resources) {
		// TODO: update docstring when fully done with method
		// iterate through each button in the top actionBar to add background when hovered
		for (Node node : actionBar.getChildren()) {
			node.setOnMouseEntered(event -> node.setStyle("-fx-background-color: #D5DCE0;"));
			node.setOnMouseExited(event -> node.setStyle("-fx-background-color: transparent;"));
		}

		// set annotation button behavior
		annotationBtn.setOnAction(event -> {
			if (!annotate) {
				annotationBtn.setText("Annotations: ON");
				this.annotate = true;
			} else {
				annotationBtn.setText("Annotations: OFF");
				this.annotate = false;
			}
			if (erase) {
				eraseBtn.fire();
			}
		});

		// set erase button behavior
		eraseBtn.setOnAction(event -> {
			if (!erase) {
				eraseBtn.setText("Erase: ON");
				this.erase = true;
			} else {
				eraseBtn.setText("Erase: OFF");
				this.erase = false;
			}
		});

		// set graphics for the undo and redo buttons
		ImageView undoImg = new ImageView(new Image(getClass().getResourceAsStream("undo-arrow.png")));
		undoImg.setFitHeight(20);
		undoImg.setPreserveRatio(true);
		undoImg.setOpacity(0.2);
		ImageView redoImg = new ImageView(new Image(getClass().getResourceAsStream("redo-arrow.png")));
		redoImg.setFitHeight(20);
		redoImg.setPreserveRatio(true);
		redoImg.setOpacity(0.2);
		undoBtn.setGraphic(undoImg);
		redoBtn.setGraphic(redoImg);

		// iterate through each Sudoku annotation button to add display on hover and toggle display on press
		ArrayList<Cell> cells = grid.getGridAsArrayList();
		for (Node cellGroup : board.getChildren()) {
			// link each cell with its corresponding Group node
			GraphicalCell link = new GraphicalCell(cells.remove(0), (Group) cellGroup);
			for (Node annotationNumBtn : ( (GridPane) ( (Group) cellGroup ).getChildren().get(0) ).getChildren()) {
				AtomicBoolean marked = new AtomicBoolean(false);    // has the button been pressed?
				// display on hover
				annotationNumBtn.setOnMouseEntered(event -> annotationNumBtn.setOpacity(1.0));
				// disappear when not hovered, only if not marked
				annotationNumBtn.setOnMouseExited(event -> {
					if (!marked.get()) {
						annotationNumBtn.setOpacity(0.0);
					}
				});
				// toggle button display when clicked based on its previous display state
				( (Button) annotationNumBtn ).setOnAction(event -> {
					if (erase) {
						// erase annotation
						if (marked.compareAndSet(true, false)) {
							annotationNumBtn.setOpacity(0.0);
						}
					} else if (annotate) {
						// display annotation
						if (marked.compareAndSet(false, true)) {
							annotationNumBtn.setOpacity(1.0);
						} else {
							marked.set(false);
							annotationNumBtn.setOpacity(0.0);
						}
					} else {
						// set the number selected as this Cell's number
						marked.set(false);
						link.setNumber(Integer.parseInt(( (Button) annotationNumBtn ).getText()));
					}
				});
			}
			link.updateGroupContents();

			// erase numbers from cells
			Label cellLabel = link.getLabel();
			cellLabel.setOnMouseClicked(event -> {
				if (erase) {
					link.removeNumber();
				}
			});
		}
	}
}
