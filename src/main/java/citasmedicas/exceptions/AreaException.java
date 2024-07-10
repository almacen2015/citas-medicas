package citasmedicas.exceptions;

public class AreaException extends RuntimeException {
    public static final String NOMBRE_NO_VALIDO = "NOMBRE_NO_VALIDO";
    public static final String NOMBRE_EXISTE = "NOMBRE_EXISTE";
    public static final String ID_NO_EXISTE = "ID_NO_EXISTE";
    public static final String ID_NO_VALIDO = "ID_NO_VALIDO";

    public AreaException(String message) {
        super(message);
    }
}
