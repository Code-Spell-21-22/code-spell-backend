package pt.ua.deti.codespell.codespellbackend.controller;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import pt.ua.deti.codespell.codespellbackend.exception.implementations.BadRequestException;
import pt.ua.deti.codespell.codespellbackend.exception.implementations.ForbiddenAccessException;
import pt.ua.deti.codespell.codespellbackend.security.auth.AuthHandler;
import pt.ua.deti.codespell.codespellbackend.service.AchievementService;
import pt.ua.deti.codespell.codespellbackend.utils.AchievementRequest;
import pt.ua.deti.codespell.codespellbackend.utils.NameChangeRequest;
import pt.ua.deti.codespell.codespellbackend.utils.PasswordChangeRequest;
import pt.ua.deti.codespell.codespellbackend.model.Achievement;
import pt.ua.deti.codespell.codespellbackend.model.User;
import pt.ua.deti.codespell.codespellbackend.request.MessageResponse;
import pt.ua.deti.codespell.codespellbackend.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
    
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final AchievementService achievementService;
    private final AuthHandler authHandler;

    @Autowired
    public UserController(PasswordEncoder passwordEncoder, UserService userService, AchievementService achievementService, AuthHandler authHandler) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.achievementService = achievementService;
        this.authHandler = authHandler;
    }

    @GetMapping("/{email}/details")
    public User getUserDetails(@PathVariable(value = "email") String email) {
        return userService.findByEmail(email);
    }


    @GetMapping("/{email}/achievements")
    public List<Achievement> getUserAchievements(@PathVariable(value = "email") String email) {

        User user = userService.findByEmail(email);

        return user.getAchievements()
                .stream()
                .map(achievementService::getAchievementById)
                .collect(Collectors.toList());

    }

    @PutMapping("/{email}/achievements")
    public MessageResponse addUserAchievements(@PathVariable(value = "email") String email, @RequestBody AchievementRequest achievementRequest) {

        User user = userService.findByEmail(email);

        if (!ObjectId.isValid(achievementRequest.getAchievementId())) {
            throw new BadRequestException("The achievement id could not be parsed.");
        }

        Achievement achievement = achievementService.getAchievementById(new ObjectId(achievementRequest.getAchievementId()));
        Set<ObjectId> userAchievements = user.getAchievements() != null ? user.getAchievements() : new HashSet<>();

        userAchievements.add(achievement.getId());
        user.setAchievements(userAchievements);

        userService.save(user);

        return new MessageResponse(Date.from(Instant.now()), "The achievement was successfully added.");

    }

    @PutMapping("/{email}/password")
    public MessageResponse changePassword(@PathVariable(value = "email") String email, @RequestBody PasswordChangeRequest passwordChangeRequest) {

        User user = userService.findByEmail(email);

        if (!user.getUsername().equals(authHandler.getCurrentUsername())) {
            throw new ForbiddenAccessException("You cannot change passwords for other users.");
        }

        user.setPassword(passwordEncoder.encode(passwordChangeRequest.getNewPassword()));
        userService.save(user);

        return new MessageResponse(Date.from(Instant.now()), "Your password was successfully changed.");

    }

    @PutMapping("/{email}/name")
    public MessageResponse changeName(@PathVariable(value = "email") String email, @RequestBody NameChangeRequest nameChangeRequest) {

        User user = userService.findByEmail(email);

        if (!user.getUsername().equals(authHandler.getCurrentUsername())) {
            throw new ForbiddenAccessException("You cannot change passwords for other users.");
        }

        if (nameChangeRequest.getNewName() == null)
            throw new BadRequestException("The provided name was 'null'.");

        user.setName(nameChangeRequest.getNewName());
        userService.save(user);

        return new MessageResponse(Date.from(Instant.now()), "Your name was successfully changed.");

    }
}
