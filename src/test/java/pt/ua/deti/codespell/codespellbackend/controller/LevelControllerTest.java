package pt.ua.deti.codespell.codespellbackend.controller;

import java.util.ArrayList;
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
import pt.ua.deti.codespell.codespellbackend.repository.LevelRepository;
import pt.ua.deti.codespell.codespellbackend.repository.ScoreRepository;
import pt.ua.deti.codespell.codespellbackend.request.LoginRequest;
import pt.ua.deti.codespell.codespellbackend.request.MessageResponse;
import pt.ua.deti.codespell.codespellbackend.security.auth.AuthTokenResponse;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureDataMongo
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LevelControllerTest {
    
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private LevelRepository levelRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    private HttpEntity<String> jwtEntity;

    private String url;

    private Level level;

    private HttpHeaders headers;
    
    @AfterEach
    public void tearDown() {
        scoreRepository.deleteAll();
        levelRepository.deleteAll();
    }
    
    @BeforeAll
    void beforeAll() {
        LoginRequest loginRequest = new LoginRequest("artur.romao@ua.pt", "artur123");
        restTemplate.postForEntity("/api/auth/register", new User("artur", "artur.romao@ua.pt", "artur123", Collections.emptyList()), MessageResponse.class);
        ResponseEntity<AuthTokenResponse> loginResponse = restTemplate.postForEntity("/api/auth/login", loginRequest, AuthTokenResponse.class);
        AuthTokenResponse authTokenResponse = loginResponse.getBody();

        String token = "Bearer " + authTokenResponse.getToken();
        headers = new HttpHeaders();
        headers.set("Authorization", token);
        jwtEntity = new HttpEntity<String>(headers);
    }

    @BeforeEach
    void setUp() {
        url = "/api/level/";
        
        Solution solution = new Solution(new ObjectId(), new ObjectId(), 100, 
        "class HelloWorldApp {\npublic static void main(String[] args) {\nString a = 'b';\nSystem.out.println(a);\n}\n}");
        List<Solution> solutions = new ArrayList<Solution>();
        solutions.add(solution);

        Documentation documentation = new Documentation(new ObjectId(), "Print in Java", "System.out.println()", 
        "https://www.javatpoint.com/how-to-print-in-java");
        List<Documentation> documentations = new ArrayList<Documentation>();
        documentations.add(documentation);

        level = new Level(new ObjectId(), "Print a Variable", "Learn how to print a variable.", 
        new ObjectId(), 1, Collections.emptyList(), Collections.emptyList(), documentations, 
        solutions, new Settings(ProgrammingLanguage.JAVA, SkillLevel.NOVICE));

        levelRepository.save(level);

        Score score = new Score(new ObjectId(), level.getId(), new ObjectId(), 
        100, new Settings(ProgrammingLanguage.JAVA, SkillLevel.NOVICE));

        scoreRepository.save(score);
    }


    /* @Test
    @DisplayName("Get level leaderboard")
    void getLevelLeaderboard() {
        url += String.valueOf(level.getId()) + "/leaderboards";

        //LevelLeaderboardRequest levelLeaderboardRequest = new LevelLeaderboardRequest(new Settings(ProgrammingLanguage.JAVA, SkillLevel.NOVICE));
        //HttpEntity<LevelLeaderboardRequest> request = new HttpEntity<>(levelLeaderboardRequest, headers);
        HttpEntity<Settings> request = new HttpEntity<>(new Settings(ProgrammingLanguage.JAVA, SkillLevel.NOVICE), headers);

        ResponseEntity<List<Score>> response = restTemplate
            .exchange(url, HttpMethod.GET, request, new ParameterizedTypeReference<List<Score>>() {
            });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).extracting(Score::getPoints).hasSize(1).doesNotContainNull();
        assertThat(response.getBody()).extracting(Score::getPoints).containsOnly(100);
    } */

    @Test
    @DisplayName("Get level documentation")
    void getLevelDocumentation() {
        url += String.valueOf(level.getId()) + "/documentation";
        ResponseEntity<List<Documentation>> response = restTemplate
            .exchange(url, HttpMethod.GET, jwtEntity, new ParameterizedTypeReference<List<Documentation>>() {
            });
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).extracting(Documentation::getTitle).hasSize(1).doesNotContainNull();
        assertThat(response.getBody()).extracting(Documentation::getTitle).containsOnly("Print in Java");
        assertThat(response.getBody()).extracting(Documentation::getDescription).containsOnly("System.out.println()");
        assertThat(response.getBody()).extracting(Documentation::getLink).containsOnly("https://www.javatpoint.com/how-to-print-in-java");
    }

    @Test
    @DisplayName("Get levels list")
    void getLevelsList() {
        url += "/";
        ResponseEntity<List<Level>> response = restTemplate
            .exchange(url, HttpMethod.GET, jwtEntity, new ParameterizedTypeReference<List<Level>>() {
            });
            
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).extracting(Level::getTitle).hasSize(1).doesNotContainNull();
        assertThat(response.getBody()).extracting(Level::getTitle).containsOnly("Print a Variable");
        assertThat(response.getBody()).extracting(Level::getDescription).containsOnly("Learn how to print a variable.");
    }

    @Test
    @DisplayName("Get level solutions")
    void getLevelSolutions() {
        url += String.valueOf(level.getId()) + "/solutions";
        ResponseEntity<List<Solution>> response = restTemplate
            .exchange(url, HttpMethod.GET, jwtEntity, new ParameterizedTypeReference<List<Solution>>() {
            });
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).extracting(Solution::getScorePoints).hasSize(1).doesNotContainNull();
        assertThat(response.getBody()).extracting(Solution::getScorePoints).containsOnly(100);
        assertThat(response.getBody()).extracting(Solution::getCode).containsOnly("class HelloWorldApp {\npublic static void main(String[] args) {\nString a = 'b';\nSystem.out.println(a);\n}\n}");
    }

    /* @Test
    @DisplayName("Submit a level solution")
    void submitLevelSolution() {
        Solution solution = new Solution(new ObjectId(), new ObjectId(), 200, "Some code...");
        List<Solution> solutions = level.getSolutions();
        solutions.add(solution);
        level.setSolutions(solutions);
        levelRepository.save(level);
        url += String.valueOf(level.getId()) + "/submit/" + String.valueOf(solution.getId());
        HttpEntity<String> request = new HttpEntity<>("Some code...", headers);

        ResponseEntity<MessageResponse> response = restTemplate
            .exchange(url, HttpMethod.POST, request, new ParameterizedTypeReference<MessageResponse>() {
            });

        List<Level> levels = levelRepository.findAll();

        assertThat(levelRepository.findById(level.getId())).isNotNull();
        assertThat(levels).hasSize(1).doesNotContainNull();
        assertThat(levels).extracting(Level::getTitle).containsOnly("Print a Variable");
        assertThat(levels).extracting(Level::getDescription).containsOnly("Learn how to print a variable.");
        assertThat(levels).extracting(Level::getSolutions).containsOnly(level.getSolutions());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).extracting(MessageResponse::getMessage).isEqualTo("Solution successfully submitted.");
    } */

    @Test
    @DisplayName("Get current level")
    void getCurrentLevel() {
        url += "/" + String.valueOf(level.getId());
        ResponseEntity<Level> response = restTemplate
            .exchange(url, HttpMethod.GET, jwtEntity, new ParameterizedTypeReference<Level>() {
            });
            
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTitle()).isEqualTo("Print a Variable");
        assertThat(response.getBody().getDescription()).isEqualTo("Learn how to print a variable.");
        assertThat(response.getBody().getNumber()).isEqualTo(1);

    }
}
