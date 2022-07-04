package sudoku.puzzle;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class representing a 3x3 region of cells in a Sudoku puzzle. Keeps track of the cells within the 3x3 region
 * and can return the Cells belonging to a particular row or column within this region.
 */
public class Nonet {
	/** The number of rows in this region */
	public static final int rows = 3;
	/** The number of columns in this region */
	public static final int cols = 3;

	/** A Box (2D Array) of Cells within this region */
	private Box<Cell> cells;

	/**
	 * Create a new Nonet with a blank Box of Cells.
	 */
	public Nonet () {
		this.cells = new Box<>(Cell.class, rows, cols);
		this.cells.createArray();
	}

	/**
	 * Get all the Cells within the given row from this Nonet.
	 *
	 * @param row row number to retrieve from
	 * @return Array of Cells
	 */
	public Cell[] getRow (int row) {
		return cells.getRow(row);
	}

	/**
	 * Get all the Cells within the given column from this Nonet.
	 *
	 * @param col column number to retrieve from
	 * @return Array of Cells
	 */
	public Cell[] getCol (int col) {
		return cells.getCol(col);
	}

	/**
	 * Check that the Nonet is valid by checking that each Cell has no repeating digits
	 * and all are in the range [1, 9].
	 *
	 * @return true if Nonet is valid
	 */
	public boolean isValid () {
		boolean result = true;
		ArrayList<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
		for (int row = 0; row < rows; row++) {
			Cell[] cellRow = getRow(row);
			for (int col = 0; col < cols; col++) {
				// remove will return true if the Integer object was found in the ArrayList of Integers
				// meaning if the method returns false, the Cell was not set, or it repeated a digit
				result = numbers.remove(Integer.valueOf(cellRow[col].getDigit()));
				if (!result) break;
			}
			if (!result) break;
		}
		return result;
	}
}
