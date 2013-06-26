package back.cells;

import java.awt.Point;

public class DownRight extends Pipe {

	public DownRight(Point p) {
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
			return true;
		case EAST:
			return false;
		default:
			return false;
		}
	}

	@Override
	public Direction revertDirection(Direction dir) {
		switch (dir) {
		case SOUTH:
			return Direction.WEST;
		case EAST:
			return Direction.NORTH;
		default:
			System.out.println("Esto no deberia pasar -DR");
			return null;
		}
	}

	@Override
	public Direction modifyDirection(Direction oldDir) {
		switch (oldDir) {
		case NORTH:
			return Direction.EAST;
		case WEST:
			return Direction.SOUTH;
		default:
			System.out.println("Esto no deberia pasar -DR");
			return null;
		}
	}

	@Override
	public String toString() {
		return "Down Right";
	}

	@Override
	public int getPipeID() {
		return PipesID.DOWN_RIGHT.getID();
	}

}
