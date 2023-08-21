package citasmedicas.exceptions;

public class EmpleadoException extends RuntimeException {
    public static final String ID_NO_EXISTE = "ID_NO_EXISTE";

    public EmpleadoException(String message) {
        super(message);
    }
}
