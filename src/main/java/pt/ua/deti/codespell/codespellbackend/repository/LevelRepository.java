package pt.ua.deti.codespell.codespellbackend.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import pt.ua.deti.codespell.codespellbackend.model.Level;
import pt.ua.deti.codespell.codespellbackend.model.Score;

public interface LevelRepository extends MongoRepository<Level, Long> {
    
    Level findByLevelId(int level_id);

    boolean existsByLevelId(int level_id);
    
    List<Score> findScoresByLevelId(int level_id); // either this is right or we need to add List<Score> to Level.java

}
