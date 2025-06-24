package com.dawkastresu.git_proxy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.stream.Collectors;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_CLASS;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_CLASS;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureWireMock(port = 8083)
@Sql(scripts = {"/import_data.sql"}, executionPhase = BEFORE_TEST_CLASS)
@Sql(scripts = "/cleanup.sql", executionPhase = AFTER_TEST_CLASS)
@TestPropertySource("classpath:application-test.properties")
public class IntegrationTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    WireMockServer wireMockServer;

    @Autowired
    RepoController repoController;

    @Autowired
    RepoRepository repository;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        wireMockServer.start();
    }

    @AfterEach
    void shutdown() {
        wireMockServer.resetAll();
        wireMockServer.stop();
    }

    @Test
    void shouldGetRepositoryFromAPI() throws Exception {
        GitHubRepo gitHubRepo = GitHubRepo.builder()
                .fullName("/name/myrepo")
                .description("goat repo for integration test")
                .watchersCount(120)
                .build();

        wireMockServer.stubFor(get("/repos/name/myrepo").willReturn(ok()
                .withHeader("Content-Type", "application/json")
                .withBody(objectMapper.writeValueAsString(gitHubRepo))));

        mockMvc.perform(MockMvcRequestBuilders.get("/repos/{owner}/{repo}", "name", "myrepo")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("/name/myrepo"))
                .andExpect(jsonPath("$.description").value("goat repo for integration test"))
                .andExpect(jsonPath("$.watchersCount").value(120));
    }

    @Test
    void shouldSaveRepositoryInDb() throws Exception {
        GitHubRepo gitHubRepo = GitHubRepo.builder()
                .fullName("/testname/myrepo")
                .description("goat repo for integration test")
                .watchersCount(120)
                .build();

        wireMockServer.stubFor(get("/repos/testname/myrepo").willReturn(ok()
                .withHeader("Content-Type", "application/json")
                .withBody(objectMapper.writeValueAsString(gitHubRepo))));

        mockMvc.perform(MockMvcRequestBuilders.post("/repos/{owner}/{repo}", "testname", "myrepo")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("/testname/myrepo"))
                .andExpect(jsonPath("$.description").value("goat repo for integration test"))
                .andExpect(jsonPath("$.watchersCount").value(120));

        // 💾 Opcjonalnie – asercja, czy zapisano do DB
        List<GitRepoEntity> all = repository.findAll()
                .stream()
                .filter(repo -> "/testname/myrepo".equals(repo.getFullName()))
                .collect(Collectors.toList());

        assertEquals(1, all.size());
    }


    @Test
    void shouldRetry() throws Exception {
        wireMockServer.stubFor(get("/repos/testname/myrepo").willReturn(serverError()));

        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/repos/{owner}/{repo}", "testname", "myrepo")
                    .accept(MediaType.APPLICATION_JSON));
        } catch (Exception ignored) {}

        verify(moreThan(1), getRequestedFor(urlEqualTo("/repos/testname/myrepo")));
    }

}
