package pt.ua.deti.codespell.codespellbackend.model;

import com.mongodb.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "games")
@Generated
@Data
@AllArgsConstructor
public class Game implements IDataEntity {

    @Id
    @NonNull
    private ObjectId id;

    private List<Chapter> chapters;

    private List<Chapter> completedChapters;

    private Score score;

    private Settings settings;

    private List<Achievement> achievements;

}
