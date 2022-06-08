package pt.ua.deti.codespell.codespellbackend.exception.implementations;

public class InvalidUsernameException extends RuntimeException{
    public InvalidUsernameException(String message) {
        super(message);
    }
}
