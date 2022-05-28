package pt.ua.deti.codespell.codespellbackend.service;


import java.util.Arrays;
import java.util.Collections;
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

import pt.ua.deti.codespell.codespellbackend.exception.implementations.LevelNotFoundException;
import pt.ua.deti.codespell.codespellbackend.model.*;
import pt.ua.deti.codespell.codespellbackend.repository.LevelRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class LevelServiceTest {
    
    @Mock
    private LevelRepository levelRepository;
    
    @InjectMocks
    private LevelService levelService;
    
    private Level level;
    
    private List<Level> listOfLevels;
    
    @BeforeEach
    void setUp() {
        level = new Level(new ObjectId(), "Variables", "Basic Java variables knowledge.", 
        new ObjectId(), 1, Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), 
        Collections.emptyList(), new Settings(ProgrammingLanguage.JAVA, SkillLevel.NOVICE));
        listOfLevels = Arrays.asList(level);
    }

    @Test
    @DisplayName("Find all levels.")
    void findAllLevels() {
        when(levelRepository.findAll()).thenReturn(listOfLevels);

        assertThat(levelService.getAllLevels())
                .isNotNull()
                .isEqualTo(listOfLevels);

        verify(levelRepository, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("Find level by id.")
    void findById() {
        
        when(levelRepository.findById(level.getId())).thenReturn(level);
        when(levelRepository.existsById(level.getId())).thenReturn(true);

        assertThat(levelService.findByLevelId(level.getId()))
                .isNotNull()
                .isEqualTo(level);

        verify(levelRepository, Mockito.times(1)).findById(any(ObjectId.class));
        verify(levelRepository, Mockito.times(1)).existsById(any(ObjectId.class));

    }

    @Test
    @DisplayName("Find level by non-existent id.")
    void findByNonExistentId() {

        when(levelRepository.existsById(level.getId())).thenReturn(false);

        assertThatThrownBy(() -> levelService.findByLevelId(level.getId()))
                .isInstanceOf(LevelNotFoundException.class)
                .hasMessage(String.format("The level %s could not be found.", level.getId()));

        verify(levelRepository, Mockito.times(0)).findById(any(ObjectId.class));
        verify(levelRepository, Mockito.times(1)).existsById(any(ObjectId.class));

    }


}
