package pt.ua.deti.codespell.codespellbackend.controller;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import pt.ua.deti.codespell.codespellbackend.exception.implementations.BadRequestException;
import pt.ua.deti.codespell.codespellbackend.utils.NameChangeRequest;
import pt.ua.deti.codespell.codespellbackend.utils.PasswordChangeRequest;
import pt.ua.deti.codespell.codespellbackend.model.Achievement;
import pt.ua.deti.codespell.codespellbackend.model.Game;
import pt.ua.deti.codespell.codespellbackend.model.User;
import pt.ua.deti.codespell.codespellbackend.request.MessageResponse;
import pt.ua.deti.codespell.codespellbackend.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
    
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Autowired
    public UserController(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @GetMapping("/{email}/details")
    public User getUserDetails(@PathVariable(value = "email") String email) {
        return userService.findByEmail(email);
    }


    @GetMapping("/{email}/achievements")
    public List<Achievement> getUserAchievements(@PathVariable(value = "email") String email) {

        User user = userService.findByEmail(email);
        List<Achievement> achievements = new ArrayList<>();
        List<Game> games = user.getGames();

        if (games != null) {
            for (Game g : games) {
                achievements.addAll(g.getAchievements());
            }
        }

        return achievements;

    }

    @PutMapping("/{email}/password")
    public MessageResponse changePassword(@PathVariable(value = "email") String email, @RequestBody PasswordChangeRequest passwordChangeRequest) {

        User user = userService.findByEmail(email);
        user.setPassword(passwordEncoder.encode(passwordChangeRequest.getNewPassword()));

        userService.save(user);

        return new MessageResponse(Date.from(Instant.now()), "Your password was successfully changed.");

    }

    @PutMapping("/{email}/name")
    public MessageResponse changeName(@PathVariable(value = "email") String email, @RequestBody NameChangeRequest nameChangeRequest) {

        User user = userService.findByEmail(email);

        if (nameChangeRequest.getNewName() == null)
            throw new BadRequestException("The provided name was 'null'.");

        user.setName(nameChangeRequest.getNewName());
        userService.save(user);

        return new MessageResponse(Date.from(Instant.now()), "Your name was successfully changed.");

    }
}
