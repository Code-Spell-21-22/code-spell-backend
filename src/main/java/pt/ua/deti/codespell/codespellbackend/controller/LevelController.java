package pt.ua.deti.codespell.codespellbackend.controller;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pt.ua.deti.codespell.codespellbackend.model.Chapter;
import pt.ua.deti.codespell.codespellbackend.model.Documentation;
import pt.ua.deti.codespell.codespellbackend.model.Level;
import pt.ua.deti.codespell.codespellbackend.model.ProgrammingLanguage;
import pt.ua.deti.codespell.codespellbackend.model.Score;
import pt.ua.deti.codespell.codespellbackend.model.SkillLevel;
import pt.ua.deti.codespell.codespellbackend.model.Solution;
import pt.ua.deti.codespell.codespellbackend.request.MessageResponse;
import pt.ua.deti.codespell.codespellbackend.service.LevelService;

@RestController
@RequestMapping("api/")
public class LevelController {
    
    private final LevelService levelService;

    @Autowired
    public LevelController(LevelService levelService) {
        this.levelService = levelService;
    }

    @GetMapping("level/{level_id}/leaderboards")
    public List<Score> getLevelLeaderboard(@PathVariable(value = "level_id") int level_id, ProgrammingLanguage language) {
        return levelService.getScoresByLevelId(level_id); // pass programmingLanguage as an argument aswell?
    }

    @GetMapping("level/{level_id}/documentation")
    public List<Documentation> getLevelDocumentation(@PathVariable(value = "level_id") int level_id, ProgrammingLanguage language) {
        Level level = levelService.findByLevelId(level_id);
        return level.getDocumentation();
    }

    @GetMapping("levels")
    public List<Level> getLevelsList(ProgrammingLanguage programmingLanguage, SkillLevel skillLevel) {
        // service method or repository method? findAllLevels
    }

    @GetMapping("chapters")
    public List<Chapter> getChaptersList(ProgrammingLanguage programmingLanguage, SkillLevel skillLevel) {
        // service method or repository method? findAllChapters
    }

    @GetMapping("level/{level_id}/solutions")
    public List<Solution> getLevelSolutions(@PathVariable(value = "level_id") int level_id, ProgrammingLanguage language) {
        Level level = levelService.findByLevelId(level_id);
        return level.getSolutions();
    }

    @PostMapping("level/{level_id}/submit/{solution_id}")
    public MessageResponse submitLevelSolution(@PathVariable(value = "level_id") int level_id, @PathVariable(value="solution_id") int solution_id, ProgrammingLanguage language, Solution solution) {
        Level level = levelService.findByLevelId(level_id);
        List<Solution> solutions = level.getSolutions();
        solutions.add(solution);
        return new MessageResponse(Date.from(Instant.now()), "Solution successfully submitted.");
    }

    @GetMapping("level/{level_id}")
    public Level getCurrentLevel(@PathVariable(value = "level_id") int level_id) {
        return levelService.findByLevelId(level_id);
    }
}
