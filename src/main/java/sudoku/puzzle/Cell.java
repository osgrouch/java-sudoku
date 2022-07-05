package sudoku.puzzle;

import java.util.HashSet;
import java.util.Set;

/**
 * Class representing a cell in a Sudoku puzzle. Keeps track of the number this Cell has been marked as,
 * and the pencil markings regarding what number this Cell could be. Also tracks what region this Cell
 * belongs to within the puzzle.
 */
public class Cell {
	/** The region within the Sudoku puzzle this Cell belongs to */
	private final int region;

	/** The number of this Cell in the Sudoku puzzle */
	private int number;
	/** The possible numbers of this Cell in the Sudoku puzzle */
	private Set<Integer> pencilMarks;

	/**
	 * Create a new Cell instance and initialize its private fields to a blank state.
	 */
	public Cell (int region) {
		this.region = region;
		this.number = 0;
		this.pencilMarks = new HashSet<>();
	}

	/**
	 * Adds the given number to this Cell's Set of pencil marks.
	 * The Set is used to keep track of the user's annotations for each Cell.
	 *
	 * @param num number to add
	 */
	public void addMark (int num) {
		this.pencilMarks.add(num);
	}

	/**
	 * Removes the given number from this Cell's Set of pencil marks.
	 * The Set is used to keep track of the user's annotations for each Cell.
	 *
	 * @param num number to remove
	 */
	public void removeMark (int num) {
		this.pencilMarks.remove(num);
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
}
