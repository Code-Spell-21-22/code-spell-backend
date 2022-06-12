package pt.ua.deti.codespell.codespellbackend.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import pt.ua.deti.codespell.codespellbackend.exception.implementations.ChapterNotFoundException;
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

    @NonNull
    public Chapter getChapterById(ObjectId chapterId) {
        if (!chapterRepository.existsById(chapterId))
            throw new ChapterNotFoundException(String.format("The chapter %s could not be found.", chapterId.toString()));
        return chapterRepository.findById(chapterId);
    }

    public boolean existsById(ObjectId objectId) {
        return chapterRepository.existsById(objectId);
    }

}
 