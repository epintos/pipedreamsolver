package back.exactAlgorithm;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import back.Path;
import back.board.MatrixBoard;
import back.cells.Cell;
import back.cells.Cross;
import back.cells.Direction;
import back.cells.Pipe;
import back.cells.PipesID;
import back.exceptions.PointAllreadyContainsCellException;
import front.PathPrinter;

public class ExactAlgorithm {

	private MatrixBoard board;
	private Path longestPath;
	private int[] pipesQuantity;
	private long time;
	private PathPrinter pathPrinter;
	private int ammountOfSol;
	private int longestPathLength = 0;
	private Path retSolution;

	public ExactAlgorithm(MatrixBoard board, PathPrinter pathPrinter) {

		this.board = board;
		pipesQuantity = board.getPipesQuantity();
		longestPath = new Path();
		this.pathPrinter = pathPrinter;
	}

	/**
	 * Calculates exact solutions
	 * 
	 * @param progressive
	 *            If equals true, then calls pathPrinter which takes care of
	 *            painting the algorithm progress
	 * @param ammountOfSol
	 *            If equals -1, then calculates the longest solution. Else, it
	 *            calculates exact solutions up to "ammountOfSol"
	 */
	public void exactPath(boolean progressive, int ammountOfSol) {
		this.ammountOfSol = ammountOfSol;
		time = System.currentTimeMillis();

		Point start = new Point();
		start.y = board.getStart().y + board.getWaterDirection().getPoint().y;
		start.x = board.getStart().x + board.getWaterDirection().getPoint().x;
		Path p = new Path();
		exactPath(start, board.getWaterDirection(), p, progressive);
		time = System.currentTimeMillis() - time;
	}

	private void exactPath(Point actual, Direction dir, Path actualPath,
			boolean progressive) {

		if (ammountOfSol == 0)
			return;
		if (board.isOutOfBoard(actual))
			return;

		Cell elem = board.getElemIn(actual.y, actual.x);
		Point next = new Point();
		if (!elem.canStepOver())
			return;

		if (elem instanceof Cross) {
			((Cross) elem).setVisitedTwice(true);
			next.y = actual.y + dir.getPoint().y;
			next.x = actual.x + dir.getPoint().x;
			exactPath(next, dir, actualPath, progressive);
			((Cross) elem).setVisitedTwice(false);
		} else {
			int rand = (int) (Math.random() * 6);
			int i, j;
			for (i = rand, j = -1; i <= pipesQuantity.length && j < rand; i++) {
				if (i == pipesQuantity.length) {
					i = j = 0;
				}
				if (j >= 0)
					j++;
				Pipe pipe = Pipe.getPipe(i);
				boolean noCross = true;
				if (pipe.getPipeID() == PipesID.CROSS.getID()
						&& oneNeighbourWall(actual, dir))
					noCross = false;
				if (longestPathLength == board.getMaxLength()) {
					return;
				}
				Point location = new Point(actual.x, actual.y);
				pipe.setLocation(location);
				if (ammountOfSol != 0 && pipesQuantity[i] > 0
						&& pipe.canReceiveFrom(dir) && elem.canStepOver()
						&& noCross) {
					try {
						board.add(actual.y, actual.x, pipe);
					} catch (PointAllreadyContainsCellException e) {
						System.out.println("No debería pasar");
						System.exit(0);
					}
					Direction oldDir = dir;
					actualPath.add(pipe);
					if (progressive)
						pathPrinter
								.printPath(pipe, actualPath.getPath().size());
					pipesQuantity[i] = pipesQuantity[i] - 1;
					dir = (pipe.modifyDirection(dir));
					if (actualPath.isSolution(board, dir)) {
						if (ammountOfSol > 0)
							ammountOfSol--;
						if (ammountOfSol == 0) {
							retSolution = new Path();
							retSolution.setPath(new ArrayList<Pipe>(actualPath
									.getPath()));
						}
						if (longestPath.length() == 0
								|| longestPath.getLength() < actualPath
										.length()) {
							Path p = new Path();
							List<Pipe> longest;
							longest = new ArrayList<Pipe>(actualPath.getPath());
							p.setPath(longest);

							longestPath.setPath(longest);
							longestPathLength = longestPath.getLength();
						}
					}

					next.y = actual.y + dir.getPoint().y;
					next.x = actual.x + dir.getPoint().x;
					exactPath(next, dir, actualPath, progressive);
					Pipe removed = actualPath.removeLast();
					if (progressive)
						pathPrinter.removePipe(removed);
					pipesQuantity[i] = pipesQuantity[i] + 1;
					;
					board.remove((int) removed.getLocation().getY(),
							(int) removed.getLocation().getX());

					dir = oldDir;
				}
			}
		}
	}

	/**
	 * This method returns true if there is one or more cells different from
	 * BlankCell around it.
	 * 
	 * @param actual
	 * @return
	 */
	private boolean oneNeighbourWall(Point actual, Direction dir) {
		Cell elem1 = null;
		Cell elem2 = null;
		if (dir.equals(Direction.NORTH) || dir.equals(Direction.SOUTH)) {
			if (pipesQuantity[PipesID.VERTICAL.getID()] == 0)
				return false;
			if (actual.x - 1 < 0) {
				if (actual.x + 1 >= board.getCols())
					return true;
				return !board.getElemIn(actual.y, actual.x + 1).canStepOver();
			}
			if (actual.x + 1 >= board.getCols())
				return !board.getElemIn(actual.y, actual.x - 1).canStepOver();
			elem1 = board.getElemIn(actual.y, actual.x - 1);
			elem2 = board.getElemIn(actual.y, actual.x + 1);
		}
		if (dir.equals(Direction.EAST) || dir.equals(Direction.WEST)) {
			if (pipesQuantity[PipesID.HORIZONTAL.getID()] == 0)
				return false;
			if (actual.y - 1 < 0) {
				if (actual.y + 1 >= board.getRows())
					return true;
				return !board.getElemIn(actual.y + 1, actual.x).canStepOver();
			}
			if (actual.y + 1 >= board.getRows())
				return !board.getElemIn(actual.y - 1, actual.x).canStepOver();
			elem1 = board.getElemIn(actual.y - 1, actual.x);
			elem2 = board.getElemIn(actual.y + 1, actual.x);
		}
		return (!elem1.canStepOver() || !elem2.canStepOver());
	}

	/**
	 * Returns the algorithm total Time
	 * 
	 * @return long
	 */
	public long getTime() {
		return time;
	}

	/**
	 * Returns the longest Path
	 * 
	 * @return Path
	 */
	public Path getLongestPath() {
		return longestPath;
	}

	/**
	 * Returns the Board
	 * 
	 * @return Board
	 */
	public MatrixBoard getBoard() {
		return board;
	}

	/**
	 * Returns the Pipes available
	 * 
	 * @return
	 */
	public int[] getPipesQuantity() {
		return pipesQuantity;
	}

	/**
	 * Returns the longest path length
	 * 
	 * @return
	 */
	public int getLongestPathLength() {
		return longestPathLength;
	}

	/**
	 * Returns the current solution for the Hill Climbing algorithm to use.
	 * 
	 * @return
	 */
	public Path getRetSolution() {
		if (retSolution != null)
			return retSolution;
		return longestPath;
	}
}