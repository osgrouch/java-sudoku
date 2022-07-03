package sudoku;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Launches the Sudoku GUI using fxml files.
 */
public class App extends Application {
	/**
	 * Start this application with the given arguments.
	 *
	 * @param args command line arguments
	 */
	public static void main (String[] args) {
		Application.launch(args);
	}

	/**
	 * The main entry point for all JavaFX applications.
	 * The start method is called after the init method has returned,
	 * and after the system is ready for the application to begin running.
	 * <p>
	 * NOTE: This method is called on the JavaFX Application Thread.
	 * </p>
	 *
	 * @param stage the primary stage for this application, onto which
	 *              the application scene can be set.
	 *              Applications may create other stages, if needed, but they will not be
	 *              primary stages.
	 */
	@Override public void start (Stage stage) {
		try {
			stage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
			stage.setTitle("JavaFX Sudoku");
			stage.setResizable(false);

			Parent root = FXMLLoader.load(getClass().getResource("puzzle.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
