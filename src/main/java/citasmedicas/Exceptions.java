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
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
