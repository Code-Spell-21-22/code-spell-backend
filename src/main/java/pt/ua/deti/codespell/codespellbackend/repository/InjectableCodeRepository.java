package pt.ua.deti.codespell.codespellbackend.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pt.ua.deti.codespell.codespellbackend.model.InjectableCode;

@Repository
public interface InjectableCodeRepository extends MongoRepository<InjectableCode, Long> {

    InjectableCode findByLevelId(ObjectId levelId);

    boolean existsByLevelId(ObjectId levelId);

}
