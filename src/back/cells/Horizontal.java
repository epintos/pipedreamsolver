package back.cells;

import java.awt.Point;

public class Horizontal extends Pipe {

	public Horizontal(Point p) {
		super(p);
	}

	@Override
	public int length() {
		return 1;
	}

	@Override
	public boolean canReceiveFrom(Direction dir) {
		switch (dir) {
		case SOUTH:
			return false;
		case NORTH:
			return false;
		case EAST:
			return true;
		case WEST:
			return true;
		default:
			return false;
		}
	}

	@Override
	public Direction modifyDirection(Direction oldDir) {
		return oldDir;
	}

	@Override
	public Direction revertDirection(Direction dir) {
		return dir;
	}

	@Override
	public String toString() {
		return "Horizontal";
	}

	@Override
	public int getPipeID() {
		return PipesID.HORIZONTAL.getID();
	}
}
