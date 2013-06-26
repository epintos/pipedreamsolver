package back.board;

import java.awt.Point;
import java.util.HashMap;

import back.cells.BlankCell;
import back.cells.Cell;
import back.cells.Direction;
import back.exceptions.PointAllreadyContainsCellException;

public class Board {

	private HashMap<Point, Cell> board;
	private int rows;
	private int cols;
	private Direction waterDirection;
	private int pipesQuantity[];
	private Point start;
	private int maxLength = 0;

	public Board(int rows, int cols) {
		if (rows <= 0 || cols <= 0)
			throw new IllegalArgumentException();
		this.rows = rows;
		this.cols = cols;
		board = new HashMap<Point, Cell>();
	}

	/**
	 * Adds a given cell in the given position (col,row).
	 * 
	 * @param row
	 * @param col
	 * @param cell
	 * @throws PointAllreadyContainsCellException
	 */
	public void add(int row, int col, Cell cell)
			throws PointAllreadyContainsCellException {

		if (isOutOfMap(new Point(col, row)))
			throw new ArrayIndexOutOfBoundsException();

		if (getElemIn(row, col).getClass() != BlankCell.class)
			throw new PointAllreadyContainsCellException();
		board.put(new Point(row, col), cell);
	}

	/**
	 * Returns the current direction of the water in the board.
	 * 
	 * @return
	 */
	public Direction getWaterDirection() {
		return waterDirection;
	}

	/**
	 * Returns an element in an specific Row and Column
	 * 
	 * @throws ArrayIndexOutOfBoundsException
	 *             If the parameter row or col exceeds the board size
	 * @param row
	 * @param col
	 * @return Cell
	 */
	public Cell getElemIn(int row, int col) {
		if (rows <= row || cols <= col)
			throw new ArrayIndexOutOfBoundsException();
		if (board.get(new Point(row, col)) == null)
			return new BlankCell(new Point(col, row));
		return board.get(new Point(row, col));
	}

	/**
	 * Returns the array that represents the amount of each type of pipes
	 * available.
	 * 
	 * @return
	 */
	public int[] getPipesQuantity() {
		return pipesQuantity;
	}

	/**
	 * Returns the start point in the board.
	 * 
	 * @return
	 */
	public Point getStart() {
		return start;
	}

	/**
	 * Returns true if the point's coordinates exceed the board size.
	 * 
	 * @param p
	 * @return True if the point's coordinates exceed the board size, false
	 *         otherwise.
	 */
	public boolean isOutOfMap(Point p) {
		if (p.y >= rows || p.y < 0 || p.x >= cols || p.x < 0)
			return true;
		return false;
	}

	/**
	 * Sets the actual water direction of the board to the given parameter.
	 * 
	 * @param waterDirection
	 */
	public void setWaterDirection(Direction waterDirection) {
		this.waterDirection = waterDirection;
	}

	/**
	 * Returns how many columns the board has.
	 * 
	 * @return The number of columns, as an int.
	 */
	public int getCols() {
		return cols;
	}

	/**
	 * Returns how many rows the board has.
	 * 
	 * @return The number of rows, as an int.
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * Returns the actual board.
	 * 
	 * @return
	 */
	public HashMap<Point, Cell> getBoard() {
		return board;
	}

	/**
	 * Sets the pipes quantity of each type to the parameter given.
	 * 
	 * @param pipesQuantity
	 */
	public void setPipesQuantity(int[] pipesQuantity) {
		this.pipesQuantity = pipesQuantity;
	}

	/**
	 * Sets the start point of the board to the parameter given.
	 * 
	 * @param point
	 */
	public void setStart(Point point) {
		start = point;

	}

	/**
	 * Prints the current board with all of its elements.
	 */
	public void print() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				System.out.print(getElemIn(j, i));
			}
			System.out.println();
		}
	}

	/**
	 * Removes the piece in the given (row,col) position. Throws
	 * ArrayIndexOutOfBoundsException if the position exceeds the board size.
	 * 
	 * @param row
	 * @param col
	 */
	public void remove(int row, int col) {
		if (rows <= row || cols <= col)
			throw new ArrayIndexOutOfBoundsException();
		board.remove(new Point(row, col));
	}

	/**
	 * Sets the maximum length that a path can have in this board, according to
	 * the amount of pieces it has.
	 * 
	 * @param maxLength
	 */
	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	/**
	 * Returns the maximum length that a path can have in this board.
	 * @return The maximum length that a path can have in this board, as an int.
	 */
	public int getMaxLength() {
		return this.maxLength;
	}
}
