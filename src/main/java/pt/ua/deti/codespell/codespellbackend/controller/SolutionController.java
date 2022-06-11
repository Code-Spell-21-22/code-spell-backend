package pt.ua.deti.codespell.codespellbackend.controller;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pt.ua.deti.codespell.codespellbackend.exception.implementations.BadRequestException;
import pt.ua.deti.codespell.codespellbackend.exception.implementations.ForbiddenAccessException;
import pt.ua.deti.codespell.codespellbackend.model.Solution;
import pt.ua.deti.codespell.codespellbackend.request.MessageResponse;
import pt.ua.deti.codespell.codespellbackend.security.auth.AuthHandler;
import pt.ua.deti.codespell.codespellbackend.service.ScoreService;
import pt.ua.deti.codespell.codespellbackend.service.SolutionService;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/solution")
public class SolutionController {

    private final SolutionService solutionService;
    private final ScoreService scoreService;
    private final AuthHandler authHandler;

    @Autowired
    public SolutionController(SolutionService solutionService, ScoreService scoreService, AuthHandler authHandler) {
        this.solutionService = solutionService;
        this.scoreService = scoreService;
        this.authHandler = authHandler;
    }

    @PostMapping("")
    public MessageResponse saveSolution(@RequestBody Solution solution) {

        if (solution.getAuthorUsername() != null) {
            throw new ForbiddenAccessException("You don't have the necessary permissions to set an author for the solution.");
        }

        String username = authHandler.getCurrentUsername();

        solution.setAuthorUsername(username);

        // All the necessary checks are made here
        solutionService.storeSolution(solution);

        scoreService.saveScore(username, solution.getLevelId(), solution.getScore(), solution.getSettings());

        return new MessageResponse(Date.from(Instant.now()), "Your solution was successfully saved.");

    }

    @GetMapping("")
    public List<Solution> getSolutions() {
        return solutionService.getStoredSolutionsForUser(authHandler.getCurrentUsername());
    }

    @GetMapping("/{level_id}")
    public List<Solution> getSolutionsForLevel(@PathVariable(name = "level_id") String levelId) {

        if (!ObjectId.isValid(levelId)) {
            throw new BadRequestException("The provided level id is not valid.");
        }

        return solutionService.getStoredSolutionsForLevel(new ObjectId(levelId));

    }

}
