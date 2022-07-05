package sudoku.puzzle;

/**
 * Class representing a 9x9 Sudoku grid.
 * Keeps track of the Cells within the 9x9 grid.
 */
public class Grid {
	/** The number of Cell rows in this grid */
	public static final int rows = 9;
	/** The number of Cell columns in this grid */
	public static final int cols = 9;

	/** 2D Array of Cells in the puzzle */
	private Cell[][] grid;

	public Grid () {
		this.grid = new Cell[rows][cols];
		int region = 1;
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				this.grid[row][col] = new Cell(region);
				if (( col + 1 ) % 3 == 0) {
					++region;
				}
			}
		}
	}
}
