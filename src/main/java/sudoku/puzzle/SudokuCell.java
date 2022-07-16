package sudoku.puzzle;

import java.util.HashSet;
import java.util.Set;

/**
 * Class representing a cell in a Sudoku puzzle.
 * Keeps track of the number this SudokuCell has been marked as.
 * Also tracks what region this SudokuCell belongs to within the puzzle.
 */
public class SudokuCell {
	/** The region within the Sudoku puzzle this SudokuCell belongs to */
	private final int region;
	/** The row within the Sudoku puzzle this SudokuCell belongs to */
	private final int row;
	/** The column within the Sudoku puzzle this SudokuCell belongs to */
	private final int col;

	/** Does this SudokuCell contain a given number? */
	private final boolean givenNumber;
	/** The number of this SudokuCell in the Sudoku puzzle */
	private int number;

	/** The Set of possible numbers this SudokuCell could be */
	private Set<Integer> annotations;

	/**
	 * Create a new SudokuCell instance with the given region, row and column number, and SudokuCell number.
	 *
	 * @param region the region within the puzzle
	 * @param row    the row number of this SudokuCell
	 * @param col    the column number of this SudokuCell
	 * @param number the number of this SudokuCell, if given
	 */
	public SudokuCell (int region, int row, int col, int number) {
		this.region = region;
		this.row = row;
		this.col = col;
		this.number = number;
		this.givenNumber = ( number != 0 );
		this.annotations = new HashSet<>();
	}

	/**
	 * Make a copy of the given SudokuCell's current state, for use in a cloned GUICell in the GUIBoard's undoStack.
	 *
	 * @param other SudokuCell to copy
	 */
	public SudokuCell (SudokuCell other) {
		this.region = other.region;
		this.row = other.row;
		this.col = other.col;
		this.number = other.number;
		this.givenNumber = other.givenNumber;
		this.annotations = other.annotations;
	}

	/**
	 * Add the given number to the HashSet of possible numbers this SudokuCell could be.
	 *
	 * @param num number to add
	 */
	public void addAnnotation (int num) {
		if (!givenNumber) {
			this.annotations.add(num);
		}
	}

	/**
	 * Remove the given number from the HashSet of possible numbers this SudokuCell could be.
	 *
	 * @param num number to remove
	 */
	public void removeAnnotation (int num) {
		if (!givenNumber) {
			this.annotations.remove(num);
		}
	}

	/**
	 * Set this SudokuCell's number as the given number, and remove all annotations for the possible numbers
	 * of this SudokuCell
	 *
	 * @param num number to set in this SudokuCell
	 */
	public void setNumber (int num) {
		if (!givenNumber) {
			this.number = num;
			this.annotations = new HashSet<>();
		}
	}

	/**
	 * Reset this SudokuCell's number back to 0, indicating the number is "erased," only if this SudokuCell
	 * does not contain a given number.
	 */
	public void removeNumber () {
		if (!givenNumber) {
			this.number = 0;
		}
	}

	/**
	 * @return the region this SudokuCell belongs to
	 */
	public int getRegion () {
		return region;
	}

	/**
	 * @return the row this SudokuCell belongs to
	 */
	public int getRow () {
		return row;
	}

	/**
	 * @return the column this SudokuCell belongs to
	 */
	public int getCol () {
		return col;
	}

	/**
	 * @return the number within this SudokuCell
	 */
	public int getNumber () {
		return number;
	}

	/**
	 * @return givenNumber boolean value
	 */
	public boolean isGivenNumber () {
		return givenNumber;
	}

	/**
	 * @return Set of possible numbers this SudokuCell could be
	 */
	public Set<Integer> getAnnotations () {
		return annotations;
	}

	/**
	 * Create a String representation of this SudokuCell using its number.
	 *
	 * @return String with this SudokuCell's number
	 */
	@Override
	public String toString () {
		return "#" + number + ", Region: " + region;
	}
}
