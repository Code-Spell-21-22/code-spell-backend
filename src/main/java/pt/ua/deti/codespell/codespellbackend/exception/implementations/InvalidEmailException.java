package pt.ua.deti.codespell.codespellbackend.exception.implementations;

public class InvalidEmailException extends RuntimeException{
    public InvalidEmailException(String message) {
        super(message);
    }
}
