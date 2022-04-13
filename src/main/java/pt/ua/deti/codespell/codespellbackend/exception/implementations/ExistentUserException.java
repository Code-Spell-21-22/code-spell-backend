package pt.ua.deti.codespell.codespellbackend.exception.implementations;

public class ExistentUserException extends RuntimeException {

    public ExistentUserException(String message) {
        super(message);
    }

}
