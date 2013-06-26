package front;

import back.Path;
import back.cells.Pipe;
import front.graphics.Frame;

public class PathPrinterImpl implements PathPrinter {

	private Frame frame;

	public PathPrinterImpl(Frame frame) {
		this.frame = frame;

	}

	@Override
	public void printPath(Pipe added, int i) {
		frame.setCell(added, i);
		frame.paint();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			System.out.println("Error en thread");
			System.exit(1);
		}
	}

	@Override
	public void printFinalPath(Path path) {
		frame.setCells(path);
		frame.paint();
	}

	@Override
	public void removePipe(Pipe remove) {
		frame.removeCell(remove);
		frame.paint();
	}

	@Override
	public void printResults(int length, double time) {
		System.out.println("Tiempo de ejecucion: " + time + " segundos");
		System.out.println("Longitud: " + length);
	}
}
