package pt.ua.deti.codespell.codespellbackend.service;

import java.util.Arrays;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import pt.ua.deti.codespell.codespellbackend.model.*;
import pt.ua.deti.codespell.codespellbackend.repository.ScoreRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ScoreServiceTest {
    
    @Mock
    private ScoreRepository scoreRepository;

    @InjectMocks
    private ScoreService scoreService;

    private Score score;

    private List<Score> listOfScores;

    @BeforeEach
    void setUp() {
        score = new Score(new ObjectId(), new ObjectId(), new ObjectId(), 30, 
        new Settings(ProgrammingLanguage.JAVA, SkillLevel.NOVICE));
        listOfScores = Arrays.asList(score);
    }

    @Test
    @DisplayName("Find all scores giving certain Settings and LevelId.")
    void findAllScoresByLevelIdAndSettings() {
        
        when(scoreRepository.findByLevelIdAndSettings(score.getLevelId(), score.getSettings())).thenReturn(listOfScores);

        assertThat(scoreService.getScoresByLevelAndSettings(score.getLevelId(), score.getSettings()))
                .isNotNull()
                .isEqualTo(listOfScores);

        verify(scoreRepository, Mockito.times(1)).findByLevelIdAndSettings(any(ObjectId.class), any(Settings.class));        
    }

}
