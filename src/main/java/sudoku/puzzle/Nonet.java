package sudoku.puzzle;

/**
 * Class representing a 3x3 region of cells in a Sudoku puzzle. Keeps track of the cells within the 3x3 region
 * and can return the Cells belonging to a particular row or column within this region.
 */
public class Nonet {
	/** The number of rows in this region */
	public static final int rows = 3;
	/** The number of columns in this region */
	public static final int cols = 3;

	/** A 2D Array of Cells within this region */
	private Cell[][] cells;

	/**
	 * Create a new Nonet with a blank Array of Cells.
	 */
	public Nonet () {
		this.cells = new Cell[rows][cols];
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				this.cells[row][col] = new Cell();
			}
		}
	}

	/**
	 * Get all the Cells within the given row from this Nonet.
	 *
	 * @param row row number to retrieve from
	 * @return Array of Cells
	 */
	public Cell[] getRow (int row) {
		return cells[row];
	}

	/**
	 * Get all the Cells within the given column from this Nonet.
	 *
	 * @param col column number to retrieve from
	 * @return Array of Cells
	 */
	public Cell[] getCol (int col) {
		Cell[] column = new Cell[3];
		for (int row = 0; row < rows; row++) {
			column[row] = cells[row][col];
		}
		return column;
	}
}
