package pt.ua.deti.codespell.codespellbackend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pt.ua.deti.codespell.codespellbackend.model.Achievement;

@Repository
public interface AchievementRepository extends MongoRepository<Achievement, Long> {

}
