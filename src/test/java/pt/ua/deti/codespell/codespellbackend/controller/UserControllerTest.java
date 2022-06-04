package pt.ua.deti.codespell.codespellbackend.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import pt.ua.deti.codespell.codespellbackend.model.*;
import pt.ua.deti.codespell.codespellbackend.repository.UserRepository;
import pt.ua.deti.codespell.codespellbackend.request.ChangeNameRequest;
import pt.ua.deti.codespell.codespellbackend.request.ChangePasswordRequest;
import pt.ua.deti.codespell.codespellbackend.request.LoginRequest;
import pt.ua.deti.codespell.codespellbackend.request.MessageResponse;
import pt.ua.deti.codespell.codespellbackend.security.auth.AuthTokenResponse;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureDataMongo
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerTest {
    
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    private User user;
    
    private String url;

    private HttpEntity<String> jwtEntity;

    private HttpHeaders headers;

    @BeforeAll
    void beforeAll() {
        user = new User("artur01", "artur.romao01@ua.pt", "12345", Collections.emptyList());
        LoginRequest loginRequest = new LoginRequest(user.getEmail(), user.getPassword());

        restTemplate.postForEntity("/api/auth/register", user, MessageResponse.class);

        ResponseEntity<AuthTokenResponse> response = restTemplate.postForEntity("/api/auth/login", loginRequest, AuthTokenResponse.class);
        AuthTokenResponse authTokenResponse = response.getBody();

        String token = "Bearer " + authTokenResponse.getToken();
        headers = new HttpHeaders();
        headers.set("Authorization", token);
        jwtEntity = new HttpEntity<String>(headers);
    }

    @BeforeEach
    void setUp() {
        url = "/api/user/";
        user = new User("artur01", "artur.romao01@ua.pt", "12345", Collections.emptyList());

        Achievement achievement = new Achievement(new ObjectId(), "First level completed!", "Congrats on your first level passed :)", new Date(), true);
        List<Achievement> achievements = new ArrayList<Achievement>();
        achievements.add(achievement);
        
        Game game = new Game(new ObjectId(), Collections.emptyList(), Collections.emptyList(), new Score(new ObjectId(), new ObjectId(), new ObjectId(), 100, new Settings(ProgrammingLanguage.JAVA, SkillLevel.NOVICE)), new Settings(ProgrammingLanguage.JAVA, SkillLevel.NOVICE), achievements);
        List<Game> games = new ArrayList<Game>();
        game.setAchievements(achievements);
        games.add(game);
        user.setGames(games);
        
        userRepository.save(user);
    }

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("Get user details")
    void getUserDetails() {
        url += user.getUsername() + "/details";
        ResponseEntity<User> response = restTemplate
            .exchange(url, HttpMethod.GET, jwtEntity, new ParameterizedTypeReference<User>() {
            });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).extracting(User::getUsername).isEqualTo("artur01");
        assertThat(response.getBody()).extracting(User::getEmail).isEqualTo("artur.romao01@ua.pt");
    }

    @Test
    @DisplayName("Get user achievements")
    void getUserAchievements() {
        url += user.getUsername() + "/achievements";
        ResponseEntity<List<Achievement>> response = restTemplate
        .exchange(url, HttpMethod.GET, jwtEntity, new ParameterizedTypeReference<List<Achievement>>() {
        });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).extracting(Achievement::getTitle).hasSize(1).doesNotContainNull();
        assertThat(response.getBody()).extracting(Achievement::getTitle).containsOnly("First level completed!");
        assertThat(response.getBody()).extracting(Achievement::getDescription).containsOnly("Congrats on your first level passed :)");
    }

    @Test
    @DisplayName("Change user password")
    void changePassword() {
        url += user.getUsername() + "/password";
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(user.getEmail(), "54321");
        HttpEntity<ChangePasswordRequest> httpEntityRequest = new HttpEntity<>(changePasswordRequest, headers);
        ResponseEntity<MessageResponse> response = restTemplate.exchange(url, HttpMethod.PUT, httpEntityRequest, new ParameterizedTypeReference<MessageResponse>(){
        });

        List<User> users = userRepository.findAll();

        assertThat(userRepository.findByUsername("artur01")).isNotNull();
        assertThat(users).hasSize(1).doesNotContainNull();
        assertThat(users).extracting(User::getUsername).containsOnly("artur01");
        assertThat(users).extracting(User::getEmail).containsOnly("artur.romao01@ua.pt");
        
        //PasswordEncoder passwordEncoder = new PasswordEncoder();
        //assertThat(users).extracting(User::getPassword).isEqualTo(passwordEncoder.encode("54321"));
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).extracting(MessageResponse::getMessage).isEqualTo("Your password was successfully changed.");
    }

    @Test
    @DisplayName("Change user name field")
    void changeName() {
        url += user.getUsername() + "/name";
        ChangeNameRequest changeNameRequest = new ChangeNameRequest("artur.romao01@ua.pt", "Artur");
        HttpEntity<ChangeNameRequest> httpEntityRequest = new HttpEntity<>(changeNameRequest, headers);
        ResponseEntity<MessageResponse> response = restTemplate.exchange(url, HttpMethod.PUT, httpEntityRequest, new ParameterizedTypeReference<MessageResponse>(){
        });

        List<User> users = userRepository.findAll();

        assertThat(userRepository.findByUsername("artur01")).isNotNull();
        assertThat(users).hasSize(1).doesNotContainNull();
        assertThat(users).extracting(User::getUsername).containsOnly("artur01");
        assertThat(users).extracting(User::getEmail).containsOnly("artur.romao01@ua.pt");
        assertThat(users).extracting(User::getName).containsOnly("Artur");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).extracting(MessageResponse::getMessage).isEqualTo("Your name was successfully changed.");
    }
}