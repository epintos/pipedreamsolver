package back.exceptions;


@SuppressWarnings("serial")
public class PointAllreadyContainsCellException extends IncorrectFileException {

	public PointAllreadyContainsCellException( ) {
		super("Error en el archivo, superposicion de pipes");

	}

}
