package pt.ua.deti.codespell.codespellbackend.model;

import com.mongodb.lang.NonNull;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Document(collection = "users")
@Generated
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements IDataEntity {

    @Id
    @Field("username")
    @NonNull
    private String username;

    @NonNull
    @Indexed(unique = true)
    private String email;

    @NonNull
    private String password;

    @NonNull
    private List<GrantedAuthority> permissions;

    private String name;

    private List<ObjectId> friendsIds;

    private List<Achievement> achievements;

    public User(@NonNull String username, @NonNull String email, @NonNull String password, @NonNull List<GrantedAuthority> permissions) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.permissions = permissions;
    }

}
