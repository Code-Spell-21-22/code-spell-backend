package pt.ua.deti.codespell.codespellbackend.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pt.ua.deti.codespell.codespellbackend.model.Achievement;
import pt.ua.deti.codespell.codespellbackend.service.AchievementService;

@Repository
public interface AchievementRepository extends MongoRepository<Achievement, Long> {

    Achievement findById(ObjectId achievementId);

    boolean existsById(ObjectId achievementId);

}
