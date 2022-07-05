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
					// increment the region every 3 columns
					++region;
				}
			}
			if (( ( row + 1 ) % 3 ) != 0) {
				// only allow region to be incremented every 3 rows
				// subtract by the number of regions in a row: 3
				region -= 3;
			}
		}
	}

	/**
	 * Get the given row of Cells in the puzzle grid.
	 * Used when verifying if current puzzle grid is a solution.
	 *
	 * @param row row number
	 * @return Array of Cells
	 */
	private Cell[] getGridRow (int row) {
		return grid[row];
	}

	/**
	 * Get the given column of Cells in the puzzle grid.
	 * Used when verifying if current puzzle grid is a solution.
	 *
	 * @param col column number
	 * @return Array of Cells
	 */
	private Cell[] getGridCol (int col) {
		Cell[] column = new Cell[rows];
		for (int row = 0; row < rows; row++) {
			column[row] = grid[row][col];
		}
		return column;
	}
}
