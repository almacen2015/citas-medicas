package citasmedicas.exceptions;

public class MenuException extends RuntimeException {

    public static String NOMBRE_NO_VALIDO = "NOMBRE_NO_VALIDO";
    public static String RUTA_NO_VALIDA = "RUTA_NO_VALIDA";
    public static String MENU_NO_ENCONTRADO = "MENU_NO_ENCONTRADO";

    public MenuException(String message) {
        super(message);
    }
}
