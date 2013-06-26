package back.cells;

import java.awt.Point;

/**
 * Enumerative which contains the four possible directions and a point that represents
 * the change of position in the board
 * 
 */
public enum Direction {

	NORTH(new Point(0, -1)), SOUTH(new Point(0, 1)), EAST(new Point(1, 0)), WEST(
			new Point(-1, 0));

	private Point point;

	Direction(Point point) {
		this.point = point;
	}

	public Point getPoint() {
		return point;
	}
}
