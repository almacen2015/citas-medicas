package citasmedicas.exceptions;

public class CitaException extends RuntimeException {

    public static final String EXISTE_CITA_MISMA_AREA = "EXISTE_CITA_MISMA_AREA";
    public static final String EXISTE_CITA_MISMA_HORA = "EXISTE_CITA_MISMA_HORA";
    public static final String TITULO_NO_VALIDO = "TITULO_NO_VALIDO";
    public static final String FECHA_INICIO_NO_VALIDO = "FECHA_INICIO_NO_VALIDO";
    public static final String FECHA_FIN_NO_VALIDO = "FECHA_FIN_NO_VALIDO";
    public static final String CLIENTE_NO_VALIDO = "CLIENTE_NO_VALIDO";
    public static final String AREA_NO_VALIDO = "CLIENTE_NO_VALIDO";

    public CitaException(String message) {
        super(message);
    }
}
