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

	/** The number of this SudokuCell in the Sudoku puzzle */
	private int number;
	/** Does this SudokuCell contain a given number? */
	private boolean givenNumber;

	/** The Set of possible numbers this SudokuCell could be */
	private Set<Integer> annotations;

	/**
	 * Create a new SudokuCell instance with the given region number and SudokuCell number.
	 */
	public SudokuCell (int region, int number) {
		this.region = region;
		this.number = number;
		this.givenNumber = ( number != 0 );
		this.annotations = new HashSet<>();
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
