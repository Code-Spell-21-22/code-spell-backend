package pt.ua.deti.codespell.codespellbackend.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.ua.deti.codespell.codespellbackend.model.User;

import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class UserDAOTest {

    @Test
    void toDataEntity() {

        UserDAO userDAO = new UserDAO("Hugo1307", "hugogoncalves13@ua.pt", "12345");
        User user = new User("Hugo1307", "hugogoncalves13@ua.pt", "12345", Collections.emptyList());

        assertThat(userDAO.toDataEntity()).isEqualTo(user);

    }

}