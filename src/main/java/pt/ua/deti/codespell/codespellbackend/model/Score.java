package pt.ua.deti.codespell.codespellbackend.model;

import com.mongodb.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "scores")
@Generated
@Data
@AllArgsConstructor
public class Score implements IDataEntity {

    @Id
    @NonNull
    private ObjectId id;

    @NonNull
    private ObjectId levelId;

    private int points;

}
