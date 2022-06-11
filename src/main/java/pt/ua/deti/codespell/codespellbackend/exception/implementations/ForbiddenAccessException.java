package pt.ua.deti.codespell.codespellbackend.exception.implementations;

public class ForbiddenAccessException extends RuntimeException {

    public ForbiddenAccessException(String message) {
        super(message);
    }

}
