package pt.ua.deti.codespell.codespellbackend.repository;

import java.util.Collections;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import pt.ua.deti.codespell.codespellbackend.model.Level;
import pt.ua.deti.codespell.codespellbackend.model.ProgrammingLanguage;
import pt.ua.deti.codespell.codespellbackend.model.Settings;
import pt.ua.deti.codespell.codespellbackend.model.SkillLevel;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
public class LevelRepositoryTest {
    
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private LevelRepository levelRepository;

    private Level level;

    @BeforeEach
    void setUp() {
        level = new Level(new ObjectId(), "Printing variables", "Learn how to initialize and print variables",
        new ObjectId(), 1, Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), 
        Collections.emptyList(), new Settings(ProgrammingLanguage.JAVA, SkillLevel.NOVICE));
    }

    @AfterEach
    void tearDown() {
        mongoTemplate.dropCollection(Level.class);
    }

    @Test
    @DisplayName("Find level by id")
    void findById() {
        levelRepository.save(level);

        Level loadedLevel = levelRepository.findById(level.getId());

        assertThat(loadedLevel)
                .isNotNull()
                .isEqualTo(level);
    }

    @Test
    @DisplayName("Find non-existent level by id")
    void findNonExistentLevelById() {
        Level loadedLevel = levelRepository.findById(new ObjectId());
        assertThat(loadedLevel).isNull();
    }

    @Test
    @DisplayName("Check if level exists by id")
    void existsById() {
        levelRepository.save(level);
        assertThat(levelRepository.existsById(level.getId())).isTrue();
    }

    @Test
    @DisplayName("Check if non-existent level exists by id")
    void noExistsById() {
        assertThat(levelRepository.existsById(new ObjectId())).isFalse();
    }
}
