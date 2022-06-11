package pt.ua.deti.codespell.codespellbackend.controller;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import pt.ua.deti.codespell.codespellbackend.code_execution.CodeExecutionHandler;
import pt.ua.deti.codespell.codespellbackend.exception.implementations.BadRequestException;
import pt.ua.deti.codespell.codespellbackend.exception.implementations.LevelNotFoundException;
import pt.ua.deti.codespell.codespellbackend.model.*;
import pt.ua.deti.codespell.codespellbackend.request.MessageResponse;
import pt.ua.deti.codespell.codespellbackend.service.LevelService;
import pt.ua.deti.codespell.codespellbackend.service.ScoreService;

@RestController
@RequestMapping("/api/level")
public class LevelController {
    
    private final LevelService levelService;
    private final ScoreService scoreService;
    private final CodeExecutionHandler codeExecutionHandler;

    @Autowired
    public LevelController(LevelService levelService, ScoreService scoreService, CodeExecutionHandler codeExecutionHandler) {
        this.levelService = levelService;
        this.scoreService = scoreService;
        this.codeExecutionHandler = codeExecutionHandler;
    }

    @GetMapping("/{level_id}/leaderboards")
    public List<Score> getLevelLeaderboard(@PathVariable(value = "level_id") ObjectId levelId, Settings settings) {
        return scoreService.getScoresByLevelAndSettings(levelId, settings);
    }

    @GetMapping("/{level_id}/documentation")
    public List<Documentation> getLevelDocumentation(@PathVariable(value = "level_id") ObjectId levelId, ProgrammingLanguage language) {
        Level level = levelService.findByLevelId(levelId);
        return level.getDocumentation();
    }

    @GetMapping("")
    public List<Level> getLevelsList(ProgrammingLanguage programmingLanguage, SkillLevel skillLevel) {
        return levelService.getAllLevels();
    }

    @GetMapping("/{level_id}/solutions")
    public List<Solution> getLevelSolutions(@PathVariable(value = "level_id") ObjectId levelId, ProgrammingLanguage language) {
        Level level = levelService.findByLevelId(levelId);
        return level.getSolutions();
    }

    @PostMapping("/{level_id}/submit/{solution_id}")
    public MessageResponse submitLevelSolution(@PathVariable(value = "level_id") String levelIdStr, @PathVariable(value="solution_id") String solutionUniqueIdStr, @RequestBody String code) {

        if (!ObjectId.isValid(levelIdStr))
            throw new BadRequestException("Unable to parse level_id.");

        if (!solutionUniqueIdStr.matches("^[\\da-fA-F]{8}-[\\da-fA-F]{4}-[\\da-fA-F]{4}-[\\da-fA-F]{4}-[\\da-fA-F]{12}$"))
            throw new BadRequestException("Unable to parse solution_id to UUID");

        ObjectId levelId = new ObjectId(levelIdStr);

        Level level = levelService.findByLevelId(levelId);
        UUID solutionUUID = UUID.fromString(solutionUniqueIdStr);

        codeExecutionHandler.scheduleCodeExecution(level, solutionUUID, code);

        return new MessageResponse(Date.from(Instant.now()), "Solution successfully submitted.");

    }

    @GetMapping("/{level_id}")
    public Level getCurrentLevel(@PathVariable(value = "level_id") String levelIdStr) {

        if (!ObjectId.isValid(levelIdStr))
            throw new BadRequestException("The level id doesn't comply with the correct format.");
        return levelService.findByLevelId(new ObjectId(levelIdStr));

    }

    @GetMapping("/{level_id}/goals")
    public List<Goal> getLevelGoals(@PathVariable(value = "level_id") String levelIdStr) {

        if (!ObjectId.isValid(levelIdStr))
            throw new BadRequestException("The level id doesn't comply with the correct format.");

        Level level = levelService.findByLevelId(new ObjectId(levelIdStr));
        return level.getGoals() != null ? level.getGoals() : new ArrayList<>();

    }

}
