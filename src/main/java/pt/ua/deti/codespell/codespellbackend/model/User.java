package pt.ua.deti.codespell.codespellbackend.model;

import com.mongodb.lang.NonNull;
import lombok.*;
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

    private List<GrantedAuthority> permissions;

}
