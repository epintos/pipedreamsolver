package back.exceptions;

@SuppressWarnings("serial")
public class TwoStartPointsException extends IncorrectFileException {

	public TwoStartPointsException() {
		super("Error en el achivo, dos puntos de comienzo");
	}

}
