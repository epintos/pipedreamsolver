package back.cells;

import java.awt.Point;

/**
 * Class which represents a Pipe, that extends Cell, which contains a location,
 * modifies water direction and returns true if another cell can step over it or
 * if it can receive water from an specific direction
 */
public abstract class Pipe extends Cell {

	public Pipe(Point p) {
		super(p);
	}

	/**
	 * Returns the length provided by the cell
	 * 
	 * @return
	 */
	public abstract int length();

	/**
	 * Returns true if the cell can receive water from dir
	 * 
	 * @param dir
	 *            direction of the incoming water
	 * @return
	 */
	public abstract boolean canReceiveFrom(Direction dir);

	@Override
	public boolean canStepOver() {
		return false;
	}

	/**
	 * Returns a new instance of a pipe according to an ID
	 * 
	 * @param id
	 * @return new Pipe
	 */
	public static Pipe getPipe(int id) {
		switch (id) {
		case 0:
			return new LeftUp(null);
		case 1:
			return new RightUp(null);
		case 2:
			return new DownRight(null);
		case 3:
			return new LeftDown(null);
		case 4:
			return new Vertical(null);
		case 5:
			return new Horizontal(null);
		case 6:
			return new Cross(null);

		default:
			return null;
		}
	}

	/**
	 * Reverts the direction in which the pipe received the water
	 * 
	 * @param dir
	 *            Represents the direction in which the pipe received the water
	 * @return new Direction
	 */
	public abstract Direction revertDirection(Direction dir);

	/**
	 * Modifies the direction that the water was going to a new direction
	 * according to the pipe that receives it
	 * 
	 * @param oldDir
	 *            Represents the direction the water was going before entering
	 *            the pipe
	 * @return new Direction
	 */
	public abstract Direction modifyDirection(Direction oldDir);

	/**
	 * Returns a pipe ID
	 * 
	 * @return
	 */
	public abstract int getPipeID();

	/**
	 * Returns true if a pipe is a corner
	 * 
	 * @return boolean
	 */
	public boolean isCorner() {
		return false;
	}
}
