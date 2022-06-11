package pt.ua.deti.codespell.codespellbackend.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import pt.ua.deti.codespell.codespellbackend.model.Score;
import pt.ua.deti.codespell.codespellbackend.model.Settings;

import java.util.List;

public interface ScoreRepository extends MongoRepository<Score, Long> {

    List<Score> findByLevelId(ObjectId objectId);

    List<Score> findByLevelIdAndSettings(ObjectId objectId, Settings settings);

    List<Score> findByUsernameAndLevelIdAndSettings(String username, ObjectId objectId, Settings settings);

}
