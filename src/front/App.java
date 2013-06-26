package front;

import java.io.IOException;

import back.Solver;
import back.exceptions.IncorrectFileException;
import front.graphics.Frame;

public class App {
	public static void main(String[] args) {
		try {
			if (args.length >= 1) {
				Solver solver = new Solver(args[0]); // File Name
				solver.setPathPrinter(new PathPrinterImpl(new Frame(solver)));
				if (args.length >= 2) {
					if (args[1].equals("exact")) {
						if (args.length >= 3) {
							if (args[2].equals("progress")) {
								solver.setProgressive(true);
							} else {
								System.out
										.println("Parametro de exact invalido");
								System.exit(0);
							}
							if (args.length >= 4) {
								System.out.println("Parametros invalidos");
								System.exit(0);
							}
						}
						solver.exact();
					} else if (args[1].equals("approx")) {
						if (args.length >= 3) {
							int time = 0;
							try {
								time = Integer.valueOf(args[2]);
							} catch (NumberFormatException e) {
								System.out
										.println("Error en formato de tiempo");
								System.exit(0);
							}
							if (time > 0) {
								if (args.length >= 4) {
									if (args[3].equals("progress")) {
										solver.setProgressive(true);
									} else {
										System.out
												.println("Parametro de approx invalido");
										System.exit(0);
									}
									if (args.length >= 5) {
										System.out
												.println("Parametros invalidos");
										System.exit(0);
									}
								}
								solver.hillClimbingSolution(time);
							} else
								System.out
										.println("Formato de tiempo invalido");
						} else
							System.out.println("Error, falta tiempo");
					} else
						System.out
								.println("Error en los argumentos, se esperaba exact o approx");
				} else
					System.out.println("Error, falta metodo");
			} else
				System.out.println("Error en los argumentos");
		} catch (IOException e) {
			System.out.println("Error en el archivo");
		} catch (IncorrectFileException e){
			System.out.println("El archivo no existe");
		}
	}
}