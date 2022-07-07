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
	 * Create a new Cell instance and initialize its private fields to a blank state.
	 */
	public Cell (int region, int number) {
		this.region = region;
		this.number = number;
	}

	/**
	 * Set this Cell's number. Displayed in the GUI and used when verifying puzzle solution.
	 *
	 * @param number number to set in this Cell
	 */
	public void setNumber (int number) {
		this.number = number;
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
	 * Create a String representation of this Cell using its number.
	 *
	 * @return String with this Cell's number
	 */
	@Override
	public String toString () {
		return "#" + number + ", Region: " + region;
	}
}
