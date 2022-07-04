package sudoku.puzzle;

import java.util.ArrayList;
import java.util.Arrays;

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

	/**
	 * Check the entire grid is valid (has only the numbers [1, 9] with no repeating digts) by:
	 *
	 * <p>1. Checking each Nonet is valid.</p>
	 * <p>2. Checking every row is valid.</p>
	 * <p>3. Checking every column is valid.</p>
	 *
	 * @return true if all three conditions are satisfied
	 */
	public boolean isValid () {
		boolean validNonets = true;
		boolean validRows = true;
		boolean validCols = true;

		// check all the Nonets are valid
		for (int row = 0; row < rows; row++) {
			Nonet[] nonetRow = getRow(row);
			for (int col = 0; col < cols; col++) {
				validNonets = nonetRow[col].isValid();
				if (!validNonets) break;
			}
			if (!validNonets) break;
		}

		// check all the rows in the grid
		ArrayList<Integer> rowNumbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));

		// check all the columns in the grid
		ArrayList<Integer> colNumbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));

		return validNonets && validRows && validCols;
	}
}
