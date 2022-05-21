package pt.ua.deti.codespell.codespellbackend.controller;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pt.ua.deti.codespell.codespellbackend.model.*;
import pt.ua.deti.codespell.codespellbackend.request.MessageResponse;
import pt.ua.deti.codespell.codespellbackend.service.LevelService;
import pt.ua.deti.codespell.codespellbackend.service.ScoreService;

@RestController
@RequestMapping("/api/level")
public class LevelController {
    
    private final LevelService levelService;
    private final ScoreService scoreService;

    @Autowired
    public LevelController(LevelService levelService, ScoreService scoreService) {
        this.levelService = levelService;
        this.scoreService = scoreService;
    }

    @GetMapping("{level_id}/leaderboards")
    public List<Score> getLevelLeaderboard(@PathVariable(value = "level_id") ObjectId levelId, Settings settings) {
        return scoreService.getScoresByLevelAndSettings(levelId, settings);
    }

    @GetMapping("{level_id}/documentation")
    public List<Documentation> getLevelDocumentation(@PathVariable(value = "level_id") ObjectId levelId, ProgrammingLanguage language) {
        Level level = levelService.findByLevelId(levelId);
        return level.getDocumentation();
    }

    @GetMapping("")
    public List<Level> getLevelsList(ProgrammingLanguage programmingLanguage, SkillLevel skillLevel) {
        return levelService.getAllLevels();
    }

    @GetMapping("{level_id}/solutions")
    public List<Solution> getLevelSolutions(@PathVariable(value = "level_id") ObjectId levelId, ProgrammingLanguage language) {
        Level level = levelService.findByLevelId(levelId);
        return level.getSolutions();
    }

    @PostMapping("{level_id}/submit/{solution_id}")
    public MessageResponse submitLevelSolution(@PathVariable(value = "level_id") ObjectId levelId, @PathVariable(value="solution_id") int solution_id, ProgrammingLanguage language, Solution solution) {
        Level level = levelService.findByLevelId(levelId);
        List<Solution> solutions = level.getSolutions();
        solutions.add(solution);
        return new MessageResponse(Date.from(Instant.now()), "Solution successfully submitted.");
    }

    @GetMapping("{level_id}")
    public Level getCurrentLevel(@PathVariable(value = "level_id") ObjectId levelId) {
        return levelService.findByLevelId(levelId);
    }
}
