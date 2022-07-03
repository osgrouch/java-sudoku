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
	 * Creates a 2D Array with the given dimensions and populates the Array with blank T elements.
	 *
	 * @param T    T.class of the elements to be used in the 2D Array
	 * @param rows the number of rows in the 2D Array to create
	 * @param cols the number of columns in the 2D Array to create
	 */
	public Box (Class<?> T, int rows, int cols) {
		this.Type = T;
		this.rows = rows;
		this.cols = cols;
	}

	/**
	 * Create a 2D Array with the dimensions previously set in this instance,
	 * and populate the array with blank T elements. The constructor for the elements of type T
	 * must have no arguments for this method to work.
	 */
	public void createArray () {
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
	 * Create a 2D Array with the dimensions previously set in this instance,
	 * and populate the array with T elements using the given Array of objects as constructor arguments of class Y.
	 * <p>If the T class constructor requires primative values, the arguments passed to this method can be
	 * boxed to their reference types, e.g. {@code (Integer.class, new Integer[]{...})}, which will be auto-unboxed
	 * when creating the objects of type T.</p>
	 * <p>NOTE: The elements within the 2D Array will all be created with the same constructor arguments
	 * provided as initArgs argument.</p>
	 *
	 * @param Y        Y.class of the elements to be used in the 2D Array, see
	 *                 {@link java.lang.Class#getDeclaredConstructor(Class[])}
	 * @param initArgs array of objects to be used as constructor arguments, see
	 *                 {@link java.lang.reflect.Constructor#newInstance(Object...)}
	 */
	public void createArray (Class<?> Y, Object[] initArgs) {
		this.grid = (T[][]) Array.newInstance(Type, rows, cols);
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				try {
					this.grid[row][col] = (T) Type.getDeclaredConstructor(Y).newInstance(initArgs);
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
