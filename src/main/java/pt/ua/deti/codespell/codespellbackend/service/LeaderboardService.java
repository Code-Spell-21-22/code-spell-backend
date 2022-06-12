package pt.ua.deti.codespell.codespellbackend.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ua.deti.codespell.codespellbackend.model.Score;
import pt.ua.deti.codespell.codespellbackend.repository.LevelRepository;
import pt.ua.deti.codespell.codespellbackend.repository.ScoreRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaderboardService {

    private final ScoreRepository scoreRepository;
    private final LevelRepository levelRepository;

    @Autowired
    public LeaderboardService(ScoreRepository scoreRepository, LevelRepository levelRepository) {
        this.scoreRepository = scoreRepository;
        this.levelRepository = levelRepository;
    }


    public List<Score> getAllScores() {
        return scoreRepository.findAll().stream().sorted(Comparator.comparingInt(Score::getPoints).reversed()).collect(Collectors.toList());
    }

    public List<Score> getChapterScores(ObjectId chapterId) {

        List<Score> allScores = getAllScores();

        return allScores.stream()
                .filter(score -> levelRepository.existsById(score.getLevelId()) && levelRepository.findById(score.getLevelId()).getChapterId().equals(chapterId))
                .sorted(Comparator.comparingInt(Score::getPoints).reversed())
                .collect(Collectors.toList());

    }

    public List<Score> getLevelScores(ObjectId levelId) {

        List<Score> allScores = getAllScores();

        return allScores.stream()
                .filter(score -> score.getLevelId().equals(levelId))
                .sorted(Comparator.comparingInt(Score::getPoints).reversed())
                .collect(Collectors.toList());

    }

}
