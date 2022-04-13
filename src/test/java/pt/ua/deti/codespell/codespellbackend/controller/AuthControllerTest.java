package pt.ua.deti.codespell.codespellbackend.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pt.ua.deti.codespell.codespellbackend.dao.UserDAO;
import pt.ua.deti.codespell.codespellbackend.exception.ErrorDetails;
import pt.ua.deti.codespell.codespellbackend.model.User;
import pt.ua.deti.codespell.codespellbackend.repository.UserRepository;
import pt.ua.deti.codespell.codespellbackend.request.LoginRequest;
import pt.ua.deti.codespell.codespellbackend.request.MessageResponse;
import pt.ua.deti.codespell.codespellbackend.security.auth.AuthTokenResponse;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureDataMongo
class AuthControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    private UserDAO userDAO;

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
    }

    @BeforeEach
    void setUp() {
        userDAO = new UserDAO("Hugo1307", "hugogoncalves13@ua.pt", "12345");
    }

    @Test
    void registerUser() {

        ResponseEntity<MessageResponse> response = restTemplate.postForEntity("/api/auth/register", userDAO, MessageResponse.class);

        List<User> users = userRepository.findAll();

        assertThat(userRepository.findByUsername("Hugo1307")).isNotNull();
        assertThat(users).hasSize(1).doesNotContainNull();
        assertThat(users).extracting(User::getUsername).containsOnly("Hugo1307");
        assertThat(users).extracting(User::getEmail).containsOnly("hugogoncalves13@ua.pt");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).extracting(MessageResponse::getMessage).isEqualTo("The user was successfully registered!");

    }

    @Test
    @DisplayName("Login with correct credentials")
    void loginUser() {

        restTemplate.postForEntity("/api/auth/register", userDAO, MessageResponse.class);

        LoginRequest loginRequest = new LoginRequest(userDAO.getEmail(), userDAO.getPassword());

        ResponseEntity<AuthTokenResponse> response = restTemplate.postForEntity("/api/auth/login", loginRequest, AuthTokenResponse.class);
        AuthTokenResponse authTokenResponse = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(authTokenResponse).isNotNull();
        assertThat(authTokenResponse).extracting(AuthTokenResponse::getToken).isNotNull();
        assertThat(authTokenResponse).extracting(AuthTokenResponse::getMessage).isEqualTo("Authentication succeeded.");

    }

    @Test
    @DisplayName("Login with wrong credentials")
    void loginWithWrongCredentials() {

        restTemplate.postForEntity("/api/auth/register", userDAO, MessageResponse.class);

        LoginRequest loginRequest = new LoginRequest(userDAO.getEmail(), "wrong_password");

        ResponseEntity<ErrorDetails> response = restTemplate.postForEntity("/api/auth/login", loginRequest, ErrorDetails.class);
        ErrorDetails errorDetailsResponse = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(errorDetailsResponse).isNotNull();
        assertThat(errorDetailsResponse).extracting(ErrorDetails::getTimestamp).isNotNull();
        assertThat(errorDetailsResponse).extracting(ErrorDetails::getMessage).isEqualTo("The provided password is wrong.");

    }

    @Test
    @DisplayName("Login with wrong body request")
    void loginWithWrongBodyRequest() {

        LoginRequest loginRequest = new LoginRequest(null, null);

        ResponseEntity<ErrorDetails> response = restTemplate.postForEntity("/api/auth/login", loginRequest, ErrorDetails.class);
        ErrorDetails errorDetailsResponse = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(errorDetailsResponse).isNotNull();
        assertThat(errorDetailsResponse).extracting(ErrorDetails::getTimestamp).isNotNull();
        assertThat(errorDetailsResponse).extracting(ErrorDetails::getMessage).isEqualTo("Please provide a valid request body.");

    }

}