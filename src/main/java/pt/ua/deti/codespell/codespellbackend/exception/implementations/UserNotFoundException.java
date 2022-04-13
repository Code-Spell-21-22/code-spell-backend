package pt.ua.deti.codespell.codespellbackend.exception.implementations;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }

}
