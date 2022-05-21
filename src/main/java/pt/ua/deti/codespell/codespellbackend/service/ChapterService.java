package pt.ua.deti.codespell.codespellbackend.service;

import java.util.List;

import com.mongodb.lang.NonNull;

import org.springframework.stereotype.Service;

import pt.ua.deti.codespell.codespellbackend.model.Chapter;
import pt.ua.deti.codespell.codespellbackend.repository.ChapterRepository;

@Service
public class ChapterService {

    private final ChapterRepository chapterRepository;

    public ChapterService(ChapterRepository chapterRepository) {
        this.chapterRepository = chapterRepository;
    }

    @NonNull 
    public List<Chapter> getAllChapters() {
        return chapterRepository.findAll();
    }
}
 