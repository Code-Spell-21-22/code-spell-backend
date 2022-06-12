package pt.ua.deti.codespell.codespellbackend.controller;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pt.ua.deti.codespell.codespellbackend.exception.implementations.BadRequestException;
import pt.ua.deti.codespell.codespellbackend.exception.implementations.ChapterNotFoundException;
import pt.ua.deti.codespell.codespellbackend.exception.implementations.LevelNotFoundException;
import pt.ua.deti.codespell.codespellbackend.model.Score;
import pt.ua.deti.codespell.codespellbackend.service.ChapterService;
import pt.ua.deti.codespell.codespellbackend.service.LeaderboardService;
import pt.ua.deti.codespell.codespellbackend.service.LevelService;

import java.util.List;

@RestController
@RequestMapping("/api/leaderboard")
public class LeaderboardsController {

    private final LeaderboardService leaderboardService;
    private final LevelService levelService;
    private final ChapterService chapterService;

    @Autowired
    public LeaderboardsController(LeaderboardService leaderboardService, LevelService levelService, ChapterService chapterService) {
        this.leaderboardService = leaderboardService;
        this.levelService = levelService;
        this.chapterService = chapterService;
    }

    @GetMapping("")
    public List<Score> getGeneralLeaderboard() {
        return leaderboardService.getAllScores();
    }

    @GetMapping("/level/{level_id}")
    public List<Score> getLeaderboardByLevel(@PathVariable(name = "level_id") String levelIdStr) {

        if (!ObjectId.isValid(levelIdStr)) {
            throw new BadRequestException("The level id could not be parsed.");
        }

        ObjectId levelId = new ObjectId(levelIdStr);

        if (!levelService.existsByLevelId(levelId)) {
            throw new LevelNotFoundException(String.format("The level %s could not be found.", levelId));
        }

        return leaderboardService.getLevelScores(levelId);

    }

    @GetMapping("/chapter/{chapter_id}")
    public List<Score> getLeaderboardByChapter(@PathVariable(name = "chapter_id") String chapterIdStr) {

        if (!ObjectId.isValid(chapterIdStr)) {
            throw new BadRequestException("The chapter id could not be parsed.");
        }

        ObjectId chapterId = new ObjectId(chapterIdStr);

        if (!chapterService.existsById(chapterId)) {
            throw new ChapterNotFoundException(String.format("The chapter %s could not be found.", chapterId));
        }

        return leaderboardService.getChapterScores(chapterId);

    }

}
