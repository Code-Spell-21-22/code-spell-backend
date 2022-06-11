package pt.ua.deti.codespell.codespellbackend.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
    @JsonSerialize(using= ToStringSerializer.class)
    private ObjectId id;

    @NonNull
    @JsonSerialize(using=ToStringSerializer.class)
    private ObjectId levelId;

    @NonNull
    private String username;

    @NonNull
    private int points;

    private Settings settings;

}
