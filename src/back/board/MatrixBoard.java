package back.board;

import java.awt.Point;

import back.cells.BlankCell;
import back.cells.Cell;
import back.cells.Direction;
import back.exceptions.PointAllreadyContainsCellException;

/**
 * Class which represents a Board as a matrix of Cells, which contains the
 * initial water direction, the number of initial pipes, a start point and a max
 * number which represent the maximum path that can be built according to the
 * initial pipe quantity
 */
public class MatrixBoard {

	private Cell[][] board;
	private Direction waterDirection;
	private int pipesQuantity[];
	private Point start;
	private int maxLength = 0;

	public MatrixBoard(int rows, int cols) {
		if (rows <= 0 || cols <= 0)
			throw new IllegalArgumentException();
		board = new Cell[rows][cols];
	}

	/**
	 * Adds a Cell to a row and col in the Board
	 * 
	 * @param row
	 * @param col
	 * @param cell
	 * @throws PointAllreadyContainsCellException
	 *             If the Board already contains a Cell different from a Blank
	 *             Cell in that point ArrayIndexOutOfBoundsException If the
	 *             point is out of the board
	 */
	public void add(int row, int col, Cell cell)
			throws PointAllreadyContainsCellException {

		if (isOutOfBoard(new Point(col, row)))
			throw new ArrayIndexOutOfBoundsException();

		if (getElemIn(row, col).getClass() != BlankCell.class)
			throw new PointAllreadyContainsCellException();
		board[row][col] = cell;
	}

	/**
	 * Returns the initial water direction
	 * 
	 * @return
	 */
	public Direction getWaterDirection() {
		return waterDirection;
	}

	/**
	 * Returns a Cell in the position (row,col)
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	public Cell getElemIn(int row, int col) {
		if (board.length <= row || board[0].length <= col)
			throw new ArrayIndexOutOfBoundsException();
		if (board[row][col] == null)
			return new BlankCell(new Point(col, row));
		return board[row][col];
	}

	/**
	 * Returns the initial pipes quantity
	 * 
	 * @return array with pipes
	 */
	public int[] getPipesQuantity() {
		return pipesQuantity;
	}

	/**
	 * Returns the start point
	 * 
	 * @return start Point
	 */
	public Point getStart() {
		return start;
	}

	/**
	 * Returns true if a point is out of the Board limits
	 * 
	 * @param point
	 * @return boolean
	 */
	public boolean isOutOfBoard(Point point) {
		if (point.y >= board.length || point.y < 0
				|| point.x >= board[0].length || point.x < 0)
			return true;
		return false;
	}

	/**
	 * Sets the initial water direction
	 * 
	 * @param waterDirection
	 */
	public void setWaterDirection(Direction waterDirection) {
		this.waterDirection = waterDirection;
	}

	/**
	 * Returns the cols size
	 * 
	 * @return cols size
	 */
	public int getCols() {
		return board[0].length;
	}

	/**
	 * Returns the rows size
	 * 
	 * @return rows size
	 */
	public int getRows() {
		return board.length;
	}

	/**
	 * Returns the Board
	 * 
	 * @return Board
	 */
	public Cell[][] getBoard() {
		return board;
	}

	/**
	 * Sets the initial pipes quantity
	 * 
	 * @param pipesQuantity
	 */
	public void setPipesQuantity(int[] pipesQuantity) {
		this.pipesQuantity = pipesQuantity;
	}

	/**
	 * Sets the start point
	 * 
	 * @param point
	 */
	public void setStart(Point point) {
		start = point;

	}

	/**
	 * Removes a Cell from (row,col)
	 * 
	 * @param row
	 * @param col
	 */
	public void remove(int row, int col) {
		if (board.length <= row || board[0].length <= col)
			throw new ArrayIndexOutOfBoundsException();
		board[row][col] = null;
	}

	/**
	 * Sets the max length a path can have
	 * 
	 * @param maxLength
	 */
	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	/**
	 * Returns the max length
	 * 
	 * @return
	 */
	public int getMaxLength() {
		return this.maxLength;
	}
}
