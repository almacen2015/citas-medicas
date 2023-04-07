package citasmedicas.exceptions;

public class AreaException extends RuntimeException {

    public static final String NOMBRE_NO_VALIDO = "NOMBRE_NO_VALIDO";

    public AreaException(String message) {
        super(message);
    }
}
