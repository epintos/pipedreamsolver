package back;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import back.board.MatrixBoard;
import back.cells.Cell;
import back.cells.Cross;
import back.cells.Direction;
import back.cells.Pipe;

/**
 * Class which represents a path with a list of pipes. A path can be modified
 * and can decide if it's solution
 * 
 */
public class Path {

	private List<Pipe> path;
	private int length;

	public Path() {
		path = new ArrayList<Pipe>();
	}

	/**
	 * Adds a pipe to the path
	 * 
	 * @param pipe
	 */
	public void add(Pipe pipe) {
		path.add(pipe);
	}

	/**
	 * Returns true if the last pipe of the path is solution, this means that
	 * the actual water direction added to he pipe position it's out of the map
	 * or it's a cross
	 * 
	 * @param board
	 * @param waterDir
	 * @return
	 */
	public boolean isSolution(MatrixBoard board, Direction waterDir) {
		if (path.size() == 0)
			return false;
		Point lastPipeLocation = new Point();
		lastPipeLocation.x = path.get(path.size() - 1).getLocation().x;
		lastPipeLocation.y = path.get(path.size() - 1).getLocation().y;
		lastPipeLocation.y += waterDir.getPoint().y;
		lastPipeLocation.x += waterDir.getPoint().x;
		if (board.isOutOfBoard(lastPipeLocation))
			return true;
		Cell c;
		if ((c = board.getElemIn(lastPipeLocation.y, lastPipeLocation.x)) instanceof Pipe) {
			if (c instanceof Cross) {
				lastPipeLocation.x = c.getLocation().x;
				lastPipeLocation.y = c.getLocation().y;
				lastPipeLocation.y += waterDir.getPoint().y;
				lastPipeLocation.x += waterDir.getPoint().x;
				if (board.isOutOfBoard(lastPipeLocation))
					return true;
			}
		}
		return false;
	}

	/**
	 * Calculates and returns the path length
	 * 
	 * @return length
	 */
	public int length() {
		int retLength = 0;

		for (Pipe each : path)
			retLength += each.length();
		return retLength;
	}

	/**
	 * Returns the list of Pipes
	 * 
	 * @return
	 */
	public List<Pipe> getPath() {
		return path;
	}

	/**
	 * Sets the path with a new list of Pipes and changes the length to the new
	 * one
	 * 
	 * @param path
	 */
	public void setPath(List<Pipe> path) {
		this.path = path;
		length = length();
	}

	/**
	 * Removes the last Pipe from the path
	 * 
	 * @return
	 */
	public Pipe removeLast() {
		return path.remove(path.size() - 1);
	}

	/**
	 * Returs the path length
	 * 
	 * @return
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Sets the path length
	 * 
	 * @param length
	 */
	public void setLength(int length) {
		this.length = length;
	}
}
