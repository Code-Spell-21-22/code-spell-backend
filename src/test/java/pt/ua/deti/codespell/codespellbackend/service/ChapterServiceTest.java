package pt.ua.deti.codespell.codespellbackend.service;

import java.util.Collections;
import java.util.List;
import java.util.Arrays;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import pt.ua.deti.codespell.codespellbackend.exception.implementations.ChapterNotFoundException;
import pt.ua.deti.codespell.codespellbackend.model.*;
import pt.ua.deti.codespell.codespellbackend.repository.ChapterRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ChapterServiceTest {
    
    @Mock
    private ChapterRepository chapterRepository;

    @InjectMocks
    private ChapterService chapterService;

    private Chapter chapter;

    private List<Chapter> listOfChapters;

    @BeforeEach
    void setUp() {
        chapter = new Chapter(new ObjectId(), "Object Oriented Programming", "Learn OOP principles", 
        3, Collections.emptyList(), Collections.emptyList(), new Settings(ProgrammingLanguage.JAVA, SkillLevel.NOVICE));
        listOfChapters = Arrays.asList(chapter);
    }

    @Test
    @DisplayName("Find all chapters.")
    void findAllChapters() {
        when(chapterRepository.findAll()).thenReturn(listOfChapters);

        assertThat(chapterService.getAllChapters())
                .isNotNull()
                .isEqualTo(listOfChapters);

        verify(chapterRepository, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("Find chapter by id.")
    void findById() {
        
        when(chapterRepository.findById(chapter.getId())).thenReturn(chapter);
        when(chapterRepository.existsById(chapter.getId())).thenReturn(true);

        assertThat(chapterService.getChapterById(chapter.getId()))
                .isNotNull()
                .isEqualTo(chapter);

        verify(chapterRepository, Mockito.times(1)).findById(any(ObjectId.class));
        verify(chapterRepository, Mockito.times(1)).existsById(any(ObjectId.class));        
    }

    @Test
    @DisplayName("Find chapter by non-existent id.")
    void findByNonExistentId() {

        when(chapterRepository.existsById(chapter.getId())).thenReturn(false);

        assertThatThrownBy(() -> chapterService.getChapterById(chapter.getId()))
                .isInstanceOf(ChapterNotFoundException.class)
                .hasMessage(String.format("The chapter %s could not be found.", chapter.getId()));

        verify(chapterRepository, Mockito.times(0)).findById(any(ObjectId.class));
        verify(chapterRepository, Mockito.times(1)).existsById(any(ObjectId.class));

    }    
}