package pt.ua.deti.codespell.codespellbackend.exception.implementations;

public class InvalidPasswordException extends RuntimeException{
    public InvalidPasswordException(String message) {
        super(message);
    }
}
