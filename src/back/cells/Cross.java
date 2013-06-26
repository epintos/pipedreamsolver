package back.cells;

import java.awt.Point;

public class Cross extends Pipe {

	private boolean visitedTwice;

	public Cross(Point p) {
		super(p);
		visitedTwice = false;
	}

	@Override
	public int length() {
		if (visitedTwice)
			return 2;
		return 1;
	}

	@Override
	public boolean canReceiveFrom(Direction dir) {
		return true;
	}

	@Override
	public boolean canStepOver() {
		return true;
	}

	@Override
	public Direction modifyDirection(Direction oldDir) {
		return oldDir;
	}

	@Override
	public Direction revertDirection(Direction dir) {
		return dir;
	}

	public void setVisitedTwice(boolean visited) {
		this.visitedTwice = visited;
	}

	@Override
	public String toString() {
		return "Cross";
	}

	@Override
	public int getPipeID() {
		return PipesID.CROSS.getID();
	}

	public boolean isVisitedTwice() {
		return visitedTwice;
	}
}
