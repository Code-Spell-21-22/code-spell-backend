package pt.ua.deti.codespell.codespellbackend.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pt.ua.deti.codespell.codespellbackend.model.Solution;

import java.util.List;

@Repository
public interface SolutionRepository extends MongoRepository<Solution, Long> {

    List<Solution> findAllByAuthorUsernameAndLevelId(String authorUsername, ObjectId levelId);

    List<Solution> findAllByAuthorUsername(String authorUsername);

    List<Solution> findAllByLevelId(ObjectId levelId);

}
