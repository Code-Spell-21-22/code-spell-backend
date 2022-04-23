package pt.ua.deti.codespell.codespellbackend.model;

import com.mongodb.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "achievements")
@Generated
@Data
@AllArgsConstructor
public class Achievement implements IDataEntity {

    @Id
    @NonNull
    private ObjectId id;

    @NonNull
    private String title;

    @NonNull
    private String description;

    private Date timestamp;

    @NonNull
    private boolean isCompleted;

}
