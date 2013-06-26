package back.cells;

import java.awt.Point;

public class Vertical extends Pipe {

	public Vertical(Point p) {
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
			return true;
		case NORTH:
			return true;
		case EAST:
			return false;
		case WEST:
			return false;
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

	public String toString() {
		return "^";
	}

	@Override
	public boolean canStepOver() {
		return super.canStepOver();
	}

	@Override
	public int getPipeID() {
		return PipesID.VERTICAL.getID();
	}
}
