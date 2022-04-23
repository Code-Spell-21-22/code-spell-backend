package pt.ua.deti.codespell.codespellbackend.model;

import com.mongodb.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "goals")
@Generated
@Data
@AllArgsConstructor
public class Goal implements IDataEntity {

    @Id
    @NonNull
    private ObjectId id;

    @NonNull
    private String title;

    @NonNull
    private String description;

}
