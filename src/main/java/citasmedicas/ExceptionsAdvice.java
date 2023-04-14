package citasmedicas;

import citasmedicas.exceptions.AreaException;
import citasmedicas.exceptions.CitaException;
import citasmedicas.exceptions.ClienteException;
import citasmedicas.exceptions.MenuException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionsAdvice {
    @ExceptionHandler(MenuException.class)
    public ResponseEntity<?> handleMenuException(MenuException e) {
        return switch (e.getMessage()) {
            case "MENU_NO_ENCONTRADO" -> new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
            case "NOMBRE_NO_VALIDO", "RUTA_NO_VALIDA" -> new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            default -> new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        };
    }

    @ExceptionHandler(ClienteException.class)
    public ResponseEntity<?> handleClienteException(ClienteException e) {
        return switch (e.getMessage()) {
            case "NOMBRE_NO_VALIDO", "APELLIDO_PATERNO_NO_VALIDO",
                    "APELLIDO_MATERNO_NO_VALIDO", "NUMERO_DOCUMENTO_NO_VALIDO",
                    "FECHA_NACIMIENTO_NO_VALIDO", "SEXO_NO_VALIDO" ->
                    new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            case "CLIENTE_NO_ENCONTRADO" -> new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
            default -> new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        };
    }

    @ExceptionHandler(AreaException.class)
    public ResponseEntity<?> handleAreaException(AreaException e) {
        return switch (e.getMessage()) {
            case "NOMBRE_NO_VALIDO" -> new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            default -> new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        };
    }

    @ExceptionHandler(CitaException.class)
    public ResponseEntity<?> handleAreaException(CitaException e) {
        return switch (e.getMessage()) {
            case "EXISTE_CITA_MISMA_AREA", "EXISTE_CITA_MISMA_HORA" ->
                    new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            default -> new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        };
    }
}
