package pt.ua.deti.codespell.codespellbackend.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import pt.ua.deti.codespell.codespellbackend.model.Chapter;

public interface ChapterRepository extends MongoRepository<Chapter, Long> {

    Chapter findById(ObjectId chapterId);

    boolean existsById(ObjectId chapterId);

}
