package pt.ua.deti.codespell.codespellbackend.model;

import com.mongodb.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "solutions")
@Generated
@Data
@AllArgsConstructor
public class Solution implements IDataEntity {

    @Id
    @NonNull
    private ObjectId id;

    private ObjectId authorId;

    private int scorePoints;

    @NonNull
    private String code;

}
