package pt.ua.deti.codespell.codespellbackend.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import pt.ua.deti.codespell.codespellbackend.model.*;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
public class ScoreRepositoryTest {
    
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ScoreRepository scoreRepository;

    private Score score;

    @BeforeEach
    void setUp() {
        score = new Score(new ObjectId(), new ObjectId(), new ObjectId(), 3000, new Settings(ProgrammingLanguage.JAVA, SkillLevel.NOVICE));
    }

    @AfterEach
    void tearDown() {
        mongoTemplate.dropCollection(Score.class);
    }

    @Test
    @DisplayName("Find all scores given certain Settings and LevelId")
    void findAllScoresByLevelIdAndSettings() {
        scoreRepository.save(score);

        List<Score> loadedScores = scoreRepository.findByLevelIdAndSettings(score.getLevelId(), score.getSettings());

        assertThat(loadedScores.get(0))
                .isNotNull()
                .isEqualTo(score);
    }

    @Test
    @DisplayName("Find non-existent scores given invalid Settings or LevelId")
    void findNonExistentScoresByInvalidLevelIdOrSettings() {
        // Test with invalid LevelId and valid Settings
        List<Score> loadedScores = scoreRepository.findByLevelIdAndSettings(new ObjectId(), score.getSettings());
        assertThat(loadedScores).isEmpty();

        // Test with valid LevelId and invalid Settings
        loadedScores = scoreRepository.findByLevelIdAndSettings(score.getLevelId(), new Settings(ProgrammingLanguage.JAVA, SkillLevel.ADVANCED));
        assertThat(loadedScores).isEmpty();
        
        // Test with invalid LevelId and invalid Settings
        loadedScores = scoreRepository.findByLevelIdAndSettings(new ObjectId(), new Settings(ProgrammingLanguage.JAVA, SkillLevel.ADVANCED));
        assertThat(loadedScores).isEmpty();
    }
}
