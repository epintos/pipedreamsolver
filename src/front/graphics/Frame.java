package front.graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import back.Path;
import back.Solver;
import back.cells.BlankCell;
import back.cells.Cell;
import back.cells.Cross;
import back.cells.DownRight;
import back.cells.Horizontal;
import back.cells.LeftDown;
import back.cells.LeftUp;
import back.cells.Pipe;
import back.cells.RightUp;
import back.cells.Vertical;
import back.cells.Wall;

@SuppressWarnings("serial")
public class Frame extends JFrame {

	private static final int CELL_SIZE = 35;
	private Solver solver;
	private JPanel panel;
	private int cols;
	private int rows;
	private HashMap<Class<?>, Image> imagesMap;
	private HashMap<String, Image> initialMap;
	private List<CellImage> imagesToPrint = new ArrayList<CellImage>();

	public Frame(Solver solver) throws IOException {
		this.solver = solver;
		cols = solver.getBoard().getCols();
		rows = solver.getBoard().getRows();

		this.initializeFrame();
		this.initializeClassMap();
		this.initializeInitialMap();
		this.initializePanel();
	}

	/**
	 * Initializes the frame and positions it in the center of the screen
	 * 
	 * @throws IOException
	 * 			If an error ocurred while loading an Image
	 */
	private void initializeFrame() throws IOException {
		setTitle("Pipe D. Solver");
		setIconImage(loadImage("resources/images/CInitial.png"));
		setSize(cols * CELL_SIZE + 6, rows * CELL_SIZE + 32);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		// Centers Frame
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension window = this.getSize();
		setLocation((screen.width - window.width) / 2,
				(screen.height - window.height) / 2);
	}

	/**
	 * Initializes classMap with class and its image
	 * 
	 * @throws IOException
	 */
	private void initializeClassMap() throws IOException {
		imagesMap = new HashMap<Class<?>, Image>();
		imagesMap.put(Cross.class, this.loadImage("resources/images/C.png"));
		imagesMap.put(DownRight.class,
				this.loadImage("resources/images/DR.png"));
		imagesMap
				.put(LeftDown.class, this.loadImage("resources/images/LD.png"));
		imagesMap.put(LeftUp.class, this.loadImage("resources/images/LU.png"));
		imagesMap.put(RightUp.class, this.loadImage("resources/images/RU.png"));
		imagesMap.put(Vertical.class, this.loadImage("resources/images/V.png"));
		imagesMap.put(Horizontal.class,
				this.loadImage("resources/images/H.png"));
		imagesMap.put(Wall.class, this.loadImage("resources/images/Wall.png"));
		imagesMap.put(BlankCell.class,
				this.loadImage("resources/images/Blank.png"));
	}

	/**
	 * Initializes the initialMap with a direction and its image
	 * 
	 * @throws IOException
	 */
	private void initializeInitialMap() throws IOException {
		initialMap = new HashMap<String, Image>();
		initialMap.put("NORTH", this.loadImage("resources/images/North.png"));
		initialMap.put("EAST", this.loadImage("resources/images/East.png"));
		initialMap.put("SOUTH", this.loadImage("resources/images/South.png"));
		initialMap.put("WEST", this.loadImage("resources/images/West.png"));
	}

	/**
	 * Initializes the panel, adds it to the frame and initializes imagesToPrint
	 * 
	 * @throws IOException
	 */
	public void initializePanel() throws IOException {
		panel = new JPanel() {
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				List<CellImage> aux = new ArrayList<CellImage>(imagesToPrint);
				for (CellImage cellImage : aux) {
					if (cellImage != null) {
						g.drawImage(cellImage.getImage(), (int) cellImage
								.getPosition().getX(), (int) cellImage
								.getPosition().getY(), null);
					}
				}
			}
		};
		panel.setLayout(null);
		panel.setSize(cols * CELL_SIZE, rows * CELL_SIZE);
		panel.setBackground(Color.WHITE);
		add(panel);
		addImages();
	}

	/**
	 * Repaints the panel
	 */
	public void paint() {
		panel.repaint();
		this.setVisible(true);
	}

	/**
	 * Adds the initial cells to imagesToPrint
	 * 
	 * @throws IOException
	 */
	private void addImages() throws IOException {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				Cell cell = solver.getBoard().getElemIn(i, j);
				Point point = new Point(CELL_SIZE * (j), CELL_SIZE * (i));
				imagesToPrint.add(new CellImage(imagesMap.get(cell.getClass()),
						point, cell instanceof BlankCell));
			}
		}
		// Adds initial cell
		int x = (int) solver.getBoard().getStart().getX();
		int y = (int) solver.getBoard().getStart().getY();
		Point initialPoint = new Point(CELL_SIZE * (x), CELL_SIZE * (y));
		String initial = solver.getBoard().getWaterDirection().toString();
		imagesToPrint.add(new CellImage(initialMap.get(initial), initialPoint,
				false));
	}

	/**
	 * Returns an image according to a name
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	private Image loadImage(String fileName) throws IOException {
		InputStream stream = ClassLoader.getSystemResourceAsStream(fileName);
		if (stream == null) {
			return ImageIO.read(new File(fileName));
		} else {
			return ImageIO.read(stream);
		}
	}

	/**
	 * Receives a path and adds it to imagesToPrint
	 * 
	 * @param path
	 * @throws IOException
	 */
	public void setCells(Path path) {
		for (Pipe pipe : path.getPath()) {
			int x = (int) pipe.getLocation().getY();
			int y = (int) pipe.getLocation().getX();
			imagesToPrint.add(new CellImage(imagesMap.get(pipe.getClass()),
					new Point(CELL_SIZE * (y), CELL_SIZE * (x)), false));
		}
	}

	/**
	 * Adds a pipe to imagesToPrint in the position i
	 * 
	 * @param pipe
	 * @param i
	 */
	public void setCell(Pipe pipe, int i) {
		int x = (int) pipe.getLocation().getY();
		int y = (int) pipe.getLocation().getX();
		imagesToPrint.add(rows * cols + i,
				new CellImage(imagesMap.get(pipe.getClass()), new Point(
						CELL_SIZE * (y), CELL_SIZE * (x)), false));
	}

	/**
	 * Removes pipe from imagesToPrint
	 * @param pipe to remove
	 */
	public void removeCell(Pipe pipe) {
		CellImage cell = new CellImage(null, new Point(pipe.getLocation().x
				* CELL_SIZE, pipe.getLocation().y * CELL_SIZE), false);
		imagesToPrint.remove(cell);
	}
}