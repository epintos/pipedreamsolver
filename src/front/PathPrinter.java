package front;

import back.Path;
import back.cells.Pipe;

public interface PathPrinter {

	/**
	 * Prints the path as the pipes are added. The idea is that the algorithm,
	 * as it adds pipes to the path, lets the PathPrinter know that it has to
	 * actualize the printing with "added"
	 * 
	 * @param added
	 *            Which is the new pipe to add to the path
	 */
	public void printPath(Pipe added, int i);

	/**
	 * Lets the PathPrinter know that a new path has been finished and therefore
	 * it must print it
	 * 
	 * @param path
	 *            longest Path
	 */
	public void printFinalPath(Path path);

	/**
	 * Lets the PathPrinter know that a pipe added must be removed from the path
	 * 
	 * @param remove
	 */
	public void removePipe(Pipe pipe);

	/**
	 * Prints a path length and total algorithm time.
	 * 
	 * @param length
	 * @param time
	 */
	public void printResults(int length, double time);
}
