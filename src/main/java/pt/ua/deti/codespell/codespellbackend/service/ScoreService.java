package pt.ua.deti.codespell.codespellbackend.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ua.deti.codespell.codespellbackend.model.Score;
import pt.ua.deti.codespell.codespellbackend.model.Settings;
import pt.ua.deti.codespell.codespellbackend.repository.ScoreRepository;

import java.util.List;

@Service
public class ScoreService {

    private final ScoreRepository scoreRepository;

    @Autowired
    public ScoreService(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    public List<Score> getScoresByLevel(ObjectId levelId) {
        return scoreRepository.findByLevelId(levelId);
    }

    public List<Score> getScoresByLevelAndSettings(ObjectId levelId, Settings settings) {
        return scoreRepository.findByLevelIdAndSettings(levelId, settings);
    }

    public void saveScore(String username, ObjectId levelId, int points, Settings settings) {

        System.out.println("Saving score");

        scoreRepository.deleteAll(scoreRepository.findByUsernameAndLevelIdAndSettings(username, levelId, settings));
        scoreRepository.save(new Score(null, levelId, username, points, settings));

    }

}
