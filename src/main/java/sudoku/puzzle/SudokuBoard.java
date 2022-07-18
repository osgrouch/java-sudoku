package sudoku.puzzle;

import sudoku.backtracking.Configuration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/** Class representing a 9x9 Sudoku board. Keeps track of the cells within the 9x9 board. */
public class SudokuBoard implements Configuration {
	/** The number of SudokuCell rows in this grid */
	public static final int rows = 9;
	/** The number of SudokuCell columns in this grid */
	public static final int cols = 9;

	/** 2D Array of SudokuCells in the puzzle */
	private final SudokuCell[][] board;

	/**
	 * Create a new SudokuBoard instance and populate the 2D Array of SudokuCells with the values
	 * from the given file. Does not check the file given is a file containing a Sudoku puzzle.
	 *
	 * @param filename a csv file containing a 9x9 Sudoku puzzle
	 */
	public SudokuBoard (String filename) {
		int[][] cellValues = new int[rows][cols];
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String line;
			int fileRow = 0;
			while (( line = br.readLine() ) != null) {
				String[] rowValues = line.split(",");
				for (int col = 0; col < cols; col++) {
					cellValues[fileRow][col] = Integer.parseInt(rowValues[col]);
				}
				++fileRow;
			}

			this.board = new SudokuCell[rows][cols];
			int region = 1;
			for (int row = 0; row < rows; row++) {
				for (int col = 0; col < cols; col++) {
					this.board[row][col] = new SudokuCell(region, row, col, cellValues[row][col]);
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
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Create a new SudokuBoard instance and populate the 2D Array of SudokuCells with clones of the SudokuCells
	 * in the given SudokuBoard 2D Array.
	 *
	 * @param other SudokuBoard to clone
	 */
	public SudokuBoard (SudokuBoard other) {
		this.board = new SudokuCell[rows][cols];
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				this.board[row][col] = new SudokuCell(other.board[row][col]);
			}
		}
	}

	/**
	 * Get all the SudokuCells within the given region.
	 * Used when verifying if current puzzle grid is a solution.
	 *
	 * @param region region to search for
	 * @return ArrayList of SudokuCells in the given region
	 */
	private ArrayList<SudokuCell> getRegion (int region) {
		ArrayList<SudokuCell> sudokuCells = new ArrayList<>(9); // there will only ever be 9 SudokuCells in a region
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				SudokuCell current = board[row][col];
				if (current.getRegion() == region) {
					sudokuCells.add(current);
				}
			}
		}
		return sudokuCells;
	}

	/**
	 * Get the given row of SudokuCells in the puzzle grid.
	 * Used when verifying if current puzzle grid is a solution.
	 *
	 * @param row row number
	 * @return Array of SudokuCells
	 */
	private SudokuCell[] getRow (int row) {
		return board[row];
	}

	/**
	 * Get the given column of SudokuCells in the puzzle grid.
	 * Used when verifying if current puzzle grid is a solution.
	 *
	 * @param col column number
	 * @return Array of SudokuCells
	 */
	private SudokuCell[] getCol (int col) {
		SudokuCell[] column = new SudokuCell[rows];
		for (int row = 0; row < rows; row++) {
			column[row] = board[row][col];
		}
		return column;
	}

	/**
	 * Get the SudokuCell at a given row and column number in the board (2D Array of SudokuCells).
	 *
	 * @param row row number
	 * @param col column number
	 * @return SudokuCell at the given coordinates
	 */
	public SudokuCell getSudokuCell (int row, int col) {
		return board[row][col];
	}

	/**
	 * Generate the successors to this instance's 2D Array of SudokuCells by placing a new number [1, 9]
	 * in the first empty SudokuCell found in the lowest numbered row and column.
	 *
	 * @return all successors, valid and invalid
	 */
	@Override
	public Collection<Configuration> getSuccessors () {
		Collection<Configuration> successors = new ArrayList<>();
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				if (getSudokuCell(row, col).getNumber() == 0) {
					for (int num = 1; num <= 9; ++num) {
						// create a copy of this board with this SudokuCell as every number in the range [1, 9]
						SudokuBoard newBoard = new SudokuBoard(this);
						newBoard.getSudokuCell(row, col).setNumber(num);
						successors.add(newBoard);
					}
					return successors;
				}
			}
		}
		return null;
	}

	/**
	 * Check if this SudokuBoard has any repeating numbers in all nine regions, rows and columns of the board.
	 * If a SudokuCell is set to 0, it is considered empty and skipped from the check, therefore, the number 0
	 * is allowed to be repeated across SudokuCells.
	 *
	 * @return true if this SudokuBoard is a solution
	 */
	@Override
	public boolean isValid () {
		// used to create arraylists below to check for repeating numbers
		final List<Integer> listOfNumbers = List.of(new Integer[]{ 1, 2, 3, 4, 5, 6, 7, 8, 9 });

		for (int region = 0; region < 9; region++) {
			// there will only be 9 regions in a 9x9 Sudoku puzzle
			ArrayList<SudokuCell> regionOfSudokuCells = getRegion(region);
			ArrayList<Integer> numbers = new ArrayList<>(listOfNumbers);

			for (SudokuCell sudokuCell : regionOfSudokuCells) {
				Integer num = sudokuCell.getNumber();
				if (num.equals(0)) {
					// skip checking the cell if it is empty
					continue;
				}
				if (!numbers.remove(num)) {
					// remove returns false only if the given object was not in the ArrayList
					// therefore a number was repeated in the region and this SudokuBoard is not a solution
					return false;
				}
			}
		}

		for (int row = 0; row < rows; row++) {
			SudokuCell[] rowOfSudokuCells = getRow(row);
			ArrayList<Integer> numbers = new ArrayList<>(listOfNumbers);

			for (SudokuCell sudokuCell : rowOfSudokuCells) {
				Integer num = sudokuCell.getNumber();
				if (num.equals(0)) {
					// skip checking the cell if it is empty
					continue;
				}
				if (!numbers.remove(num)) {
					// remove returns false only if the given object was not in the ArrayList
					// therefore a number was repeated in the row and this SudokuBoard is not a solution
					return false;
				}
			}
		}

		for (int col = 0; col < cols; col++) {
			SudokuCell[] colOfSudokuCells = getCol(col);
			ArrayList<Integer> numbers = new ArrayList<>(listOfNumbers);

			for (SudokuCell sudokuCell : colOfSudokuCells) {
				Integer num = sudokuCell.getNumber();
				if (num.equals(0)) {
					// skip checking the cell if it is empty
					continue;
				}
				if (!numbers.remove(num)) {
					// remove returns false only if the given object was not in the ArrayList
					// therefore a number was repeated in the column and this SudokuBoard is not a solution
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Check if this SudokuBoard is a solution to the Sudoku puzzle by checking the following conditions:
	 *
	 * <ol>
	 *     <li>Each region has no repeating numbers from [1, 9].</li>
	 *     <li>Each row has no repeating numbers from [1, 9].</li>
	 *     <li>Each column has no repeating numbers from [1, 9].</li>
	 * </ol>
	 *
	 * @return true if this SudokuBoard is a solution
	 */
	@Override
	public boolean isGoal () {
		// used to create arraylists below to check for repeating numbers
		final List<Integer> listOfNumbers = List.of(new Integer[]{ 1, 2, 3, 4, 5, 6, 7, 8, 9 });

		for (int region = 0; region < 9; region++) {
			// there will only be 9 regions in a 9x9 Sudoku puzzle
			ArrayList<SudokuCell> regionOfSudokuCells = getRegion(region);
			ArrayList<Integer> numbers = new ArrayList<>(listOfNumbers);

			for (SudokuCell sudokuCell : regionOfSudokuCells) {
				Integer num = sudokuCell.getNumber();
				if (!numbers.remove(num)) {
					// remove returns false only if the given object was not in the ArrayList
					// therefore a number was repeated in the region and this SudokuBoard is not a solution
					return false;
				}
			}
		}

		for (int row = 0; row < rows; row++) {
			SudokuCell[] rowOfSudokuCells = getRow(row);
			ArrayList<Integer> numbers = new ArrayList<>(listOfNumbers);

			for (SudokuCell sudokuCell : rowOfSudokuCells) {
				Integer num = sudokuCell.getNumber();
				if (!numbers.remove(num)) {
					// remove returns false only if the given object was not in the ArrayList
					// therefore a number was repeated in the row and this SudokuBoard is not a solution
					return false;
				}
			}
		}

		for (int col = 0; col < cols; col++) {
			SudokuCell[] colOfSudokuCells = getCol(col);
			ArrayList<Integer> numbers = new ArrayList<>(listOfNumbers);

			for (SudokuCell sudokuCell : colOfSudokuCells) {
				Integer num = sudokuCell.getNumber();
				if (!numbers.remove(num)) {
					// remove returns false only if the given object was not in the ArrayList
					// therefore a number was repeated in the column and this SudokuBoard is not a solution
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Create a String representation of the current state of the SudokuBoard by displaying the SudokuCell numbers
	 * distinctly seperated by regions.
	 *
	 * @return visual String representation of 2D Array of SudokuCells
	 */
	@Override
	public String toString () {
		StringBuilder str = new StringBuilder();
		for (int row = 0; row < rows; row++) {
			int number = board[row][0].getNumber();
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
				number = board[row][col].getNumber();
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
