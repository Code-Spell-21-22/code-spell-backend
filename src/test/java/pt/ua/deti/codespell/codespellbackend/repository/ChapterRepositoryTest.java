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

import pt.ua.deti.codespell.codespellbackend.model.Chapter;
import pt.ua.deti.codespell.codespellbackend.model.ProgrammingLanguage;
import pt.ua.deti.codespell.codespellbackend.model.Settings;
import pt.ua.deti.codespell.codespellbackend.model.SkillLevel;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
public class ChapterRepositoryTest {
    
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ChapterRepository chapterRepository;

    private Chapter chapter;

    @BeforeEach
    void setUp() {
        chapter = new Chapter(new ObjectId(), "Object Oriented Programming", "Learn OOP principles.",
        5, Collections.emptyList(), Collections.emptyList(), new Settings(ProgrammingLanguage.JAVA, SkillLevel.NOVICE));
    }

    @AfterEach
    void tearDown() {
        mongoTemplate.dropCollection(Chapter.class);
    }

    @Test
    @DisplayName("Find chapter by id")
    void findById() {
        chapterRepository.save(chapter);

        Chapter loadedChapter = chapterRepository.findById(chapter.getId());

        assertThat(loadedChapter)
                .isNotNull()
                .isEqualTo(chapter);
    }

    @Test
    @DisplayName("Find non-existent chapter by id")
    void findNonExistentChapterById() {
        Chapter loadedChapter = chapterRepository.findById(new ObjectId());
        assertThat(loadedChapter).isNull();
    }

    @Test
    @DisplayName("Check if chapter exists by id")
    void existsById() {
        chapterRepository.save(chapter);
        assertThat(chapterRepository.existsById(chapter.getId())).isTrue();
    }

    @Test
    @DisplayName("Check if chapter exists by id")
    void noExistsById() {
        assertThat(chapterRepository.existsById(new ObjectId())).isFalse();
    }
}
