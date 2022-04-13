package pt.ua.deti.codespell.codespellbackend.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import pt.ua.deti.codespell.codespellbackend.model.User;

import java.util.ArrayList;

@Generated
@Data
@AllArgsConstructor
public class UserDAO implements IAbstractDAO {

    private String username;
    private String email;
    private String password;

    public User toDataEntity() {
        return new User(username, email, password, new ArrayList<>());
    }

}
