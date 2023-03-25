package citasmedicas;

import citasmedicas.exceptions.MenuException;
import citasmedicas.exceptions.RutaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionsAdvice {
    @ExceptionHandler(MenuException.class)
    public ResponseEntity<?> handleMenuException(MenuException e) {
        return switch (e.getMessage()) {
            case "MENU_NO_ENCONTRADO" -> new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
            case "NOMBRE_NO_VALIDO", "RUTA_NO_VALIDA" -> new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            default -> new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        };
    }

    @ExceptionHandler(RutaException.class)
    public ResponseEntity<?> handleRutaException(RutaException e) {
        return switch (e.getMessage()) {
            case "COMPONENT_NO_VALIDO", "PATH_NO_VALIDO" ->
                    new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            default -> new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        };
    }
}
