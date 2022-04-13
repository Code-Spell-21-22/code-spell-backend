package pt.ua.deti.codespell.codespellbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.ua.deti.codespell.codespellbackend.dao.UserDAO;
import pt.ua.deti.codespell.codespellbackend.exception.implementations.BadRequestException;
import pt.ua.deti.codespell.codespellbackend.model.User;
import pt.ua.deti.codespell.codespellbackend.request.LoginRequest;
import pt.ua.deti.codespell.codespellbackend.request.MessageResponse;
import pt.ua.deti.codespell.codespellbackend.security.auth.AuthTokenResponse;
import pt.ua.deti.codespell.codespellbackend.security.auth.JWTTokenUtils;
import pt.ua.deti.codespell.codespellbackend.service.SpringUserDetailsService;
import pt.ua.deti.codespell.codespellbackend.service.UserService;

import java.time.Instant;
import java.util.Date;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final SpringUserDetailsService springUserDetailsService;
    private final JWTTokenUtils jwtTokenUtils;

    @Autowired
    public AuthController(PasswordEncoder passwordEncoder, UserService userService, SpringUserDetailsService springUserDetailsService, JWTTokenUtils jwtTokenUtils) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.springUserDetailsService = springUserDetailsService;
        this.jwtTokenUtils = jwtTokenUtils;
    }

    @PostMapping("/register")
    public MessageResponse registerUser(@RequestBody UserDAO userDAO) {

        // Encode password
        String userPassword = userDAO.getPassword();
        userDAO.setPassword(passwordEncoder.encode(userPassword));

        userService.registerUser(userDAO.toDataEntity());

        return new MessageResponse(Date.from(Instant.now()), "The user was successfully registered!");

    }

    @PostMapping("/login")
    public AuthTokenResponse loginUser(@RequestBody LoginRequest loginRequest) {

        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        if (email == null || password == null)
            throw new BadRequestException("Please provide a valid request body.");

        User user = userService.findByEmail(email);
        UserDetails userDetails = springUserDetailsService.loadUserByUsername(user.getUsername());

        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new BadCredentialsException("The provided password is wrong.");

        String token = jwtTokenUtils.generateToken(userDetails);
        return new AuthTokenResponse("Authentication succeeded.", token);

    }

}
