package pt.ua.deti.codespell.codespellbackend.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import pt.ua.deti.codespell.codespellbackend.exception.implementations.ExistentUserException;
import pt.ua.deti.codespell.codespellbackend.exception.implementations.UserNotFoundException;
import pt.ua.deti.codespell.codespellbackend.model.User;
import pt.ua.deti.codespell.codespellbackend.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @NonNull
    public User findByUsername(String username) {

        if (!userRepository.existsByUsername(username))
            throw new UserNotFoundException(String.format("The user %s could not be found.", username));
        return userRepository.findByUsername(username);

    }

    @NonNull
    public User findByEmail(String email) {

        if (!userRepository.existsByEmail(email))
            throw new UserNotFoundException(String.format("The user %s could not be found.", email));
        return userRepository.findByEmail(email);

    }

    public void registerUser(User user) {

        if (userRepository.existsByUsername(user.getUsername()))
            throw new ExistentUserException("The provided username is already taken.");
        userRepository.save(user);

    }

}
