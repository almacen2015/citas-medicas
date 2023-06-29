package citasmedicas.exceptions;

public class AreaException extends RuntimeException {
    public static final String NOMBRE_NO_VALIDO = "NOMBRE_NO_VALIDO";
    public static final String NOMBRE_EXISTE = "NOMBRE_EXISTE";

    public AreaException(String message) {
        super(message);
    }
}
