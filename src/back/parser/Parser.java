package back.parser;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import back.board.MatrixBoard;
import back.cells.Direction;
import back.cells.Horizontal;
import back.cells.Vertical;
import back.cells.Wall;
import back.exceptions.FileHasWrongFormatException;
import back.exceptions.PointAllreadyContainsCellException;
import back.exceptions.TwoStartPointsException;

public class Parser {

	private BufferedReader readFrom;
	private MatrixBoard board;
	private int[] pipesQuantity;

	public Parser(String fileName) throws IOException,
			FileHasWrongFormatException, PointAllreadyContainsCellException,
			TwoStartPointsException {
		File f;
		f = new File(fileName);
		readFrom = new BufferedReader(new FileReader(f));

		String read = readLine();
		String[] points = read.split(",");

		if(points.length != 2)
			throw new FileHasWrongFormatException();
		try {
		board = new MatrixBoard(Integer.valueOf(points[0]), Integer.valueOf(points[1]));
		} catch(IllegalArgumentException e) {
			throw new FileHasWrongFormatException();
		}

		// leo la matriz
		boolean startExists = false;
		int index = 0; 
		while (index < board.getRows()) {
			read = readFrom.readLine();
			if(read.length() < board.getCols())
				throw new FileHasWrongFormatException();
			for (int i = 0; i < board.getCols(); i++) {
				char each = read.charAt(i);
				switch (each) {
				case ' ':
					break;
				case '#':
					board.add(index, i, new Wall(new Point(index, i)));
					break;
				case 'N':
					if (startExists)
						throw new TwoStartPointsException();
					board.add(index, i, new Vertical(new Point(index, i)));
					board.setWaterDirection(Direction.NORTH);
					board.setStart(new Point(i, index));
					startExists = true;
					break;
				case 'S':
					if (startExists)
						throw new TwoStartPointsException();
					board.add(index, i, new Vertical(new Point(index, i)));
					board.setStart(new Point(i, index));
					board.setWaterDirection(Direction.SOUTH);
					startExists = true;
					break;
				case 'W':
					if (startExists)
						throw new TwoStartPointsException();
					board.add(index, i, new Horizontal(new Point(index, i)));
					board.setStart(new Point(i, index));
					board.setWaterDirection(Direction.WEST);
					startExists = true;
					break;
				case 'E':
					if (startExists)
						throw new TwoStartPointsException();
					board.add(index, i, new Horizontal(new Point(index, i)));
					board.setStart(new Point(i, index));
					board.setWaterDirection(Direction.EAST);
					startExists = true;
					break;

				default:
					throw new FileHasWrongFormatException();
				}
			}
			index++;
		}
		
		//Reads the quantity of each pipe type
		pipesQuantity = new int[7];
		int maxLength = 0;
		for (int i = 0; i < pipesQuantity.length; i++) {
			String read2 = readLine();
			int quantity;
			try {
			quantity = Integer.valueOf(read2);
			} catch (NumberFormatException e) {
				throw new FileHasWrongFormatException();
			}
			if(quantity <0)
				throw new FileHasWrongFormatException();
			if(i==6)
				maxLength += quantity*2;
			else
				maxLength += quantity;
			pipesQuantity[i]= quantity;
		}
		board.setPipesQuantity(pipesQuantity);
		board.setMaxLength(maxLength);
	}

	/**
	 * Reads a line and returns it with no spaces or tabs in between
	 * @return
	 * @throws IOException
	 * @throws FileHasWrongFormatException
	 */
	private String readLine() throws IOException, FileHasWrongFormatException {

		String read;
		do {
			read = readFrom.readLine();
			if (read == null)
				throw new FileHasWrongFormatException();

			read = read.replaceAll(" ", "").replaceAll("\t", "");
		} while (read.equals(""));

		return read;
	}


	public MatrixBoard getBoard() {
		return board;
	}

	public int[] getPipesQuantity() {
		return pipesQuantity;
	}
}
