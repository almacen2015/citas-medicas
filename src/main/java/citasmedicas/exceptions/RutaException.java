package citasmedicas.exceptions;

public class RutaException extends RuntimeException {

    public static String COMPONENT_NO_VALIDO = "COMPONENT_NO_VALIDO";
    public static String PATH_NO_VALIDO = "PATH_NO_VALIDO";

    public RutaException(String message) {
        super(message);
    }
}
