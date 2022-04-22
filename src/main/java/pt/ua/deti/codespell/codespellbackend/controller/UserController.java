package pt.ua.deti.codespell.codespellbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pt.ua.deti.codespell.codespellbackend.model.User;
import pt.ua.deti.codespell.codespellbackend.model.Achievement;
import pt.ua.deti.codespell.codespellbackend.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
    
    @Autowired
    private UserService userService;

    @GetMapping("/{userEmail}/details")
    public User userDetails(@PathVariable(value="userEmail") String userEmail) {
        return userService.findByEmail(userEmail);
    }

    @GetMapping("/{userEmail}/achievements")
    public List<Achievement> userAchievements(@PathVariable(value="userEmail") String userEmail) {
        
    }
}
