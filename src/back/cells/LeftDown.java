package back.cells;

import java.awt.Point;

public class LeftDown extends Pipe {

	public LeftDown(Point p) {
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
			return true;
		case SOUTH:
			return false;
		case WEST:
			return false;
		case EAST:
			return true;
		default:
			return false;
		}
	}

	@Override
	public Direction modifyDirection(Direction oldDir) {
		switch (oldDir) {
		case EAST:
			return Direction.SOUTH;
		case NORTH:
			return Direction.WEST;
		default:
			return null;
		}
	}

	@Override
	public Direction revertDirection(Direction dir) {
		switch (dir) {
		case SOUTH:
			return Direction.EAST;
		case WEST:
			return Direction.NORTH;
		default:
			return null;
		}
	}

	@Override
	public String toString() {
		return "Left Down";
	}

	@Override
	public int getPipeID() {
		return PipesID.LEFT_DOWN.getID();
	}
}
