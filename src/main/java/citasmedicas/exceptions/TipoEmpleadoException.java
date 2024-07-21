package citasmedicas.exceptions;

public class TipoEmpleadoException extends RuntimeException {
    public static final String NOMBRE_EXISTE = "NOMBRE_EXISTE";
    public static final String NOMBRE_NO_EXISTE = "NOMBRE_NO_EXISTE";
    public static final String ID_NO_EXISTE = "ID_NO_EXISTE";
    public static final String NOMBRE_VACIO = "NOMBRE_VACIO";
    public static final String NOMBRE_EXCESO_CARACTERES = "NOMBRE_EXCESO_CARACTERES";

    public TipoEmpleadoException(String message) {
        super(message);
    }
}
