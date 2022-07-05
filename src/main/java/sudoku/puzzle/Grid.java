package sudoku.puzzle;

/**
 * Class representing a 9x9 Sudoku grid.
 * Keeps track of the Cells within the 9x9 grid.
 */
public class Grid {
	/** The number of Cell rows in this grid */
	public static final int gridRows = 9;
	/** The number of Cell columns in this grid */
	public static final int gridCols = 9;

	/** The number of Cell rows in a region, also the number of regions in a column */
	public static final int regionRows = 3;
	/** The number of Cell columns in a region, also the number of regions in a row */
	public static final int regionCols = 3;

	/** 2D Array of Cells in the puzzle */
	private Cell[][] grid;

	public Grid () {
		this.grid = new Cell[gridRows][gridCols];
		int region = 1;
		for (int row = 0; row < gridRows; row++) {
			for (int col = 0; col < gridCols; col++) {
				this.grid[row][col] = new Cell(region);
				if (( col + 1 ) % regionCols == 0) {
					// increment the region every time regionCols count has been reached
					++region;
				}
			}
			if (( ( row + 1 ) % regionRows ) != 0) {
				// only allow region to be incremented every time regionRows count has been reached
				// subtract by the number of columns in a region which is also the number of regions in a row
				region -= regionCols;
			}
		}
	}
}
