package back.cells;

import java.awt.Point;

public class RightUp extends Pipe {

	public RightUp(Point p) {
		super(p);
	}

	@Override
	public int length() {
		return 1;
	}

	@Override
	public boolean isCorner() {
		return true;
	}

	@Override
	public boolean canReceiveFrom(Direction dir) {
		switch (dir) {
		case SOUTH:
			return true;
		case NORTH:
			return false;
		case EAST:
			return false;
		case WEST:
			return true;
		default:
			return false;
		}
	}

	@Override
	public Direction modifyDirection(Direction oldDir) {
		switch (oldDir) {
		case WEST:
			return Direction.NORTH;
		case SOUTH:
			return Direction.EAST;
		default:
			return null;
		}
	}

	@Override
	public Direction revertDirection(Direction dir) {
		switch (dir) {
		case NORTH:
			return Direction.WEST;
		case EAST:
			return Direction.SOUTH;
		default:
			return null;
		}
	}

	@Override
	public String toString() {
		return "Right Up";
	}

	@Override
	public int getPipeID() {
		return PipesID.RIGHT_UP.getID();
	}
}
