package pt.ua.deti.codespell.codespellbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.ua.deti.codespell.codespellbackend.model.Achievement;
import pt.ua.deti.codespell.codespellbackend.service.AchievementService;

import java.util.List;

@RestController
@RequestMapping("/api/achievements")
public class AchievementsController {

    private final AchievementService achievementService;

    @Autowired
    public AchievementsController(AchievementService achievementService) {
        this.achievementService = achievementService;
    }

    @GetMapping("")
    public List<Achievement> getAchievementList() {
        return achievementService.getAllAchievements();
    }

}
