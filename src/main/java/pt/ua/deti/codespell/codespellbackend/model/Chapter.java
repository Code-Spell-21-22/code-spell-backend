package pt.ua.deti.codespell.codespellbackend.model;

import com.mongodb.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "chapters")
@Generated
@Data
@AllArgsConstructor
public class Chapter implements IDataEntity {

    @Id
    @NonNull
    private ObjectId id;

    @NonNull
    private String title;

    private String description;

    private int number;

    private List<Level> levels;

    private List<Level> completedLevels;

    private Settings settings;

}
