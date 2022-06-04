package pt.ua.deti.codespell.codespellbackend.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import pt.ua.deti.codespell.codespellbackend.model.Level;

public interface LevelRepository extends MongoRepository<Level, Long> {
    
    Level findById(ObjectId levelId);

    boolean existsById(ObjectId levelId);

}
