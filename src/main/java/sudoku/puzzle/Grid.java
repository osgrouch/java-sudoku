package sudoku.puzzle;

import java.util.ArrayList;
import java.util.List;

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
	 * Get all the Cells within the given region.
	 * Used when verifying if current puzzle grid is a solution.
	 *
	 * @param region region to search for
	 * @return ArrayList of Cells in the given region
	 */
	private Cell[] getRegion (int region) {
		ArrayList<Cell> cells = new ArrayList<>(9); // there will only ever be 9 Cells in a region
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				Cell current = grid[row][col];
				if (current.getRegion() == region) {
					cells.add(current);
				}
			}
		}
		return (Cell[]) cells.toArray();
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

	/**
	 * Check if this Grid is a solution to the Sudoku puzzle by checking the following conditions:
	 *
	 * <ol>
	 *     <li>Each region has no repeating numbers from [1, 9].</li>
	 *     <li>Each row has no repeating numbers from [1, 9].</li>
	 *     <li>Each column has no repeating numbers from [1, 9].</li>
	 * </ol>
	 *
	 * @return true if this Grid is a solution
	 */
	public boolean isSolution () {
		final List<Integer> listOfNumbers = List.of(new Integer[]{ 1, 2, 3, 4, 5, 6, 7, 8, 9 });

		for (int region = 0; region < 9; region++) {
			// there will only be 9 regions in a 9x9 Sudoku puzzle
			Cell[] regionOfCells = getRegion(region);
			ArrayList<Integer> numbers = new ArrayList<>(listOfNumbers);

			for (Cell cell : regionOfCells) {
				Integer num = cell.getNumber();
				if (!numbers.remove(num)) {
					// remove returns false only if the given object was not in the ArrayList
					// therefore a number was repeated in the region and this Grid is not a solution
					return false;
				}
			}
		}

		return true;
	}
}
