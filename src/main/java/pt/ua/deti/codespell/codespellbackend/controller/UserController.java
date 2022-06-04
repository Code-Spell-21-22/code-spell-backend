package pt.ua.deti.codespell.codespellbackend.controller;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pt.ua.deti.codespell.codespellbackend.model.Achievement;
import pt.ua.deti.codespell.codespellbackend.model.Game;
import pt.ua.deti.codespell.codespellbackend.model.User;
import pt.ua.deti.codespell.codespellbackend.request.ChangeNameRequest;
import pt.ua.deti.codespell.codespellbackend.request.ChangePasswordRequest;
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

    @GetMapping("/{username}/details")
    public User getUserDetails(@PathVariable(value = "username") String username) {
        return userService.findByUsername(username);
    }


    @GetMapping("/{username}/achievements")
    public List<Achievement> getUserAchievements(@PathVariable(value = "username") String username) {
        User user = getUserDetails(username);
        List<Achievement> achievements = new ArrayList<>();
        List<Game> games = user.getGames();
        for (Game g : games) {
            achievements.addAll(g.getAchievements());
        }
        return achievements;
    }

    @PutMapping("/{username}/password")
    public MessageResponse changePassword(@PathVariable(value = "username") String username, @RequestBody ChangePasswordRequest changePasswordRequest) {
        User user = getUserDetails(username);
        String newPassword = changePasswordRequest.getPassword();
        user.setPassword(passwordEncoder.encode(newPassword));
        userService.updateUser(user);
        return new MessageResponse(Date.from(Instant.now()), "Your password was successfully changed.");
    }

    @PutMapping("/{username}/name")
    public MessageResponse changeName(@PathVariable(value = "username") String username, @RequestBody ChangeNameRequest changeNameRequest) {
        User user = getUserDetails(username);
        String newName = changeNameRequest.getName();
        user.setName(newName);
        userService.updateUser(user);
        return new MessageResponse(Date.from(Instant.now()), "Your name was successfully changed.");
    }
}
