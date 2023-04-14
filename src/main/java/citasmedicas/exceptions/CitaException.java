package citasmedicas.exceptions;

public class CitaException extends RuntimeException {

    public static final String EXISTE_CITA_MISMA_AREA = "EXISTE_CITA_MISMA_AREA";
    public static final String EXISTE_CITA_MISMA_HORA = "EXISTE_CITA_MISMA_HORA";

    public CitaException(String message) {
        super(message);
    }
}
