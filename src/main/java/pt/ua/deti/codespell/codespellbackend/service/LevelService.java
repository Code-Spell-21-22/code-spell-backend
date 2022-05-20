package pt.ua.deti.codespell.codespellbackend.service;

import java.util.List;

import com.mongodb.lang.NonNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pt.ua.deti.codespell.codespellbackend.exception.implementations.LevelNotFoundException;
import pt.ua.deti.codespell.codespellbackend.model.Level;
import pt.ua.deti.codespell.codespellbackend.model.Score;
import pt.ua.deti.codespell.codespellbackend.repository.LevelRepository;

@Service
public class LevelService {
    
    private final LevelRepository levelRepository;
    
    @Autowired
    public LevelService(LevelRepository levelRepository) {
        this.levelRepository = levelRepository;
    }

    @NonNull 
    public List<Level> getAllLevels() {
        return levelRepository.findAll();
    }

    @NonNull
    public Level findByLevelId(int level_id) {
        if (!levelRepository.existsByLevelId(level_id))
        throw new LevelNotFoundException(String.format("The level %d could not be found.", level_id));
        return levelRepository.findByLevelId(level_id);
    }

    @NonNull
    public List<Score> getScoresByLevelId(int level_id) {
        if (!levelRepository.existsByLevelId(level_id))
        throw new LevelNotFoundException(String.format("The level %d could not be found.", level_id));
        return levelRepository.findScoresByLevelId(level_id);
    }
}
