package back.cells;

import java.awt.Point;

public class BlankCell extends Cell {

	public BlankCell(Point p) {
		super(p);
	}

	@Override
	public boolean canStepOver() {
		return true;
	}

	@Override
	public String toString() {
		return " ";
	}
}
