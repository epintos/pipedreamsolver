package back.hillClimbing;

import java.awt.Point;
import java.util.List;

import back.Path;
import back.board.MatrixBoard;
import back.cells.BlankCell;
import back.cells.Cross;
import back.cells.Direction;
import back.cells.Pipe;
import back.cells.PipesID;
import back.exceptions.PointAllreadyContainsCellException;
import front.PathPrinter;

public class HillClimbing {

	private Path longestPath;
	private Path actualLongestPath;
	private MatrixBoard board;
	private int[] pipesQuantity;
	private PathPrinter pathPrinter;
	private boolean progressive;
	private int addedLength;

	private final int NS = 0;
	private final int EW = 1;

	public HillClimbing(MatrixBoard b, int[] pipesQty, PathPrinter pathPrinter) {
		this.board = b;
		this.pipesQuantity = pipesQty;
		this.pathPrinter = pathPrinter;
	}

	/**
	 * Prints the actual longest path.
	 */
	public void printPath() {
		for (int i = 0; i < actualLongestPath.getPath().size(); i++)
			pathPrinter.printPath(actualLongestPath.getPath().get(i), i);
	}

	/**
	 * Starts the algorithm to try to find a longer path than the one given as a
	 * parameter.
	 * 
	 * @param path
	 * @param progressive
	 */
	public void start(Path path, boolean progressive) {
		this.progressive = progressive;
		actualLongestPath = new Path();
		actualLongestPath = path;
		addPathToBoard();
		if (progressive)
			pathPrinter.printFinalPath(actualLongestPath);
		analyzePath();
		removePathFromBoard();
		if (progressive)
			removeImagesFromBoard();
	}

	/**
	 * Adds all the pipes of the actual longest path to the current board.
	 */
	private void addPathToBoard() {
		for (Pipe each : actualLongestPath.getPath())
			try {
				board.add(each.getLocation().y, each.getLocation().x, each);
				pipesQuantity[each.getPipeID()] = pipesQuantity[each
						.getPipeID()] - 1;
			} catch (PointAllreadyContainsCellException e) {
				e.printStackTrace();
			}
	}

	/**
	 * Removes all the pipes of the actual longest path from the current board.
	 */
	private void removePathFromBoard() {
		for (Pipe each : actualLongestPath.getPath()) {
			board.remove(each.getLocation().y, each.getLocation().x);
			pipesQuantity[each.getPipeID()] = pipesQuantity[each.getPipeID()] + 1;
		}
	}

	/**
	 * Removes all the images of the actual longest path from the front.
	 */
	private void removeImagesFromBoard() {
		for (Pipe each : actualLongestPath.getPath()) {
			pathPrinter.removePipe(each);
		}
	}

	/**
	 * Searches for specific combinations of pieces on the path and if the board
	 * can support them it performs the change.
	 */
	public void analyzePath() {
		addedLength = actualLongestPath.getLength();
		Direction dir = board.getWaterDirection();
		List<Pipe> path = actualLongestPath.getPath();
		for (int i = 0; i < path.size(); i++) {
			if (path.get(i).isCorner()) {
				if (!replaceWithCross(path.get(i), i, dir))
					if (i + 1 < path.size() && path.get(i + 1).isCorner())
						if (twoCorners(path.get(i), path.get(i + 1), dir))
							addStraightPipes(path, i, dir);
			}
			dir = path.get(i).modifyDirection(dir);
		}
		actualLongestPath.setPath(path);
		actualLongestPath.setLength(addedLength);
		if (longestPath == null
				|| longestPath.getLength() < actualLongestPath.getLength())
			longestPath = actualLongestPath;
	}

	/**
	 * Changes two consecutive and opposite corners for a vertical
	 * (horizontal/cross) pipe, the two previous corners and another vertical
	 * (horizontal/cross) pipe. This adds two pieces to the path, increasing its
	 * length in 2. It first checks if the amount of pieces available is enough
	 * (in case there are not enough vertical/horizontal pieces it also checks
	 * for crosses) and if the corresponding cells are free for adding pipes.
	 * 
	 * @param path
	 * @param i
	 * @param dir
	 * @return Returns true if the pieces could be added, false otherwise.
	 */
	private boolean addStraightPipes(List<Pipe> path, int i, Direction dir) {

		int addedPipeID = 0;

		if (dir.equals(Direction.WEST) || dir.equals(Direction.EAST)) {
			if (!(pipesQuantity[PipesID.HORIZONTAL.getID()]
					+ pipesQuantity[PipesID.CROSS.getID()] >= 2))
				return false;
			addedPipeID = PipesID.HORIZONTAL.getID();
		} else if (dir.equals(Direction.NORTH) || dir.equals(Direction.SOUTH)) {
			if (!(pipesQuantity[PipesID.VERTICAL.getID()]
					+ pipesQuantity[PipesID.CROSS.getID()] >= 2))
				return false;
			addedPipeID = PipesID.VERTICAL.getID();
		}

		Point point = new Point(path.get(i + 1).getLocation());
		point.x += dir.getPoint().x;
		point.y += dir.getPoint().y;
		if (board.isOutOfBoard(point)
				|| !(board.getElemIn(point.y, point.x) instanceof BlankCell))
			return false;

		point = new Point(path.get(i).getLocation());
		point.x += dir.getPoint().x;
		point.y += dir.getPoint().y;
		if (board.isOutOfBoard(point)
				|| !(board.getElemIn(point.y, point.x) instanceof BlankCell))
			return false;

		Pipe p;
		if (pipesQuantity[addedPipeID] > 0) {
			p = Pipe.getPipe(addedPipeID);
			pipesQuantity[addedPipeID] = pipesQuantity[addedPipeID] - 1;
		} else {
			p = Pipe.getPipe(PipesID.CROSS.getID());
			pipesQuantity[PipesID.CROSS.getID()] = pipesQuantity[PipesID.CROSS
					.getID()] - 1;
		}

		p.setLocation(path.get(i).getLocation());
		path.add(i, p);
		board.remove(p.getLocation().y, p.getLocation().x);
		try {
			board.add(p.getLocation().y, p.getLocation().x, p);
			if (progressive) {
				pathPrinter.removePipe(p);
				pathPrinter.printPath(p, i);
			}
		} catch (PointAllreadyContainsCellException e) {
			e.printStackTrace();
		}
		p = path.get(i + 1);
		point = new Point(p.getLocation());
		if (dir.equals(Direction.EAST) || dir.equals(Direction.WEST))
			point.x += dir.getPoint().x;
		if (dir.equals(Direction.NORTH) || dir.equals(Direction.SOUTH))
			point.y += dir.getPoint().y;
		p.setLocation(point);
		try {
			board.add(p.getLocation().y, p.getLocation().x, p);
			if (progressive)
				pathPrinter.printPath(p, i + 1);
		} catch (PointAllreadyContainsCellException e) {
			e.printStackTrace();
		}

		p = path.get(i + 2);
		Point back = p.getLocation();
		point = new Point(p.getLocation());
		if (dir.equals(Direction.EAST) || dir.equals(Direction.WEST))
			point.x += dir.getPoint().x;
		if (dir.equals(Direction.NORTH) || dir.equals(Direction.SOUTH))
			point.y += dir.getPoint().y;
		p.setLocation(point);
		try {
			board.add(p.getLocation().y, p.getLocation().x, p);
			if (progressive)
				pathPrinter.printPath(p, i + 2);
		} catch (PointAllreadyContainsCellException e) {
			e.printStackTrace();
		}

		if (pipesQuantity[addedPipeID] > 0) {
			p = Pipe.getPipe(addedPipeID);
			pipesQuantity[addedPipeID] = pipesQuantity[addedPipeID] - 1;
		} else {
			p = Pipe.getPipe(PipesID.CROSS.getID());
			pipesQuantity[PipesID.CROSS.getID()] = pipesQuantity[PipesID.CROSS
					.getID()] - 1;
		}
		p.setLocation(back);
		path.add(i + 3, p);
		board.remove(p.getLocation().y, p.getLocation().x);
		try {
			board.add(p.getLocation().y, p.getLocation().x, p);
			if (progressive) {
				pathPrinter.removePipe(p);
				pathPrinter.printPath(p, i + 3);
			}
		} catch (PointAllreadyContainsCellException e) {
			e.printStackTrace();
		}
		addedLength += 2;

		return true;
	}

	/**
	 * Checks if the path has two consecutive and opposite corners
	 * (RightUp-LeftUp, RightDown-LeftDown, RightDown-RightUp, LeftDown-LeftUp)
	 * 
	 * @param actual
	 * @param next
	 * @param dir
	 * @return Returns true if the path has two consecutive and opposite
	 *         corners, false otherwise.
	 */
	private boolean twoCorners(Pipe actual, Pipe next, Direction dir) {
		Direction middDir = actual.modifyDirection(dir);
		if ((actual.getLocation().x + middDir.getPoint().x != next.getLocation().x)
				|| (actual.getLocation().y + middDir.getPoint().y != next.getLocation().y))
			return false;

		Direction prev = dir;
		Direction post = next.modifyDirection(middDir);
		if ((prev.equals(Direction.SOUTH) && post.equals(Direction.NORTH))
				|| (post.equals(Direction.SOUTH) && prev
						.equals(Direction.NORTH)))
			return true;

		if ((prev.equals(Direction.WEST) && post.equals(Direction.EAST))
				|| (post.equals(Direction.WEST) && prev.equals(Direction.EAST)))
			return true;

		return false;

	}

	/**
	 * Switches through the ID of the pipe to be replaced and calls the
	 * corresponding methods to perform the check and replacement of the piece
	 * for another set of pieces.
	 * 
	 * @param pipe
	 * @param i
	 * @param dir
	 * @return Returns true if the changes could be performed, false otherwise.
	 */
	private boolean replaceWithCross(Pipe pipe, int i, Direction dir) {
		if (pipesQuantity[PipesID.CROSS.getID()] == 0)
			return false;

		int id = pipe.getPipeID();
		int x = pipe.getLocation().x;
		int y = pipe.getLocation().y;
		Point p1;
		Point p2;
		Point p3;

		switch (id) {
		case 0: // LU
			p1 = new Point(x, y + 1);
			p2 = new Point(x + 1, y);
			p3 = new Point(x + 1, y + 1);
			if (checkRoundAbout(p1, p2, p3, PipesID.RIGHT_UP.getID(),
					PipesID.LEFT_DOWN.getID()))
				try {
					if (dir.equals(Direction.EAST))
						replaceCorner(pipe, EW, PipesID.LEFT_DOWN.getID(),
								PipesID.RIGHT_UP.getID(), i, new Point(1, 1));
					else
						replaceCorner(pipe, NS, PipesID.LEFT_DOWN.getID(),
								PipesID.RIGHT_UP.getID(), i, new Point(1, 1));
					return true;
				} catch (PointAllreadyContainsCellException e) {
					e.printStackTrace();
				}
			break;
		case 1: // UR
			p1 = new Point(x - 1, y);
			p2 = new Point(x, y + 1);
			p3 = new Point(x - 1, y + 1);
			if (checkRoundAbout(p1, p2, p3, PipesID.DOWN_RIGHT.getID(),
					PipesID.LEFT_UP.getID()))
				try {
					if (dir.equals(Direction.WEST))
						replaceCorner(pipe, EW, PipesID.DOWN_RIGHT.getID(),
								PipesID.LEFT_UP.getID(), i, new Point(-1, 1));
					else
						replaceCorner(pipe, NS, PipesID.DOWN_RIGHT.getID(),
								PipesID.LEFT_UP.getID(), i, new Point(-1, 1));
					return true;
				} catch (PointAllreadyContainsCellException e) {
					e.printStackTrace();
				}
			break;
		case 2: // DR
			p1 = new Point(x, y - 1);
			p2 = new Point(x - 1, y - 1);
			p3 = new Point(x - 1, y);
			if (checkRoundAbout(p1, p2, p3, PipesID.RIGHT_UP.getID(),
					PipesID.LEFT_DOWN.getID()))
				try {
					if (dir.equals(Direction.WEST))
						replaceCorner(pipe, EW, PipesID.RIGHT_UP.getID(),
								PipesID.LEFT_DOWN.getID(), i, new Point(-1, -1));
					else
						replaceCorner(pipe, NS, PipesID.RIGHT_UP.getID(),
								PipesID.LEFT_DOWN.getID(), i, new Point(-1, -1));
					return true;
				} catch (PointAllreadyContainsCellException e) {
					e.printStackTrace();
				}
			break;
		case 3: // LD
			p1 = new Point(x, y - 1);
			p2 = new Point(x + 1, y - 1);
			p3 = new Point(x + 1, y);
			if (checkRoundAbout(p1, p2, p3, PipesID.DOWN_RIGHT.getID(),
					PipesID.LEFT_UP.getID()))
				try {
					if (dir.equals(Direction.EAST))
						replaceCorner(pipe, EW, PipesID.LEFT_UP.getID(),
								PipesID.DOWN_RIGHT.getID(), i, new Point(1, -1));
					else
						replaceCorner(pipe, NS, PipesID.LEFT_UP.getID(),
								PipesID.DOWN_RIGHT.getID(), i, new Point(1, -1));
					return true;
				} catch (PointAllreadyContainsCellException e) {
					e.printStackTrace();
				}
			break;

		default:
			break;
		}
		return false;
	}

	/**
	 * Checks for the availability of the corresponding pipes to perform the
	 * change and for the cells in the map to be free. If so, it decreases in 1
	 * the amount of the pieces available.
	 * 
	 * @param p1
	 * @param p2
	 * @param p3
	 * @param ID1
	 * @param ID2
	 * @return Returns true if the change can be performed, false otherwise.
	 */
	private boolean checkRoundAbout(Point p1, Point p2, Point p3, int ID1,
			int ID2) {
		if (!(pipesQuantity[ID1] >= 1 && pipesQuantity[ID2] >= 1))
			return false;
		if (!(pipesQuantity[PipesID.CROSS.getID()] >= 1))
			return false;

		if (!board.isOutOfBoard(new Point(p1.x, p1.y))
				&& board.getElemIn(p1.y, p1.x) instanceof BlankCell)
			if (!board.isOutOfBoard(new Point(p2.x, p2.y))
					&& board.getElemIn(p2.y, p2.x) instanceof BlankCell)
				if (!board.isOutOfBoard(new Point(p3.x, p3.y))
						&& board.getElemIn(p3.y, p3.x) instanceof BlankCell) {
					pipesQuantity[ID1] = pipesQuantity[ID1] - 1;
					pipesQuantity[ID2] = pipesQuantity[ID2] - 1;
					pipesQuantity[PipesID.CROSS.getID()] = pipesQuantity[PipesID.CROSS
							.getID()] - 1;
					return true;
				}
		return false;
	}

	/**
	 * Replaces a corner in the path with a cross and three more corners, adding
	 * three pieces to the board and increasing the length in 4.
	 * 
	 * @param p
	 * @param dir
	 * @param ID1
	 * @param ID2
	 * @param i
	 * @param point
	 * @throws PointAllreadyContainsCellException
	 */
	private void replaceCorner(Pipe p, int dir, int ID1, int ID2, int i,
			Point point) throws PointAllreadyContainsCellException {

		List<Pipe> path = actualLongestPath.getPath();
		Pipe cross = Pipe.getPipe(PipesID.CROSS.getID());
		cross.setLocation(new Point(p.getLocation()));
		((Cross) cross).setVisitedTwice(true);
		path.add(i, cross);
		if (progressive) {
			pathPrinter.removePipe(cross);
			pathPrinter.printPath(cross, i);
		}

		if (dir == EW) {
			Pipe corner1 = Pipe.getPipe(ID1);
			corner1.setLocation(new Point(p.getLocation().x + point.x, p
					.getLocation().y));
			board.add(corner1.getLocation().y, corner1.getLocation().x, corner1);
			if (progressive)
				pathPrinter.printPath(corner1, i + 1);
			path.add(i + 1, corner1);
		} else {
			Pipe corner2 = Pipe.getPipe(ID2);
			corner2.setLocation(new Point(p.getLocation().x, p.getLocation().y
					+ point.y));
			board.add(corner2.getLocation().y, corner2.getLocation().x, corner2);
			if (progressive)
				pathPrinter.printPath(corner2, i + 1);
			path.add(i + 1, corner2);
		}

		p.getLocation().x += point.x;
		p.getLocation().y += point.y;
		if (progressive)
			pathPrinter.printPath(p, i + 2);

		if (dir == EW) {
			Pipe corner1 = Pipe.getPipe(ID2);
			corner1.setLocation(new Point(p.getLocation().x - point.x, p
					.getLocation().y));
			board.add(corner1.getLocation().y, corner1.getLocation().x, corner1);
			if (progressive)
				pathPrinter.printPath(corner1, i + 3);
			path.add(i + 3, corner1);
		} else {
			Pipe corner2 = Pipe.getPipe(ID1);
			corner2.setLocation(new Point(p.getLocation().x, p.getLocation().y
					- point.y));
			board.add(corner2.getLocation().y, corner2.getLocation().x, corner2);
			if (progressive)
				pathPrinter.printPath(corner2, i + 3);
			path.add(i + 3, corner2);
		}

		addedLength += 4;
	}

	/**
	 * Returns the actual longest path, as a Path.
	 * 
	 * @return
	 */
	public Path getLongestPath() {
		return actualLongestPath;
	}

}
