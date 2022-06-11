package pt.ua.deti.codespell.codespellbackend.controller;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.ua.deti.codespell.codespellbackend.exception.implementations.BadRequestException;
import pt.ua.deti.codespell.codespellbackend.model.Achievement;
import pt.ua.deti.codespell.codespellbackend.service.AchievementService;
import pt.ua.deti.codespell.codespellbackend.utils.AchievementRequest;

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

    @GetMapping("/{achievement_id}")
    public Achievement getAchievementById(@PathVariable(name = "achievement_id") String achievementIdStr) {

        if (!ObjectId.isValid(achievementIdStr)) {
            throw new BadRequestException("The achievement id could not be parsed");
        }

        return achievementService.getAchievementById(new ObjectId(achievementIdStr));

    }

}
