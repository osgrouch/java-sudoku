package sudoku.puzzle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
	private final Cell[][] grid;

	/**
	 * Create a new Grid instance and populate the 2D Array of Cells with the values
	 * from the given file. Does not check the file given is a file containing a Sudoku puzzle.
	 *
	 * @param filename a csv file containing a 9x9 Sudoku puzzle
	 */
	public Grid (String filename) {
		int[][] cellValues = new int[rows][cols];
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String line;
			int row = 0;
			while (( line = br.readLine() ) != null) {
				String[] rowValues = line.split(",");
				for (int col = 0; col < cols; col++) {
					cellValues[row][col] = Integer.parseInt(rowValues[col]);
				}
				++row;
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		this.grid = new Cell[rows][cols];
		int region = 1;
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				this.grid[row][col] = new Cell(region, cellValues[row][col]);
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
	 * Get the 2D Array of Cells stored within this Grid instance as an ArrayList of Cells
	 * from left to right per row, starting at row 0 and ending at row 8.
	 *
	 * @return ArrayList of Cells
	 */
	public ArrayList<Cell> getGridAsArrayList () {
		ArrayList<Cell> cells = new ArrayList<>(81);
		for (int row = 0; row < rows; row++) {
			cells.addAll(List.of(getGridRow(row)));
		}
		return cells;
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
		// used to create arraylists below to check for repeating numbers
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

		for (int row = 0; row < rows; row++) {
			Cell[] rowOfCells = getGridRow(row);
			ArrayList<Integer> numbers = new ArrayList<>(listOfNumbers);

			for (Cell cell : rowOfCells) {
				Integer num = cell.getNumber();
				if (!numbers.remove(num)) {
					// remove returns false only if the given object was not in the ArrayList
					// therefore a number was repeated in the row and this Grid is not a solution
					return false;
				}
			}
		}

		for (int col = 0; col < cols; col++) {
			Cell[] colOfCells = getGridCol(col);
			ArrayList<Integer> numbers = new ArrayList<>(listOfNumbers);

			for (Cell cell : colOfCells) {
				Integer num = cell.getNumber();
				if (!numbers.remove(num)) {
					// remove returns false only if the given object was not in the ArrayList
					// therefore a number was repeated in the column and this Grid is not a solution
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Create a String representation of the current state of the Grid by displaying the Cell numbers
	 * distinctly seperated by regions.
	 *
	 * @return visual String representation of 2D Array of Cells
	 */
	@Override
	public String toString () {
		StringBuilder str = new StringBuilder();
		for (int row = 0; row < rows; row++) {
			int number = grid[row][0].getNumber();
			if (number == 0) {
				str.append(".");
			} else {
				str.append(number);
			}
			for (int col = 1; col < cols; col++) {
				if (( col % 3 ) == 0) {
					str.append(" | ");
				} else {
					str.append(" ");
				}
				number = grid[row][col].getNumber();
				if (number == 0) {
					str.append(".");
				} else {
					str.append(number);
				}
			}
			str.append("\n");
			if (( ( row + 1 ) != rows ) && ( ( row + 1 ) % 3 ) == 0) {
				for (int col = 0; col < cols; col++) {
					if (col == 0) {
						str.append("-");
					} else if (( col % 3 ) == 0) {
						str.append("----");
					} else {
						str.append("--");
					}
				}
				str.append("\n");
			}
		}
		return str.toString();
	}
}
