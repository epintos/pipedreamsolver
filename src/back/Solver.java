package back;

import java.io.IOException;

import back.board.MatrixBoard;
import back.exactAlgorithm.ExactAlgorithm;
import back.exceptions.FileHasWrongFormatException;
import back.exceptions.PointAllreadyContainsCellException;
import back.exceptions.TwoStartPointsException;
import back.hillClimbing.HillClimbing;
import back.parser.Parser;
import front.PathPrinter;
import front.PathPrinterImpl;

public class Solver {

	private Parser parser;
	private MatrixBoard board;
	private PathPrinter pathPrinter;
	private boolean progressive;

	public Solver(String fileName) throws IOException,
			FileHasWrongFormatException, PointAllreadyContainsCellException,
			TwoStartPointsException {
		this.parser = new Parser(fileName);
		board = parser.getBoard();
	}

	public void exact() {
		ExactAlgorithm exact = new ExactAlgorithm(board, pathPrinter);
		exact.exactPath(progressive, -1);
		double time = (exact.getTime()) / (double) 1000;
		pathPrinter
				.printResults((exact.getLongestPath().getLength() + 1), time);
		pathPrinter.printFinalPath(exact.getLongestPath());
	}

	public void hillClimbingSolution(int time) {
		long timeInMS = time * 60 * 1000;

		long startTime = System.currentTimeMillis();
		boolean finished = false;
		ExactAlgorithm exact = new ExactAlgorithm(board, pathPrinter);
		HillClimbing hc = new HillClimbing(exact.getBoard(),
				exact.getPipesQuantity(), pathPrinter);
		while ((System.currentTimeMillis() - startTime) < timeInMS && !finished) {
			int rand = (int) (Math.random() * 200) + 1;
			exact.exactPath(progressive, rand);
			hc.start(exact.getRetSolution(), progressive);
			if (hc.getLongestPath().getLength() == board.getMaxLength())
				finished = true;
		}
		double totalTime = (System.currentTimeMillis() - startTime)
				/ (double) 1000;
		pathPrinter
				.printResults(hc.getLongestPath().getLength() + 1, totalTime);
		pathPrinter.printFinalPath(hc.getLongestPath());
	}

	/**
	 * Sets progressive's state
	 * 
	 * @param progressive
	 */
	public void setProgressive(boolean progressive) {
		this.progressive = progressive;
	}

	/**
	 * Initializes the PathPrinter
	 * 
	 * @param pathPrinter
	 */
	public void setPathPrinter(PathPrinterImpl pathPrinter) {
		this.pathPrinter = pathPrinter;
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
	 * Returns true if progressive is true
	 * 
	 * @return boolean
	 */
	public boolean isProgressive() {
		return progressive;
	}
}
