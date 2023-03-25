package citasmedicas;

import citasmedicas.exceptions.MenuException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class Exceptions {
    @ExceptionHandler(MenuException.class)
    public ResponseEntity<?> handleMenuException(MenuException e) {
        return switch (e.getMessage()) {
            case "MENU_NO_ENCONTRADO" -> new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
            case "NOMBRE_NO_VALIDO", "RUTA_NO_VALIDA" -> new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            default -> new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        };
    }
}
