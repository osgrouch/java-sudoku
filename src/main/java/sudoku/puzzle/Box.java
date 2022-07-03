package sudoku.puzzle;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;

/**
 * Class representing a 2D Array of T elements.
 * Has methods to retrieve elements from a given row or column of the 2D Array.
 */
public class Box<T> {
	/** The Class metadata of the generic class T */
	private final Class<?> Type;
	/** The number of rows of the grid to be created */
	private int rows;
	/** The number of columns of the grid to be created */
	private int cols;
	/** A 2D Array of T elements */
	private T[][] grid;

	/**
	 * Create a new Box instance of type T with the given rows and columns.
	 */
	public Box (Class<?> T, int rows, int cols) {
		this.Type = T;
		this.rows = rows;
		this.cols = cols;
		this.grid = (T[][]) Array.newInstance(Type, rows, cols);
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				try {
					this.grid[row][col] = (T) Type.getDeclaredConstructor().newInstance();
				} catch (InstantiationException | InvocationTargetException |
				         IllegalAccessException | NoSuchMethodException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	/**
	 * Get all the elements within the given row of this Box.
	 *
	 * @param row row number to retrieve from
	 * @return Array of T elements
	 */
	public T[] getRow (int row) {
		return grid[row];
	}

	/**
	 * Get all the elements within the given column of this Box.
	 *
	 * @param col column number to retrieve from
	 * @return Array of T elements
	 */
	public T[] getCol (int col) {
		T[] column = (T[]) Array.newInstance(Type, rows);
		for (int row = 0; row < rows; row++) {
			column[row] = grid[row][col];
		}
		return column;
	}
}
