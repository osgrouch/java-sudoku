package sudoku.puzzle;

/**
 * Class representing a cell in a Sudoku puzzle.
 * Keeps track of the number this Cell has been marked as.
 * Also tracks what region this Cell belongs to within the puzzle.
 */
public class Cell {
	/** The region within the Sudoku puzzle this Cell belongs to */
	private final int region;

	/** The number of this Cell in the Sudoku puzzle */
	private int number;
	/**
	 * Is this number a given number?
	 * Used to apply different rules to Cells with given numbers (can't change the number, can't annotate)
	 * and to color them differently to indicate they're given numbers.
	 */
	private boolean givenNumber;

	/**
	 * Create a new Cell instance and initialize its private fields to a blank state.
	 */
	public Cell (int region, int number) {
		this.region = region;
		this.number = number;
		this.givenNumber = ( number != 0 );
	}

	/**
	 * Reset this Cell's number back to 0, indicating the number is "erased."
	 * Then call method to update this Cell's Group for graphical display.
	 * Only performs the above if this Cell is not a givenNumber, a boolean flag set during creation of this Cell.
	 */
	public void removeNumber () {
		if (!givenNumber) {
			this.number = 0;
		}
	}

	/**
	 * Set this Cell's number, then call method to update this Cell's Group for graphical display.
	 *
	 * @param number number to set in this Cell
	 */
	public void setNumber (int number) {
		if (!givenNumber) {
			this.number = number;
		}
	}

	/**
	 * @return the region this Cell belongs to
	 */
	public int getRegion () {
		return region;
	}

	/**
	 * @return the number within this Cell
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
	 * Create a String representation of this Cell using its number.
	 *
	 * @return String with this Cell's number
	 */
	@Override
	public String toString () {
		return "#" + number + ", Region: " + region;
	}
}
