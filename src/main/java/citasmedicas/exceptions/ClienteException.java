package citasmedicas.exceptions;

public class ClienteException extends RuntimeException {

    public static String NOMBRE_NO_VALIDO = "NOMBRE_NO_VALIDO";
    public static String APELLIDO_PATERNO_NO_VALIDO = "APELLIDO_PATERNO_NO_VALIDO";
    public static String APELLIDO_MATERNO_NO_VALIDO = "APELLIDO_MATERNO_NO_VALIDO";
    public static String NUMERO_DOCUMENTO_NO_VALIDO = "NUMERO_DOCUMENTO_NO_VALIDO";
    public static String CLIENTE_NO_ENCONTRADO = "CLIENTE_NO_ENCONTRADO";

    public ClienteException(String message) {
        super(message);
    }
}
