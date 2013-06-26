package back.exceptions;

@SuppressWarnings("serial")
public class FileHasWrongFormatException extends IncorrectFileException {

	public FileHasWrongFormatException() {
		super("Error en formacion del archivo");
	}

}
