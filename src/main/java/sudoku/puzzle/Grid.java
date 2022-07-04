package sudoku.puzzle;

/**
 * Class representing a Sudoku grid with nine 3x3 regions of cells (a Nonet).
 * Can return the Nonets belonging to a particular row or column within this grid.
 */
public class Grid {
	/** The number of rows in this region */
	public static final int rows = 3;
	/** The number of columns in this region */
	public static final int cols = 3;

	/** A Box (2D Array) of Nonets within this region */
	private Box<Nonet> nonets;

	/**
	 * Create a new Grid with a blank Box of Nonets.
	 */
	public Grid () {
		this.nonets = new Box<>(Nonet.class, rows, cols);
		this.nonets.createArray();
	}

	/**
	 * Get all the Nonets within the given row from this Grid.
	 *
	 * @param row row number to retrieve from
	 * @return Array of Nonets
	 */
	public Nonet[] getRow (int row) {
		return nonets.getRow(row);
	}

	/**
	 * Get all the Nonets within the given column from this Grid.
	 *
	 * @param col column number to retrieve from
	 * @return Array of Nonets
	 */
	public Nonet[] getCol (int col) {
		return nonets.getCol(col);
	}
}
