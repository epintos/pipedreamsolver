package back.cells;

import java.awt.Point;

public class Wall extends Cell {

	public Wall(Point p) {
		super(p);
	}

	@Override
	public boolean canStepOver() {
		return false;
	}

	@Override
	public String toString() {
		return "#";
	}
}
