package pt.ua.deti.codespell.codespellbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import pt.ua.deti.codespell.codespellbackend.exception.implementations.*;

import java.util.Date;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> userNotFoundException(UserNotFoundException userNotFoundException, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), userNotFoundException.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExistentUserException.class)
    public ResponseEntity<?> existentUserException(ExistentUserException existentUserException, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), existentUserException.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> badRequestException(BadRequestException badRequestException, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), badRequestException.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LevelNotFoundException.class)
    public ResponseEntity<?> levelNotFoundException(LevelNotFoundException levelNotFoundException, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), levelNotFoundException.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ChapterNotFoundException.class)
    public ResponseEntity<?> chapterNotFoundException(ChapterNotFoundException levelNotFoundException, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), levelNotFoundException.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

}