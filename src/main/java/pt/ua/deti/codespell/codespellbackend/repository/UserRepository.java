package pt.ua.deti.codespell.codespellbackend.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import pt.ua.deti.codespell.codespellbackend.model.User;

public interface UserRepository extends MongoRepository<User, Long> {

    User findByUsername(String username);

    User findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

}
