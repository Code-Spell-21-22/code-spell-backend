package pt.ua.deti.codespell.codespellbackend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pt.ua.deti.codespell.codespellbackend.model.Achievement;

public interface AchievementRepository extends MongoRepository<Achievement, Long> {

    Achievement findByTitle(String title);

    boolean existsByTitle(String title);

}
