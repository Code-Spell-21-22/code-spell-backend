package pt.ua.deti.codespell.codespellbackend.exception.implementations;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }

}
