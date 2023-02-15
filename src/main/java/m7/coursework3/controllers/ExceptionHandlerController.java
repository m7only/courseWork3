package m7.coursework3.controllers;

import jakarta.validation.ConstraintViolationException;
import m7.coursework3.exceptions.SocksNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {
    private final String notValidParameters = "Not valid parameters";
    private final String internalServerError = "Internal server error";


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> illegalArgumentException(IllegalArgumentException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(notValidParameters + ": " + exception.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> constraintViolationException(ConstraintViolationException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(notValidParameters + ": " + exception.getMessage());
    }

    @ExceptionHandler(SocksNotExistException.class)
    public ResponseEntity<String> socksNotExistException(Exception exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(notValidParameters + ": " + exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exception(Exception exception) {
        exception.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(internalServerError + ": " + exception.getMessage());
    }
}
