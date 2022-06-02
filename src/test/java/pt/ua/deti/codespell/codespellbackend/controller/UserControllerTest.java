package pt.ua.deti.codespell.codespellbackend.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.type.CollectionType;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

import pt.ua.deti.codespell.codespellbackend.model.*;
import pt.ua.deti.codespell.codespellbackend.repository.UserRepository;
import pt.ua.deti.codespell.codespellbackend.request.LoginRequest;
import pt.ua.deti.codespell.codespellbackend.request.MessageResponse;
import pt.ua.deti.codespell.codespellbackend.security.auth.AuthTokenResponse;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureDataMongo
public class UserControllerTest {
    
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    private User user;
    
    private String url;

    private AuthTokenResponse authTokenResponse;

    private LoginRequest loginRequest;

    private HttpEntity<String> jwtEntity;

    @BeforeEach
    void setUp() {
        url = "/api/user/";
        user = new User("artur01", "artur.romao01@ua.pt", "12345", Collections.emptyList());

        loginRequest = new LoginRequest(user.getEmail(), user.getPassword());

        restTemplate.postForEntity("/api/auth/register", user, MessageResponse.class);

        ResponseEntity<AuthTokenResponse> response = restTemplate.postForEntity("/api/auth/login", loginRequest, AuthTokenResponse.class);
        authTokenResponse = response.getBody();

        String token = "Bearer " + authTokenResponse.getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        jwtEntity = new HttpEntity<String>(headers);
        
    }

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void getUserDetails() {
        userRepository.save(user);

        url += user.getUsername() + "/details";
        ResponseEntity<User> response = restTemplate
            .exchange(url, HttpMethod.GET, jwtEntity, new ParameterizedTypeReference<User>() {
            });
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).extracting(User::getUsername).isEqualTo("artur01");
        assertThat(response.getBody()).extracting(User::getEmail).isEqualTo("artur.romao01@ua.pt");
    }

    @Test
    void getUserAchievements() {
        Achievement achievement = new Achievement(new ObjectId(), "First level completed!", "Congrats on your first level passed :)", new Date(), true);
        List<Achievement> achievements = new ArrayList<Achievement>();
        achievements.add(achievement);
        
        Game game = new Game(new ObjectId(), Collections.emptyList(), Collections.emptyList(), new Score(new ObjectId(), new ObjectId(), new ObjectId(), 100, new Settings(ProgrammingLanguage.JAVA, SkillLevel.NOVICE)), new Settings(ProgrammingLanguage.JAVA, SkillLevel.NOVICE), achievements);
        List<Game> games = new ArrayList<Game>();
        game.setAchievements(achievements);
        games.add(game);
        user.setGames(games);

        userRepository.save(user);
        
        url += user.getUsername() + "/achievements";
        ResponseEntity<List<Achievement>> response = restTemplate
        .exchange(url, HttpMethod.GET, jwtEntity, new ParameterizedTypeReference<List<Achievement>>() {
        });
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).extracting(Achievement::getTitle).containsOnly("First level completed!");
        assertThat(response.getBody()).extracting(Achievement::getDescription).containsOnly("Congrats on your first level passed :)");
    }

    @Test
    void changePassword() {

    }

    @Test
    void changeName() {

    }
}