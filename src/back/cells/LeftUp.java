package back.cells;

import java.awt.Point;

public class LeftUp extends Pipe {

	public LeftUp(Point p) {
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
		case NORTH:
			return false;
		case SOUTH:
			return true;
		case EAST:
			return true;
		case WEST:
			return false;
		default:
			return false;
		}
	}

	@Override
	public Direction modifyDirection(Direction oldDir) {
		switch (oldDir) {
		case EAST:
			return Direction.NORTH;
		case SOUTH:
			return Direction.WEST;
		default:
			return null;
		}
	}

	@Override
	public Direction revertDirection(Direction dir) {
		switch (dir) {
		case WEST:
			return Direction.SOUTH;
		case NORTH:
			return Direction.EAST;
		default:
			return null;
		}
	}

	@Override
	public String toString() {
		return "Left Up";
	}

	@Override
	public int getPipeID() {
		return PipesID.LEFT_UP.getID();
	}
}
