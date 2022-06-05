package pt.ua.deti.codespell.codespellbackend.controller;

import java.util.Collections;
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

import pt.ua.deti.codespell.codespellbackend.model.*;
import pt.ua.deti.codespell.codespellbackend.repository.ChapterRepository;
import pt.ua.deti.codespell.codespellbackend.request.LoginRequest;
import pt.ua.deti.codespell.codespellbackend.request.MessageResponse;
import pt.ua.deti.codespell.codespellbackend.security.auth.AuthTokenResponse;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureDataMongo
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ChapterControllerTest {
    
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ChapterRepository chapterRepository;

    private HttpEntity<String> jwtEntity;

    @BeforeAll
    void beforeAll() {
        LoginRequest loginRequest = new LoginRequest("artur.romao@ua.pt", "artur123");
        restTemplate.postForEntity("/api/auth/register", new User("artur", "artur.romao@ua.pt", "artur123", Collections.emptyList()), MessageResponse.class);
        ResponseEntity<AuthTokenResponse> loginResponse = restTemplate.postForEntity("/api/auth/login", loginRequest, AuthTokenResponse.class);
        AuthTokenResponse authTokenResponse = loginResponse.getBody();

        String token = "Bearer " + authTokenResponse.getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        jwtEntity = new HttpEntity<String>(headers);
    }

    @BeforeEach
    void setUp() {
        Chapter chapter1 = new Chapter(new ObjectId(), "Variables", "Learn the basics about Java variables.", 
        1, Collections.emptyList(), Collections.emptyList(), new Settings(ProgrammingLanguage.JAVA, SkillLevel.NOVICE));
        Chapter chapter2 = new Chapter(new ObjectId(), "Object Oriented Programming", "Learn the basics about Java OOP.", 
        2, Collections.emptyList(), Collections.emptyList(), new Settings(ProgrammingLanguage.JAVA, SkillLevel.NOVICE));

        chapterRepository.save(chapter1);
        chapterRepository.save(chapter2);
    }

    @AfterEach
    void tearDown() {
        chapterRepository.deleteAll();
    }

    @Test
    @DisplayName("Return the list of all chapters")
    void getAllChapters() {
        String url = "/api/chapter/";

        ResponseEntity<List<Chapter>> response = restTemplate
        .exchange(url, HttpMethod.GET, jwtEntity, new ParameterizedTypeReference<List<Chapter>>() {
        });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).extracting(Chapter::getTitle).hasSize(2).doesNotContainNull();
        assertThat(response.getBody()).extracting(Chapter::getTitle).containsOnly("Variables", "Object Oriented Programming");
        assertThat(response.getBody()).extracting(Chapter::getDescription).containsOnly("Learn the basics about Java variables.", "Learn the basics about Java OOP.");
    }
}
