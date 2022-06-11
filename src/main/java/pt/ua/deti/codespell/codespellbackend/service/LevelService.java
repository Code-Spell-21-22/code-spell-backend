package pt.ua.deti.codespell.codespellbackend.service;

import java.util.List;

import com.mongodb.lang.NonNull;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pt.ua.deti.codespell.codespellbackend.exception.implementations.LevelNotFoundException;
import pt.ua.deti.codespell.codespellbackend.model.Level;
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
    public Level findByLevelId(ObjectId levelId) {
        if (!levelRepository.existsById(levelId))
            throw new LevelNotFoundException(String.format("The level %s could not be found.", levelId));
        return levelRepository.findById(levelId);
    }


    public boolean existsByLevelId(ObjectId levelId) {
        return levelRepository.existsById(levelId);
    }

}
