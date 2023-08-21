package citasmedicas.exceptions;

public class TipoEmpleadoException extends RuntimeException {
    public static final String NOMBRE_REPETIDO = "NOMBRE_REPETIDO";
    public static final String NOMBRE_NO_EXISTE = "NOMBRE_NO_EXISTE";
    public static final String ID_NO_EXISTE = "ID_NO_EXISTE";

    public TipoEmpleadoException(String message) {
        super(message);
    }
}
